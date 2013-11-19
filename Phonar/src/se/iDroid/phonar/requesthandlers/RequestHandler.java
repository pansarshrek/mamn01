package se.iDroid.phonar.requesthandlers;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;

public abstract class RequestHandler {
	
	DataInputStream dis;
	
	
	public void handleRequest(DatagramPacket packet) {
		dis = new DataInputStream(new ByteArrayInputStream(packet.getData(), 1, packet.getLength()));
		try {
			internalHandle();
		} catch (IOException e) {
			// Handle it bitch
		}
	}
	
	public abstract void internalHandle() throws IOException;

}
