package se.iDroid.phonar.communication;

public class SendThread extends Thread {
	
	private CommunicationMonitor comMon;
	
	public SendThread(CommunicationMonitor comMon) {
		this.comMon = comMon;
	}
	
	public void run() {
		while (true) {
			comMon.sendPendingTasks();
		}
	}

}
