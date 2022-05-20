package org.macula.cloud.core.session;

import org.macula.cloud.core.encrypt.RSAConfiguration;
import org.macula.engine.assistant.support.ApplicationId;

public class SessionRSAConfiguration extends RSAConfiguration {

	private String issuer = ApplicationId.current().getGroupKey();

	public String getSignIssuer() {
		return this.issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
}
