package com.tvd12.ezyfoxserver.client.codec;

import com.tvd12.ezyfoxserver.client.entity.EzyArray;
import com.tvd12.ezyfoxserver.client.entity.EzyArrayList;
import com.tvd12.ezyfoxserver.client.entity.EzyHashMap;
import com.tvd12.ezyfoxserver.client.entity.EzyObject;
import com.tvd12.ezyfoxserver.client.function.EzyParser;
import com.tvd12.ezyfoxserver.client.io.EzyBytes;
import com.tvd12.ezyfoxserver.client.io.EzyCastToByte;
import com.tvd12.ezyfoxserver.client.io.EzyDataConverter;
import com.tvd12.ezyfoxserver.client.io.EzyStrings;
import com.tvd12.ezyfoxserver.client.util.EzyBoolsIterator;
import com.tvd12.ezyfoxserver.client.util.EzyDoublesIterator;
import com.tvd12.ezyfoxserver.client.util.EzyFloatsIterator;
import com.tvd12.ezyfoxserver.client.util.EzyIntsIterator;
import com.tvd12.ezyfoxserver.client.util.EzyLongsIterator;
import com.tvd12.ezyfoxserver.client.util.EzyShortsIterator;
import com.tvd12.ezyfoxserver.client.util.EzyStringsIterator;
import com.tvd12.ezyfoxserver.client.util.EzyWrapperIterator;

