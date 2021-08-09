package com.tvd12.ezyfoxserver.client.codec;

import com.tvd12.ezyfoxserver.client.sercurity.EzyAesCrypt;

public class MsgPackAesObjectToByteEncoder extends MsgPackObjectToByteEncoder {

	private final EzyAesCrypt cryptor = EzyAesCrypt.getDefault();
	
	public MsgPackAesObjectToByteEncoder(
			EzyMessageToBytes messageToBytes, 
			EzyObjectToMessage objectToMessage) {
		super(messageToBytes, objectToMessage);
	}

	@Override
	protected byte[] doEncrypt(
			byte[] messageContent, byte[] encryptionKey) throws Exception {
		return cryptor.encrypt(messageContent, encryptionKey);
	}
	
}
