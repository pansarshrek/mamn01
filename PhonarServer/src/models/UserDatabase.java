package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import exceptions.UserExistsException;

public class UserDatabase {

	private static HashMap<String, User> users = new HashMap<String, User>();
	private static final String DEFAULT_NAME = "DEFAULT";
	
	public static boolean addUser(String name) throws UserExistsException {
		if (!DEFAULT_NAME.equals(name)) {
			if (users.containsKey(name)) {
				throw new UserExistsException();
			} else {
				users.put(name, new User(name, new Position(0, 0, 0)));
			}
			return true;
		}
		return false;
	}
	
	public static User getUser(String name) {
		User search = users.get(name);
		if(search != null) {
			return search;
		} else {
			return null;
		}
	}

	public static List<User> getUsers() {
		List<User> list = new ArrayList<User>();
		for (User u : users.values()) {
			list.add(u);
		}
		return list;
	}
	
	public static boolean removeUser(String name) {
		return users.remove(new User(name, null)) != null;
	}
	
	public static void updateUserPos(String name, Position pos) throws UserExistsException {
		if (!DEFAULT_NAME.equals(name)) {
			User u = getUser(name);
			if (u == null) {
				UserDatabase.addUser(name);
				u = getUser(name);
			}
			u.setPosition(pos);
		}
	}
	
}