import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class MsgPackSimpleSerializer 
		extends EzyAbstractToBytesSerializer {

	protected IntSerializer intSerializer = new IntSerializer();
	protected FloatSerializer floatSerializer = new FloatSerializer();
	protected DoubleSerializer doubleSerializer = new DoubleSerializer();
	protected BinSizeSerializer binSizeSerializer = new BinSizeSerializer();
	protected MapSizeSerializer mapSizeSerializer = new MapSizeSerializer();
	protected ArraySizeSerializer arraySizeSerializer = new ArraySizeSerializer();
	protected StringSizeSerializer stringSizeSerializer = new StringSizeSerializer();

	@Override
	protected void addParsers(Map<Class<?>, EzyParser<Object, byte[]>> parsers) {
		parsers.put(Boolean.class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseBoolean(input);
			}
		});
		parsers.put(Byte.class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseByte(input);
			}
		});
		parsers.put(Character.class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseChar(input);
			}
		});
		parsers.put(Double.class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseDouble(input);
			}
		});
		parsers.put(Float.class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseFloat(input);
			}
		});
		parsers.put(Integer.class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseInt(input);
			}
		});
		parsers.put(Long.class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseInt(input);
			}
		});
		parsers.put(Short.class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseShort(input);
			}
		});
		parsers.put(String.class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseString(input);
			}
		});

		parsers.put(boolean[].class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parsePrimitiveBooleans(input);
			}
		});
		parsers.put(byte[].class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseBin(input);
			}
		});
		parsers.put(char[].class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parsePrimitiveChars(input);
			}
		});
		parsers.put(double[].class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parsePrimitiveDoubles(input);
			}
		});
		parsers.put(float[].class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parsePrimitiveFloats(input);
			}
		});
		parsers.put(int[].class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parsePrimitiveInts(input);
			}
		});
		parsers.put(long[].class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parsePrimitiveLongs(input);
			}
		});
		parsers.put(short[].class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parsePrimitiveShorts(input);
			}
		});
		parsers.put(String[].class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseStrings(input);
			}
		});
		
		parsers.put(Byte[].class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseWrapperBytes(input);
			}
		});
		parsers.put(Boolean[].class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseWrapperBooleans(input);
			}
		});
		parsers.put(Character[].class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseWrapperChars(input);
			}
		});
		parsers.put(Double[].class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseWrapperDoubles(input);
			}
		});
		parsers.put(Float[].class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseWrapperFloats(input);
			}
		});
		parsers.put(Integer[].class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseWrapperInts(input);
			}
		});
		parsers.put(Long[].class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseWrapperLongs(input);
			}
		});
		parsers.put(Short[].class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseWrapperShorts(input);
			}
		});
		
		parsers.put(Map.class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseMap(input);
			}
		});
		parsers.put(AbstractMap.class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseMap(input);
			}
		});
		parsers.put(HashMap.class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseMap(input);
			}
		});
		parsers.put(EzyObject.class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseObject(input);
			}
		});
		parsers.put(EzyHashMap.class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseObject(input);
			}
		});
		parsers.put(EzyArray.class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseArray(input);
			}
		});
		parsers.put(EzyArrayList.class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseArray(input);
			}
		});
		parsers.put(Collection.class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseCollection(input);
			}
		});
		parsers.put(AbstractCollection.class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseCollection(input);
			}
		});
		parsers.put(Set.class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseCollection(input);
			}
		});
		parsers.put(AbstractSet.class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseCollection(input);
			}
		});
		parsers.put(List.class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseCollection(input);
			}
		});
		parsers.put(AbstractList.class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseCollection(input);
			}
		});
		parsers.put(HashSet.class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseCollection(input);
			}
		});
		parsers.put(ArrayList.class, new EzyParser<Object, byte[]>() {
			@Override
			public byte[] parse(Object input) {
				return parseCollection(input);
			}
		});
	}
	
	//
	protected byte[] parsePrimitiveBooleans(Object array) {
		return parseBooleans((boolean[])array);
	}
	
	protected byte[] parsePrimitiveChars(Object array) {
		return parseChars((char[])array);
	}
	
	protected byte[] parsePrimitiveDoubles(Object array) {
		return parseDoubles((double[])array);
	}
	
	protected byte[] parsePrimitiveFloats(Object array) {
		return parseFloats((float[])array);
	}
	
	protected byte[] parsePrimitiveInts(Object array) {
		return parseInts((int[])array);
	}
	
	protected byte[] parsePrimitiveLongs(Object array) {
		return parseLongs((long[])array);
	}
	
	protected byte[] parsePrimitiveShorts(Object array) {
		return parseShorts((short[])array);
	}
	
	protected byte[] parseStrings(Object array) {
		return parseStrings((String[])array);
	}
	//
	
	protected byte[] parseBooleans(boolean[] array) {
		return parseArray(EzyBoolsIterator.wrap(array), array.length);
	}
	
	protected byte[] parseChars(char[] array) {
		return parseBin(EzyDataConverter.charArrayToByteArray(array));
	}
	
	protected byte[] parseDoubles(double[] array) {
		return parseArray(EzyDoublesIterator.wrap(array), array.length);
	}
	
	protected byte[] parseFloats(float[] array) {
		return parseArray(EzyFloatsIterator.wrap(array), array.length);
	}
	
	protected byte[] parseInts(int[] array) {
		return parseArray(EzyIntsIterator.wrap(array), array.length);
	}
	
	protected byte[] parseLongs(long[] array) {
		return parseArray(EzyLongsIterator.wrap(array), array.length);
	}
	
	protected byte[] parseShorts(short[] array) {
		return parseArray(EzyShortsIterator.wrap(array), array.length);
	}
	
	protected byte[] parseStrings(String[] array) {
		return parseArray(EzyStringsIterator.wrap(array), array.length);
	}
	
	//=============
	protected byte[] parseWrapperBooleans(Object array) {
		return parseBooleans((Boolean[])array);
	}
	
	protected byte[] parseWrapperBytes(Object array) {
		return parseBytes((Byte[])array);
	}
	
	protected byte[] parseWrapperChars(Object array) {
		return parseChars((Character[])array);
	}
	
	protected byte[] parseWrapperDoubles(Object array) {
		return parseDoubles((Double[])array);
	}
	
	protected byte[] parseWrapperFloats(Object array) {
		return parseFloats((Float[])array);
	}
	
	protected byte[] parseWrapperInts(Object array) {
		return parseInts((Integer[])array);
	}
	
	protected byte[] parseWrapperLongs(Object array) {
		return parseLongs((Long[])array);
	}
	
	protected byte[] parseWrapperShorts(Object array) {
		return parseShorts((Short[])array);
	}
	//
	//=============
	
	//
	protected byte[] parseBooleans(Boolean[] array) {
		return parseArray(EzyWrapperIterator.wrap(array), array.length);
	}
	
	protected byte[] parseBytes(Byte[] array) {
		return parseBin(EzyDataConverter.toPrimitiveByteArray(array));
	}
	
	protected byte[] parseChars(Character[] array) {
		return parseBin(EzyDataConverter.charWrapperArrayToPrimitiveByteArray(array));
	}
	
	protected byte[] parseDoubles(Double[] array) {
		return parseArray(EzyWrapperIterator.wrap(array), array.length);
	}
	
	protected byte[] parseFloats(Float[] array) {
		return parseArray(EzyWrapperIterator.wrap(array), array.length);
	}
	
	protected byte[] parseInts(Integer[] array) {
		return parseArray(EzyWrapperIterator.wrap(array), array.length);
	}
	
	protected byte[] parseLongs(Long[] array) {
		return parseArray(EzyWrapperIterator.wrap(array), array.length);
	}
	
	protected byte[] parseShorts(Short[] array) {
		return parseArray(EzyWrapperIterator.wrap(array), array.length);
	}
	//
	
	protected byte[] parseBoolean(Object value) {
		return parseBoolean((Boolean)value);
	}
	
	protected byte[] parseBoolean(Boolean value) {
		return value ? parseTrue() : parseFalse();
	}
	
	protected byte[] parseFalse() {
		return new byte[] {cast(0xc2)};
	}
	
	protected byte[] parseTrue() {
		return new byte[] {cast(0xc3)};
	}
	
	protected byte[] parseByte(Object value) {
		return parseByte((Byte)value);
	}
	
	protected byte[] parseByte(Byte value) {
		return parseInt(value.intValue());
	}
	
	protected byte[] parseChar(Object value) {
		return parseChar((Character)value);
	}
	
	protected byte[] parseChar(Character value) {
		return parseByte((byte)value.charValue());
	}
	
	protected byte[] parseDouble(Object value) {
		return parseDouble((Double)value);
	}
	
	protected byte[] parseDouble(Double value) {
		return doubleSerializer.serialize(value);
	}
	
	protected byte[] parseFloat(Object value) {
		return parseFloat((Float)value);
	}
	
	protected byte[] parseFloat(Float value) {
		return floatSerializer.serialize(value);
	}
	
	protected byte[] parseInt(Object value) {
		return intSerializer.serialize(((Number)value).longValue());
	}
	
	protected byte[] parseShort(Object value) {
		return parseShort((Short)value);
	}
	
	protected byte[] parseShort(Short value) {
		return parseInt(value.intValue());
	}
	
	protected byte[] parseString(Object string) {
		return parseString((String)string);
	}
	
	@SuppressWarnings({ "rawtypes" })
	protected byte[] parseMap(Object map) {
		return parseMap((Map)map);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected byte[] parseMap(Map map) {
		return parseEntries(map.entrySet());
	}
	
	protected byte[] parseObject(Object obj) {
		return parseEntries(((EzyObject)obj).entrySet());
	}
	
	@SuppressWarnings({ "rawtypes" })
	protected byte[] parseCollection(Object coll) {
		return parseCollection((Collection)coll);
	}
	
	protected byte[] parseArray(Object array) {
		return parseArray((EzyArray)array);
	}
	
	@SuppressWarnings({ "rawtypes" })
	protected byte[] parseCollection(Collection coll) {
		return parseIterable(coll, coll.size());
	}
	
	protected byte[] parseArray(EzyArray array) {
		return parseArray(array.iterator(), array.size());
	}
	
	protected byte[] parseNil() {
		return new byte[] {cast(0xc0)};
	}
	
	protected byte[] parseBin(Object bin) {
		return parseBin((byte[])bin);
	}
	
	protected byte[] parseBin(byte[] bin) {
		byte[][] bytess = new byte[2][];
		bytess[0] = parseBinSize(bin.length);
		bytess[1] = bin;
		return EzyBytes.merge(bytess);
	}
	
	protected byte[] parseBinSize(int size) {
		return binSizeSerializer.serialize(size);
	}
	
	protected byte[] parseString(String string) {
		byte[][] bytess = new byte[2][];
		bytess[1] = EzyStrings.getUtfBytes(string);
		bytess[0] = parseStringSize(bytess[1].length);
		return EzyBytes.merge(bytess);
	}
	
	protected byte[] parseStringSize(int size) {
		return stringSizeSerializer.serialize(size);
	}
	
	@SuppressWarnings("rawtypes")
	protected byte[] parseIterable(Iterable iterable, int size) {
		return parseArray(iterable.iterator(), size);
	}
	
	@SuppressWarnings("rawtypes")
	protected byte[] parseArray(Iterator iterator, int size) {
		int index = 1;
		byte[][] bytess = new byte[size + 1][];
		bytess[0] = parseArraySize(size);
		while(iterator.hasNext())
			bytess[index ++] = serialize(iterator.next());
		return EzyBytes.merge(bytess);
	}
	
	protected byte[] parseArraySize(int size) {
		return arraySizeSerializer.serialize(size);
	}
	
	protected byte[] parseEntries(Set<Entry<Object, Object>> entries) {
		int index = 1;
		int size = entries.size();
		byte[][] bytess = new byte[size * 2 + 1][];
		bytess[0] = parseMapSize(size);
		for(Entry<Object, Object> e : entries) {
			bytess[index++] = serialize(e.getKey());
			bytess[index++] = serialize(e.getValue());
		}
		return EzyBytes.merge(bytess);
	}
	
	protected byte[] parseMapSize(int size) {
		return mapSizeSerializer.serialize(size);
	}

	protected byte cast(int value) {
		return (byte)value;
	}

	protected byte cast(long value) {
		return (byte)value;
	}
}

