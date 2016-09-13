package thinking.chapter.concurrency;

import java.util.concurrent.locks.*;


public class MutexEvenGenerator extends IntGenerator {
	private int currentEvenValue = 0;
	private Lock lock = new ReentrantLock();
	public int next() {
		lock.lock();
		try {
			++currentEvenValue;
			Thread.yield();
			++currentEvenValue;
			return currentEvenValue;
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EvenChecker.test(new MutexEvenGenerator());

	}

}
