package extthread.one.chapter;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyThread mt1 = new MyThread(1);
		MyThread mt2 = new MyThread(2);
		MyThread mt3 = new MyThread(3);
		MyThread mt4 = new MyThread(4);
		MyThread mt5 = new MyThread(5);
		MyThread mt6 = new MyThread(6);
		mt1.start();
		mt2.start();
		mt3.start();
		mt4.start();
		mt5.start();
		mt6.start();

	}

}