class BinSizeSerializer extends EzyCastToByte {
	
	public byte[] serialize(int size) {
		if(size <= MsgPackConstant.MAX_BIN8_SIZE)
			return parse8(size);
		if(size <= MsgPackConstant.MAX_BIN16_SIZE)
			return parse16(size);
		return parse32(size);
	}
	
	private byte[] parse8(int size) {
		return new byte[] {
			cast(0xc4), cast(size)
		};
	}
	
	private byte[] parse16(int size) {
		return EzyBytes.getBytes(0xc5 , size, 2);
	}
	
	private byte[] parse32(int size) {
		return EzyBytes.getBytes(0xc6 , size, 4);
	}
}

class StringSizeSerializer extends EzyCastToByte {
	
	public byte[] serialize(int size) {
		if(size <= MsgPackConstant.MAX_FIXSTR_SIZE)
			return parseFix(size);
		if(size <= MsgPackConstant.MAX_STR8_SIZE)
			return parse8(size);
		if(size <= MsgPackConstant.MAX_STR16_SIZE)
			return parse16(size);
		return parse32(size);
	}
	
	private byte[] parseFix(int size) {
		return new byte[] {
			cast(0xa0 | size)
		};
	}
	
	private byte[] parse8(int size) {
		return EzyBytes.getBytes(0xd9, size, 1);
	}
	
