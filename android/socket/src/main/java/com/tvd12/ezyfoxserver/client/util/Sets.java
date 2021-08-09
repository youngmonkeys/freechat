package com.tvd12.ezyfoxserver.client.util;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tavandung12 on 9/19/18.
 */

public final class Sets {

    private Sets() {
    }

    public static <E> Set<E> newHashSet(E... items) {
        Set<E> set = new HashSet<>();
        for (E item : items)
            set.add(item);
        return set;
    }
}
