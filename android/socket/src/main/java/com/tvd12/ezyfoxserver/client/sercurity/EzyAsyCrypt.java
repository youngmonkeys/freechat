package com.tvd12.ezyfoxserver.client.sercurity;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class EzyAsyCrypt {

	protected final Cipher cipher;
	protected final byte[] publicKey;
	protected final byte[] privateKey;
	protected final KeyFactory keyFactory;
	
	public static final String DEFAULT_ALGORITHM = "RSA";
	public static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";
	
	protected EzyAsyCrypt(Builder<?> builder) {
		try {
			this.cipher = builder.newCipher();
			this.publicKey = builder.getPublicKey();
			this.privateKey = builder.getPrivateKey();
			this.keyFactory = builder.newKeyFactory();
		}
		catch(Exception e) {
			throw new IllegalStateException("init asymmetric encryption error", e);
		}
	}
	
	public byte[] encrypt(byte[] data) throws Exception {
		cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());
		return cipher.doFinal(data);
    }
	
	public byte[] decrypt(byte[] data) throws Exception {
		cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());
		return cipher.doFinal(data);
    }

	/**
	 * https://docs.oracle.com/javase/8/docs/api/java/security/spec/PKCS8EncodedKeySpec.html
	 * 
	 * @return the private key object
	 * @throws Exception when get any errors
	 */
	protected PrivateKey getPrivateKey() throws Exception {
		return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKey));
	}

	/**
	 * https://docs.oracle.com/javase/8/docs/api/java/security/spec/X509EncodedKeySpec.html
	 * 
	 * @return the public key
	 * @throws Exception when get any error
	 */
	protected PublicKey getPublicKey() throws Exception {
		return keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));
	}
	
	public static Builder<?> builder() {
		return new Builder<>();
	}
	
	@SuppressWarnings("unchecked")
	public static class Builder<B extends Builder<B>> {
		
		protected byte[] publicKey;
		protected byte[] privateKey;
		protected String algorithm = DEFAULT_ALGORITHM;
		protected String transformation = TRANSFORMATION;
		
		public B algorithm(String algorithm) {
			this.algorithm = algorithm;
			return (B)this;
		}

		public B transformation(String transformation) {
			this.transformation = transformation;
			return (B)this;
		}
		
		public B publicKey(byte[] publicKey) {
			this.publicKey = publicKey;
			return (B)this;
		}
		
		public B privateKey(byte[] privateKey) {
			this.privateKey = privateKey;
			return (B)this;
		}
		
		protected byte[] getPublicKey() {
			return publicKey;
		}
		
		protected byte[] getPrivateKey() {
			return privateKey;
		}
		
		public EzyAsyCrypt build() {
			return new EzyAsyCrypt(this);
		}
		
		protected Cipher newCipher() throws Exception {
			return Cipher.getInstance(transformation);
		}
		
		protected KeyFactory newKeyFactory() throws Exception {
			return KeyFactory.getInstance(algorithm);
		}
	}

}