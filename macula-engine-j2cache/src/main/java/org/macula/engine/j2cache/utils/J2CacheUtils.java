package org.macula.engine.j2cache.utils;

import java.util.Collection;
import java.util.Collections;

import org.springframework.cache.CacheManager;

/**
 * J2Cache Utility Class
 */
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
		cacheManager.getCache(cacheName).put(key, value);
	}

	/**
	 * 从缓存中移除
	 *
	 * @param cacheName
	 * @param key
	 */
	public static void evict(String cacheName, String key) {
		cacheManager.getCache(cacheName).evict(key);
	}

	/**
	 * 获取缓存的所有key
	 *
	 * @param cacheName
	 * @return
	 */
	public static Collection<String> keys(String cacheName) {
		return Collections.emptyList();
	}

	/**
	 * Clear the cache
	 *
	 * @param cacheName: Cache region name
	 */
	public static void clear(String cacheName) {
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
		return cacheManager.getCache(region).get(key) != null;
	}
}