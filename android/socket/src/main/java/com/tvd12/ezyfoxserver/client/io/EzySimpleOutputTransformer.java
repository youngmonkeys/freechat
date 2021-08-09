package com.tvd12.ezyfoxserver.client.io;

import com.tvd12.ezyfoxserver.client.entity.EzyArray;
import com.tvd12.ezyfoxserver.client.entity.EzyObject;
import com.tvd12.ezyfoxserver.client.function.EzyToObject;
import com.tvd12.ezyfoxserver.client.reflect.EzyClasses;
import com.tvd12.ezyfoxserver.client.sercurity.EzyBase64;
import com.tvd12.ezyfoxserver.client.util.EzyDates;
import com.tvd12.ezyfoxserver.client.logger.EzyLogger;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.tvd12.ezyfoxserver.client.io.EzyDataConverter.byteArrayToCharArray;
import static com.tvd12.ezyfoxserver.client.io.EzyDataConverter.collectionToPrimitiveBoolArray;
import static com.tvd12.ezyfoxserver.client.io.EzyDataConverter.collectionToPrimitiveDoubleArray;
import static com.tvd12.ezyfoxserver.client.io.EzyDataConverter.collectionToPrimitiveFloatArray;
import static com.tvd12.ezyfoxserver.client.io.EzyDataConverter.collectionToPrimitiveIntArray;
import static com.tvd12.ezyfoxserver.client.io.EzyDataConverter.collectionToPrimitiveLongArray;
import static com.tvd12.ezyfoxserver.client.io.EzyDataConverter.collectionToPrimitiveShortArray;
import static com.tvd12.ezyfoxserver.client.io.EzyDataConverter.collectionToStringArray;
import static com.tvd12.ezyfoxserver.client.io.EzyDataConverter.collectionToWrapperBoolArray;
import static com.tvd12.ezyfoxserver.client.io.EzyDataConverter.collectionToWrapperDoubleArray;
import static com.tvd12.ezyfoxserver.client.io.EzyDataConverter.collectionToWrapperFloatArray;
import static com.tvd12.ezyfoxserver.client.io.EzyDataConverter.collectionToWrapperIntArray;
import static com.tvd12.ezyfoxserver.client.io.EzyDataConverter.collectionToWrapperLongArray;
import static com.tvd12.ezyfoxserver.client.io.EzyDataConverter.collectionToWrapperShortArray;
import static com.tvd12.ezyfoxserver.client.io.EzyDataConverter.toByteWrapperArray;
import static com.tvd12.ezyfoxserver.client.io.EzyDataConverter.toCharWrapperArray;

public class EzySimpleOutputTransformer implements EzyOutputTransformer {
	
	@SuppressWarnings("rawtypes")
	protected final Map<Class, EzyToObject> transformers = defaultTransformers();
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object transform(Object value, Class type) {
		return  value == null 
				? transformNullValue(value) 
				: transformNonNullValue(value, type);
	}
	
	protected Object transformNullValue(Object value) {
		return value;
	}
	
