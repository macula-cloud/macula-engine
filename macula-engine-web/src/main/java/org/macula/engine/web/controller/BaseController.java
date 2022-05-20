package org.macula.engine.web.controller;

import java.io.Serializable;

import org.macula.engine.assistant.protocol.Result;
import org.macula.engine.commons.domain.Entity;
import org.macula.engine.data.service.ReadableService;

/**
 * <p> Common Controller </p>
 * <p>
 * 单独提取出一些公共方法，是为了解决某些支持feign的controller，requestMapping 不方便统一编写的问题。
 */
public abstract class BaseController<E extends Entity, ID extends Serializable> implements WriteableController<E, ID> {

	/**
	 * 获取Service
	 *
	 * @return Service
	 */
	@Override
	public ReadableService<E, ID> getReadableService() {
		return this.getReadableService();
	}

	@Override
	public Result<E> saveOrUpdate(E domain) {
		E savedDomain = getWriteableService().saveOrUpdate(domain);
		return result(savedDomain);
	}

	@Override
	public Result<String> delete(ID id) {
		Result<String> result = result(String.valueOf(id));
		getWriteableService().deleteById(id);
		return result;
	}
}
