
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

		while (true)
		{
			if (t1.numRuns > 3 && t2.numRuns > 3)
			{
				synchronized (lock)
				{
					System.out.println("Done!");
					System.exit(0);
				}
			}
		}
	}

	static class PingPongThread extends Thread
	{
		int numRuns = 0;
		String name;
		boolean hasLock = false;
		PingPongThread other;

		PingPongThread(String name)
		{
			this.name = name;
		}

		@Override
		public void run()
		{
			while (true)
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
