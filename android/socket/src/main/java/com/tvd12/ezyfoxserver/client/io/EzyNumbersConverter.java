package com.tvd12.ezyfoxserver.client.io;

import com.tvd12.ezyfoxserver.client.function.EzyFunction;
import com.tvd12.ezyfoxserver.client.function.EzyNewArray;
import com.tvd12.ezyfoxserver.client.function.EzyNumber;

import java.util.Collection;

@SuppressWarnings({"rawtypes"})
public final class EzyNumbersConverter {

	private EzyNumbersConverter() {
	}
	
	// primitive
	public static byte[] numbersToPrimitiveBytes(Collection coll) {
		int index = 0;
		byte[] answer = new byte[coll.size()];
		for(Object obj : coll)
			answer[index ++] = ((Number)obj).byteValue();
		return answer;
	}
	
	public static char[] numbersToPrimitiveChars(Collection coll) {
		int index = 0;
		char[] answer = new char[coll.size()];
		for(Object obj : coll)
			answer[index ++] = objectToChar(obj);
		return answer;
	}
	
	public static double[] numbersToPrimitiveDoubles(Collection coll) {
		int index = 0;
		double[] answer = new double[coll.size()];
		for(Object obj : coll)
			answer[index ++] = ((Number)obj).doubleValue();
		return answer;
	}
	
	public static float[] numbersToPrimitiveFloats(Collection coll) {
		int index = 0;
		float[] answer = new float[coll.size()];
		for(Object obj : coll)
			answer[index ++] = ((Number)obj).floatValue();
		return answer;
	}
	
	public static int[] numbersToPrimitiveInts(Collection coll) {
		int index = 0;
		int[] answer = new int[coll.size()];
		for(Object obj : coll)
			answer[index ++] = ((Number)obj).intValue();
		return answer;
	}
	
	public static long[] numbersToPrimitiveLongs(Collection coll) {
		int index = 0;
		long[] answer = new long[coll.size()];
		for(Object obj : coll)
			answer[index ++] = ((Number)obj).longValue();
		return answer;
	}
	
	public static short[] numbersToPrimitiveShorts(Collection coll) {
		int index = 0;
		short[] answer = new short[coll.size()];
		for(Object obj : coll)
			answer[index ++] = ((Number)obj).shortValue();
		return answer;
	}
	
	// wrapper
	public static <I,O> O[] objectsToWrapperNumbers(
			Collection<I> coll, EzyNewArray<O> newer, EzyFunction<I, O> mapper) {
		int index = 0;
		O[] answer = newer.apply(coll.size());
		for(I item : coll) {
			O o = mapper.apply(item);
			answer[index ++] = o;
		}
		return answer;
	}
	
	public static <O> O[] numbersToWrapperNumbers(
			Collection<Number> coll, EzyNewArray<O> applier, final EzyNumber<O> converter) {
		return objectsToWrapperNumbers(coll, applier, new EzyFunction<Number, O>() {
			@Override
			public O apply(Number o) {
				return convertNumber(o, converter);
			}
		});
	}
	
	public static <I,O> O[] objectsToWrapperNumbers(
			I[] array, EzyNewArray<O> newer, EzyFunction<I, O> mapper) {
		O[] answer = newer.apply(array.length);
		for(int i = 0 ; i < array.length ; ++i) {
			O o = mapper.apply(array[i]);
			answer[i] = o;
		}
		return answer;
	}
	
	public static <O> O[] numbersToWrapperNumbers(
			Number[] numbers, EzyNewArray<O> applier, final EzyNumber<O> converter) {
		return objectsToWrapperNumbers(numbers, applier, new EzyFunction<Number, O>() {
			@Override
			public O apply(Number number) {
				return convertNumber(number, converter);
			}
		});
	}
	
	public static Byte[] numbersToWrapperBytes(Collection<Number> coll) {
		return numbersToWrapperNumbers(coll, new EzyNewArray<Byte>() {
			@Override
			public Byte[] apply(int size) {
				return new Byte[size];
			}
		}, new EzyNumber<Byte>() {
			@Override
			public Byte apply(Number number) {
				return number.byteValue();
			}
		});
	}
	
	public static Character[] numbersToWrapperChars(Collection coll) {
		int index = 0;
		Character[] answer = new Character[coll.size()];
		for(Object obj : coll)
			answer[index ++] = objectToChar(obj);
		return answer;
	}
	
	public static Double[] numbersToWrapperDoubles(Collection<Number> coll) {
		return numbersToWrapperNumbers(coll, new EzyNewArray<Double>() {
			@Override
			public Double[] apply(int size) {
				return new Double[size];
			}
		}, new EzyNumber<Double>() {
			@Override
			public Double apply(Number number) {
				return number.doubleValue();
			}
		});
	}
	
