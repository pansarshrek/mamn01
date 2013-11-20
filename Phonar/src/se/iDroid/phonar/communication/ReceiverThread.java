package se.iDroid.phonar.communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import android.util.Log;

import se.iDroid.phonar.model.Model;

public class ReceiverThread extends Thread {
	
	private Model model;
	private DatagramSocket socket;
	
	public ReceiverThread(Model model, DatagramSocket socket) {
		this.model = model;
		this.socket = socket;
	}

	public void run() {
		byte[] megaBuffer = new byte[65535];
		DatagramPacket megaDatagramPacket = new DatagramPacket(megaBuffer, 0);
		Log.d("phonar:ReceiverThread", "Waiting for packets on port " + socket.getLocalPort());
		while (true) {
			megaDatagramPacket.setLength(megaBuffer.length);
			try {
				socket.receive(megaDatagramPacket);
				Log.d("phonar:ReceiverThread", "Received packet");
			} catch (IOException e) {
				Log.e("phonar:ReceiverThread", "IOException in receive loop", e);
			}
		}
		
	}

}
