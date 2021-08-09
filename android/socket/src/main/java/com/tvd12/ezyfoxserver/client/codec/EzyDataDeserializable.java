package com.tvd12.ezyfoxserver.client.codec;

import com.tvd12.ezyfoxserver.client.entity.EzyData;

import java.io.Serializable;

/**
 * Created by tavandung12 on 10/7/18.
 */

public interface EzyDataDeserializable<T extends EzyData> extends Serializable {

    void deserialize(T data);

}
