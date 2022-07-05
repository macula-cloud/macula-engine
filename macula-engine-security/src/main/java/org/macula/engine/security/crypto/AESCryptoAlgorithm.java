package org.macula.engine.security.crypto;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import lombok.extern.slf4j.Slf4j;

/**
 * <p> AES 加密算法处理器 </p>
 */
@Slf4j
public class AESCryptoAlgorithm implements CryptoAlgorithm {

	@Override
	public CryptoKey createKey() {
		return CryptoKey.asSymmetricKey(RandomUtil.randomStringUpper(16));
	}

	@Override
	public String decrypt(String data, String key) {
		AES aes = SecureUtil.aes(StrUtil.utf8Bytes(key));
		byte[] result = aes.decrypt(Base64.decode(StrUtil.utf8Bytes(data)));
		log.debug("[Macula] |- AES crypto decrypt data, value is : [{}]", result);
		return StrUtil.utf8Str(result);
	}

	@Override
	public String encrypt(String data, String key) {
		AES aes = SecureUtil.aes(StrUtil.utf8Bytes(key));
		byte[] result = aes.encrypt(StrUtil.utf8Bytes(data));
		log.debug("[Macula] |- AES crypto encrypt data, value is : [{}]", result);
		return StrUtil.utf8Str(result);
	}
}
