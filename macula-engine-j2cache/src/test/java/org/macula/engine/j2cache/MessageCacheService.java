package org.macula.engine.j2cache;

import lombok.extern.slf4j.Slf4j;
import org.macula.engine.j2cache.event.CacheUpdateEvent;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * J2Cache Massage Test Service
 */
@Component
@Slf4j
public class MessageCacheService {

	@Cacheable("j2cache")
	public CacheUpdateEvent getMessage(String in) {
		log.info("[Macula] |- J2CACHE - GetMessage [{}]", in);
		return new CacheUpdateEvent("j2cache", in);
	}

}
