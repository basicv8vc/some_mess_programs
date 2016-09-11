package myrunnable.one.chapter;

public class Run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Runnable runnable = new MyRunnable();
		Thread thread = new Thread(runnable);
		thread.start();
		System.out.println("Run end.");

	}

}
