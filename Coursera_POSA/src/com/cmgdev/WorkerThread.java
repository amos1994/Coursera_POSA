package com.cmgdev;

import java.util.Queue;
import java.util.Random;

/**
 * @author cmgdev
 * 
 *         Represents a thread that pulls data off the queue and does some
 *         processing
 * 
 */
public abstract class WorkerThread extends Thread
{

	protected int sleepSeconds;
	protected Queue<Integer> queue;
	protected boolean keepRunning;

	public WorkerThread(Queue<Integer> queue, int sleepSeconds)
	{
		this.sleepSeconds = sleepSeconds;
		this.queue = queue;
		this.keepRunning = true;
	}

	protected abstract void doWork();

	public void run()
	{
		Random random = new Random(System.currentTimeMillis());
		while (keepRunning)
		{
			try
			{
				 System.out.println("sleeping " + sleepSeconds);
				Thread.sleep(random.nextInt(sleepSeconds * 1000));
				doWork();
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void end()
	{
		this.keepRunning = false;
	}

}
