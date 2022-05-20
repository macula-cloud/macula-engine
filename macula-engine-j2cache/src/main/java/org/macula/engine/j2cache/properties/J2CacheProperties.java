package org.macula.engine.j2cache.properties;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import lombok.Data;
import lombok.ToString;
import org.macula.engine.j2cache.constants.J2CacheConstants;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>J2Cache Properties Settings </p>
 */
@ToString
@ConfigurationProperties(prefix = J2CacheConstants.PROPERTY_PREFIX_CACHE)
public class J2CacheProperties extends Expire {

	/**
	 * 分布式缓存Redis端是否进行数据脱敏， 默认值，true <p> Hibernate二级缓存中，会基于SQL进行数据缓存。这种缓存以SQL作为key，一方面这个Key会比较长，另一方面SQL明文存入Redis缺少安全性。
	 * 通过这个配置，可以设定是否对Hibernate二级缓存的SQL进行脱敏，脱敏后会将SQL转换为MD5值。当然这也会带来一定的性能损耗
	 */
	private Boolean desensitization = true;

	/**
	 * 退出时是否清理远端缓存，默认值，false <p> 服务退出时，会清理本地以及远端的缓存，为了在集群情况下避免因此导致的缓存击穿问题，默认退出时不清除远端缓存。
	 */
	private Boolean clearRemoteOnExit = false;

	/**
	 * 是否允许存储空值
	 */
	private Boolean allowNullValues = true;

	/**
	 * 缓存名称转换分割符。默认值，"-" <p> 默认缓存名称采用 Redis Key 格式（使用 ":" 分割），使用 ":" 分割的字符串作为Map的Key，":"会丢失。 指定一个分隔符，用于 ":" 分割符的转换
	 */
	private String separator = "-";

	/** Time to live for Redis entries */
	private Duration defaultTimeToLive = Duration.ofHours(24L);

	private Map<String, Duration> timeToLive = new HashMap<>();

	/**
	 * 是否开启熔断
	 */
	private boolean openCircuitBreaker = true;

	/** 熔断设置 */
	private CircuitBreakerProperties circuitBreaker = new CircuitBreakerProperties();

	/** Topic to use in order to synchronize eviction of entries */
	private String broadTopic = "macula:j2cache:topic";

	/**
	 * 针对不同实体单独设置的过期时间，如果不设置，则统一使用默认时间。
	 */
	private Map<String, Expire> expires = new HashMap<>();

	public Boolean getDesensitization() {
		return desensitization;
	}

	public void setDesensitization(Boolean desensitization) {
		this.desensitization = desensitization;
	}

	public Boolean getClearRemoteOnExit() {
		return clearRemoteOnExit;
	}

	public void setClearRemoteOnExit(Boolean clearRemoteOnExit) {
		this.clearRemoteOnExit = clearRemoteOnExit;
	}

	public Boolean getAllowNullValues() {
		return allowNullValues;
	}

	public void setAllowNullValues(Boolean allowNullValues) {
		this.allowNullValues = allowNullValues;
	}

	public Map<String, Expire> getExpires() {
		return expires;
	}

