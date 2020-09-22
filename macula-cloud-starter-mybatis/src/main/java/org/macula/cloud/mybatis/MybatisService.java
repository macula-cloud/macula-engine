package org.macula.cloud.mybatis;

import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;

import tk.mybatis.mapper.entity.Example;

public interface MybatisService<T extends MybatisPojo> {

	// 根据主键查询
	T queryByPrimaryKey(Serializable id);

	// 查询全部
	List<T> queryAll();

	// 根据条件查询
	List<T> queryByWhere(T t);

	// 根据条件查询总记录数
	int queryCountByWhere(T t);

	// 分页查询
	List<T> queryByPage(Integer page, Integer rows);

	// 根据条件分页查询
	List<T> queryByPageWithCondition(T t, Integer page, Integer rows);

	// 选择性新增
	T saveSelective(T t);

	// 选择性更新
	T updateSelective(T t);

	// 根据id删除
	void deleteById(Serializable id);

	// 批量物理删除
	void deleteByIds(Serializable[] ids);

	// 依据id批量查询
	List<T> queryByIds(Serializable... ids);

	// 依据条件查数量
	int queryCountByExample(Example example);

	PageBean<T> queryByPage4PageBean(T t, Integer page, Integer pageSize);

	// 逻辑删除
	void deleteLogiclyByIds(T t, Serializable[] ids);

	// 校验用户操作是否合法,不能操作非用户中心新增的数据 比如同步过来的数据
	void validateOperation(Predicate<T> filter, Serializable... ids);

	// 校验重复
	T validateDuplicate(T condition, String msg);

	/*
	 * 校验数据在唯一标识相同情况下是否能操作 情况: 当同步过来的数据唯一标识与本地数据冲突 比如username 这时候 还是允许相同username的本地用户操作自己的数据的
	 */
	void validateDuplicateWithTheSameSign(T condition, String msg);

	// 菜单 功能项 校验是否继承出问题
	void validateElement(BaseElement element);

	// 菜单 功能项 是否可删除
	void validateElementOperation(Serializable... elementIds);

}
