package com.tvd12.ezyfoxserver.client.socket;

import com.tvd12.ezyfoxserver.client.event.EzyEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EzySocketEventQueue {

	protected final Queue<EzyEvent> events;

	public EzySocketEventQueue() {
		this.events = new LinkedList<>();
	}

	public void addEvent(EzyEvent evt) {
		synchronized (events) {
			events.offer(evt);
		}
	}

	public void popAll(List<EzyEvent> buffer) {
		synchronized (events) {
			while (events.size() > 0)
				buffer.add(events.poll());
		}
	}

	public void clear() {
		synchronized (events) {
			events.clear();
		}
	}

}
