package com.tvd12.ezyfoxserver.client.codec;

import com.tvd12.ezyfoxserver.client.entity.EzyArray;
import com.tvd12.ezyfoxserver.client.entity.EzyArrayList;
import com.tvd12.ezyfoxserver.client.entity.EzyHashMap;
import com.tvd12.ezyfoxserver.client.entity.EzyObject;
import com.tvd12.ezyfoxserver.client.function.EzyParser;
import com.tvd12.ezyfoxserver.client.io.EzyByteBuffers;
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

import java.nio.ByteBuffer;
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
import java.util.Set;

public class MsgPackToByteBufferSerializer 
		extends EzyAbstractToByteBufferSerializer {

	protected IntSerializer intSerializer = new IntSerializer();
	protected FloatSerializer floatSerializer = new FloatSerializer();
	protected DoubleSerializer doubleSerializer = new DoubleSerializer();
	protected BinSizeSerializer binSizeSerializer = new BinSizeSerializer();
	protected MapSizeSerializer mapSizeSerializer = new MapSizeSerializer();
	protected ArraySizeSerializer arraySizeSerializer = new ArraySizeSerializer();
	protected StringSizeSerializer stringSizeSerializer = new StringSizeSerializer();

	@Override
	protected void addParsers(Map<Class<?>, EzyParser<Object, ByteBuffer>> parsers) {
		parsers.put(Boolean.class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseBoolean(input);
			}
		});
		parsers.put(Byte.class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseByte(input);
			}
		});
		parsers.put(Character.class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseChar(input);
			}
		});
		parsers.put(Double.class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseDouble(input);
			}
		});
		parsers.put(Float.class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseFloat(input);
			}
		});
		parsers.put(Integer.class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseInt(input);
			}
		});
		parsers.put(Long.class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseInt(input);
			}
		});
		parsers.put(Short.class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseShort(input);
			}
		});
		parsers.put(String.class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseString(input);
			}
		});
		
		parsers.put(boolean[].class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parsePrimitiveBooleans(input);
			}
		});
		parsers.put(byte[].class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseBin(input);
			}
		});
		parsers.put(char[].class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parsePrimitiveChars(input);
			}
		});
		parsers.put(double[].class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parsePrimitiveDoubles(input);
			}
		});
		parsers.put(float[].class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parsePrimitiveFloats(input);
			}
		});
		parsers.put(int[].class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parsePrimitiveInts(input);
			}
		});
		parsers.put(long[].class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parsePrimitiveLongs(input);
			}
		});
		parsers.put(short[].class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parsePrimitiveShorts(input);
			}
		});
		parsers.put(String[].class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseStrings(input);
			}
		});
		
		parsers.put(Byte[].class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseWrapperBytes(input);
			}
		});
		parsers.put(Boolean[].class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseWrapperBooleans(input);
			}
		});
		parsers.put(Character[].class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseWrapperChars(input);
			}
		});
		parsers.put(Double[].class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseWrapperDoubles(input);
			}
		});
		parsers.put(Float[].class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseWrapperFloats(input);
			}
		});
		parsers.put(Integer[].class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseWrapperInts(input);
			}
		});
		parsers.put(Long[].class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseWrapperLongs(input);
			}
		});
		parsers.put(Short[].class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseWrapperShorts(input);
			}
		});
		
		parsers.put(Map.class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseMap(input);
			}
		});
		parsers.put(AbstractMap.class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseMap(input);
			}
		});
		parsers.put(HashMap.class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseMap(input);
			}
		});
		parsers.put(EzyObject.class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseObject(input);
			}
		});
		parsers.put(EzyHashMap.class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseObject(input);
			}
		});
		parsers.put(EzyArray.class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseArray(input);
			}
		});
		parsers.put(EzyArrayList.class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseArray(input);
			}
		});
		parsers.put(Collection.class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseCollection(input);
			}
		});
		parsers.put(AbstractCollection.class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseCollection(input);
			}
		});
		parsers.put(Set.class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseCollection(input);
			}
		});
		parsers.put(AbstractSet.class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseCollection(input);
			}
		});
		parsers.put(List.class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseCollection(input);
			}
		});
		parsers.put(AbstractList.class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseCollection(input);
			}
		});
		parsers.put(HashSet.class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseCollection(input);
			}
		});
		parsers.put(ArrayList.class, new EzyParser<Object, ByteBuffer>() {
			@Override
			public ByteBuffer parse(Object input) {
				return parseCollection(input);
			}
		});
	}
	
	//
	protected ByteBuffer parsePrimitiveBooleans(Object array) {
		return parseBooleans((boolean[])array);
	}
	
	protected ByteBuffer parsePrimitiveChars(Object array) {
		return parseChars((char[])array);
	}
	
	protected ByteBuffer parsePrimitiveDoubles(Object array) {
		return parseDoubles((double[])array);
	}
	
	protected ByteBuffer parsePrimitiveFloats(Object array) {
		return parseFloats((float[])array);
	}
	
	protected ByteBuffer parsePrimitiveInts(Object array) {
		return parseInts((int[])array);
	}
	
	protected ByteBuffer parsePrimitiveLongs(Object array) {
		return parseLongs((long[])array);
	}
	
	protected ByteBuffer parsePrimitiveShorts(Object array) {
		return parseShorts((short[])array);
	}
	
	protected ByteBuffer parseStrings(Object array) {
		return parseStrings((String[])array);
	}
	//
	
	protected ByteBuffer parseBooleans(boolean[] array) {
		return parseArray(EzyBoolsIterator.wrap(array), array.length);
	}
	
	protected ByteBuffer parseChars(char[] array) {
		return parseBin(EzyDataConverter.charArrayToByteArray(array));
	}
	
	protected ByteBuffer parseDoubles(double[] array) {
		return parseArray(EzyDoublesIterator.wrap(array), array.length);
	}
	
	protected ByteBuffer parseFloats(float[] array) {
		return parseArray(EzyFloatsIterator.wrap(array), array.length);
	}
	
	protected ByteBuffer parseInts(int[] array) {
		return parseArray(EzyIntsIterator.wrap(array), array.length);
	}
	
	protected ByteBuffer parseLongs(long[] array) {
		return parseArray(EzyLongsIterator.wrap(array), array.length);
	}
	
	protected ByteBuffer parseShorts(short[] array) {
		return parseArray(EzyShortsIterator.wrap(array), array.length);
	}
	
	protected ByteBuffer parseStrings(String[] array) {
		return parseArray(EzyStringsIterator.wrap(array), array.length);
	}
	
	//=============
	protected ByteBuffer parseWrapperBooleans(Object array) {
		return parseBooleans((Boolean[])array);
	}
	
	protected ByteBuffer parseWrapperBytes(Object array) {
		return parseBytes((Byte[])array);
	}
	
	protected ByteBuffer parseWrapperChars(Object array) {
		return parseChars((Character[])array);
	}
	
	protected ByteBuffer parseWrapperDoubles(Object array) {
		return parseDoubles((Double[])array);
	}
	
	protected ByteBuffer parseWrapperFloats(Object array) {
		return parseFloats((Float[])array);
	}
	
	protected ByteBuffer parseWrapperInts(Object array) {
		return parseInts((Integer[])array);
	}
	
	protected ByteBuffer parseWrapperLongs(Object array) {
		return parseLongs((Long[])array);
	}
	
	protected ByteBuffer parseWrapperShorts(Object array) {
		return parseShorts((Short[])array);
	}
	//
	//=============
	
	//
	protected ByteBuffer parseBooleans(Boolean[] array) {
		return parseArray(EzyWrapperIterator.wrap(array), array.length);
	}
	
	protected ByteBuffer parseBytes(Byte[] array) {
		return parseBin(EzyDataConverter.toPrimitiveByteArray(array));
	}
	
	protected ByteBuffer parseChars(Character[] array) {
		return parseBin(EzyDataConverter.charWrapperArrayToPrimitiveByteArray(array));
	}
	
	protected ByteBuffer parseDoubles(Double[] array) {
		return parseArray(EzyWrapperIterator.wrap(array), array.length);
	}
	
	protected ByteBuffer parseFloats(Float[] array) {
		return parseArray(EzyWrapperIterator.wrap(array), array.length);
	}
	
	protected ByteBuffer parseInts(Integer[] array) {
		return parseArray(EzyWrapperIterator.wrap(array), array.length);
	}
	
	protected ByteBuffer parseLongs(Long[] array) {
		return parseArray(EzyWrapperIterator.wrap(array), array.length);
	}
	
	protected ByteBuffer parseShorts(Short[] array) {
		return parseArray(EzyWrapperIterator.wrap(array), array.length);
	}
	//
	
	protected ByteBuffer parseBoolean(Object value) {
		return parseBoolean((Boolean)value);
	}
	
	protected ByteBuffer parseBoolean(Boolean value) {
		return value ? parseTrue() : parseFalse();
	}
	
	protected ByteBuffer parseFalse() {
		return ByteBuffer.wrap(new byte[] {cast(0xc2)});
	}
	
	protected ByteBuffer parseTrue() {
		return ByteBuffer.wrap(new byte[] {cast(0xc3)});
	}
	
	protected ByteBuffer parseByte(Object value) {
		return parseByte((Byte)value);
	}
	
	protected ByteBuffer parseByte(Byte value) {
		return parseInt(value.intValue());
	}
	
	protected ByteBuffer parseChar(Object value) {
		return parseChar((Character)value);
	}
	
	protected ByteBuffer parseChar(Character value) {
		return parseByte((byte)value.charValue());
	}
	
	protected ByteBuffer parseDouble(Object value) {
		return parseDouble((Double)value);
	}
	
	protected ByteBuffer parseDouble(Double value) {
		return ByteBuffer.wrap(doubleSerializer.serialize(value));
	}
	
	protected ByteBuffer parseFloat(Object value) {
		return parseFloat((Float)value);
	}
	
	protected ByteBuffer parseFloat(Float value) {
		return ByteBuffer.wrap(floatSerializer.serialize(value));
	}
	
	protected ByteBuffer parseInt(Object value) {
		return ByteBuffer.wrap(intSerializer.serialize(((Number)value).longValue()));
	}
	
	protected ByteBuffer parseShort(Object value) {
		return parseShort((Short)value);
	}
	
	protected ByteBuffer parseShort(Short value) {
		return parseInt(value.intValue());
	}
	
	protected ByteBuffer parseString(Object string) {
		return parseString((String)string);
	}
	
	@SuppressWarnings({ "rawtypes" })
	protected ByteBuffer parseMap(Object map) {
		return parseMap((Map)map);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected ByteBuffer parseMap(Map map) {
		return parseEntries(map.entrySet());
	}
	
	protected ByteBuffer parseObject(Object obj) {
		return parseEntries(((EzyObject)obj).entrySet());
	}
	
	@SuppressWarnings({ "rawtypes" })
	protected ByteBuffer parseCollection(Object coll) {
		return parseCollection((Collection)coll);
	}
	
	protected ByteBuffer parseArray(Object array) {
		return parseArray((EzyArray)array);
	}
	
	@SuppressWarnings({ "rawtypes" })
	protected ByteBuffer parseCollection(Collection coll) {
		return parseIterable(coll, coll.size());
	}
	
	protected ByteBuffer parseArray(EzyArray array) {
		return parseArray(array.iterator(), array.size());
	}
	
	protected ByteBuffer parseNil() {
		return ByteBuffer.wrap(new byte[] {cast(0xc0)});
	}
	
	protected ByteBuffer parseBin(Object bin) {
		return parseBin((byte[])bin);
	}
	
	protected ByteBuffer parseBin(byte[] bin) {
		ByteBuffer[] buffers = new ByteBuffer[2];
		buffers[0] = ByteBuffer.wrap(parseBinSize(bin.length));
		buffers[1] = ByteBuffer.wrap(bin);
		return EzyByteBuffers.merge(buffers);
	}
	
	protected byte[] parseBinSize(int size) {
		return binSizeSerializer.serialize(size);
	}
	
	protected ByteBuffer parseString(String string) {
		ByteBuffer[] buffers = new ByteBuffer[2];
		buffers[1] = ByteBuffer.wrap(EzyStrings.getUtfBytes(string));
		buffers[0] = ByteBuffer.wrap(parseStringSize(buffers[1].remaining()));
		return EzyByteBuffers.merge(buffers);
	}
	
	protected byte[] parseStringSize(int size) {
		return stringSizeSerializer.serialize(size);
	}
	
	@SuppressWarnings("rawtypes")
	protected ByteBuffer parseIterable(Iterable iterable, int size) {
		return parseArray(iterable.iterator(), size);
	}
	
	@SuppressWarnings("rawtypes")
	protected ByteBuffer parseArray(Iterator iterator, int size) {
		int index = 1;
		ByteBuffer[] buffers = new ByteBuffer[size + 1];
		buffers[0] = ByteBuffer.wrap(parseArraySize(size));
		while(iterator.hasNext())
			buffers[index ++] = write(iterator.next());
		return EzyByteBuffers.merge(buffers);
	}
	
	protected byte[] parseArraySize(int size) {
		return arraySizeSerializer.serialize(size);
	}
	
	protected ByteBuffer parseEntries(Set<Map.Entry<Object, Object>> entries) {
		int index = 1;
		int size = entries.size();
		ByteBuffer[] buffers = new ByteBuffer[size * 2 + 1];
		buffers[0] = ByteBuffer.wrap(parseMapSize(size));
		for(Map.Entry<Object, Object> e : entries) {
			buffers[index++] = write(e.getKey());
			buffers[index++] = write(e.getValue());
		}
		return EzyByteBuffers.merge(buffers);
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