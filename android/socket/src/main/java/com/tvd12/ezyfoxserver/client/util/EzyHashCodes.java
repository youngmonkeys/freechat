package com.tvd12.ezyfoxserver.client.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EzyHashCodes {

	protected int prime = 31;
	protected int initial = 1;
	protected List<Object> values = new ArrayList<>();
	
	public int toHashCode() {
		int result = initial;
		for(Object value : values)
			result = result * prime + (value == null ? 43 : value.hashCode());
		return result;
	}
	
	public EzyHashCodes prime(int prime) {
		this.prime = prime;
		return this;
	}
	
	public EzyHashCodes initial(int initial) {
		this.initial = initial;
		return this;
	}
	
	public EzyHashCodes append(Object value) {
		this.values.add(value);
		return this;
	}
	
	public EzyHashCodes append(Object... values) {
		return append(Sets.newHashSet(values));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public EzyHashCodes append(Collection values) {
		this.values.addAll(values);
		return this;
	}
	
}
