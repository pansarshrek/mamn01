package se.iDroid.phonar.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Model {
	
	private User me;
	private HashMap<String, User> users;
	
	public Model(String username) {
		me = new User(username, 0, 0, 0);
		users = new HashMap<String, User>();
	}
	
	public Double myLongitude() {
		return me.getLongitude();
	}
	
	public Double myLatitude() {
		return me.getLatitude();
	}
	
	public Double myAltitude() {
		return me.getAltitude();
	}
	
	public String myName() {
		return me.getName();
	}
	
	public void setLatitude(double lat) {
		me.setLatitude(lat);
	}
	
	public void setLongitude(double longitude) {
		me.setLongitude(longitude);
	}
	
	public void updateUserCoords(ArrayList<User> userList) {
		for (User u : userList) {
			users.put(u.getName(), u);
		}
	}
	
	

}
