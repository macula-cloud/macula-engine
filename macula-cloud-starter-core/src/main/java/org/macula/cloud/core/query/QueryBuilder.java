package org.macula.cloud.core.query;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.macula.cloud.core.principal.SubjectPrincipal;

public class QueryBuilder {

	/**
	 * 翻译后的SQL
	 */
	private String query;

	/**
	 * 提取的参数
	 */
	private final Map<String, Object> extractParams;

	private final Map<TokenHolder, TokenHandler> tokenHandlers;

	/**
	 * 构造
	 */
	public QueryBuilder(String query, SubjectPrincipal userContext) {
		this.query = query;
		this.extractParams = new HashMap<String, Object>();

		tokenHandlers = new LinkedHashMap<TokenHolder, TokenHandler>();
		tokenHandlers.put(new TokenHolder("#[", "]#"), new VariableTokenHandler(userContext));
		tokenHandlers.put(new TokenHolder("#(", ")#"), new ParameterTokenHandler(userContext, extractParams));
		tokenHandlers.put(new TokenHolder("#P(", ")#"), new DataParamTokenHandler(extractParams));

		build();
	}

	public String getQuery() {
		return this.query;
	}

	public Map<String, Object> getParams() {
		return this.extractParams;
	}

	private void build() {
		for (Map.Entry<TokenHolder, TokenHandler> entry : tokenHandlers.entrySet()) {
			TokenHolder holder = entry.getKey();
			GenericTokenParser parse = new GenericTokenParser(holder.getOpenToken(), holder.getCloseToken(), entry.getValue());
			query = parse.parse(query);
		}
	}
}
