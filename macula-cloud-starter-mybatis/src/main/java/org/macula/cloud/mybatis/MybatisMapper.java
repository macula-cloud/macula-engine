package org.macula.cloud.mybatis;

import java.util.List;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface MybatisMapper<T> extends Mapper<T>, MySqlMapper<T> {

	void insertBatch(List<T> map);

	void deleteBatch(List<T> map);

	void updateBatch(List<T> map);

}
