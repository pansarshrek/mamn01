package se.iDroid.phonar.communication;


public class PollThread extends Thread {

	private CommunicationMonitor com;

	public PollThread(CommunicationMonitor com) {
		this.com = com;
	}

	public void run() {
		while (true) {
			com.sendCoords();
			com.updateCoords();
			tick(5000);
		}
	}

	public void tick(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