	public void setExpires(Map<String, Expire> expires) {
		this.expires = expires;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	/**
	 * @return the openCircuitBreaker
	 */
	public boolean isOpenCircuitBreaker() {
		return this.openCircuitBreaker;
	}

	/**
	 * @param openCircuitBreaker the openCircuitBreaker to set
	 */
	public void setOpenCircuitBreaker(boolean openCircuitBreaker) {
		this.openCircuitBreaker = openCircuitBreaker;
	}

	/**
	 * @return the circuitBreaker
	 */
	public CircuitBreakerProperties getCircuitBreaker() {
		return this.circuitBreaker;
	}

	/**
	 * @param circuitBreaker the circuitBreaker to set
	 */
	public void setCircuitBreaker(CircuitBreakerProperties circuitBreaker) {
		this.circuitBreaker = circuitBreaker;
	}

	/**
	 * @return the broadTopic
	 */
	public String getBroadTopic() {
		return this.broadTopic;
	}

	/**
	 * @param broadTopic the broadTopic to set
	 */
	public void setBroadTopic(String broadTopic) {
		this.broadTopic = broadTopic;
	}

	/**
	 * @return the defaultTimeToLive
	 */
	public Duration getDefaultTimeToLive() {
		return this.defaultTimeToLive;
	}

	/**
	 * @param defaultTimeToLive the defaultTimeToLive to set
	 */
	public void setDefaultTimeToLive(Duration defaultTimeToLive) {
		this.defaultTimeToLive = defaultTimeToLive;
	}

	/**
	 * @return the timeToLive
	 */
	public Map<String, Duration> getTimeToLive() {
		return this.timeToLive;
	}

	/**
	 * @param timeToLive the timeToLive to set
	 */
	public void setTimeToLive(Map<String, Duration> timeToLive) {
		this.timeToLive = timeToLive;
	}

	/**
	* Circuit breaker just records calls to Redis - it does not time out them.
	*
	* <p>To simplify defaults, we rely on 4 core properties:
	*
	* <ul>
	*   <li>{@code failureRateThreshold}
	*   <li>{@code slowCallRateThreshold}
	*   <li>{@code slowCallDurationThreshold}
	*   <li>{@code slidingWindowType}
	* </ul>
	*
	* To compute appropriate values for your properties - use your slow calls as a baseline and
	* consider sliding window type:
	*
	* <ul>
	*   <li>Assuming that call is considered to be slow after 250ms
	*   <li>Then in 1 second we should be able to process more than 4 calls
	*   <li>If sliding window is count based then {@code permittedNumberOfCallsInHalfOpenState}
	*       should be 4, {@code minimumNumberOfCalls} is 2 and {@code slidingWindowSize} is 8 (calls
	*       == 2 seconds)
	*   <li>If sliding window is time based then {@code permittedNumberOfCallsInHalfOpenState} should
	*       be 4, {@code minimumNumberOfCalls} is 2 and {@code slidingWindowSize} is 2 (seconds == 8
	*       seconds)
	* </ul>
	*/
	@Data
	public static class CircuitBreakerProperties {

		/** Percent of call failures to prohibit further calls to Redis */
		private int failureRateThreshold = 25;

		/** Percent of slow calls to prohibit further calls to Redis */
		private int slowCallRateThreshold = 25;

		/** Defines the duration after which Redis call considered to be slow */
		private Duration slowCallDurationThreshold = Duration.ofMillis(250);

		/** Sliding window type for connectivity analysis */
		private CircuitBreakerConfig.SlidingWindowType slidingWindowType = CircuitBreakerConfig.SlidingWindowType.COUNT_BASED;

		/** Amount of Redis calls to test if backend is responsive when circuit breaker closes */
		private int permittedNumberOfCallsInHalfOpenState = (int) (Duration.ofSeconds(5).toNanos()
				/ slowCallDurationThreshold.toNanos());

		/** Amount of time to wait before closing circuit breaker, 0 - wait for all permitted calls. */
		private Duration maxWaitDurationInHalfOpenState = slowCallDurationThreshold
				.multipliedBy(permittedNumberOfCallsInHalfOpenState);

		/** Sliding window size for Redis calls analysis (calls / seconds) */
		private int slidingWindowSize = permittedNumberOfCallsInHalfOpenState * 2;

		/** Minimum number of calls which are required before calculating error or slow call rate */
		private int minimumNumberOfCalls = permittedNumberOfCallsInHalfOpenState / 2;

		/** Time to wait before permitting Redis calls to test backend connectivity. */
		private Duration waitDurationInOpenState = slowCallDurationThreshold.multipliedBy(minimumNumberOfCalls);
	}

}
