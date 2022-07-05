package org.macula.engine.security.protocol;

import org.macula.engine.security.crypto.CryptoKey;

/**
 * Encrypt / Decrypt Protocol ( like Http )
 */
public interface CryptoProtocol {

	/** 设置秘钥 */
	void setCryptoKey(String identity, CryptoKey key);

	/** 加密内容*/
	String encrypt(String identity, String content);

	/** 解密内容 **/
	String decrypt(String identity, String content);

	/** 前端获取后端生成 AES Key */
	String exchange(String identity, String confidential);
}
