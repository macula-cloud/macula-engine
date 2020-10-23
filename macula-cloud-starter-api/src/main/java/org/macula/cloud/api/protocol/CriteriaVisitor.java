package org.macula.cloud.api.protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.macula.cloud.api.utils.TokenCreator;

/**
 * <p> <b>CriteriaVisitor</b> 是参数收集器. </p>
 */
public class CriteriaVisitor {

	private final List<String> queryParts = new ArrayList<String>();
	private final Map<String, Object> queryParams = new HashMap<String, Object>();
	private final TokenCreator tokenCreator = TokenCreator.SIMLPLE;

	public void addQueryPart(String part) {
		queryParts.add("(" + part + ")");
	}

	public void addQueryParam(String paramName, Object paramValue) {
		queryParams.put(paramName, paramValue);
	}

	public List<String> getQueryParts() {
		return this.queryParts;
	}

	public Map<String, Object> getQueryParams() {
		return this.queryParams;
	}

	public TokenCreator getTokenCreator() {
		return this.tokenCreator;
	}
}
