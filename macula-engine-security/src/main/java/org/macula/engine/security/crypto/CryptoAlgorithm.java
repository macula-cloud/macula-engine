package org.macula.engine.security.crypto;

/**
 * Crypto Algorithm
 */
public interface CryptoAlgorithm {

	/** Create CryptoKey */
	CryptoKey createKey();

	/**
	 * 用私钥解密
	 *
	 * @param key 非对称算法 KeyPair 私钥(对称算法为key)
	 * @param content    待解密数据
	 * @return 解密后的数据
	 */
	String decrypt(String content, String key);

	/**
	 * 用公钥加密
	 *
	 * @param key 非对称算法 KeyPair 公钥(对称算法为key)
	 * @param content   待加密数据
	 * @return 加密后的数据
	 */
	String encrypt(String content, String key);

}
