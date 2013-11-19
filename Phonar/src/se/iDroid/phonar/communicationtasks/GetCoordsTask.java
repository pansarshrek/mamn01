package se.iDroid.phonar.communicationtasks;

import java.io.IOException;
import java.net.DatagramPacket;

import se.iDroid.phonar.communication.Protocol;

public class GetCoordsTask extends SendTask {

	@Override
	public DatagramPacket createPacket() {
		try {
			dos.writeByte(Protocol.COM_GET_COORDS);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] buffer = baos.toByteArray();
		
		return new DatagramPacket(buffer, buffer.length);
	}
	
	

}
