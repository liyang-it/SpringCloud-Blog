package com.liyangit.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class SymmetricEncryption {
	private final static Logger log = LoggerFactory.getLogger(SymmetricEncryption.class);
	
	/**
	 * 对称加解密，key固定。
	 *
	 * @param text 加密时，是密码明文，解密时，是密码密文。
	 * @param mode true加密，false:解密.
	 * @return 加解密的密文或者明文
	 */
	public static String extract(String text, boolean mode) throws Exception {
		try {
			String key = "12345678qwertyui"; // AES的key要16位
			Cipher cipher = Cipher.getInstance("AES");
			SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
			cipher.init(mode ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKeySpec);
			byte[] bytes = cipher.doFinal(mode ? text.getBytes(StandardCharsets.UTF_8) : Base64.getDecoder().decode(text.getBytes()));
			return mode ? new String(Base64.getEncoder().encode(bytes)) : new String(bytes);
		} catch (Exception e) {
			log.error("====对称加密/解密异常:{}", e.getMessage());
			throw new Exception("加密/解密异常");
		}
		
	}
}
