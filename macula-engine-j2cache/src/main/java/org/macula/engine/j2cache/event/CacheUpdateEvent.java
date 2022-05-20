package org.macula.engine.j2cache.event;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.macula.engine.assistant.support.ApplicationId;

/**
 * Small message used by this caching library in order to sync eviction of entries when necessary
 */
@NoArgsConstructor
@ToString
public class CacheUpdateEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	private ApplicationId applicationId;
	@Getter
	@Setter
	private String cacheName;
	@Getter
	@Setter
	private String cacheKey;

	public CacheUpdateEvent(String cacheName, String cacheKey) {
		this.cacheName = cacheName;
		this.cacheKey = cacheKey;
		this.applicationId = ApplicationId.current();
	}

}
