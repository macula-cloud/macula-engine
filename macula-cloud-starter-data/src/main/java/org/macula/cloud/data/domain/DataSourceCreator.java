package org.macula.cloud.data.domain;

/**
 * <p> <b>DataSourceCreator</b> 是创建DataSource的接口. </p>
 */
public interface DataSourceCreator<T> {

	T createTargetDataSource(DataSource ds);
}
