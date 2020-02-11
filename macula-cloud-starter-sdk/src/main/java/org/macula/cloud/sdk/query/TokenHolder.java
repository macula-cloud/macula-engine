package org.macula.cloud.sdk.query;

public class TokenHolder {

	private final String openToken;
	private final String closeToken;

	public TokenHolder(String openToken, String closeToken) {
		this.openToken = openToken;
		this.closeToken = closeToken;
	}

	/**
	 * @return the openToken
	 */
	public String getOpenToken() {
		return openToken;
	}

	/**
	 * @return the closeToken
	 */
	public String getCloseToken() {
		return closeToken;
	}
}
