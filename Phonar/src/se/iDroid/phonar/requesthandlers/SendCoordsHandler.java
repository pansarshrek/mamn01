package se.iDroid.phonar.requesthandlers;

import java.io.IOException;

import se.iDroid.phonar.communication.Protocol;

public class SendCoordsHandler extends RequestHandler{
	
	public void internalHandle() throws IOException {
		int status = dis.readInt();
		
		if(Protocol.ANS_SUCCESS == status) {
			// Success
		} else {
			// Fail
		}
	}

}
