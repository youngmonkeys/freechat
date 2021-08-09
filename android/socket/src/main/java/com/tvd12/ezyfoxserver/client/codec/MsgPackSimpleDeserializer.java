package com.tvd12.ezyfoxserver.client.codec;

import com.tvd12.ezyfoxserver.client.builder.EzyArrayBuilder;
import com.tvd12.ezyfoxserver.client.builder.EzyObjectBuilder;
import com.tvd12.ezyfoxserver.client.entity.EzyArray;
import com.tvd12.ezyfoxserver.client.entity.EzyObject;
import com.tvd12.ezyfoxserver.client.function.EzyParser;
import com.tvd12.ezyfoxserver.client.io.EzyBytes;
import com.tvd12.ezyfoxserver.client.io.EzyInts;
import com.tvd12.ezyfoxserver.client.io.EzyLongs;
import com.tvd12.ezyfoxserver.client.io.EzyStrings;
import com.tvd12.ezyfoxserver.client.util.EzyEntityBuilders;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MsgPackSimpleDeserializer
		extends EzyEntityBuilders
		implements EzyMessageDeserializer {

	private MsgPackTypeParser typeParser = new MsgPackTypeParser();
	private MapSizeDeserializer mapSizeDeserializer = new MapSizeDeserializer();
	private ArraySizeDeserializer arraySizeDeserializer = new ArraySizeDeserializer();
	private StringSizeDeserializer stringSizeDeserializer = new StringSizeDeserializer();
	private Map<MsgPackType, EzyParser<ByteBuffer, Object>> parsers = defaultParsers();

	@Override
	public <T> T read(ByteBuffer buffer) {
		return (T)deserialize(buffer);
	}

	@Override
	public <T> T read(byte[] buffer) {
		return read(ByteBuffer.wrap(buffer));
	}

	@Override
	public <T> T deserialize(byte[] data) {
		return (T)deserialize(ByteBuffer.wrap(data));
	}

	@Override
	public <T> T deserialize(String text) {
		return deserialize(EzyStrings.getUtfBytes(text));
	}
	
	@SuppressWarnings("unchecked")
	public Object deserialize(ByteBuffer buffer) {
		return deserialize(buffer, buffer.get() & 0xff);
	}
	
	protected Object deserialize(ByteBuffer buffer, int type) {
		updateBufferPosition(buffer);
		return deserialize(buffer, getDataType(type));
	}
	
	protected Object deserialize(ByteBuffer buffer, MsgPackType type) {
		return parsers.get(type).parse(buffer);
	}
	
	protected Map<MsgPackType, EzyParser<ByteBuffer, Object>> defaultParsers() {
		Map<MsgPackType, EzyParser<ByteBuffer, Object>> parsers = new ConcurrentHashMap<>();
		addParsers(parsers);
		return parsers;
	}
	
	protected void addParsers(Map<MsgPackType, EzyParser<ByteBuffer, Object>> parsers) {
		parsers.put(MsgPackType.POSITIVE_FIXINT, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parsePositiveFixInt(input);
			}
		}); //0K
		parsers.put(MsgPackType.NEGATIVE_FIXINT, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseNegativeFixInt(input);
			}
		}); //OK
		parsers.put(MsgPackType.UINT8, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseUInt8(input);
			}
		}); //OK
		parsers.put(MsgPackType.UINT16, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseUInt16(input);
			}
		}); //OK
		parsers.put(MsgPackType.UINT32, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseUInt32(input);
			}
		}); //OK
		parsers.put(MsgPackType.UINT64, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseUInt64(input);
			}
		}); //OK
		parsers.put(MsgPackType.INT8, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseInt8(input);
			}
		}); //OK
		parsers.put(MsgPackType.INT16, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseInt16(input);
			}
		}); //OK
		parsers.put(MsgPackType.INT32, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseInt32(input);
			}
		}); //OK
		parsers.put(MsgPackType.INT64, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseInt64(input);
			}
		}); //OK
		parsers.put(MsgPackType.FIXMAP, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseFixMap(input);
			}
		}); //OK
		parsers.put(MsgPackType.MAP16, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseMap16(input);
			}
		}); //OK
		parsers.put(MsgPackType.MAP32, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseMap32(input);
			}
		}); //OK
		parsers.put(MsgPackType.FIXARRAY, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseFixArray(input);
			}
		}); //OK
		parsers.put(MsgPackType.ARRAY16, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseArray16(input);
			}
		}); //OK
		parsers.put(MsgPackType.ARRAY32, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseArray32(input);
			}
		}); //OK
		parsers.put(MsgPackType.FIXSTR, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseFixStr(input);
			}
		});
		parsers.put(MsgPackType.STR8, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseStr8(input);
			}
		});
		parsers.put(MsgPackType.STR16, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseStr16(input);
			}
		});
		parsers.put(MsgPackType.STR32, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseStr32(input);
			}
		});
		parsers.put(MsgPackType.NIL, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseNil(input);
			}
		});
		parsers.put(MsgPackType.FALSE, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseFalse(input);
			}
		});
		parsers.put(MsgPackType.TRUE, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseTrue(input);
			}
		});
		parsers.put(MsgPackType.BIN8, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseBin8(input);
			}
		});
		parsers.put(MsgPackType.BIN16, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseBin16(input);
			}
		});
		parsers.put(MsgPackType.BIN32, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseBin32(input);
			}
		});
		parsers.put(MsgPackType.FLOAT32, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseFloat32(input);
			}
		});
		parsers.put(MsgPackType.FLOAT64, new EzyParser<ByteBuffer, Object>() {
			@Override
			public Object parse(ByteBuffer input) {
				return parseFloat64(input);
			}
		});
	}
	
	protected Object parseFloat32(ByteBuffer buffer) {
		buffer.get();
		return buffer.getFloat();
	}
	
	protected Object parseFloat64(ByteBuffer buffer) {
		buffer.get();
		return buffer.getDouble();
	}
	
	protected Object parseBin32(ByteBuffer buffer) {
		return parseBin(buffer, getBinLength(buffer, 4));
	}
	
	protected Object parseBin16(ByteBuffer buffer) {
		return parseBin(buffer, getBinLength(buffer, 2));
	}
	
	protected Object parseBin8(ByteBuffer buffer) {
		return parseBin(buffer, getBinLength(buffer, 1));
	}
	
	protected int getBinLength(ByteBuffer buffer, int size) {
		buffer.get();
		return EzyInts.bin2uint(buffer, size);
	}
	
	protected Object parseBin(ByteBuffer buffer, int length) {
		return EzyBytes.copy(buffer, length);
	}
	
	protected Object parseTrue(ByteBuffer buffer) {
		return parseValue(buffer, Boolean.TRUE);
	}
	
	protected Object parseFalse(ByteBuffer buffer) {
		return parseValue(buffer, Boolean.FALSE);
	}
	
	protected Object parseNil(ByteBuffer buffer) {
		return parseValue(buffer, null);
	}
	
	protected Object parseValue(ByteBuffer buffer, Object value) {
		buffer.get();
		return value;
	}
	
	protected EzyObject parseFixMap(ByteBuffer buffer) {
		return parseMap(buffer, getMapSize(buffer, 1));
	}
	
	protected EzyObject parseMap16(ByteBuffer buffer) {
		return parseMap(buffer, getMapSize(buffer, 3));
	}
	
	protected EzyObject parseMap32(ByteBuffer buffer) {
		return parseMap(buffer, getMapSize(buffer, 5));
	}
	
	protected int getMapSize(ByteBuffer buffer, int nbytes) {
		return mapSizeDeserializer.deserialize(buffer, nbytes);
	}
	
	protected EzyObject parseMap(ByteBuffer buffer, int size) {
		EzyObjectBuilder builder = newObjectBuilder();
		for(int i = 0 ; i < size ; ++i)
			builder.append(deserialize(buffer), deserialize(buffer));
		return builder.build();
	}
	
	protected String parseStr32(ByteBuffer buffer) {
		return parseString(buffer, 5);
	}
	
	protected String parseStr16(ByteBuffer buffer) {
		return parseString(buffer, 3);
	}
	
	protected String parseStr8(ByteBuffer buffer) {
		return parseString(buffer, 2);
	}
	
	protected String parseFixStr(ByteBuffer buffer) {
		return parseString(buffer, 1);
	}

	protected String parseString(ByteBuffer buffer, int nbytes) {
		return EzyStrings.newUtf(buffer, parseStringSize(buffer, nbytes));
	}
	
	protected int parseStringSize(ByteBuffer buffer, int nbytes) {
		return stringSizeDeserializer.deserialize(buffer, nbytes);
	}
	
	protected int parsePositiveFixInt(ByteBuffer buffer) {
		return (buffer.get() & 0x7F);
	}
	
	protected int parseNegativeFixInt(ByteBuffer buffer) {
		return buffer.get();
	}
	
	protected int parseUInt8(ByteBuffer buffer) {
		return parseUInt(buffer, 1);
	}
	
	protected int parseUInt16(ByteBuffer buffer) {
		return parseUInt(buffer, 2);
	}
	
	protected int parseUInt32(ByteBuffer buffer) {
		return parseUInt(buffer, 4);
	}
	
	protected long parseUInt64(ByteBuffer buffer) {
		return parseULong(buffer, 8);
	}
	
	protected int parseInt8(ByteBuffer buffer) {
		return parseInt(buffer, 1);
	}
	
	protected int parseInt16(ByteBuffer buffer) {
		return parseInt(buffer, 2);
	}
	
	protected int parseInt32(ByteBuffer buffer) {
		return parseInt(buffer, 4);
	}
	
	protected long parseInt64(ByteBuffer buffer) {
		return parseLong(buffer, 8);
	}
	
	protected int parseUInt(ByteBuffer buffer, int size) {
		return (int) parseULong(buffer, size);
	}
	
	protected long parseULong(ByteBuffer buffer, int size) {
		buffer.get();
		return EzyLongs.bin2ulong(buffer, size);
	}
	
	protected int parseInt(ByteBuffer buffer, int size) {
		return (int) parseLong(buffer, size);
	}
	
	protected long parseLong(ByteBuffer buffer, int size) {
		buffer.get();
		return EzyLongs.bin2long(buffer, size);
	}
	
	protected EzyArray parseFixArray(ByteBuffer buffer) {
		return parseArray(buffer, parseArraySize(buffer, 1));
	}
	
	protected EzyArray parseArray16(ByteBuffer buffer) {
		return parseArray(buffer, parseArraySize(buffer, 3));
	}
	
	protected EzyArray parseArray32(ByteBuffer buffer) {
		return parseArray(buffer, parseArraySize(buffer, 5));
	}
	
	protected int parseArraySize(ByteBuffer buffer, int nbytes) {
		return arraySizeDeserializer.deserialize(buffer, nbytes);
	}
	
	protected EzyArray parseArray(ByteBuffer buffer, int size) {
		EzyArrayBuilder builder = newArrayBuilder();
		for(int i = 0 ; i < size ; ++i)
			builder.append(deserialize(buffer));
		return builder.build();
	}
	
	protected MsgPackType getDataType(int type) {
		return typeParser.parse(type);
	}
	
	protected void updateBufferPosition(ByteBuffer buffer) {
		updateBufferPosition(buffer, -1);
	}
	
	protected void updateBufferPosition(ByteBuffer buffer, int offset) {
		buffer.position(buffer.position() + offset);
	}
}

abstract class AbstractSizeDeserializer {
	
	public int deserialize(ByteBuffer buffer, int nbytes) {
		return nbytes == 1
				? getFix(buffer)
				: getOther(buffer, nbytes);
	}
	
	protected abstract int getFix(ByteBuffer buffer);
	
	protected int getOther(ByteBuffer buffer, int nbytes) {
		buffer.get();
		return EzyInts.bin2uint(buffer, nbytes - 1);
	}
}

class StringSizeDeserializer extends AbstractSizeDeserializer {
	
	@Override
	protected int getFix(ByteBuffer buffer) {
		return buffer.get() & 0x1F;
	}
	
}

class MapSizeDeserializer extends AbstractSizeDeserializer {
	
	@Override
	protected int getFix(ByteBuffer buffer) {
		return buffer.get() & 0xF;
	}
	
}

class ArraySizeDeserializer extends AbstractSizeDeserializer {
	
	@Override
	protected int getFix(ByteBuffer buffer) {
		return buffer.get() & 0xF;
	}
	
}