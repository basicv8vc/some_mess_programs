package thinking.chapter.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExceptionThread implements Runnable {
	@Override
	public void run() {
		throw new RuntimeException();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new ExceptionThread());

	}

}
