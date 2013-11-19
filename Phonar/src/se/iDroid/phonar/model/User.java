package se.iDroid.phonar.model;

import java.io.DataInputStream;
import java.io.IOException;

public class User {
	
	private String name;
	private double latitude;
	private double longitude;
	private double altitude;
	
	public User(String name, double latitude, double longitude, double altitude) {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
	}
	
	public User(DataInputStream dis) throws IOException {
		this.name = dis.readUTF();
		this.latitude = dis.readDouble();
		this.longitude = dis.readDouble();
		this.altitude = dis.readDouble();
	}
	
	public User(String name) {
		this.name = name;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public double getAltitude() {
		return altitude;
	}
	
	public String getName() {
		return name;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	

}
