package org.macula.cloud.data.service;

import org.macula.cloud.data.domain.DataSource;

/**
 * <p>
 * <b>DataSourceService</b> 数据源Service
 * </p>
 */
public interface DataSourceService {

	/**
	 * 根据代码获取数据源配置
	 * @param dataSourceCode 数据源代码
	 * @return DataSource
	 */
	DataSource findByCode(String dataSourceCode);
}
