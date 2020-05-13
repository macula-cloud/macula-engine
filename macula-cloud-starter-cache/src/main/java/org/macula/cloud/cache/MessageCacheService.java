package org.macula.cloud.cache;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class MessageCacheService {

	private AtomicInteger times = new AtomicInteger(0);

	@Cacheable(value = "users", key = "#username")
	public String getMessage(String username) {
		return String.format("Hello %d %s", times.incrementAndGet(), username);
	}
}