	public static Float[] numbersToWrapperFloats(Collection<Number> coll) {
		return numbersToWrapperNumbers(coll, new EzyNewArray<Float>() {
			@Override
			public Float[] apply(int size) {
				return new Float[size];
			}
		}, new EzyNumber<Float>() {
			@Override
			public Float apply(Number number) {
				return number.floatValue();
			}
		});
	}
	
	public static Integer[] numbersToWrapperInts(Collection<Number> coll) {
		return numbersToWrapperNumbers(coll, new EzyNewArray<Integer>() {
			@Override
			public Integer[] apply(int size) {
				return new Integer[size];
			}
		}, new EzyNumber<Integer>() {
			@Override
			public Integer apply(Number number) {
				return number.intValue();
			}
		});
	}
	
	public static Long[] numbersToWrapperLongs(Collection<Number> coll) {
		return numbersToWrapperNumbers(coll, new EzyNewArray<Long>() {
			@Override
			public Long[] apply(int size) {
				return new Long[size];
			}
		}, new EzyNumber<Long>() {
			@Override
			public Long apply(Number number) {
				return number.longValue();
			}
		});
	}
	
	public static Short[] numbersToWrapperShorts(Collection<Number> coll) {
		return numbersToWrapperNumbers(coll, new EzyNewArray<Short>() {
			@Override
			public Short[] apply(int size) {
				return new Short[size];
			}
		}, new EzyNumber<Short>() {
			@Override
			public Short apply(Number number) {
				return number.shortValue();
			}
		});
	}
	
	//=================================================
	public static Byte[] numbersToWrapperBytes(Number[] array) {
		return numbersToWrapperNumbers(array, new EzyNewArray<Byte>() {
			@Override
			public Byte[] apply(int size) {
				return new Byte[size];
			}
		}, new EzyNumber<Byte>() {
			@Override
			public Byte apply(Number number) {
				return number.byteValue();
			}
		});
	}
	
	public static Character[] numbersToWrapperChars(Object[] value) {
		Character[] answer = new Character[value.length];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = objectToChar(value[i]);
		return answer;
	}
	
	public static Double[] numbersToWrapperDoubles(Number[] array) {
		return numbersToWrapperNumbers(array, new EzyNewArray<Double>() {
			@Override
			public Double[] apply(int size) {
				return new Double[size];
			}
		}, new EzyNumber<Double>() {
			@Override
			public Double apply(Number number) {
				return number.doubleValue();
			}
		});
	}
	
	public static Float[] numbersToWrapperFloats(Number[] array) {
		return numbersToWrapperNumbers(array, new EzyNewArray<Float>() {
			@Override
			public Float[] apply(int size) {
				return new Float[size];
			}
		}, new EzyNumber<Float>() {
			@Override
			public Float apply(Number number) {
				return number.floatValue();
			}
		});
	}
	
	public static Integer[] numbersToWrapperInts(Number[] array) {
		return numbersToWrapperNumbers(array, new EzyNewArray<Integer>() {
			@Override
			public Integer[] apply(int size) {
				return new Integer[size];
			}
		}, new EzyNumber<Integer>() {
			@Override
			public Integer apply(Number number) {
				return number.intValue();
			}
		});
	}
	
	public static Long[] numbersToWrapperLongs(Number[] array) {
		return numbersToWrapperNumbers(array, new EzyNewArray<Long>() {
			@Override
			public Long[] apply(int size) {
				return new Long[size];
			}
		}, new EzyNumber<Long>() {
			@Override
			public Long apply(Number number) {
				return number.longValue();
			}
		});
	}
	
	public static Short[] numbersToWrapperShorts(Number[] array) {
		return numbersToWrapperNumbers(array, new EzyNewArray<Short>() {
			@Override
			public Short[] apply(int size) {
				return new Short[size];
			}
		}, new EzyNumber<Short>() {
			@Override
			public Short apply(Number number) {
				return number.shortValue();
			}
		});
	}
	//=================================================
	
	public static Byte numberToByte(Number number) {
		return convertNumber(number, new EzyNumber<Byte>() {
			@Override
			public Byte apply(Number number) {
				return number.byteValue();
			}
		});
	}

	public static Character numberToChar(Number number) {
		return convertNumber(number, new EzyNumber<Character>() {
			@Override
			public Character apply(Number number) {
				return (char)number.byteValue();
			}
		});
	}
	
