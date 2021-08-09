package com.tvd12.ezyfoxserver.client.function;

/**
 * Created by tavandung12 on 10/25/18.
 */

public interface EzyBiFunction<T,U,R> {

    R accept(T t, U u);

}
