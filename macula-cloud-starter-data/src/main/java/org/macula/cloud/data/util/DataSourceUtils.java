package org.macula.cloud.data.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;

import org.macula.cloud.core.utils.StringUtils;
import org.macula.cloud.data.dialect.HSQLDialect;
import org.macula.cloud.data.dialect.JdbcDialect;
import org.macula.cloud.data.dialect.MySQLDialect;
import org.macula.cloud.data.dialect.OracleDialect;
import org.macula.cloud.data.dialect.SQLServerDialect;
import org.macula.cloud.data.domain.DataSourceFactory;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * <p> <b>DataSourceUtils</b> 是数据源助手. </p>
 * 
 */
public final class DataSourceUtils {

	private static ConcurrentMap<String, Object> cache = new ConcurrentHashMap<String, Object>();
	private static ConcurrentMap<DataSource, JdbcDialect> dialects = new ConcurrentHashMap<DataSource, JdbcDialect>();

	private DataSourceUtils() {
		//nothing
	}

	/** 将macula定义的DataSource转为标准的J2EE下的DataSource */
	@SuppressWarnings("unchecked")
	public static <T> T get(org.macula.cloud.data.domain.DataSource ds) {
		String code = ds.getCode();
		Object dataSource = cache.get(code);
		if (dataSource == null) {
			dataSource = DataSourceFactory.createTargetDataSource(ds);
			if (dataSource != null) {
				cache.put(code, dataSource);
			}
		}
		return (T) dataSource;
	}

	/**
	 * 清除数据源缓存
	 */
	public static void remove(org.macula.cloud.data.domain.DataSource ds) {
		if (ds != null) {
			remove(ds.getCode());
		}
	}

	/**
	 * 清除数据源缓存
	 */
	public static void remove(String dsCode) {
		if (!StringUtils.isEmpty(dsCode)) {
			Object dc = cache.remove(dsCode);
			if (dc != null) {
				dialects.remove(dc);
				if (dc instanceof DruidDataSource) {
					((DruidDataSource) dc).close();
				}
			}
		}
	}

	/** 获取指定DataSource的方言信息 */
	public static JdbcDialect getDialect(org.macula.cloud.data.domain.DataSource ds) {
		Object dataSource = get(ds);
		if (dataSource instanceof DataSource) {
			return getDialect((DataSource) dataSource);
		}
		return null;
	}

	/** 获取指定DataSource的方言信息 */
	public static JdbcDialect getDialect(DataSource dataSource) {
		JdbcDialect dialect = dialects.get(dataSource);
		if (dialect == null) {
			Connection conn = null;
			try {
				conn = dataSource.getConnection();
				JdbcDialect[] exists = new JdbcDialect[] { new OracleDialect(), new SQLServerDialect(), new MySQLDialect(), new HSQLDialect() };
				String dbName = conn.getMetaData().getDatabaseProductName();
				for (int i = 0; i < exists.length; i++) {
					if (exists[i].match(dbName)) {
						dialect = exists[i];
						break;
					}
				}
			} catch (SQLException e) {
				// ignore
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						// ignore
					}
				}
			}
			dialects.put(dataSource, dialect);
		}
		return dialect;
	}
}
