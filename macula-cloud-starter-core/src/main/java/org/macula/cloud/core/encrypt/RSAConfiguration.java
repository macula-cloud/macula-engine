package org.macula.cloud.core.encrypt;

import org.springframework.beans.factory.annotation.Value;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAConfiguration {

	@Value("${macula.cloud.auth.rsa.private-key}")
	private String privateKey;
	@Value("${macula.cloud.auth.rsa.public-key}")
	private String publicKey;

	private String algorithm = "RSA";

	public PrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] decodeKey = Base64.getDecoder().decode(privateKey);
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decodeKey);
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		return keyFactory.generatePrivate(spec);
	}

	public PublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] decodeKey = Base64.getDecoder().decode(publicKey);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(decodeKey);
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		return keyFactory.generatePublic(spec);
	}

}
