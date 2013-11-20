package se.iDroid.phonar.requesthandlers;

import java.awt.font.NumericShaper;
import java.io.IOException;
import java.util.ArrayList;

import android.util.Log;

import se.iDroid.phonar.model.Model;
import se.iDroid.phonar.model.User;

public class GetCoordsHandler extends RequestHandler {
	
	private Model model;
	
	public GetCoordsHandler(Model model) {
		super();
		this.model = model;
	}
	
	public void internalHandle() throws IOException {
		int numberOfUsers = dis.readInt();
		Log.d("phonar:GetCoordsHandler", "Number of users: " + numberOfUsers);
		
		ArrayList<User> users = new ArrayList<User>();
		
		for (int i = 0; i < numberOfUsers; i++) {
			users.add(new User(dis));
		}
		
		model.updateUserCoords(users);
	}

}
