package com.tvd12.ezyfoxserver.client.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tavandung12 on 9/19/18.
 */

public final class ArrayUtils {

    private ArrayUtils() {
    }

    //===================== to primitive =====================
    public static boolean[] toPrimitive(Boolean[] array) {
        if(array == null) return null;
        boolean[] answer = new boolean[array.length];
        for (int i = 0 ; i < array.length ; ++i) answer[i] = array[i];
        return answer;
    }

    public static byte[] toPrimitive(Byte[] array) {
        if(array == null) return null;
        byte[] answer = new byte[array.length];
        for (int i = 0 ; i < array.length ; ++i) answer[i] = array[i];
        return answer;
    }

    public static char[] toPrimitive(Character[] array) {
        if(array == null) return null;
        char[] answer = new char[array.length];
        for (int i = 0 ; i < array.length ; ++i) answer[i] = array[i];
        return answer;
    }

    public static double[] toPrimitive(Double[] array) {
        if(array == null) return null;
        double[] answer = new double[array.length];
        for (int i = 0 ; i < array.length ; ++i) answer[i] = array[i];
        return answer;
    }

    public static float[] toPrimitive(Float[] array) {
        if(array == null) return null;
        float[] answer = new float[array.length];
        for (int i = 0 ; i < array.length ; ++i) answer[i] = array[i];
        return answer;
    }

    public static int[] toPrimitive(Integer[] array) {
        if(array == null) return null;
        int[] answer = new int[array.length];
        for (int i = 0 ; i < array.length ; ++i) answer[i] = array[i];
        return answer;
    }

    public static long[] toPrimitive(Long[] array) {
        if(array == null) return null;
        long[] answer = new long[array.length];
        for (int i = 0 ; i < array.length ; ++i) answer[i] = array[i];
        return answer;
    }

    public static short[] toPrimitive(Short[] array) {
        if(array == null) return null;
        short[] answer = new short[array.length];
        for (int i = 0 ; i < array.length ; ++i) answer[i] = array[i];
        return answer;
    }

    //===================== to wrapper =====================
    public static Boolean[] toWrapper(boolean[] array) {
        if(array == null) return null;
        Boolean[] answer = new Boolean[array.length];
        for (int i = 0 ; i < array.length ; ++i) answer[i] = array[i];
        return answer;
    }

    public static Byte[] toWrapper(byte[] array) {
        if(array == null) return null;
        Byte[] answer = new Byte[array.length];
        for (int i = 0 ; i < array.length ; ++i) answer[i] = array[i];
        return answer;
    }

    public static Character[] toWrapper(char[] array) {
        if(array == null) return null;
        Character[] answer = new Character[array.length];
        for (int i = 0 ; i < array.length ; ++i) answer[i] = array[i];
        return answer;
    }

    public static Double[] toWrapper(double[] array) {
        if(array == null) return null;
        Double[] answer = new Double[array.length];
        for (int i = 0 ; i < array.length ; ++i) answer[i] = array[i];
        return answer;
    }

    public static Float[] toWrapper(float[] array) {
        if(array == null) return null;
        Float[] answer = new Float[array.length];
        for (int i = 0 ; i < array.length ; ++i) answer[i] = array[i];
        return answer;
    }

    public static Integer[] toWrapper(int[] array) {
        if(array == null) return null;
        Integer[] answer = new Integer[array.length];
        for (int i = 0 ; i < array.length ; ++i) answer[i] = array[i];
        return answer;
    }

    public static Long[] toWrapper(long[] array) {
        if(array == null) return null;
        Long[] answer = new Long[array.length];
        for (int i = 0 ; i < array.length ; ++i) answer[i] = array[i];
        return answer;
    }

    public static Short[] toWrapper(short[] array) {
        if(array == null) return null;
        Short[] answer = new Short[array.length];
        for (int i = 0 ; i < array.length ; ++i) answer[i] = array[i];
        return answer;
    }

    //======================== list =======================
    public static List<Boolean> toList(boolean[] array) {
        if(array == null) return null;
        List<Boolean> list = new ArrayList<>();
        for(int i = 0 ; i < array.length ; ++i) list.add(array[i]);
        return list;
    }

    public static List<Byte> toList(byte[] array) {
        if(array == null) return null;
        List<Byte> list = new ArrayList<>();
        for(int i = 0 ; i < array.length ; ++i) list.add(array[i]);
        return list;
    }

    public static List<Character> toList(char[] array) {
        if(array == null) return null;
        List<Character> list = new ArrayList<>();
        for(int i = 0 ; i < array.length ; ++i) list.add(array[i]);
        return list;
    }

    public static List<Double> toList(double[] array) {
        if(array == null) return null;
        List<Double> list = new ArrayList<>();
        for(int i = 0 ; i < array.length ; ++i) list.add(array[i]);
        return list;
    }

    public static List<Float> toList(float[] array) {
        if(array == null) return null;
        List<Float> list = new ArrayList<>();
        for(int i = 0 ; i < array.length ; ++i) list.add(array[i]);
        return list;
    }

    public static List<Integer> toList(int[] array) {
        if(array == null) return null;
        List<Integer> list = new ArrayList<>();
        for(int i = 0 ; i < array.length ; ++i) list.add(array[i]);
        return list;
    }

    public static List<Long> toList(long[] array) {
        if(array == null) return null;
        List<Long> list = new ArrayList<>();
        for(int i = 0 ; i < array.length ; ++i) list.add(array[i]);
        return list;
    }

    public static List<Short> toList(short[] array) {
        if(array == null) return null;
        List<Short> list = new ArrayList<>();
        for(int i = 0 ; i < array.length ; ++i) list.add(array[i]);
        return list;
    }
}
