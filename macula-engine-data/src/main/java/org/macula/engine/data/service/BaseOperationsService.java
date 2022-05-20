package org.macula.engine.data.service;

import java.io.Serializable;

import org.macula.engine.assistant.constants.SymbolConstants;
import org.macula.engine.commons.domain.Entity;

/**
 * <p> 基于自研Hibernate多层二级缓存的基础服务 </p>
 */
public abstract class BaseOperationsService<E extends Entity, ID extends Serializable>
		implements ReadableService<E, ID>, WriteableService<E, ID> {

	protected String like(String property) {
		return SymbolConstants.PERCENT + property + SymbolConstants.PERCENT;
	}
}
