package thinking.chapter.concurrency;

public class SynchronizedEvenGenerator extends IntGenerator {
	private int currentEvenValue = 0;
	public synchronized int next() {
		++currentEvenValue;
		Thread.yield();
		++currentEvenValue;
		return currentEvenValue;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EvenChecker.test(new SynchronizedEvenGenerator());

	}

}
