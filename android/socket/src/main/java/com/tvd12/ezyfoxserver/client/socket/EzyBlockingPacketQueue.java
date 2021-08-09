package com.tvd12.ezyfoxserver.client.socket;

import java.util.LinkedList;
import java.util.Queue;

public class EzyBlockingPacketQueue implements EzyPacketQueue {

	private final int capacity;
	private final Queue<EzyPacket> queue;
	private volatile boolean empty = true;
	private volatile boolean processing = false;

	public EzyBlockingPacketQueue() {
		this(10000);
	}

	public EzyBlockingPacketQueue(int capacity) {
		this.capacity = capacity;
		this.queue = new LinkedList<>();
	}

	@Override
	public int size() {
		synchronized (this) {
			int size = queue.size();
			return size;
		}
	}

	@Override
	public void clear() {
		synchronized (this) {
			queue.clear();
			empty = true;
			processing = false;
		}
	}

	@Override
	public EzyPacket take() {
		synchronized (this) {
			EzyPacket packet = queue.poll();
			processing = false;
			empty = queue.isEmpty();
			notifyAll();
			return packet;
		}
	}

	@Override
	public EzyPacket peek() throws InterruptedException {
		synchronized (this) {
			while(empty || processing)
				wait();
			processing = true;
			EzyPacket packet = queue.peek();
			return packet;
		}

	}

	@Override
	public boolean isFull() {
		synchronized (this) {
			int size = queue.size();
			boolean full = size >= capacity;
			return full;
		}
	}

	@Override
	public boolean isEmpty() {
		synchronized (this) {
			boolean empty = queue.isEmpty();
			return empty;
		}
	}

	@Override
	public boolean add(EzyPacket packet) {
		synchronized (this) {
			int size = queue.size();
			if(size >= capacity)
				return false;
			boolean success = queue.offer(packet);
			if(success) {
				empty = false;
				if(!processing)
					notifyAll();
			}
			return success;
		}
	}

	@Override
	public void again() {
		synchronized (this) {
			this.processing = false;
		}
	}

	@Override
	public void wakeup() {
		synchronized (this) {
			queue.offer(null);
			empty = false;
			processing = false;
			notifyAll();
		}
	}
}
