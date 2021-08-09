package com.tvd12.ezyfoxserver.client.reflect;

import com.tvd12.ezyfoxserver.client.util.Sets;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;

public final class EzyClasses {

	private static final String DOT = ".";
	
	private EzyClasses() {
	}
	
	@SuppressWarnings("rawtypes")
	public static String getSimpleName(Class clazz) {
		String simpleName = clazz.getSimpleName();
		if(!simpleName.isEmpty()) return simpleName;
		String fullName = clazz.getName();
		if(!fullName.contains(DOT)) return fullName;
		return fullName.substring(fullName.lastIndexOf(DOT) + 1);
	}
	
	@SuppressWarnings("rawtypes")
	public static String getVariableName(Class clazz) {
		return getVariableName(clazz, "");
	}
	
	@SuppressWarnings("rawtypes")
	public static String getVariableName(Class clazz, String ignoredSuffix) {
		String name = getSimpleName(clazz);
		String vname = name.substring(0, 1).toLowerCase() + name.substring(1);
		if(ignoredSuffix.isEmpty() 
				|| !vname.endsWith(ignoredSuffix)
				|| vname.length() == ignoredSuffix.length())
			return vname;
		int endIndex = vname.indexOf(ignoredSuffix);
		return vname.substring(0, endIndex);
		
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(String className) {
		Class<T> clazz = getClass(className);
		return newInstance(clazz);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(String className, ClassLoader classLoader) {
		Class<T> clazz = getClass(className, classLoader);
		return newInstance(clazz);
	}
	
	@SuppressWarnings("rawtypes")
	public static Class getClass(String className) {
		try {
			Class clazz = Class.forName(className);
			return clazz;
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static Class getClass(String className, ClassLoader classLoader) {
		try {
			Class clazz = Class.forName(className, true, classLoader);
			return clazz;
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public static <T> T newInstance(Class<T> clazz) {
		try {
			T instance = clazz.newInstance();
			return instance;
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public static <T> T newInstance(Constructor<T> constructor, Object... arguments) {
		try {
			T instance = constructor.newInstance(arguments);
			return instance;
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... paramTypes) {
		try {
			Constructor<T> constructor = clazz.getDeclaredConstructor(paramTypes);
			return constructor;
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static Set<Class> flatSuperClasses(Class clazz) {
		return flatSuperClasses(clazz, false);
	}
	
	@SuppressWarnings("rawtypes")
	public static Set<Class> flatSuperClasses(Class clazz, boolean includeObject) {
		Set<Class> classes = new HashSet<>();
		Class superClass = clazz.getSuperclass();
		while(superClass != null) {
			if(superClass.equals(Object.class) && !includeObject )
				break;
			classes.add(superClass);
			superClass = superClass.getSuperclass();
		}
		return classes;
	}
	
	@SuppressWarnings("rawtypes")
	public static Set<Class> flatInterfaces(Class clazz) {
		Set<Class> classes = new HashSet<>();
		Class[] interfaces = clazz.getInterfaces();
		classes.addAll(Sets.newHashSet(interfaces));
		for(Class itf : interfaces)
			classes.addAll(flatInterfaces(itf));
		return classes;
	}
	
	@SuppressWarnings("rawtypes")
	public static Set<Class> flatSuperAndInterfaceClasses(Class clazz) {
		return flatSuperAndInterfaceClasses(clazz, false);
	}
	
	@SuppressWarnings("rawtypes")
	public static Set<Class> flatSuperAndInterfaceClasses(Class clazz, boolean includeObject) {
		Set<Class> classes = new HashSet<>();
		Set<Class> interfaces = flatInterfaces(clazz);
		Set<Class> superClasses = flatSuperClasses(clazz, includeObject);
		classes.addAll(interfaces);
		for(Class superClass : superClasses) {
			Set<Class> superAndInterfaceClasses = flatSuperAndInterfaceClasses(superClass, includeObject);
			classes.add(superClass);
			classes.addAll(superAndInterfaceClasses);
		}
		return classes;
	}
	
}
