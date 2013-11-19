package communication;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import requesthandlers.CreateUserHandler;
import requesthandlers.GetCoordsHandler;
import requesthandlers.NoHandler;
import requesthandlers.RequestHandler;
import requesthandlers.UpdateCoordsHandler;

public class Communication {
	
	public static final byte COM_UPDATE_COORDS = 0,
		COM_GET_COORDS = 1,
		COM_CREATE_USER = 5,
		ANS_SUCCESS = 6,		// ex: success to create user
		ANS_FAILURE = 7,		// ex: failed to create user
		ANS_GET_COORDS = 8;		// contains data for COM_GET_COORDS request
	
	public static void handleRequest(DatagramSocket socket, DatagramPacket packet) {
		if (packet.getLength() > 0) {
			byte firstByte = packet.getData()[0];
			RequestHandler handler = null;
			System.out.println((int) firstByte);
			switch (firstByte) {
			case COM_UPDATE_COORDS:
				handler = new UpdateCoordsHandler();
				break;
			case COM_GET_COORDS:
				handler = new GetCoordsHandler();
				break;
			case COM_CREATE_USER:
				handler = new CreateUserHandler();
				break;
			default:
				handler = new NoHandler();
				break;
			}
			
			handler.handleRequest(socket, packet);
			
		}
		
	}

}
