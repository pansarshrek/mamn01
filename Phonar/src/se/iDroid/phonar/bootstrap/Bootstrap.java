package se.iDroid.phonar.bootstrap;

import java.net.DatagramSocket;
import java.net.SocketException;

import se.iDroid.phonar.communication.CommunicationMonitor;
import se.iDroid.phonar.communication.PollThread;
import se.iDroid.phonar.communication.SendThread;
import se.iDroid.phonar.model.Model;
import android.util.Log;

public class Bootstrap {
	
	private static Bootstrap instance;
	
	private Model model;
	private CommunicationMonitor comMon;

	private Bootstrap() {
		DatagramSocket socket;
		try {
			Log.d("Phonar:UDP", "Creating DatagramSocket...");
			socket = new DatagramSocket();
			Log.d("Phonar:UDP", "DatagramSocket created");
			model = new Model();
			comMon = new CommunicationMonitor(socket, model);
			SendThread sentThread = new SendThread(comMon);
			PollThread pollThread = new PollThread(comMon);
			sentThread.start();
			pollThread.start();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public CommunicationMonitor getCommunicationMonitor() {
		return comMon;
	}
	
	public static Bootstrap getInstance() {
		if (instance == null) {
			instance = new Bootstrap();
		}
		return instance;
	}
}
