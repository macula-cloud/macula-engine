package org.macula.engine.data.p6spy;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>P6Spy自定义格式化</p>
 */
public class P6SpyMessageFormatting implements MessageFormattingStrategy {

	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

	@Override
	public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared,
			String sql, String url) {

		/**
		 *
		 * StringBuilder 是为了避免字符串拼接过程中产生很多不必要的字符串对象。
		 * 经过编译器优化，多个字符串相‘+’，优化后，与StringBuilder等价
		 *
		 * 关注idea的“'StringBuilder builder' can be replaced with 'String'”提示
		 */
		// @formatter:off
		String builder = this.format.format(new Date()) + " | took " +
                elapsed +
                "ms | " +
                category +
                " | connection " +
                connectionId +
                " | url " +
                url +
                "\n------------------------| " +
                sql +
                ";";
		return StringUtils.isNotEmpty(sql.trim()) ? String.valueOf(builder) : "";
	}
	// @formatter:on
}
