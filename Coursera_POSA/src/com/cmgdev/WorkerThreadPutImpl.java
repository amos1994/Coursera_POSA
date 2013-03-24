package com.cmgdev;

import java.util.Queue;

public class WorkerThreadPutImpl extends WorkerThread
{

	int current = 1;

	public WorkerThreadPutImpl(Queue<Integer> queue, int sleepSeconds)
	{
		super(queue, sleepSeconds);
	}

	@Override
	protected void doWork()
	{
//		System.out.println("putting on queue");
		queue.offer(current++);
	}

}
