package com.tvd12.ezyfoxserver.client.factory;

import com.tvd12.ezyfoxserver.client.builder.EzyArrayBuilder;
import com.tvd12.ezyfoxserver.client.builder.EzyObjectBuilder;
import com.tvd12.ezyfoxserver.client.builder.EzySimpleArrayBuilder;
import com.tvd12.ezyfoxserver.client.builder.EzySimpleObjectBuilder;
import com.tvd12.ezyfoxserver.client.entity.EzyArray;
import com.tvd12.ezyfoxserver.client.entity.EzyArrayList;
import com.tvd12.ezyfoxserver.client.entity.EzyHashMap;
import com.tvd12.ezyfoxserver.client.entity.EzyObject;
import com.tvd12.ezyfoxserver.client.function.EzySafeSupplier;
import com.tvd12.ezyfoxserver.client.io.EzyCollectionConverter;
import com.tvd12.ezyfoxserver.client.io.EzyInputTransformer;
import com.tvd12.ezyfoxserver.client.io.EzyOutputTransformer;
import com.tvd12.ezyfoxserver.client.io.EzySingletonCollectionConverter;
import com.tvd12.ezyfoxserver.client.io.EzySingletonInputTransformer;
import com.tvd12.ezyfoxserver.client.io.EzySingletonOutputTransformer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class EzySimpleEntityCreator implements EzyEntityCreator {

	@SuppressWarnings("rawtypes")
	private final Map<Class, EzySafeSupplier> suppliers;
	private static final EzySimpleEntityCreator INSTANCE = new EzySimpleEntityCreator();
	
	private EzySimpleEntityCreator() {
		this.suppliers = defaultSuppliers();
	}
	
	public static EzySimpleEntityCreator getInstance() {
		return INSTANCE;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T create(Class<T> productType) {
		EzySafeSupplier<T> supplier = suppliers.get(productType);
		T answer = supplier.get();
		return answer;
	}
	
	public EzyObject newObject() {
		return new EzyHashMap(
				getInputTransformer(), 
				getOutputTransformer());
	}
	
	public EzyArray newArray() {
		return new EzyArrayList(
				getInputTransformer(),
				getOutputTransformer(),
				getCollectionConverter());
	}
	
	public EzyObjectBuilder newObjectBuilder() {
		return new EzySimpleObjectBuilder(
				getInputTransformer(),
				getOutputTransformer());
	}
	
	public EzyArrayBuilder newArrayBuilder() {
		return new EzySimpleArrayBuilder(
				getInputTransformer(),
				getOutputTransformer(),
				getCollectionConverter());
	}
	
	protected EzyInputTransformer getInputTransformer() {
		return EzySingletonInputTransformer.getInstance();
	}
	
	protected EzyOutputTransformer getOutputTransformer() {
		return EzySingletonOutputTransformer.getInstance();
	}
	
	protected EzyCollectionConverter getCollectionConverter() {
		return EzySingletonCollectionConverter.getInstance();
	}
	
	@SuppressWarnings("rawtypes")
	private final Map<Class, EzySafeSupplier> defaultSuppliers() {
		Map<Class, EzySafeSupplier> answer = new ConcurrentHashMap<>();
		answer.put(EzyObject.class, new EzySafeSupplier() {
			@Override
			public Object get() {
				return newObject();
			}
		});
		answer.put(EzyArray.class, new EzySafeSupplier() {
			@Override
			public Object get() {
				return newArray();
			}
		});
		answer.put(EzyObjectBuilder.class, new EzySafeSupplier() {
			@Override
			public Object get() {
				return newObjectBuilder();
			}
		});
		answer.put(EzyArrayBuilder.class, new EzySafeSupplier() {
			@Override
			public Object get() {
				return newArrayBuilder();
			}
		});
		return answer;
	}

}
