package org.macula.cloud.core.query;

import java.util.Map;

public class DataParamTokenHandler implements TokenHandler {

	private final Map<String, Object> dataContext;

	public final static String DATA_PARAM_KEY_SUFFIX = "Label";
	private static String seprator = "\\$\\$";

	public DataParamTokenHandler(Map<String, Object> params) {
		this.dataContext = params;
	}

	@Override
	public String handleToken(String content) {
		String[] split = content.split(seprator);
		String column = split[0];
		if (split.length > 1) {
			// 将参数字段加上_label存入对应参数的CODE，给前面翻译这个参数用
			String label = (column + "_" + DATA_PARAM_KEY_SUFFIX).replace("\"", "").replace("'", "");
			dataContext.put(label.toUpperCase(), split[1]);
		}
		return column;
	}
}
