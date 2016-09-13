package thinking.chapter.concurrency;

class Sleeper extends Thread {
	private int duration;
	public  Sleeper(String name, int sleepTime) {
		super(name); //Thread(String name)
		duration =  sleepTime;
		start();
	}
	@Override
	public void run() {
		try {
			sleep(duration);
		} catch(InterruptedException e) {
			System.out.println(getName() + " was interrupted. " +
						"isInterruped(): " + isInterrupted()); 
			return;
		}
		System.out.println(getName() + " has awakened."); 
	}
}

class Joiner extends Thread {
	private Sleeper sleeper;
	public Joiner(String name, Sleeper sleeper) {
		super(name);
		this.sleeper = sleeper;
		start();
	}
	@Override
	public void run() {
		try {
			sleeper.join();
		} catch(InterruptedException e) {
			System.out.println("Interrupted");
		}
		System.out.println(getName() + " join completed."); 
	}
}

public class Joining {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sleeper sleepy = new Sleeper("Sleepy", 1500),
				grumpy = new Sleeper("Grumpy", 1500);
		Joiner
			dopey = new Joiner("Dopey", sleepy),
			doc = new Joiner("Doc", grumpy);
		grumpy.interrupt();

	}

}
