package mythread.one.chapter;

public class IsAliveOperate extends Thread {
	public IsAliveOperate() {
		System.out.println("IsAliveOperate constructor --- begin");
		System.out.println("Thread.currentThread().getName()=" + Thread.currentThread().getName());
		System.out.println("Thread.currentThread().isAlive()=" + Thread.currentThread().isAlive());
		System.out.println("this.getName()="+this.getName());
		System.out.println("this.isAlive()=" + this.isAlive());
		System.out.println("this.currentThread().getName()=" + this.currentThread().getName());
		System.out.println("this.currentThread().isAlive()=" + this.currentThread().isAlive());
		System.out.println("IsAliveOperate constructor -- end");
	}
	
	@Override
	public void run() {
		System.out.println("run method --- begin");
		System.out.println("Thread.currentThread().getName()=" + Thread.currentThread().getName());
		System.out.println("Thread.currentThread().isAlive()=" + Thread.currentThread().isAlive());
		System.out.println("this.getName()="+this.getName());
		System.out.println("this.isAlive()=" + this.isAlive());
		System.out.println("this.currentThread().getName()=" + this.currentThread().getName());
		System.out.println("this.currentThread().isAlive()=" + this.currentThread().isAlive());
		System.out.println("run method -- end");
	}

}