	public static Character objectToChar(Object object) {
		return (object instanceof Number) 
				? numberToChar((Number)object) : (Character) object;
	}

	public static Double numberToDouble(Number number) {
		return convertNumber(number, new EzyNumber<Double>() {
			@Override
			public Double apply(Number number) {
				return number.doubleValue();
			}
		});
	}

	public static Float numberToFloat(Number number) {
		return convertNumber(number, new EzyNumber<Float>() {
			@Override
			public Float apply(Number number) {
				return number.floatValue();
			}
		});
	}

	public static Integer numberToInt(Number number) {
		return convertNumber(number, new EzyNumber<Integer>() {
			@Override
			public Integer apply(Number number) {
				return number.intValue();
			}
		});
	}

	public static Long numberToLong(Number number) {
		return convertNumber(number, new EzyNumber<Long>() {
			@Override
			public Long apply(Number number) {
				return number.longValue();
			}
		});
	}

	public static Short numberToShort(Number number) {
		return convertNumber(number, new EzyNumber<Short>() {
			@Override
			public Short apply(Number number) {
				return number.shortValue();
			}
		});
	}
	
	public static <T> T convertNumber(Object number, EzyNumber<T> converter) {
		return converter.apply((Number)number);
	}
	
	public static <T> T convertNumber(Number number, EzyNumber<T> converter) {
		return converter.apply(number);
	}
	
	//================ wrapper to primitive array ===========
	public static boolean[] boolArrayWrapperToPrimitive(Boolean[] value) {
		boolean[] answer = new boolean[value.length];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = value[i];
		return answer;
	}
	
	public static byte[] numbersToPrimitiveBytes(Number[] value) {
		byte[] answer = new byte[value.length];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = value[i].byteValue();
		return answer;
	}
	
	public static char[] numbersToPrimitiveChars(Object[] value) {
		char[] answer = new char[value.length];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = objectToChar(value[i]);
		return answer;
	}
	
	public static double[] numbersToPrimitiveDoubles(Number[] value) {
		double[] answer = new double[value.length];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = value[i].doubleValue();
		return answer;
	}
	
	public static float[] numbersToPrimitiveFloats(Number[] value) {
		float[] answer = new float[value.length];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = value[i].floatValue();
		return answer;
	}
	
	public static int[] numbersToPrimitiveInts(Number[] value) {
		int[] answer = new int[value.length];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = value[i].intValue();
		return answer;
	}
	
	public static long[] numbersToPrimitiveLongs(Number[] value) {
		long[] answer = new long[value.length];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = value[i].longValue();
		return answer;
	}
	
	public static short[] numbersToPrimitiveShorts(Number[] value) {
		short[] answer = new short[value.length];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = value[i].shortValue();
		return answer;
	}
	
	//====================== primitive to wrapper array =========
	public static Boolean[] boolArrayPrimitiveToWrapper(boolean[] value) {
		Boolean[] answer = new Boolean[value.length];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = value[i];
		return answer;
	}
	
	public static Byte[] byteArrayPrimitiveToWrapper(byte[] value) {
		Byte[] answer = new Byte[value.length];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = value[i];
		return answer;
	}
	
	public static Character[] charArrayPrimitiveToWrapper(char[] value) {
		Character[] answer = new Character[value.length];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = value[i];
		return answer;
	}
	
	public static Double[] doubleArrayPrimitiveToWrapper(double[] value) {
		Double[] answer = new Double[value.length];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = value[i];
		return answer;
	}
	
	public static Float[] floatArrayPrimitiveToWrapper(float[] value) {
		Float[] answer = new Float[value.length];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = value[i];
		return answer;
	}
	
	public static Integer[] intArrayPrimitiveToWrapper(int[] value) {
		Integer[] answer = new Integer[value.length];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = value[i];
		return answer;
	}
	
	public static Long[] longArrayPrimitiveToWrapper(long[] value) {
		Long[] answer = new Long[value.length];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = value[i];
		return answer;
	}
	
	public static Short[] shortArrayPrimitiveToWrapper(short[] value) {
		Short[] answer = new Short[value.length];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = value[i];
		return answer;
	}
	
	//====================== two-dimensions wrapper array to primitive array =========
	public static boolean[][] boolArraysWrapperToPrimitive(Boolean[][] value) {
		boolean[][] answer = new boolean[value.length][];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = boolArrayWrapperToPrimitive(value[i]);
		return answer;
	}
	
	public static byte[][] numbersToPrimitiveByteArrays(Number[][] value) {
		byte[][] answer = new byte[value.length][];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = numbersToPrimitiveBytes(value[i]);
		return answer;
	}
	
