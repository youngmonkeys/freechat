package com.tvd12.ezyfoxserver.client.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EzyQueue<E>
{
    protected final int capacity;
    protected final Queue<E> queue;

    public EzyQueue() {
        this(Integer.MAX_VALUE);
    }

    public EzyQueue(int capacity)
    {
        this.capacity = capacity;
        this.queue = newQueue(capacity);
    }

    protected Queue<E> newQueue(int capacity) {
        return new LinkedList<>();
    }

    public boolean add(E e) {
        if (queue.size() >= capacity) {
            return false;
        }
        queue.offer(e);
        return true;
    }

    public boolean offer(E e) {
        return add(e);
    }

    public E peek() {
        return queue.peek();
    }

    public E poll() {
        return queue.poll();
    }

    public void pollAll(List<E> list) {
        while (queue.size() > 0)
            list.add(queue.poll());
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void clear() {
        queue.clear();
    }

    public int getCapacity() {
        return capacity;
    }
}