	@SuppressWarnings("rawtypes")
	protected Object transformNonNullValue(Object value, Class type) {
		return transformNonNullValue(value, type, transformers);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object transformNonNullValue(
			Object value, Class type, Map<Class, EzyToObject> transformers) {
		EzyToObject trans = transformers.get(type);
		if(trans != null)
			return trans.transform(value);
		if(type.isEnum())
			return Enum.valueOf(type, value.toString());
		return value;
	}
	
	//tank
	@SuppressWarnings("rawtypes")
	private Map<Class, EzyToObject> 
			defaultTransformers() {
		Map<Class, EzyToObject> answer = new ConcurrentHashMap<>();
		addOtherTransformers(answer);
		addEntityTransformers(answer);
		addWrapperTransformers(answer);
		addPrimitiveTransformers(answer);
		addWrapperArrayTransformers(answer);
		addPrimitiveArrayTransformers(answer);
		addTwoDimensionsWrapperArrayTransformers(answer);
		addTwoDimensionsPrimitiveArrayTransformers(answer);
		return answer;
	}
	
	protected EzyObject[] toObjectArray(EzyArray value) {
		EzyObject[] answer = new EzyObject[value.size()];
		for(int i = 0 ; i < value.size() ; ++i) 
			answer[i] = value.get(i, EzyObject.class);
		return answer;
	}
	
	@SuppressWarnings("rawtypes")
	protected void addPrimitiveTransformers(Map<Class, EzyToObject> answer) {
		answer.put(boolean.class, new EzyToObject<Boolean>() {
			@Override
			public Object transform(Boolean value) {
				return value;
			}
		});
		answer.put(byte.class, new EzyToObject<Number>() {
			@Override
			public Object transform(Number value) {
				return value.byteValue();
			}
		});
		answer.put(char.class, new EzyToObject<Object>() {
			@Override
			public Object transform(Object value) {
				return EzyNumbersConverter.objectToChar(value);
			}
		});
		answer.put(double.class, new EzyToObject<Number>() {
			@Override
			public Object transform(Number value) {
				return value.doubleValue();
			}
		});
		answer.put(float.class, new EzyToObject<Number>() {
			@Override
			public Object transform(Number value) {
				return value.floatValue();
			}
		});
		answer.put(int.class, new EzyToObject<Number>() {
			@Override
			public Object transform(Number value) {
				return value.intValue();
			}
		});
		answer.put(long.class, new EzyToObject<Number>() {
			@Override
			public Object transform(Number value) {
				return value.longValue();
			}
		});
		answer.put(short.class, new EzyToObject<Number>() {
			@Override
			public Object transform(Number value) {
				return value.shortValue();
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	protected void addWrapperTransformers(Map<Class, EzyToObject> answer) {
		answer.put(Boolean.class, new EzyToObject<Boolean>() {
			@Override
			public Object transform(Boolean value) {
				return value;
			}
		});
		answer.put(Byte.class, new EzyToObject<Number>() {
			@Override
			public Object transform(Number value) {
				return value.byteValue();
			}
		});
		answer.put(Character.class, new EzyToObject<Object>() {
			@Override
			public Object transform(Object value) {
				return EzyNumbersConverter.objectToChar(value);
			}
		});
		answer.put(Double.class, new EzyToObject<Number>() {
			@Override
			public Object transform(Number value) {
				return value.doubleValue();
			}
		});
		answer.put(Float.class, new EzyToObject<Number>() {
			@Override
			public Object transform(Number value) {
				return value.floatValue();
			}
		});
		answer.put(Integer.class, new EzyToObject<Number>() {
			@Override
			public Object transform(Number value) {
				return value.intValue();
			}
		});
		answer.put(Long.class, new EzyToObject<Number>() {
			@Override
			public Object transform(Number value) {
				return value.longValue();
			}
		});
		answer.put(Short.class, new EzyToObject<Number>() {
			@Override
			public Object transform(Number value) {
				return value.shortValue();
			}
		});
		answer.put(String.class, new EzyToObject<String>() {
			@Override
			public Object transform(String value) {
				return value;
			}
		});
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	protected void addPrimitiveArrayTransformers(Map<Class, EzyToObject> answer) {
		answer.put(boolean[].class, new EzyToObject<Object>() {
			@Override
			public Object transform(Object value) {
				if(value instanceof Collection)
					return collectionToPrimitiveBoolArray((Collection)value);
				return ((EzyArray)value).toArray(boolean.class);
			}
		});
		answer.put(byte[].class, new EzyToObject<Object>() {
			@Override
			public Object transform(Object value) {
				if(value instanceof byte[])
					return value;
				if(value instanceof String)
					return EzyBase64.decode((String)value);
				return ((EzyArray)value).toArray(byte.class);
			}
		});
		answer.put(char[].class, new EzyToObject<Object>() {
			@Override
			public Object transform(Object value) {
				if(value instanceof byte[])
					return byteArrayToCharArray((byte[])value);
				if(value instanceof String)
					return ((String)value).toCharArray();
				return ((EzyArray)value).toArray(char.class);
			}
		});
		answer.put(double[].class, new EzyToObject<Object>() {
			@Override
			public Object transform(Object value) {
				if(value instanceof Collection)
					return collectionToPrimitiveDoubleArray((Collection)value);
				return ((EzyArray)value).toArray(double.class);
			}
		});
		answer.put(float[].class, new EzyToObject<Object>() {
			@Override
			public Object transform(Object value) {
				if(value instanceof Collection)
					return collectionToPrimitiveFloatArray((Collection)value);
				return ((EzyArray)value).toArray(float.class);
			}
		});
		answer.put(int[].class, new EzyToObject<Object>() {
			@Override
			public Object transform(Object value) {
				if(value instanceof Collection)
					return collectionToPrimitiveIntArray((Collection)value);
				return ((EzyArray)value).toArray(int.class);
			}
		});
		answer.put(long[].class, new EzyToObject<Object>() {
			@Override
			public Object transform(Object value) {
				if(value instanceof Collection)
					return collectionToPrimitiveLongArray((Collection)value);
				return ((EzyArray)value).toArray(long.class);
			}
		});
		answer.put(short[].class, new EzyToObject<Object>() {
			@Override
			public Object transform(Object value) {
				if(value instanceof Collection)
					return collectionToPrimitiveShortArray((Collection)value);
				return ((EzyArray)value).toArray(short.class);
			}
		});
		answer.put(String[].class, new EzyToObject<Object>() {
			@Override
			public Object transform(Object value) {
				if(value instanceof Collection)
					return collectionToStringArray((Collection)value);
				return ((EzyArray)value).toArray(String.class);
			}
		});
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	protected void addWrapperArrayTransformers(Map<Class, EzyToObject> answer) {
		answer.put(Boolean[].class, new EzyToObject<Object>() {
			@Override
			public Object transform(Object value) {
				if(value instanceof Collection)
					return collectionToWrapperBoolArray((Collection)value);
				return ((EzyArray)value).toArray(Boolean.class);
			}
		});
		answer.put(Byte[].class, new EzyToObject<Object>() {
			@Override
			public Object transform(Object value) {
				if(value instanceof byte[])
					return toByteWrapperArray((byte[])value);
				return ((EzyArray)value).toArray(Byte.class);
			}
		});
		answer.put(Character[].class, new EzyToObject<Object>() {
			@Override
			public Object transform(Object value) {
				if(value instanceof byte[])
					return toCharWrapperArray((byte[])value);
				return ((EzyArray)value).toArray(Character.class);
			}
		});
		answer.put(Double[].class, new EzyToObject<Object>() {
			@Override
			public Object transform(Object value) {
				if(value instanceof Collection)
					return collectionToWrapperDoubleArray((Collection)value);
				return ((EzyArray)value).toArray(Double.class);
			}
		});
		answer.put(Float[].class, new EzyToObject<Object>() {
			@Override
			public Object transform(Object value) {
				if(value instanceof Collection)
					return collectionToWrapperFloatArray((Collection)value);
				return ((EzyArray)value).toArray(Float.class);
			}
		});
		answer.put(Integer[].class, new EzyToObject<Object>() {
			@Override
			public Object transform(Object value) {
				if(value instanceof Collection)
					return collectionToWrapperIntArray((Collection)value);
				return ((EzyArray)value).toArray(Integer.class);
			}
		});
		answer.put(Long[].class, new EzyToObject<Object>() {
			@Override
			public Object transform(Object value) {
				if(value instanceof Collection)
					return collectionToWrapperLongArray((Collection)value);
				return ((EzyArray)value).toArray(Long.class);
			}
		});
		answer.put(Short[].class, new EzyToObject<Object>() {
			@Override
			public Object transform(Object value) {
				if(value instanceof Collection)
					return collectionToWrapperShortArray((Collection)value);
				return ((EzyArray)value).toArray(Short.class);
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	protected void addOtherTransformers(Map<Class, EzyToObject> answer) {
		answer.put(Date.class, new EzyToObject<String>() {
			@Override
			public Object transform(String value) {
				try {
					return EzyDates.parse(value);
				} catch (Exception e) {
					EzyLogger.warn("value = " + value + " is invalid", e);
				}
				return null;
			}
		});
		
		//other
		answer.put(Class.class, new EzyToObject<String>() {
			@Override
			public Object transform(String value) {
				try {
					return EzyClasses.getClass(value);
				} catch (Exception e) {
					EzyLogger.warn("value = " + value + " is invalid", e);
				}
				return null;
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	protected void addEntityTransformers(Map<Class, EzyToObject> answer) {
		answer.put(EzyObject[].class, new EzyToObject<EzyArray>() {
			@Override
			public Object transform(EzyArray value) {
				return toObjectArray(value);
			}
		});
		
		answer.put(EzyObject[][].class, new EzyToObject<EzyArray>() {
			@Override
			public Object transform(EzyArray value) {
				EzyObject[][] answer = new EzyObject[value.size()][];
				for(int i = 0 ; i < value.size() ; ++i)
					answer[i] = toObjectArray(value.get(i, EzyArray.class));
				return answer;
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	protected void addTwoDimensionsPrimitiveArrayTransformers(Map<Class, EzyToObject> answer) {
		answer.put(boolean[][].class, new EzyToObject<EzyArray>() {
			@Override
			public Object transform(EzyArray value) {
				return value.toArray(boolean[].class);
			}
		});
		answer.put(byte[][].class, new EzyToObject<EzyArray>() {
			@Override
			public Object transform(EzyArray value) {
				return value.toArray(byte[].class);
			}
		});
		answer.put(char[][].class, new EzyToObject<EzyArray>() {
			@Override
			public Object transform(EzyArray value) {
				return value.toArray(char[].class);
			}
		});
		answer.put(double[][].class, new EzyToObject<EzyArray>() {
			@Override
			public Object transform(EzyArray value) {
				return value.toArray(double[].class);
			}
		});
		answer.put(float[][].class, new EzyToObject<EzyArray>() {
			@Override
			public Object transform(EzyArray value) {
				return value.toArray(float[].class);
			}
		});
		answer.put(int[][].class, new EzyToObject<EzyArray>() {
			@Override
			public Object transform(EzyArray value) {
				return value.toArray(int[].class);
			}
		});
		answer.put(long[][].class, new EzyToObject<EzyArray>() {
			@Override
			public Object transform(EzyArray value) {
				return value.toArray(long[].class);
			}
		});
		answer.put(short[][].class, new EzyToObject<EzyArray>() {
			@Override
			public Object transform(EzyArray value) {
				return value.toArray(short[].class);
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	protected void addTwoDimensionsWrapperArrayTransformers(Map<Class, EzyToObject> answer) {
		answer.put(Boolean[][].class, new EzyToObject<EzyArray>() {
			@Override
			public Object transform(EzyArray value) {
				return value.toArray(Boolean[].class);
			}
		});
		answer.put(Byte[][].class, new EzyToObject<EzyArray>() {
			@Override
			public Object transform(EzyArray value) {
				return value.toArray(Byte[].class);
			}
		});
		answer.put(Character[][].class, new EzyToObject<EzyArray>() {
			@Override
			public Object transform(EzyArray value) {
				return value.toArray(Character[].class);
			}
		});
		answer.put(Double[][].class, new EzyToObject<EzyArray>() {
			@Override
			public Object transform(EzyArray value) {
				return value.toArray(Double[].class);
			}
		});
		answer.put(Float[][].class, new EzyToObject<EzyArray>() {
			@Override
			public Object transform(EzyArray value) {
				return value.toArray(Float[].class);
			}
		});
		answer.put(Integer[][].class, new EzyToObject<EzyArray>() {
			@Override
			public Object transform(EzyArray value) {
				return value.toArray(Integer[].class);
			}
		});
		answer.put(Long[][].class, new EzyToObject<EzyArray>() {
			@Override
			public Object transform(EzyArray value) {
				return value.toArray(Long[].class);
			}
		});
		answer.put(Short[][].class, new EzyToObject<EzyArray>() {
			@Override
			public Object transform(EzyArray value) {
				return value.toArray(Short[].class);
			}
		});
		answer.put(String[][].class, new EzyToObject<EzyArray>() {
			@Override
			public Object transform(EzyArray value) {
				return value.toArray(String[].class);
			}
		});
	}
	
}
