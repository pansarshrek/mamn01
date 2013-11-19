package se.iDroid.phonar.communicationtasks;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;



public abstract class SendTask {
	
	protected DataOutputStream dos;
	protected ByteArrayOutputStream baos;
	
	public SendTask() {
		baos = new ByteArrayOutputStream();
		dos = new DataOutputStream(baos);
	}

	public void execute(DatagramSocket socket) {
		try {
			DatagramPacket packet = createPacket();
			packet.setAddress(InetAddress.getByName("pansarshrek.se"));
			packet.setPort(13337);
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public abstract DatagramPacket createPacket();

}
