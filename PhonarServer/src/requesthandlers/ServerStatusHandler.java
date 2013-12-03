package requesthandlers;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.List;

import models.User;
import models.UserDatabase;

public class ServerStatusHandler extends RequestHandler {

	@Override
	public DatagramPacket internalHandle(DataInputStream dis)
			throws IOException {
		List<User> users = UserDatabase.getUsers();
		StringBuilder sb = new StringBuilder();
		sb.append(users.size());
		sb.append(";users:");
		for (User u : users) {
			sb.append(u.getName());
			sb.append(';');
		}
		
		dos.writeUTF(sb.toString());
		byte[] buffer = baos.toByteArray();
		return new DatagramPacket(buffer, buffer.length);
	}

}
