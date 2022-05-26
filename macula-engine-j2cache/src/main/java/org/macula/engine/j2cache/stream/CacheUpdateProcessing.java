package org.macula.engine.j2cache.stream;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.connection.stream.StringRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.util.StringUtils;

/**
 * Synchronized Local Cache (L1Cache) by Redis Topic & Message
 */
@Slf4j
@AllArgsConstructor
public class CacheUpdateProcessing implements StreamListener<String, MapRecord<String, String, String>> {

	private final StringRedisTemplate messageRedisTemplate;
	private final String broadTopic;
	private final CacheManager cacheManager;

	public void sendBroadcast(String cacheName, String cacheKey) {
		StringRecord message = StreamRecords.string(Collections.singletonMap(cacheName, cacheKey)).withStreamKey(broadTopic);
		RecordId result = messageRedisTemplate.opsForStream().add(message);
		Long sequence = result.getSequence();
		Long timestamp = result.getTimestamp();
		log.trace("[Macula] |- J2CACHE - Send cache evict: key [{}], cacheName [{}], sequence [{}], timestamp [{}]", cacheKey, cacheName, sequence,
				timestamp);
	}

	@Override
	public void onMessage(MapRecord<String, String, String> message) {
		Map<String, String> events = message.getValue();

		for (Entry<String, String> event : events.entrySet()) {
			String cacheName = event.getKey();
			String cacheKey = event.getValue();
			log.trace("[Macula] |- J2CACHE - Received update message to evict key [{}] from caffine  cache [{}]", cacheKey, cacheName);

			Cache cache = null;

			if (StringUtils.hasText(cacheName) && (cache = cacheManager.getCache(cacheName)) != null) {
				if (cacheKey == null && cache.getNativeCache() instanceof com.github.benmanes.caffeine.cache.Cache) {
					((com.github.benmanes.caffeine.cache.Cache<?, ?>) cache.getNativeCache()).invalidateAll();
				} else {
					cache.evict(cacheKey);
				}
			}
		}
	}

}
