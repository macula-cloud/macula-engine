package org.macula.cloud.core.principal;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginCredential implements Serializable {

	private static final long serialVersionUID = 1L;

	// 客户端号
	private String clientId;

	// 账号密码登录
	private String username;
	private String password;

	// 手机验证码登录
	private String mobile;
	private String sms;

	// 验证码
	private String captcha;
	private String captchaCode;

	// 来源
	private String source;

	// 重定向地址
	private String redirectToUrl;

	// 签名
	private String sign;

	// sessionId
	private String sessionId;

	private String remoteAddress;
}
