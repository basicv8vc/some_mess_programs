package thinking.chapter.concurrency;

import java.util.concurrent.TimeUnit;

//Using a named inner class:
class InnerThread1 {
	private int countDown = 5;
	private Inner inner;
	private class Inner extends Thread {
		Inner(String name) {
			super(name);
			start();
		}
		public void run() {
			try {
				while(true) {
					System.out.println(this);
					if(--countDown == 0) return;
					sleep(10);
				}
			} catch(InterruptedException e) {
				System.out.println("interrupted");
			}
		}
		public String toString() {
			return getName() + ": " + countDown;
		}
	}
	
	public InnerThread1(String name) {
		inner = new Inner(name);
	}
}

//Using an anonymous inner class:
class InnerThread2 {
	private int countDown = 5;
	private Thread t;
	public InnerThread2(String name) {
		t = new Thread(name) {
			public void run() {
				try {
					while(true) {
						System.out.println(this);
						if(--countDown == 0) return;
						sleep(10);
					}
				} catch(InterruptedException e) {
					System.out.println("sleep() interrupted");
				}
			}
			public String toString() {
				return getName() + ": " + countDown;
			}
		};
		t.start();
	}
}

//Using a named Runnable implementation:
class InnerRunnable1 {
	private int countDown = 5;
	private Inner inner;
	private class Inner implements Runnable {
		Thread t;
		Inner(String name) {
			t = new Thread(this, name);
			t.start();
		}
		@Override
		public void run() {
			try {
				while(true) {
					System.out.println(this);
					if(--countDown == 0) return;
					TimeUnit.MICROSECONDS.sleep(10);
				}
			} catch(InterruptedException e) {
				System.out.println("sleep() interrupted");
			}
		}
		
		public String toString() {
			return t.getName() + ": " + countDown;
		}
	}
	
	public InnerRunnable1(String name) {
		inner = new Inner(name);
	}
}

//Using an anonymous Runnable implementation:
class InnerRunnable2 {
	private int countDown = 5;
	private Thread t;
	public InnerRunnable2(String name) {
		t = new Thread(new Runnable() { //匿名类做参数
			public void run() {
				try {
					while(true) {
						System.out.println(this);
						if(--countDown == 0) return;
						TimeUnit.MICROSECONDS.sleep(10);
					}
				} catch(InterruptedException e) {
					System.out.println("sleep() interrupted");
				}
			}
			public String toString() {
				return Thread.currentThread().getName() + 
						": " + countDown;
			}
		}, name);
		t.start();
	}
	
}

//A separate method to run some code as a task
class ThreadMethod {
	private int countDown = 5;
	private Thread t;
	private String name;
	public ThreadMethod(String name) {
		this.name = name;
	}
	
	public void runTask() {
		if(t == null) {
			t = new Thread(name) {
				public void run() {
					try {
						while(true) {
							System.out.println(this);
							if(--countDown == 0) return;
							sleep(10);
						}
					} catch(InterruptedException e) {
						System.out.println("sleep() interrupted");
					}
				}
				public String toString() {
					return getName() + ": " + countDown;
				}
			};
			t.start();
			
		}
	}
}


public class ThreadVariations {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new InnerThread1("InnerThread1");
		new InnerThread2("InnerThread2");
		new InnerRunnable1("InnerRunnable1");
		new InnerRunnable2("InnerRunnable2");
		new ThreadMethod("ThreadMethod").runTask();
		

	}

}
