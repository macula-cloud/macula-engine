package org.macula.cloud.core.query;

import java.util.concurrent.atomic.AtomicInteger;

import org.macula.cloud.core.utils.StringUtils;

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
