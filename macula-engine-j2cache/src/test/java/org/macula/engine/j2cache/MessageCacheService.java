package org.macula.engine.j2cache;

import java.util.Collections;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * J2Cache Massage Test Service
 */
@Component
@Slf4j
public class MessageCacheService {

	@Cacheable("j2cache")
	public Map<String, String> getMessage(String in) {
		log.info("[Macula] |- J2CACHE - GetMessage [{}]", in);
		return Collections.singletonMap("j2cache", in);
	}

}