	private byte[] parse16(int size) {
		return EzyBytes.getBytes(0xda, size, 2);
	}
	
	private byte[] parse32(int size) {
		return EzyBytes.getBytes(0xdb, size, 4);
	}
}

class ArraySizeSerializer extends EzyCastToByte {
	
	public byte[] serialize(int size) {
		if(size <= MsgPackConstant.MAX_FIXARRAY_SIZE)
			return parseFix(size);
		if(size <= MsgPackConstant.MAX_ARRAY16_SIZE)
			return parse16(size);
		return parse32(size);
	}
	
	private byte[] parseFix(int size) {
		return new byte[] {
			cast(0x90 | size)
		};
	}
	
	private byte[] parse16(int size) {
		return EzyBytes.getBytes(0xdc, size, 2);
	}
	
	private byte[] parse32(int size) {
		return EzyBytes.getBytes(0xdd, size, 4);
	}
}

class MapSizeSerializer extends EzyCastToByte {
	
	public byte[] serialize(int size) {
		if(size <= MsgPackConstant.MAX_FIXMAP_SIZE)
			return parseFix(size);
		if(size <= MsgPackConstant.MAX_MAP16_SIZE)
			return parse16(size);
		return parse32(size);
	}
	
	private byte[] parseFix(int size) {
		return new byte[] {
			cast(0x80 | size)
		};
	}
	
