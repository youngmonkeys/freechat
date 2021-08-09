package com.tvd12.ezyfoxserver.client.util;

import com.tvd12.ezyfoxserver.client.function.EzyNewArray;

import java.util.Collection;

/**
 * Created by tavandung12 on 9/20/18.
 */

public final class EzyCollections {

    private EzyCollections() {
    }

    public static <T> T[] toArray(Collection<T> coll, EzyNewArray<T> newer) {
        T[] array = newer.apply(coll.size());
        int index = 0;
        for(T t : coll)
            array[index ++] = t;
        return array;
    }
}
