package mythread.one.chapter;

public class Run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		CountOperate c = new CountOperate();
//		Thread t = new Thread(c);
//		t.setName("t");
//		t.start();
		
		CountOperate co = new CountOperate();
		co.start();

	}

}
