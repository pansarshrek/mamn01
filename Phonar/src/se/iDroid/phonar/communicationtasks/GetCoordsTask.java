package se.iDroid.phonar.communicationtasks;

import java.io.IOException;
import java.net.DatagramPacket;

import se.iDroid.phonar.communication.Protocol;
import se.iDroid.phonar.model.Model;

public class GetCoordsTask extends SendTask {
	
	private Model model;
	
	public GetCoordsTask(Model model) {
		this.model = model;
	}

	@Override
	public DatagramPacket createPacket() {
		try {
			dos.writeByte(Protocol.COM_GET_COORDS);
			dos.writeUTF(model.myName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] buffer = baos.toByteArray();
		
		return new DatagramPacket(buffer, buffer.length);
	}
	
	

}
