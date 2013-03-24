package com.cmgdev;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ThreadingTest
{

	private static int MIN_POOL_SIZE = 1;
	private static int MAX_POOL_SIZE = 20;

	public static void main(String[] args)
	{
		Queue<Integer> queue = new ConcurrentLinkedQueue<>();
		Random random = new Random(System.currentTimeMillis());
		List<WorkerThread> workerGetThreadPool = new ArrayList<>();
		List<WorkerThread> workerPutThreadPool = new ArrayList<>();

		WorkerThread putThread = new WorkerThreadPutImpl(queue, random.nextInt(10) + 1);
		workerPutThreadPool.add(putThread);
		putThread.start();

		while (true)
		{
			int queueSize = queue.size();
			int workerGetThreadPoolSize = workerGetThreadPool.size();
			int workerPutThreadPoolSize = workerPutThreadPool.size();

			if ((queueSize / 2) > workerGetThreadPoolSize)
			{
				if (workerGetThreadPoolSize <= MAX_POOL_SIZE && random.nextBoolean())
				{
					System.out.println("creating new get thread. queue size: " + queueSize + ", threadpool size: " + workerGetThreadPoolSize);
					WorkerThread t = new WorkerThreadGetImpl(queue, random.nextInt(10) + 2);
					workerGetThreadPool.add(t);
					t.start();
				}
				else if (workerPutThreadPoolSize <= MAX_POOL_SIZE && random.nextBoolean())
				{
					System.out.println("creating new put thread. queue size: " + queueSize + ", threadpool size: " + workerPutThreadPoolSize);
					WorkerThread t = new WorkerThreadPutImpl(queue, random.nextInt(10) + 1);
					workerPutThreadPool.add(t);
					t.start();
				}
			}
			else if (!queue.isEmpty() && workerGetThreadPoolSize > queueSize)
			{
				if (workerGetThreadPoolSize > MIN_POOL_SIZE && random.nextBoolean())
				{
					System.out.println("killing get thread. queue size: " + queueSize + ", threadpool size: " + workerGetThreadPoolSize);
					WorkerThread t = workerGetThreadPool.remove(0);
					t.end();
				}
				else if (workerPutThreadPoolSize > MIN_POOL_SIZE && random.nextBoolean())
				{
					System.out.println("killing put thread. queue size: " + queueSize + ", threadpool size: " + workerPutThreadPoolSize);
					WorkerThread t = workerPutThreadPool.remove(0);
					t.end();
				}
			}

		}
	}

}
