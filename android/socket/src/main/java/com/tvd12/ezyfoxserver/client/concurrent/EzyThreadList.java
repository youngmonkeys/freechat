package com.tvd12.ezyfoxserver.client.concurrent;

import java.util.concurrent.ThreadFactory;

public class EzyThreadList {

	protected final Thread[] threads;
	
	public EzyThreadList(int size, Runnable task, String threadName) {
		this(size, task, EzyExecutors.newThreadFactory(threadName));
	}
	
	public EzyThreadList(int size, Runnable task, ThreadFactory threadFactory) {
		this.threads = new Thread[size];
		for(int i = 0 ; i < threads.length ; ++i)
			threads[i] = threadFactory.newThread(task);
	}
	
	public void execute() {
		for(Thread thread : threads)
			thread.start();
	}
	
}
