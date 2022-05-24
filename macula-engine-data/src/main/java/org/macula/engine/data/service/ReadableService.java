package org.macula.engine.data.service;

import java.io.Serializable;
import java.util.List;

import org.macula.engine.assistant.domain.Entity;
import org.macula.engine.data.repository.BaseRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p> 只读Service，可以提供基于视图实体的操作 </p>
 */
public interface ReadableService<E extends Entity, ID extends Serializable> {

	/**
	 * 获取Repository
	 *
	 * @return {@link BaseRepository}
	 */
	BaseRepository<E, ID> getRepository();

	/**
	 * 根据ID查询数据
	 *
	 * @param id 数据ID
	 * @return 与ID对应的数据，如果不存在则返回空
	 */
	default E findById(ID id) {
		return getRepository().findById(id).orElse(null);
	}

	/**
	 * 数据是否存在
	 *
	 * @param id 数据ID
	 * @return true 存在，false 不存在
	 */
	default boolean existsById(ID id) {
		return getRepository().existsById(id);
	}

	/**
	 * 查询数量
	 *
	 * @return 数据数量
	 */
	default long count() {
		return getRepository().count();
	}

	/**
	 * 查询数量
	 *
	 * @param specification {@link Specification}
	 * @return 数据数量
	 */
	default long count(Specification<E> specification) {
		return getRepository().count(specification);
	}

	/**
	 * 查询全部数据
	 *
	 * @return 全部数据列表
	 */
	default List<E> findAll() {
		return getRepository().findAll();
	}

	/**
	 * 查询全部数据
	 *
	 * @param sort {@link Sort}
	 * @return 已排序的全部数据列表
	 */
	default List<E> findAll(Sort sort) {
		return getRepository().findAll(sort);
	}

	/**
	 * 查询全部数据
	 *
	 * @param specification {@link Specification}
	 * @return 全部数据列表
	 */
	default List<E> findAll(Specification<E> specification) {
		return getRepository().findAll(specification);
	}

	/**
	 * 查询全部数据
	 *
	 * @param specification {@link Specification}
	 * @param sort          {@link Sort}
	 * @return 全部数据列表
	 */
	default List<E> findAll(Specification<E> specification, Sort sort) {
		return getRepository().findAll(specification, sort);
	}

	/**
	 * 查询分页数据
	 *
	 * @param pageable {@link Pageable}
	 * @return 分页数据
	 */
	default Page<E> findByPage(Pageable pageable) {
		return getRepository().findAll(pageable);
	}

	/**
	 * 查询分页数据
	 *
	 * @param pageNumber 当前页码, 起始页码 0
	 * @param pageSize   每页显示的数据条数
	 * @return 分页数据
	 */
	default Page<E> findByPage(int pageNumber, int pageSize) {
		return findByPage(PageRequest.of(pageNumber, pageSize));
	}

	/**
	 * 查询分页数据
	 *
	 * @param specification {@link Specification}
	 * @param pageable      {@link Pageable}
	 * @return 分页数据
	 */
	default Page<E> findByPage(Specification<E> specification, Pageable pageable) {
		return getRepository().findAll(specification, pageable);
	}

	/**
	 * 查询分页数据
	 *
	 * @param specification {@link Specification}
	 * @param pageNumber    当前页码, 起始页码 0
	 * @param pageSize      每页显示的数据条数
	 * @return 分页数据
	 */
	default Page<E> findByPage(Specification<E> specification, int pageNumber, int pageSize) {
		return getRepository().findAll(specification, PageRequest.of(pageNumber, pageSize));
	}

	/**
	 * 查询分页数据
	 *
	 * @param pageNumber 当前页码, 起始页码 0
	 * @param pageSize   每页显示的数据条数
	 * @param direction  {@link org.springframework.data.domain.Sort.Direction}
	 * @return 分页数据
	 */
	default Page<E> findByPage(int pageNumber, int pageSize, Sort.Direction direction) {
		return findByPage(PageRequest.of(pageNumber, pageSize, direction));
	}

	/**
	 * 查询分页数据
	 *
	 * @param pageNumber 当前页码, 起始页码 0
	 * @param pageSize   每页显示的数据条数
	 * @param direction  {@link org.springframework.data.domain.Sort.Direction}
	 * @param properties 排序的属性名称
	 * @return 分页数据
	 */
	default Page<E> findByPage(int pageNumber, int pageSize, Sort.Direction direction, String... properties) {
		return findByPage(PageRequest.of(pageNumber, pageSize, direction, properties));
	}
}
