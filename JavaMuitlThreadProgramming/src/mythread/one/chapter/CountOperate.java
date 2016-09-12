package mythread.one.chapter;

public class CountOperate extends Thread {
	public CountOperate() {
		System.out.println("CountOperate constructor --- begin");
		System.out.println("Thread.currentThread().getName()=" +
						  Thread.currentThread().getName());
		System.out.println("this.getName()=" + this.getName());
		System.out.println("this.currentThread().getName()=" + this.currentThread().getName());
		System.out.println("CountOperate constructor --- end");
	}
	
	@Override
	public void run() {
		System.out.println("run()-------begin");
		System.out.println("Thread.currentThread().getName()=" +
				  Thread.currentThread().getName());
		System.out.println("this.getName()=" + this.getName());
		System.out.println("this.currentThread().getName()=" + this.currentThread().getName());
		System.out.println("run() --- end");
	}

}