	private byte[] parse16(int size) {
		return EzyBytes.getBytes(0xde, size, 2);
	}
	
	private byte[] parse32(int size) {
		return EzyBytes.getBytes(0xdf, size, 4);
	}
}

class IntSerializer extends EzyCastToByte {
	
	public byte[] serialize(long value) {
		return value >= 0 
				? parsePositive(value) 
				: parseNegative(value);
	}
	
	private byte[] parsePositive(long value) {
		if(value <= MsgPackConstant.MAX_POSITIVE_FIXINT)
			return parsePositiveFix(value);
		if(value <= MsgPackConstant.MAX_UINT8)
			return parseU8(value);
		if(value <= MsgPackConstant.MAX_UINT16)
			return parseU16(value);
		if(value <= MsgPackConstant.MAX_UINT32)
			return parseU32(value);
		return parseU64(value);
	}
	
	private byte[] parsePositiveFix(long value) {
		return new byte[] {cast(0x0 | value)};
	}
	
	private byte[] parseU8(long value) {
		return EzyBytes.getBytes(0xcc, value, 1);
	}
	
	private byte[] parseU16(long value) {
		return EzyBytes.getBytes(0xcd, value, 2);
	}
	
	private byte[] parseU32(long value) {
		return EzyBytes.getBytes(0xce, value, 4);
	}
	
	private byte[] parseU64(long value) {
		return EzyBytes.getBytes(0xcf, value, 8);
	}
	
	private byte[] parseNegative(long value) {
		if(value >= MsgPackConstant.MIN_NEGATIVE_FIXINT)
			return parseNegativeFix(value);
		if(value >= MsgPackConstant.MIN_INT8)
			return parse8(value);
		if(value >= MsgPackConstant.MIN_INT16)
			return parse16(value);
		if(value >= MsgPackConstant.MIN_INT32)
			return parse32(value);
		return parse64(value);
	}
	
	private byte[] parseNegativeFix(long value) {
		return new byte[] {cast(0xe0 | value)};
	}
	
	private byte[] parse8(long value) {
		return EzyBytes.getBytes(0xd0, value, 1);
	}
	
	private byte[] parse16(long value) {
		return EzyBytes.getBytes(0xd1, value, 2);
	}
	
	private byte[] parse32(long value) {
		return EzyBytes.getBytes(0xd2, value, 4);
	}
	
	private byte[] parse64(long value) {
		return EzyBytes.getBytes(0xd3, value, 8);
	}
}

class DoubleSerializer extends EzyCastToByte {
	
	public byte[] serialize(double value) {
		return EzyBytes.getBytes(cast(0xcb), value);
	}
	
}

class FloatSerializer extends EzyCastToByte {
	
	public byte[] serialize(float value) {
		return EzyBytes.getBytes(cast(0xca), value);
	}
	
}