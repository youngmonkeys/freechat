package com.tvd12.ezyfoxserver.client.concurrent;

import com.tvd12.ezyfoxserver.client.util.EzyQueue;

import java.util.List;

public class EzySynchronizedQueue<E> extends EzyQueue<E> {
    public EzySynchronizedQueue() {
        super();
    }

    public EzySynchronizedQueue(int capacity) {
        super(capacity);
    }

    @Override
    public boolean add(E e) {
        synchronized (queue) {
            if (queue.size() >= capacity) {
                return false;
            }
            queue.offer(e);
        }
        return true;
    }

    @Override
    public boolean offer(E e) {
        boolean success = add(e);
        return success;
    }

    @Override
    public E peek() {
        synchronized (queue) {
            E e = queue.peek();
            return e;
        }
    }

    @Override
    public E poll() {
        synchronized (queue) {
            E e = queue.poll();
            return e;
        }
    }

    @Override
    public void pollAll(List<E> list) {
        synchronized (queue) {
            while (queue.size() > 0)
                list.add(queue.poll());
        }
    }

    @Override
    public int size() {
        synchronized (queue) {
            int count = queue.size();
            return count;
        }
    }

    @Override
    public void clear() {
        synchronized (queue) {
            queue.clear();
        }
    }
}