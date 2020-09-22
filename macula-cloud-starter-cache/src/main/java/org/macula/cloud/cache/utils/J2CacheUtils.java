package org.macula.cloud.cache.utils;

import java.util.Collection;
import java.util.Collections;

import org.springframework.cache.CacheManager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class J2CacheUtils {

	/** J2Cache存放Event的区间 */
	public final static String EVENTS_REGION = "events";

	/** J2Cache存放Principal的区间 */
	public final static String PRINCIPALS_REGION = "principals";

	/** J2Cache存放Session的区间 */
	public final static String SESSIONS_REGION = "sessions";

	/** J2Cache存放Captcha的区间 */
	public final static String CAPTCHA_REGION = "captcha";

	/** J2Cache存放的用户属性 */
	public final static String PRINCIPAL_PROPERTY = "user_attrs";

	/** J2Cache存放Cache的区间 */
	public final static String CACHE_REGION = "cache";

	private static CacheManager cacheManager;

	private static final Cache<String, Object> guvaCache = CacheBuilder.newBuilder().build();

	public static void setCacheManager(CacheManager theCacheChannel) {
		cacheManager = theCacheChannel;
	}

	public static CacheManager getCacheManager() {
		return cacheManager;
	}

	/**
	 * 获取缓存
	 *
	 * @param cacheName
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(String cacheName, String key) {
		if (cacheManager == null) {
			return (T) guvaCache.getIfPresent(getGuavaKey(cacheName, key));
		}
		return (T) cacheManager.getCache(cacheName).get(key).get();
	}

	/**
	 * 写入缓存
	 *
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public static void set(String cacheName, String key, Object value) {
		if (cacheManager == null) {
			guvaCache.put(getGuavaKey(cacheName, key), value);
			return;
		}
		cacheManager.getCache(cacheName).put(key, value);
	}

	/**
	 * 从缓存中移除
	 *
	 * @param cacheName
	 * @param key
	 */
	public static void evict(String cacheName, String key) {
		if (cacheManager == null) {
			guvaCache.invalidate(getGuavaKey(cacheName, key));
			return;
		}
		cacheManager.getCache(cacheName).evict(key);
	}

	/**
	 * 获取缓存的所有key
	 *
	 * @param cacheName
	 * @return
	 */
	public static Collection<String> keys(String cacheName) {
		if (cacheManager == null) {
			return guvaCache.asMap().keySet();
		}
		return Collections.emptyList();
	}

	/**
	 * Clear the cache
	 *
	 * @param cacheName: Cache region name
	 */
	public static void clear(String cacheName) {
		if (cacheManager == null) {
			guvaCache.cleanUp();
			return;
		}
		cacheManager.getCache(cacheName).invalidate();
	}

	/**
	 * 判断某个缓存键是否存在
	 *
	 * @param region Cache region name
	 * @param key    cache key
	 * @return true if key exists
	 */
	public static boolean exists(String region, String key) {
		if (cacheManager == null) {
			return guvaCache.getIfPresent(getGuavaKey(region, key)) != null;
		}
		return cacheManager.getCache(region).get(key) != null;
	}

	private static String getGuavaKey(String cacheName, String key) {
		return cacheName + "::" + key;
	}
}
