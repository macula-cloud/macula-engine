package org.macula.engine.security.protocol;

import lombok.extern.slf4j.Slf4j;
import org.macula.engine.j2cache.utils.J2CacheUtils;
import org.macula.engine.security.crypto.CryptoAlgorithm;
import org.macula.engine.security.crypto.CryptoKey;

@Slf4j
public class HttpCryptoProtocol implements CryptoProtocol {

	private static final String CACHE_NAME = "CACHE::CRYPTO";
	private final CryptoAlgorithm cryptoAlgorithm;
	// private final Integer accessTokenValiditySeconds;

	public HttpCryptoProtocol(CryptoAlgorithm cryptoAlgorithm, Integer accessTokenValiditySeconds) {
		this.cryptoAlgorithm = cryptoAlgorithm;
		// this.accessTokenValiditySeconds = accessTokenValiditySeconds;
	}

	@Override
	public void setCryptoKey(String identity, CryptoKey key) {
		J2CacheUtils.set(CACHE_NAME, identity, key);
	}

	@Override
	public String encrypt(String identity, String content) {
		try {
			CryptoKey cryptoKey = getCryptoKey(identity);
			String result = cryptoAlgorithm.encrypt(content, cryptoKey.getSymmetricKey());
			log.debug("[Macula] |- Encrypt content from [{}] to [{}].", content, result);
			return result;
		} catch (Exception e) {
			log.warn("[Macula] |- Symmetric can not Encrypt content [{}], Skip!", content);
			return content;
		}
	}

	@Override
	public String decrypt(String identity, String content) {
		try {
			CryptoKey cryptoKey = getCryptoKey(identity);
			String result = cryptoAlgorithm.decrypt(content, cryptoKey.getSymmetricKey());
			log.debug("[Macula] |- Decrypt content from [{}] to [{}].", content, result);
			return result;
		} catch (Exception e) {
			log.warn("[Macula] |- Symmetric can not Decrypt content [{}], Skip!", content);
			return content;
		}
	}

	@Override
	public String exchange(String identity, String confidential) {
		CryptoKey secretKey = getCryptoKey(identity);
		String frontendPublicKey = decryptFrontendPublicKey(confidential, secretKey.getPrivateKey());
		return encryptBackendKey(secretKey.getSymmetricKey(), frontendPublicKey);
	}

	private CryptoKey getCryptoKey(String identity) {
		CryptoKey cryptoKey = J2CacheUtils.get(CACHE_NAME, identity);
		return cryptoKey;
	}

	/**
	* 用后端非对称加密算法私钥，解密前端传递过来的、用后端非对称加密算法公钥加密的前端非对称加密算法公钥
	*
	* @param privateKey 后端非对称加密算法私钥
	* @param content    传回的已加密前端非对称加密算法公钥
	* @return 前端非对称加密算法公钥
	*/
	private String decryptFrontendPublicKey(String content, String privateKey) {
		String frontendPublicKey = cryptoAlgorithm.decrypt(content, privateKey);
		log.debug("[Macula] |- Decrypt frontend public key, value is : [{}]", frontendPublicKey);
		return frontendPublicKey;
	}

	/**
	 * 用前端非对称加密算法公钥加密后端生成的对称加密算法 Key
	 *
	 * @param symmetricKey 对称算法秘钥
	 * @param publicKey    前端非对称加密算法公钥
	 * @return 用前端前端非对称加密算法公钥加密后的对称算法秘钥
	 */
	private String encryptBackendKey(String symmetricKey, String publicKey) {
		String encryptedAesKey = cryptoAlgorithm.encrypt(symmetricKey, publicKey);
		log.debug("[Macula] |- Encrypt symmetric key use frontend public key, value is : [{}]", encryptedAesKey);
		return encryptedAesKey;
	}

}
