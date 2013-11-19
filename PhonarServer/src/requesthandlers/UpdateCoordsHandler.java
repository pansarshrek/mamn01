package requesthandlers;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;

import models.Position;
import models.UserDatabase;

import communication.Communication;

import exceptions.UserDoesNotExistException;

public class UpdateCoordsHandler extends RequestHandler {

	@Override
	public DatagramPacket internalHandle(DataInputStream dis) throws IOException {
		
		try {
			dos.writeByte(Communication.COM_UPDATE_COORDS);
			String name = dis.readUTF();
			Position pos = new Position(dis);
			
			UserDatabase.updateUserPos(name, pos);
			
			dos.writeByte(Communication.ANS_SUCCESS);
		}catch (UserDoesNotExistException e) {
			
			dos.writeByte(Communication.ANS_FAILURE);
		}
		
		byte[] buffer = baos.toByteArray();
		
		return new DatagramPacket(buffer, buffer.length);
	}


}
