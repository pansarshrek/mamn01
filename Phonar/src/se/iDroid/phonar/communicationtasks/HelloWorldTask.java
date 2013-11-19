package se.iDroid.phonar.communicationtasks;

import java.net.DatagramPacket;

public class HelloWorldTask extends SendTask {

	@Override
	public DatagramPacket createPacket() {
		String hw = "Herro World!";
		byte[] bytes = hw.getBytes();
		DatagramPacket packet = new DatagramPacket(bytes, bytes.length);		
		return packet;
	}

}
