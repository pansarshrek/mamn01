package se.iDroid.phonar.communication;

public class Protocol {
	
	public static final byte COM_UPDATE_COORDS = 0, // COM_UPDATE_COORD <username> <latitude> <longitude> <latitude>
			COM_GET_COORDS = 1, // COM_GET_COORD
			COM_CREATE_USER = 5, // COM_CREATE_USER <username>
			ANS_SUCCESS = 6,		// ex: success to create group
			ANS_FAILURE = 7,		// ex: failed to create user
			ANS_GET_COORDS = 8;	

}
