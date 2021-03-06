package se.iDroid.phonar.bootstrap;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import se.iDroid.phonar.activities.MainActivity;
import se.iDroid.phonar.communication.CommunicationMonitor;
import se.iDroid.phonar.communication.PollThread;
import se.iDroid.phonar.communication.ReceiverThread;
import se.iDroid.phonar.communication.SendThread;
import se.iDroid.phonar.data.Data;
import se.iDroid.phonar.feedback.PhonarVibrator;
import se.iDroid.phonar.model.Model;
import se.iDroid.phonar.model.User;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class Bootstrap implements Observer {

	private Model model;
	private CommunicationMonitor comMon;
	private MainActivity activity;
	private PhonarVibrator pv;
	private boolean isPointed;
	

	public Bootstrap(MainActivity activity) {
		DatagramSocket socket;
		this.activity = activity;
		pv = new PhonarVibrator(activity);
		isPointed = false;
		
		
		try {
			Log.d("Phonar:UDP", "Creating DatagramSocket...");
			socket = new DatagramSocket();
			Log.d("Phonar:UDP", "DatagramSocket created");
			String username = activity.getSharedPreferences(Data.DATAFILE, 0).getString(Data.USERNAME, "DEFAULT");
			model = new Model(username);
			model.addObserver(this);
			comMon = new CommunicationMonitor(socket, model);
			SendThread sentThread = new SendThread(comMon);
			PollThread pollThread = new PollThread(comMon);
			Log.d("udp port", "bootstrap constructor " + socket.getLocalPort());
			ReceiverThread receiverThread = new ReceiverThread(model, socket);
			receiverThread.start();
			sentThread.start();
			pollThread.start();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public CommunicationMonitor getCommunicationMonitor() {
		return comMon;
	}
	
	public Model getModel() {
		return model;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				HashMap<String, User> users = model.getUsers();
				for (Entry<String, User> e : users.entrySet()) {
					activity.setMarker(e.getKey(), new LatLng(e.getValue().getLatitude(), e.getValue().getLongitude()));
				}
			}
		});		
	}
	
	public void vibrateIfPointedAt(Location locA, Location locB, float bearing) {
		
		boolean amIPointingAtAnyone = false;

		HashMap<String, User> users = model.getUsers();
		
		for (Entry<String, User> e : users.entrySet()) {
			User user = e.getValue();
			
			locB.setLatitude(user.getLatitude());
			locB.setLongitude(user.getLongitude());
			
			long now = System.currentTimeMillis();

			if (Math.abs(locA.bearingTo(locB) - bearing) < 1) {
				if (!isPointed) {
					pv.vib();  // vibrate
					Log.d("Phonar", "I am pointing at " + e.getValue().getName());
					isPointed = true;
				}
				
				amIPointingAtAnyone = true;
			}
		}
		
		if(!amIPointingAtAnyone) {
			isPointed = false;
		}
	}
}
