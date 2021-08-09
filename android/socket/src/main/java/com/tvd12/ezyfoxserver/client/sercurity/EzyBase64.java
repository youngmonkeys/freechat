package com.tvd12.ezyfoxserver.client.sercurity;

import android.util.Base64;

import com.tvd12.ezyfoxserver.client.io.EzyStrings;


public final class EzyBase64 {

	private EzyBase64() {
	}
	
	public static byte[] encode(byte[] input) {
		byte[] answer = Base64.encode(input, Base64.DEFAULT);
		return answer;
	}
	
	public static byte[] decode(byte[] input) {
		byte[] answer = Base64.decode(input, Base64.DEFAULT);
		return answer;
	}
	
	public static byte[] decode(String input) {
		byte[] answer = Base64.decode(input, Base64.DEFAULT);
		return answer;
	}
	
	public static byte[] encode(String input) {
		byte[] bytes = EzyStrings.getUtfBytes(input);
		byte[] answer = Base64.encode(bytes, Base64.DEFAULT);
		return answer;
	}
	
	public static String encodeUtf(String input) {
		byte[] bytes = EzyStrings.getUtfBytes(input);
		String answer = Base64.encodeToString(bytes, Base64.DEFAULT);
		return answer;
	}
	
	public static String decodeUtf(String input) {
		byte[] bytes = Base64.decode(input, Base64.DEFAULT);
		String answer = EzyStrings.newUtf(bytes);
		return answer;
	}
	
	public static String encode2utf(byte[] input) {
		String answer = EzyStrings.newUtf(encode(input));
		return answer;
	}
	
	public static String decode2utf(byte[] input) {
		String answer = EzyStrings.newUtf(decode(input));
		return answer;
	}
	
}
