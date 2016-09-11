package one.chapter;

//Example 1
//public class MyThread extends Thread {
//	@Override
//	public void run() {
//		super.run();
//		System.out.println("MyThread");
//	}
//
//}



//Example 2
public class MyThread extends Thread {
	@Override
	public void run() {
		try {
			for (int i=0; i<10; i++) {
				int time = (int) (Math.random() * 1000);
				Thread.sleep(time);
				System.out.println("run=" + Thread.currentThread().getName());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
