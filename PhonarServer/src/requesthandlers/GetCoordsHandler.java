package requesthandlers;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.List;

import models.User;
import models.UserDatabase;

import communication.Communication;

public class GetCoordsHandler extends RequestHandler {

	@Override
	public DatagramPacket internalHandle(DataInputStream dis) throws IOException {
		
			dos.writeByte(Communication.COM_GET_COORDS);
			
			List<User> users = UserDatabase.getUsers();
			
			dos.writeInt(users.size());
			
			for(User u : users) {
				u.toStream(dos);
			}
		


		byte[] buffer = baos.toByteArray();
		
		return new DatagramPacket(buffer, buffer.length);
	}


}
