package org.macula.cloud.core.utils;

import java.util.Collection;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import net.oschina.j2cache.CacheChannel;

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

	private static CacheChannel cacheChannel;

	private static final Cache<String, Object> guvaCache = CacheBuilder.newBuilder().build();

	public static void setCacheChannel(CacheChannel theCacheChannel) {
		cacheChannel = theCacheChannel;
	}

	public static CacheChannel getCacheChannel() {
		return cacheChannel;
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
		if (cacheChannel == null) {
			return (T) guvaCache.getIfPresent(getGuavaKey(cacheName, key));
		}
		return (T) cacheChannel.get(cacheName, key).getValue();
	}

	/**
	 * 写入缓存
	 *
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public static void set(String cacheName, String key, Object value) {
		if (cacheChannel == null) {
			guvaCache.put(getGuavaKey(cacheName, key), value);
			return;
		}
		cacheChannel.set(cacheName, key, value);
	}

	public static void set(String cacheName, String key, Object value, long timeToLiveInSeconds) {
		if (cacheChannel == null) {
			guvaCache.put(getGuavaKey(cacheName, key), value);
			return;
		}
		cacheChannel.set(cacheName, key, value, timeToLiveInSeconds);
	}

	/**
	 * 从缓存中移除
	 *
	 * @param cacheName
	 * @param key
	 */
	public static void evict(String cacheName, String key) {
		if (cacheChannel == null) {
			guvaCache.invalidate(getGuavaKey(cacheName, key));
			return;
		}
		cacheChannel.evict(cacheName, key);
	}

	/**
	 * 获取缓存的所有key
	 *
	 * @param cacheName
	 * @return
	 */
	public static Collection<String> keys(String cacheName) {
		if (cacheChannel == null) {
			return guvaCache.asMap().keySet();
		}
		return cacheChannel.keys(cacheName);
	}

	/**
	 * Clear the cache
	 *
	 * @param cacheName: Cache region name
	 */
	public static void clear(String cacheName) {
		if (cacheChannel == null) {
			guvaCache.cleanUp();
			return;
		}
		cacheChannel.clear(cacheName);
	}

	/**
	 * 判断某个缓存键是否存在
	 *
	 * @param region Cache region name
	 * @param key    cache key
	 * @return true if key exists
	 */
	public static boolean exists(String region, String key) {
		if (cacheChannel == null) {
			return guvaCache.getIfPresent(getGuavaKey(region, key)) != null;
		}
		return check(region, key) > 0;
	}

	/**
	 * 判断某个key存在于哪级的缓存中
	 *
	 * @param region cache region
	 * @param key    cache key
	 * @return 0(不存在), 1(一级), 2(二级)
	 */
	public static int check(String region, String key) {
		if (cacheChannel == null) {
			return -1;
		}
		return cacheChannel.check(region, key);
	}

	private static String getGuavaKey(String cacheName, String key) {
		return cacheName + "::" + key;
	}
}
