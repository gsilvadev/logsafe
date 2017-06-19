package com.xerpass.logsafe.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

	public static String encriptar(String senha) {
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			m.update(senha.getBytes(), 0, senha.length());
			return new BigInteger(1,m.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return senha;
	}
}