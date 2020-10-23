package org.macula.cloud.data.service;

import org.macula.cloud.data.domain.DataSet;

/**
 * <p>
 * <b>DataSetService</b> 数据集Service
 * </p>
 *
 */
public interface DataSetService {
	/**
	 * 根据代码查询DataSet
	 * @param dataSetCode
	 * @return DataSet
	 */
	public DataSet findByCode(String dataSetCode);
}
