package org.macula.engine.assistant.query;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;

/**
 * <p> <b>TokenCreator</b> 是创建上下文中唯一的token占位符. </p>
 */
public interface TokenCreator {

	String create(String prefix);

	TokenCreator SIMLPLE = new TokenCreator() {

		private final AtomicInteger base = new AtomicInteger(0);

		@Override
		public String create(String prefix) {
			return StringUtils.replaceChars(prefix, '.', '_') + base.addAndGet(1);
		}

	};
}
