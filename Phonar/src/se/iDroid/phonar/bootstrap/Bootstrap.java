package se.iDroid.phonar.bootstrap;

import java.net.DatagramSocket;
import java.net.SocketException;

import se.iDroid.phonar.communication.CommunicationMonitor;
import se.iDroid.phonar.communication.PollThread;
import se.iDroid.phonar.communication.SendThread;
import se.iDroid.phonar.data.Data;
import se.iDroid.phonar.model.Model;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.location.LocationClient;

public class Bootstrap {
	
	private static Bootstrap instance;
	private Model model;
	private CommunicationMonitor comMon;
	private Context context;

	private Bootstrap(Context context) {
		setContext(context);
		DatagramSocket socket;
		try {
			Log.d("Phonar:UDP", "Creating DatagramSocket...");
			socket = new DatagramSocket();
			Log.d("Phonar:UDP", "DatagramSocket created");
			String username = context.getSharedPreferences(Data.DATAFILE, 0).getString(Data.USERNAME, "DEFAULT");
			model = new Model(username);
			comMon = new CommunicationMonitor(socket, model);
			SendThread sentThread = new SendThread(comMon);
			PollThread pollThread = new PollThread(comMon);
			sentThread.start();
			pollThread.start();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	public CommunicationMonitor getCommunicationMonitor() {
		return comMon;
	}
	
	public Model getModel() {
		return model;
	}
	
	public static Bootstrap getInstance(Context context) {
		if (instance == null) {
			instance = new Bootstrap(context);
		} else {
			instance.setContext(context);
		}
		return instance;
	}
}
