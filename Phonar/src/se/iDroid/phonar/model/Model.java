package se.iDroid.phonar.model;

import java.util.ArrayList;

public class Model {
	
	private User me;
	private ArrayList<User> users;
	
	public Model(String username) {
		me = new User(username, 0, 0, 0);
		users = new ArrayList<User>();
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
	
	public void updateUserCoords(ArrayList<User> users) {
		this.users = users;
	}
	
	

}
