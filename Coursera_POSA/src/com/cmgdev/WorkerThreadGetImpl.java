package com.cmgdev;

import java.util.Queue;

/**
 * @author cmgdev
 * 
 */
public class WorkerThreadGetImpl extends WorkerThread
{

	public WorkerThreadGetImpl(Queue<Integer> queue, int sleepSeconds)
	{
		super(queue, sleepSeconds);
	}

	protected void doWork()
	{
//		System.out.println("doing work");
		Integer i = queue.poll();
		if (i != null)
		{
			System.out.println(this.getId() + " " + i * 2);
		}
	}

}
