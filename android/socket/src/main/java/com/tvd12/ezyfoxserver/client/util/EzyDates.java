package com.tvd12.ezyfoxserver.client.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class EzyDates {
	
	private EzyDates() {
	}
	
	// =================== java 7 ===============
	public static String format(long millis) {
		return format(millis, getPattern());
	}
	
	public static String format(Date date) {
		return format(date, getPattern());
	}
	
	public static Date parse(String source) {
		return parse(source, getPattern());
	}
	
	public static String format(long millis, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String answer = format.format(millis);
		return answer;
	}
	
	public static String format(Date date, String pattern) {
		if(date == null)
			return null;
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String answer = format.format(date);
		return answer;
	}
	
	public static Date parse(String source, String pattern) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			Date date = format.parse(source);
			return date;
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public static String getPattern() {
		return "yyyy-MM-dd'T'HH:mm:ss:SSS";
	}
	
	// =========================================
	public static boolean between(Date date, Date before, Date after) {
		long time = date.getTime();
		return time >= before.getTime() && time <= after.getTime();
	}
	
}
