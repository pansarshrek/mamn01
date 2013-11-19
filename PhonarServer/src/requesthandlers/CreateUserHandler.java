package requesthandlers;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import models.Position;
import models.UserDatabase;

import communication.Communication;

import exceptions.UserExistsException;

public class CreateUserHandler extends RequestHandler {

	@Override
	public DatagramPacket internalHandle(DataInputStream dis) throws IOException {
		
		try {
			dos.writeByte(Communication.COM_CREATE_USER);
			String name = dis.readUTF();
			Position pos = new Position(dis);
			
			if (UserDatabase.addUser(name, pos)) {
				dos.writeByte(Communication.ANS_SUCCESS);
			} 
		} catch (UserExistsException e) {
			dos.writeByte(Communication.ANS_FAILURE);
		}
		
		byte[] buffer = baos.toByteArray();
		
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		
		return packet;
	}



}
