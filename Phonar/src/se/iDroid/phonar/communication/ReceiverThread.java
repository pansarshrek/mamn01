package se.iDroid.phonar.communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import se.iDroid.phonar.model.Model;
import se.iDroid.phonar.requesthandlers.CreateUserHandler;
import se.iDroid.phonar.requesthandlers.GetCoordsHandler;
import se.iDroid.phonar.requesthandlers.RequestHandler;
import se.iDroid.phonar.requesthandlers.SendCoordsHandler;
import android.util.Log;

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
				if (megaDatagramPacket.getLength() > 0) {
					byte firstByte = megaDatagramPacket.getData()[0];
					RequestHandler handler = null;
					System.out.println((int) firstByte);
					switch (firstByte) {
					case Protocol.COM_UPDATE_COORDS:
						handler = new SendCoordsHandler();
						break; 
					case Protocol.COM_GET_COORDS:
						handler = new GetCoordsHandler(model);
						break;
					case Protocol.COM_CREATE_USER:
						handler = new CreateUserHandler();
						break;
					default:
						Log.d("phonar", "PFFT");
						break;
					}
					
					handler.handleRequest(megaDatagramPacket);
					
				}
				Log.d("phonar:ReceiverThread", "Received packet");
			} catch (IOException e) {
				Log.e("phonar:ReceiverThread", "IOException in receive loop", e);
			}
		}
		
	}

}
