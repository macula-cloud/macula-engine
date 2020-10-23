package org.macula.cloud.data.handle.impl;

import org.macula.cloud.core.principal.SubjectPrincipal;
import org.macula.cloud.data.builder.SimpleQueryBuilder;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * <p> <b>QueryParserDataHandler</b> 是按照Macula解析SQL的实现. </p>
 * 
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class QueryParserDataHandler extends StringDataHandler {

	public static final String NAME = "QueryParserDataHandler";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String handleInternal(Object data, SubjectPrincipal userContext) {
		SimpleQueryBuilder builder = new SimpleQueryBuilder(data.toString(), userContext);
		getOutputParameters().putAll(builder.getParams());
		return builder.getQuery();
	}

}
