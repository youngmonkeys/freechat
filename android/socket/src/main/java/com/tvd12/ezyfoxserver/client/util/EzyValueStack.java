package com.tvd12.ezyfoxserver.client.util;

import java.util.List;
import java.util.Stack;

public class EzyValueStack<V> {
    protected V topValue;
    protected V lastValue;
    protected V defaultValue;
    protected final Stack<V> values;

    public EzyValueStack(V defValue) {
        values = new Stack<V>();
        topValue = defValue;
        lastValue = defValue;
        defaultValue = defValue;
    }

    public V top() {
        synchronized (this) {
            return topValue;
        }
    }

    public V last() {
        synchronized (this) {
            return lastValue;
        }
    }

    public V pop() {
        synchronized (this) {
            int size = values.size();
            if (size > 0) {
                topValue = values.pop();
            }
            else {
                topValue = defaultValue;
            }
            return topValue;
        }
    }

    public void popAll(List<V> buffer) {
        synchronized (this) {
            while (values.size() > 0)
                buffer.add(values.pop());
        }
    }


    public void push(V value) {
        synchronized (this) {
            topValue = value;
            lastValue = value;
            values.push(value);
        }
    }

    public void clear() {
        synchronized (this) {
            topValue = defaultValue;
            lastValue = defaultValue;
            values.clear();
        }
    }


    public int size() {
        synchronized (this) {
            int size = values.size();
            return size;
        }
    }
}
