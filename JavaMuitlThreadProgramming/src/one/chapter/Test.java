package one.chapter;

//Example 2
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			MyThread thread = new MyThread();
			thread.setName("myThread");
			thread.start();
			for (int i=0; i<10; i++) {
				int time = (int) (Math.random() * 1000);
				Thread.sleep(time);
				System.out.println("main=" + Thread.currentThread().getName());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
