package org.macula.engine.oauth2;

import lombok.ToString;

@ToString
public class OAuth2Properties {

	private Jwk jwk = new Jwk();

	public Jwk getJwk() {
		return jwk;
	}

	public void setJwk(Jwk jwk) {
		this.jwk = jwk;
	}

	@ToString
	public static class Jwk {

		private enum Strategy {
			STANDARD,
			CUSTOM
		}

		/**
		 * 证书策略：standard OAuth2 标准证书模式；custom 自定义证书模式
		 */
		private Strategy certificate = Strategy.CUSTOM;

		/**
		 * jks证书文件路径
		 */
		private String jksKeyStore = "classpath*:certificate/macula-cloud.jks";
		/**
		 * jks证书密码
		 */
		private String jksKeyPassword = "Macula-Cloud";
		/**
		 * jks证书密钥库密码
		 */
		private String jksStorePassword = "Macula-Cloud";
		/**
		 * jks证书别名
		 */
		private String jksKeyAlias = "macula-cloud";

		public Strategy getCertificate() {
			return certificate;
		}

		public void setCertificate(Strategy certificate) {
			this.certificate = certificate;
		}

		public String getJksKeyStore() {
			return jksKeyStore;
		}

		public void setJksKeyStore(String jksKeyStore) {
			this.jksKeyStore = jksKeyStore;
		}

		public String getJksKeyPassword() {
			return jksKeyPassword;
		}

		public void setJksKeyPassword(String jksKeyPassword) {
			this.jksKeyPassword = jksKeyPassword;
		}

		public String getJksStorePassword() {
			return jksStorePassword;
		}

		public void setJksStorePassword(String jksStorePassword) {
			this.jksStorePassword = jksStorePassword;
		}

		public String getJksKeyAlias() {
			return jksKeyAlias;
		}

		public void setJksKeyAlias(String jksKeyAlias) {
			this.jksKeyAlias = jksKeyAlias;
		}
	}
}
