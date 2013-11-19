package models;

import java.io.DataOutputStream;
import java.io.IOException;

public class User {
	
	private String name;
	private Position position;
	
	public User(String name, Position position) {
		this.name = name;
		setPosition(position);
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}
	
	public Position getPosition() {
		return position;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			return ((User) obj).name.equals(name);
		}
		return false;
	}
	
	public void toStream(DataOutputStream dos) throws IOException {
		dos.writeUTF(name);
		position.toStream(dos);
	}
}
