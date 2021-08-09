package com.tvd12.ezyfoxserver.client.sercurity;

import com.tvd12.ezyfoxserver.client.builder.EzyBuilder;

import java.util.concurrent.ThreadLocalRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EzyAesCrypt {
	
	private final int initVectorSize;
	private final String keySpecAlgorithm;
	private final String transformation;
	
	public static final int DEFAULT_KEY_SIZE = 32;
	private static final EzyAesCrypt DEFAULT = EzyAesCrypt.builder().build();
	
	public EzyAesCrypt(Builder builder) {
		this.initVectorSize = builder.initVectorSize;
		this.keySpecAlgorithm = builder.keySpecAlgorithm;
		this.transformation = builder.transformation;
	}

	public static EzyAesCrypt getDefault() {
		return DEFAULT;
	}
	
	public static byte[] randomKey() {
		return randomKey(DEFAULT_KEY_SIZE);
	}
	
	public static byte[] randomKey(int size) {
		return EzyKeysGenerator.randomKey(size);
	}

	public byte[] encrypt(byte[] message, byte[] key) throws Exception {
        byte[] iv = new byte[initVectorSize];
        ThreadLocalRandom.current().nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        SecretKeySpec secretKeySpec = new SecretKeySpec(key, keySpecAlgorithm);

        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(message);

        byte[] encryptedIvAndMessage = new byte[initVectorSize + encrypted.length];
        System.arraycopy(iv, 0, encryptedIvAndMessage, 0, initVectorSize);
        System.arraycopy(encrypted, 0, encryptedIvAndMessage, initVectorSize, encrypted.length);

        return encryptedIvAndMessage;
    }

    public byte[] decrypt(byte[] message, byte[] key) throws Exception {
        byte[] iv = new byte[initVectorSize];
        System.arraycopy(message, 0, iv, 0, iv.length);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        int encryptedSize = message.length - iv.length;
        byte[] encryptedBytes = new byte[encryptedSize];
        System.arraycopy(message, initVectorSize, encryptedBytes, 0, encryptedSize);

        SecretKeySpec secretKeySpec = new SecretKeySpec(key, keySpecAlgorithm);

        Cipher cipherDecrypt = Cipher.getInstance(transformation);
        cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        return cipherDecrypt.doFinal(encryptedBytes);
    }
    
    public static Builder builder() {
    	return new Builder();
    }
    
    public static class Builder implements EzyBuilder<EzyAesCrypt> {
    	private int initVectorSize = 16;
    	private String keySpecAlgorithm = "AES";
    	private String transformation = "AES/CBC/PKCS5Padding";
    	
    	public Builder initVectorSize(int initVectorSize) {
    		this.initVectorSize = initVectorSize;
    		return this;
    	}
    	
    	public Builder keySpecAlgorithm(String keySpecAlgorithm) {
    		this.keySpecAlgorithm = keySpecAlgorithm;
    		return this;
    	}
    	
    	public Builder transformation(String transformation) {
    		this.transformation = transformation;
    		return this;
    	}
    	
    	@Override
    	public EzyAesCrypt build() {
    		return new EzyAesCrypt(this);
    	}
    	
    }
	
}
