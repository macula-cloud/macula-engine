package org.macula.cloud.cache.multi;

import org.springframework.cache.Cache;

public abstract class MultiCache implements Cache {

	private final String name;

	public MultiCache(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public final Object getNativeCache() {
		return this;
	}

}
