package org.macula.cloud.sdk.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Query;

import org.macula.cloud.sdk.utils.StringUtils;

public class CommonQueryUtils {

	public static final int ORACLE_IN_MAX = 900;

	public static <T> Map<String, Collection<T>> createQuerySegement(String column, Collection<T> list,
			StringBuilder sql) {
		return createQuerySegement(column, TokenCreator.SIMLPLE, list, sql);
	}

	public static <T> Map<String, Collection<T>> createQuerySegement(String column, TokenCreator tokenCreator,
			Collection<T> list, StringBuilder sql) {

		if (list.size() <= ORACLE_IN_MAX) {
			String token = tokenCreator.create(column);
			sql.append(column + " in (:" + token + ") ");
			return Collections.singletonMap(token, list);
		}

		List<T> pValue = new ArrayList<T>(list);
		int size = (int) Math.ceil((double) list.size() / (double) ORACLE_IN_MAX);
		int length = list.size();
		Map<String, Collection<T>> segement = new HashMap<String, Collection<T>>(size);
		String[] sqlParts = new String[size];
		for (int i = 0; i < size; i++) {
			String token = tokenCreator.create(column);
			sqlParts[i] = column + " in (:" + token + ")";
			segement.put(token, pValue.subList(ORACLE_IN_MAX * i, Math.min(ORACLE_IN_MAX * (i + 1), length)));
		}
		sql.append("(" + StringUtils.join(sqlParts, " or ") + ")");
		return segement;
	}

	public static <T> void setParameters(Query query, Map<String, Collection<T>> segements) {
		for (Entry<String, Collection<T>> entry : segements.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}
}
