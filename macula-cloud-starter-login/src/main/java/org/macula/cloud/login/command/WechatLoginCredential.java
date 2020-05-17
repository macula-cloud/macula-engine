package org.macula.cloud.login.command;

import org.macula.cloud.core.principal.LoginCredential;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WechatLoginCredential extends LoginCredential {

	private static final long serialVersionUID = 1L;

	private String code;
	private String encryptedData;
	private String iv;

	public String getBindUsername() {
		String bindName = this.getUsername();
		if (bindName == null) {
			bindName = this.getMobile();
		}
		return bindName;
	}
}
