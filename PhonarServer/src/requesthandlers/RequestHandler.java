package requesthandlers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public abstract class RequestHandler {
	
	protected DataOutputStream dos;
	protected ByteArrayOutputStream baos;

	public void handleRequest(DatagramSocket socket, DatagramPacket packet) {
		baos = new ByteArrayOutputStream();
		dos = new DataOutputStream(baos);
		
		// Offset 1 to get rid of first byte which determines the type of
		// request
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(
				packet.getData(), 1, packet.getLength()));
		try {
			DatagramPacket res = internalHandle(dis);
			res.setAddress(packet.getAddress());
			res.setPort(packet.getPort());
			
			socket.send(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public abstract DatagramPacket internalHandle(DataInputStream dis) throws IOException;

}
