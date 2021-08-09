package com.tvd12.ezyfoxserver.client.request;

import com.tvd12.ezyfoxserver.client.entity.EzyData;

import java.io.Serializable;

/**
 * Created by tavandung12 on 10/1/18.
 */

public interface EzyRequest extends Serializable {

    Object getCommand();

    EzyData serialize();

}
