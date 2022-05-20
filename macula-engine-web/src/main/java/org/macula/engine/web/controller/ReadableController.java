package org.macula.engine.web.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.macula.engine.assistant.protocol.Result;
import org.macula.engine.commons.domain.Entity;
import org.macula.engine.data.service.ReadableService;

import org.springframework.data.domain.Page;

/**
 * <p> Read Only Controller </p>
 */
public interface ReadableController<E extends Entity, ID extends Serializable> extends Controller {

	/**
	 * 获取Service
	 *
	 * @return Service
	 */
	ReadableService<E, ID> getReadableService();

	/**
	 * 查询分页数据
	 * @param pageNumber 当前页码，起始页码 0
	 * @param pageSize 每页显示数据条数
	 * @return {@link Result}
	 */
	default Result<Map<String, Object>> findByPage(Integer pageNumber, Integer pageSize) {
		Page<E> pages = getReadableService().findByPage(pageNumber, pageSize);
		return result(pages);
	}

	default Result<List<E>> findAll() {
		List<E> domains = getReadableService().findAll();
		return result(domains);
	}

	default Result<E> findById(ID id) {
		E domain = getReadableService().findById(id);
		return result(domain);
	}
}
