package org.macula.engine.security.crypto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * <p>Crypto Algorithm Key</p>
 */
public class CryptoKey implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 数据存储身份标识
	 */
	private String identity;

	/**
	 * 对称加密算法秘钥
	 */
	private String symmetricKey;

	/**
	 * 服务器端非对称加密算法公钥
	 * 1. RSA 为 Base64 格式
	 * 2. SM2 为 Hex 格式
	 */
	private String publicKey;

	/**
	 * 服务器端非对称加密算法私钥
	 */
	private String privateKey;

	/**
	 * 创建时间戳
	 */
	private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

	public static CryptoKey asSymmetricKey(String key) {
		CryptoKey cryptoKey = new CryptoKey();
		cryptoKey.setSymmetricKey(key);
		return cryptoKey;
	}

	public static CryptoKey asAsymmetricKey(String privateKey, String publicKey) {
		CryptoKey cryptoKey = new CryptoKey();
		cryptoKey.setPrivateKey(privateKey);
		cryptoKey.setPublicKey(publicKey);
		return cryptoKey;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getSymmetricKey() {
		return symmetricKey;
	}

	public void setSymmetricKey(String symmetricKey) {
		this.symmetricKey = symmetricKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CryptoKey cryptoKey = (CryptoKey) o;
		return Objects.equal(identity, cryptoKey.identity) && Objects.equal(timestamp, cryptoKey.timestamp);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(identity, timestamp);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("identity", identity).add("symmetricKey", symmetricKey).add("publicKey", publicKey)
				.add("privateKey", privateKey).add("timestamp", timestamp).toString();
	}
}
