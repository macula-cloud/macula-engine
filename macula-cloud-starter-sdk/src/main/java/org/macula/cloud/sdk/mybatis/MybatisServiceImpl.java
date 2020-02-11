package org.macula.cloud.sdk.mybatis;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import org.macula.cloud.sdk.constant.GlobalConstant;
import org.macula.cloud.sdk.exception.MaculaCloudException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.github.pagehelper.page.PageMethod;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

public class MybatisServiceImpl<T extends MybatisPojo> implements MybatisService<T> {

	// 用户是否来源隔离
	@Value("${macula.user.different_source_actionable:true}")
	private boolean userDifferentSourceActionable;

	@Autowired
	private Mapper<T> mapper;

	private Class<T> clazz;

	@Autowired
	private MybatisMapper<T> adminBaseMapper;

	@Autowired

	@SuppressWarnings("unchecked")
	public MybatisServiceImpl() {
		// 通过子类创建对象的时候,得到父类的泛型类T
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		// 获得确切的泛型的类对象
		clazz = (Class<T>) type.getActualTypeArguments()[0];
	}

	public Class<T> getClazz() {
		return clazz;
	}

	@Override
	public T queryByPrimaryKey(Serializable id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<T> queryAll() {
		return mapper.selectAll();
	}

	@Override
	public List<T> queryByWhere(T t) {
		return mapper.select(t);
	}

	@Override
	public int queryCountByWhere(T t) {
		return mapper.selectCount(t);
	}

	@Override
	public List<T> queryByPageWithCondition(T t, Integer page, Integer rows) {
		PageMethod.startPage(page, rows);
		return mapper.select(t);
	}

	@Override
	public List<T> queryByPage(Integer page, Integer rows) {
		PageMethod.startPage(page, rows);
		return mapper.selectAll();
	}

	@Override
	public T saveSelective(T t) {
		if (t.getCreateTime() == null) {
			t.setCreateTime(new Date());
			t.setUpdateTime(t.getCreateTime());
		}
		else if (t.getUpdateTime() == null) {
			t.setUpdateTime(new Date());
		}
		mapper.insertSelective(t);
		return t;
	}

	@Override
	public T updateSelective(T t) {
		if (t.getUpdateTime() == null) {
			t.setUpdateTime(new Date());
		}
		mapper.updateByPrimaryKeySelective(t);
		return t;
	}

	@Override
	public void deleteById(Serializable id) {
		mapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteByIds(Serializable[] ids) {
		Example example = new Example(clazz);
		example.createCriteria().andIn("id", Arrays.asList(ids));
		mapper.deleteByExample(example);
	}

	@Override
	public List<T> queryByIds(Serializable... ids) {
		Example example = new Example(clazz);
		example.createCriteria().andIn("id", Arrays.asList(ids));
		return mapper.selectByExample(example);
	}

	@Override
	public int queryCountByExample(Example example) {
		return mapper.selectCountByExample(example);
	}

	@Override
	public PageBean<T> queryByPage4PageBean(T t, Integer page, Integer pageSize) {
		int totalCount = this.queryCountByWhere(t);
		Integer offset = (page - 1) * pageSize;
		PageMethod.offsetPage(offset, pageSize);
		List<T> list = this.queryByWhere(t);
		return new PageBean<T>(pageSize, totalCount, page, list);
	}

	@Override
	public void deleteLogiclyByIds(T t, Serializable[] ids) {
		Example example = new Example(getClazz());
		example.createCriteria().andIn("id", Arrays.asList(ids));
		adminBaseMapper.updateByExampleSelective(t, example);
	}

	/**
	 * 校验用户操作是否合法 非用户中心新增数据不可操作
	 *
	 * @param filter
	 * @param ids
	 */
	@Override
	public void validateOperation(Predicate<T> filter, Serializable... ids) {
		if (!userDifferentSourceActionable) {
			List<T> list = queryByIds(ids);
			list.forEach(obj -> {
				if (!filter.test(obj)) {
					throw new MaculaCloudException("非用户中心新增的数据不可修改!");
				}
			});
		}
	}

	@Override
	public T validateDuplicate(T condition, String msg) {
		Long id = condition.getId();
		condition.setId(null);
		List<T> ts = queryByWhere(condition);
		// 区别新增和修改 修改的话最多找到1个 id还是自己
		if (ts.size() > 0 && !ts.get(0).getId().equals(id)) {
			throw new MaculaCloudException(msg);
		}
		return ts.size() == 0 ? null : ts.get(0);
	}

	@Override
	public void validateDuplicateWithTheSameSign(T condition, String msg) {
		Long id = condition.getId();
		condition.setId(null);
		List<T> list = queryByWhere(condition);
		// 有结果 遍历结果,如果id有属于自己的 说明是修改
		if (list.size() > 0) {
			boolean flag = list.stream().anyMatch(t -> t.getId().equals(id));
			if (!flag) {
				throw new MaculaCloudException(msg);
			}
		}
	}

	@Override
	public void validateElement(BaseElement element) {
		// 没有父的标记为根
		element.setParentId(element.getParentId() == null ? GlobalConstant.PARENT_ROOT : element.getParentId());
	}

	@Override
	public void validateElementOperation(Serializable... elementIds) {
		// 校验是否可删除
		Example functionExample = new Example(clazz);
		functionExample.createCriteria().andEqualTo("isDeletable", GlobalConstant.IS_NOT_DELETABLE).andIn("id",
				Arrays.asList(elementIds));
		if (this.queryCountByExample(functionExample) > 0) {
			throw new MaculaCloudException("系统管理项不可删除！");
		}

	}

}
