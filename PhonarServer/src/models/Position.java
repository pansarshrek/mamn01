package models;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Position {

	private double latitude;
	private double longitude;
	private double altitude;

	public Position(double latitude, double longitude, double altitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
	}

	public Position(DataInputStream dis) throws IOException {
		this.latitude = dis.readDouble();
		this.longitude = dis.readDouble();
		this.altitude = dis.readDouble();

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
	
	public void toStream(DataOutputStream dos) throws IOException {
		dos.writeDouble(latitude);
		dos.writeDouble(longitude);
		dos.writeDouble(altitude);
	}

}
