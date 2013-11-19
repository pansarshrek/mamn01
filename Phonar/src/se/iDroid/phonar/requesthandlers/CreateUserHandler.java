package se.iDroid.phonar.requesthandlers;

import java.io.IOException;

import se.iDroid.phonar.communication.Protocol;

public class CreateUserHandler extends RequestHandler {
	
	public void internalHandle() throws IOException {
		
		byte status = dis.readByte();
		
		if(Protocol.ANS_SUCCESS == status) {
			// Det fakking funkade;
		} else {
			// Du kunde inte läggas till.
		}
		
	}

}
