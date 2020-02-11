package org.macula.cloud.security.authentication;

import org.macula.cloud.sdk.configure.SDKConfigurationProperties;
import org.macula.cloud.sdk.configure.model.SDKSecurityProperties;
import org.macula.cloud.sdk.utils.J2CacheUtils;
import org.springframework.stereotype.Service;

@Service
public class CaptchaValidationPolicy {

	private static final String CAPTCHA_USER_PREFIX = "~login_attempt_times~";

	private SDKSecurityProperties properties;

	public CaptchaValidationPolicy(SDKConfigurationProperties configuration) {
		properties = configuration.getSecurity();
	}

	public boolean isNeedCaptchaValidate(String username) {
		boolean needCaptcha = properties.isCaptcha();
		if (needCaptcha) {
			Long times = J2CacheUtils.get(CAPTCHA_USER_PREFIX, username);
			needCaptcha = times != null && times >= properties.getCaptchaTimes();
		}
		return needCaptcha;
	}

	public void increaseCaptchaMarks(String username) {
		boolean needCaptcha = properties.isCaptcha();
		if (needCaptcha) {
			Long times = J2CacheUtils.get(CAPTCHA_USER_PREFIX, username);
			if (times == null) {
				times = 0L;
			}
			times++;
			J2CacheUtils.set(CAPTCHA_USER_PREFIX, username, times);
		}
	}

	public void clearCaptchaMarks(String username) {
		J2CacheUtils.evict(CAPTCHA_USER_PREFIX, username);
	}
}
