package org.macula.engine.web.controller;

import java.io.Serializable;

import org.macula.engine.assistant.protocol.Result;
import org.macula.engine.commons.domain.Entity;
import org.macula.engine.data.service.WriteableService;

/**
 * <p> Writeable Controller </p>
 */
public interface WriteableController<E extends Entity, ID extends Serializable> extends ReadableController<E, ID> {

	/**
	 * 获取Service
	 *
	 * @return Service
	 */
	WriteableService<E, ID> getWriteableService();

	/**
	 * 保存或更新实体
	 * @param domain 实体参数
	 * @return 用Result包装的实体
	 */

	default Result<E> saveOrUpdate(E domain) {
		E savedDomain = getWriteableService().saveOrUpdate(domain);
		return result(savedDomain);
	}

	/**
	 * 删除数据
	 * @param id 实体ID
	 * @return 用Result包装的信息
	 */
	default Result<String> delete(ID id) {
		Result<String> result = result(String.valueOf(id));
		getWriteableService().deleteById(id);
		return result;
	}
}
