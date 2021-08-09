package com.tvd12.ezyfoxserver.client.entity;

/**
 * Created by tavandung12 on 10/2/18.
 */

public class EzySimpleUser implements EzyUser {

    protected final long id;
    protected final String name;

    public EzySimpleUser(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("User(")
                .append("id: ").append(id).append(", ")
                .append("name: ").append(name)
                .append(")")
                .toString();
    }
}
