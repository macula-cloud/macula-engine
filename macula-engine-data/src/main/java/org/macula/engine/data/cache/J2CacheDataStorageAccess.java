package org.macula.engine.data.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.cache.spi.support.DomainDataStorageAccess;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import org.springframework.cache.Cache;

/**
 * <p>自定义Hibernate二级缓存DomainDataStorageAccess </p>
 *
 */
@Slf4j
public class J2CacheDataStorageAccess implements DomainDataStorageAccess {

	private final Cache cache;

	public J2CacheDataStorageAccess(Cache cache) {
		this.cache = cache;
	}

	private Object get(Object key) {
		Cache.ValueWrapper value = cache.get(key);

		if (ObjectUtils.isNotEmpty(value)) {
			return value.get();
		}
		return null;
	}

	@Override
	public boolean contains(Object key) {
		Object value = this.get(key);
		log.trace("[Macula] |- CACHE - SPI check is key : [{}] exist.", key);
		return ObjectUtils.isNotEmpty(value);
	}

	@Override
	public Object getFromCache(Object key, SharedSessionContractImplementor session) {
		Object value = this.get(key);
		log.trace("[Macula] |- CACHE - SPI get from cache key is : [{}], value is : [{}]", key, value);
		return value;
	}

	@Override
	public void putIntoCache(Object key, Object value, SharedSessionContractImplementor session) {
		log.trace("[Macula] |- CACHE - SPI put into cache key is : [{}], value is : [{}]", key, value);
		cache.put(key, value);
	}

	@Override
	public void removeFromCache(Object key, SharedSessionContractImplementor session) {
		log.trace("[Macula] |- CACHE - SPI remove from cache key is : [{}]", key);
		cache.evict(key);
	}

	@Override
	public void evictData(Object key) {
		log.trace("[Macula] |- CACHE - SPI evict key : [{}] from cache.", key);
		cache.evict(key);
	}

	@Override
	public void clearCache(SharedSessionContractImplementor session) {
		this.evictData();
	}

	@Override
	public void evictData() {
		log.trace("[Macula] |- CACHE - SPI clear all cache data.");
		cache.clear();
	}

	@Override
	public void release() {
		log.trace("[Macula] |- CACHE - SPI cache release.");
		cache.invalidate();
	}
}
