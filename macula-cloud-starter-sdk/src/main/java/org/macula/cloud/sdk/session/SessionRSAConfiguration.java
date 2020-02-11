package org.macula.cloud.sdk.session;

import org.macula.cloud.sdk.application.ApplicationId;
import org.macula.cloud.sdk.encrypt.RSAConfiguration;

public class SessionRSAConfiguration extends RSAConfiguration {

	private String issuer = ApplicationId.current().getGroupKey();

	public String getSignIssuer() {
		return this.issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
}
