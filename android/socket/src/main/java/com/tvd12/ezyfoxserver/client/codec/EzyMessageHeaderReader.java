package com.tvd12.ezyfoxserver.client.codec;

public final class EzyMessageHeaderReader {

	private EzyMessageHeaderReader() {}

	public static boolean readBigSize(byte header) {
		return (header & 1 << 0) != 0;
	}

	public static boolean readEncrypted(byte header) {
		return (header & (1 << 1)) != 0;
	}

	public static boolean readCompressed(byte header) {
		return (header & (1 << 2)) != 0;
	}

	public static boolean readText(byte header) {
		return (header & (1 << 3)) != 0;
	}

	public static boolean readRawBytes(byte header) {
		return (header & (1 << 4)) != 0;
	}

	public static boolean readUdpHandshake(byte header) {
		return (header & (1 << 5)) != 0;
	}

	public static boolean readHasNext(byte header) {
		return (header & (1 << 7)) != 0;
	}

	public static EzyMessageHeader read(byte header) {
		return new EzySimpleMessageHeader(
				readBigSize(header),
				readEncrypted(header),
				readCompressed(header),
				readText(header),
				readRawBytes(header),
				readUdpHandshake(header));
	}
	
}