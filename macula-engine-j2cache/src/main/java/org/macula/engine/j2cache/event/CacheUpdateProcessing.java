package org.macula.engine.j2cache.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.macula.engine.assistant.support.ApplicationId;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

/**
 * Synchronized Local Cache (L1Cache) by Redis Topic & Message
 */
@Slf4j
@AllArgsConstructor
public class CacheUpdateProcessing implements MessageListener {

	private final RedisTemplate<Object, Object> messageRedisTemplate;
	private final String broadTopic;
	private final CacheManager cacheManager;

	public void sendBroadcast(String cacheName, String cacheKey) {
		messageRedisTemplate.convertAndSend(broadTopic, new CacheUpdateEvent(cacheName, cacheKey));
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		Object body = messageRedisTemplate.getValueSerializer().deserialize(message.getBody());
		CacheUpdateEvent event = (CacheUpdateEvent) body;

		if (event == null
			|| !event.getApplicationId().sameGroup(ApplicationId.current())
			|| event.getApplicationId().sameInstance(ApplicationId.current())) {
			log.trace("[Macula] |- J2CACHE - Received Redis message, but shouldn't process clear logic.");
			return;
		}

		String cacheName = event.getCacheName();
		String cacheKey = event.getCacheKey();

		if (!StringUtils.hasText(cacheName)) {
			return;
		}

		Cache cache = cacheManager.getCache(cacheName);

		if (cache == null) {
			return;
		}

		log.trace("[Macula] |- J2CACHE - Received update message to evict key [{}] from caffine  cache [{}]", cacheKey,
				cacheName);
		if (cacheKey == null && cache.getNativeCache() instanceof com.github.benmanes.caffeine.cache.Cache) {
			((com.github.benmanes.caffeine.cache.Cache<?, ?>) cache.getNativeCache()).invalidateAll();
		} else {
			cache.evict(cacheKey);
		}
	}

}
