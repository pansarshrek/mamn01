package se.iDroid.phonar.communication;


public class PollThread extends Thread {

	private CommunicationMonitor com;

	public PollThread(CommunicationMonitor com) {
		this.com = com;
	}

	public void run() {
		long sleepTime = 5000;
		long sleepUntil = System.currentTimeMillis();
		while (true) {
			com.sendCoords();
			com.updateCoords();
			sleepUntil += sleepTime;
			try {
				Thread.sleep(sleepUntil - System.currentTimeMillis());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
