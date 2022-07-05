package org.macula.engine.security.crypto;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;

/**
 * <p>国密 SM2 算法处理 </p>
 * <p>
 * 主要用于前后端数据加密处理，与 sm-crypto 交互
 */
@Slf4j
public class SM2CryptoAlgorithm implements CryptoAlgorithm {

	@Override
	public CryptoKey createKey() {
		// 随机生成秘钥
		SM2 sm2 = SmUtil.sm2();
		// sm2的加解密时有两种方式即 C1C2C3、 C1C3C2，
		sm2.setMode(SM2Engine.Mode.C1C3C2);
		// 生成私钥
		String privateKey = HexUtil.encodeHexStr(BCUtil.encodeECPrivateKey(sm2.getPrivateKey()));
		// 生成公钥
		String publicKey = HexUtil.encodeHexStr(((BCECPublicKey) sm2.getPublicKey()).getQ().getEncoded(false));
		return CryptoKey.asAsymmetricKey(privateKey, publicKey);
	}

	@Override
	public String decrypt(String content, String privateKey) {
		// 可用的 Hutool SM2 解密
		SM2 sm2 = SmUtil.sm2(privateKey, null);
		sm2.setMode(SM2Engine.Mode.C1C3C2);

		String result = StrUtil.utf8Str(sm2.decryptFromBcd(content, KeyType.PrivateKey));
		log.debug("[Macula] |- SM2 crypto decrypt data, value is : [{}]", result);

		return result;
	}

	@Override
	public String encrypt(String content, String publicKey) {
		SM2 sm2 = SmUtil.sm2(null, publicKey);

		String result = sm2.encryptBcd(content, KeyType.PublicKey);
		log.debug("[Macula] |- SM2 crypto encrypt data, value is : [{}]", result);
		return result;
	}

}
