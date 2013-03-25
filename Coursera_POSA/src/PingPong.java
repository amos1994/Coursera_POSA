import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PingPong
{
	static Lock lock = new ReentrantLock();

	public static void main(String[] args)
	{
		PingPongThread t1 = new PingPongThread("Ping!");
		PingPongThread t2 = new PingPongThread("Pong!");
		t1.other = t2;
		t2.other = t1;

		t1.hasLock = true;
		t1.start();
		t2.start();

		while (t1.keepRunning && t2.keepRunning)
		{
			if (t1.numRuns >= 3)
			{
				t1.keepRunning = false;
			}
			if (t2.numRuns >= 3)
			{
				t2.keepRunning = false;
			}
		}
		System.out.println("Done!");
	}

	static class PingPongThread extends Thread
	{
		int numRuns = 0;
		String name;
		boolean hasLock = false;
		boolean keepRunning = true;
		PingPongThread other;

		PingPongThread(String name)
		{
			this.name = name;
		}

		@Override
		public void run()
		{
			while (keepRunning)
			{
				if (hasLock)
				{
					synchronized (lock)
					{
						lock.lock();
						System.out.println(name);
						lock.unlock();
						numRuns++;
						hasLock = false;
						other.hasLock = true;
					}
				}
			}
		}
	}
}
