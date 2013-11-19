package se.iDroid.phonar.requesthandlers;

import java.io.IOException;
import java.util.ArrayList;

import se.iDroid.phonar.model.User;

public class GetCoordsHandler extends RequestHandler {
	
	public void internalHandle() throws IOException {
		int numberOfUsers = dis.readInt();
		
		ArrayList<User> users = new ArrayList<User>();
		
		for (int i = 0; i < numberOfUsers; i++) {
			users.add(new User(dis));
		}
	}

}
