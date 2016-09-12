package mythread.one.chapter;

public class RunIsAlive {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IsAliveOperate iao = new IsAliveOperate();
//		iao.start();
		Thread t = new Thread(iao);
		System.out.println("main begin t isAlive()=" + t.isAlive());
		t.setName("t");
		t.start();
		System.out.println("main end t isAlive()=" + t.isAlive());

	}

}