	public static char[][] numbersToPrimitiveCharArrays(Object[][] value) {
		char[][] answer = new char[value.length][];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = numbersToPrimitiveChars(value[i]);
		return answer;
	}
	
	public static double[][] numbersToPrimitiveDoubleArrays(Number[][] value) {
		double[][] answer = new double[value.length][];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = numbersToPrimitiveDoubles(value[i]);
		return answer;
	}
	
	public static float[][] numbersToPrimitiveFloatArrays(Number[][] value) {
		float[][] answer = new float[value.length][];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = numbersToPrimitiveFloats(value[i]);
		return answer;
	}
	
	public static int[][] numbersToPrimitiveIntArrays(Number[][] value) {
		int[][] answer = new int[value.length][];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = numbersToPrimitiveInts(value[i]);
		return answer;
	}
	
	public static long[][] numbersToPrimitiveLongArrays(Number[][] value) {
		long[][] answer = new long[value.length][];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = numbersToPrimitiveLongs(value[i]);
		return answer;
	}
	
	public static short[][] numbersToPrimitiveShortArrays(Number[][] value) {
		short[][] answer = new short[value.length][];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = numbersToPrimitiveShorts(value[i]);
		return answer;
	}
	
	//====================== two-dimensions wrapper array to primitive array =========
	public static Boolean[][] boolArraysPrimitiveToWrapper(boolean[][] value) {
		Boolean[][] answer = new Boolean[value.length][];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = boolArrayPrimitiveToWrapper(value[i]);
		return answer;
	}
	
	public static Byte[][] byteArraysPrimitiveToWrapper(byte[][] value) {
		Byte[][] answer = new Byte[value.length][];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = byteArrayPrimitiveToWrapper(value[i]);
		return answer;
	}
	
	public static Character[][] charArraysPrimitiveToWrapper(char[][] value) {
		Character[][] answer = new Character[value.length][];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = charArrayPrimitiveToWrapper(value[i]);
		return answer;
	}
	
	public static Double[][] doubleArraysPrimitiveToWrapper(double[][] value) {
		Double[][] answer = new Double[value.length][];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = doubleArrayPrimitiveToWrapper(value[i]);
		return answer;
	}
	
	public static Float[][] floatArraysPrimitiveToWrapper(float[][] value) {
		Float[][] answer = new Float[value.length][];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = floatArrayPrimitiveToWrapper(value[i]);
		return answer;
	}
	
	public static Integer[][] intArraysPrimitiveToWrapper(int[][] value) {
		Integer[][] answer = new Integer[value.length][];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = intArrayPrimitiveToWrapper(value[i]);
		return answer;
	}
	
	public static Long[][] longArraysPrimitiveToWrapper(long[][] value) {
		Long[][] answer = new Long[value.length][];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = longArrayPrimitiveToWrapper(value[i]);
		return answer;
	}
	
	public static Short[][] shortArraysPrimitiveToWrapper(short[][] value) {
		Short[][] answer = new Short[value.length][];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = shortArrayPrimitiveToWrapper(value[i]);
		return answer;
	}
	
	//============ numbers to wrapper arrays ==========
	public static Byte[][] numbersToWrapperByteArrays(Number[][] value) {
		Byte[][] answer = new Byte[value.length][];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = numbersToWrapperBytes(value[i]);
		return answer;
	}
	
	public static Character[][] numbersToWrapperCharArrays(Object[][] value) {
		Character[][] answer = new Character[value.length][];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = numbersToWrapperChars(value[i]);
		return answer;
	}
	
	public static Double[][] numbersToWrapperDoubleArrays(Number[][] value) {
		Double[][] answer = new Double[value.length][];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = numbersToWrapperDoubles(value[i]);
		return answer;
	}
	
	public static Float[][] numbersToWrapperFloatArrays(Number[][] value) {
		Float[][] answer = new Float[value.length][];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = numbersToWrapperFloats(value[i]);
		return answer;
	}
	
	public static Integer[][] numbersToWrapperIntArrays(Number[][] value) {
		Integer[][] answer = new Integer[value.length][];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = numbersToWrapperInts(value[i]);
		return answer;
	}
	
	public static Long[][] numbersToWrapperLongArrays(Number[][] value) {
		Long[][] answer = new Long[value.length][];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = numbersToWrapperLongs(value[i]);
		return answer;
	}
	
	public static Short[][] numbersToWrapperShortArrays(Number[][] value) {
		Short[][] answer = new Short[value.length][];
		for(int i = 0 ; i < value.length ; ++i)
			answer[i] = numbersToWrapperShorts(value[i]);
		return answer;
	}
}
