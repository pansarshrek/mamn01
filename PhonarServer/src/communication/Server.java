package communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {

	private static final int DEFAULT_PORT = 13337;
	private DatagramSocket socket;
	
	public Server(int port) throws IOException {
		socket = new DatagramSocket(port);
	}
	
	public static void main(String[] args) {
		int port = DEFAULT_PORT;
		if (args.length >= 1) {
			port = Integer.parseInt(args[0]);
		}
		
		try {
			Server server = new Server(port);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("\n\nFailed to start server on port: " + port);
		}
		
	}

	private void start() {
		byte[] megaBuffer = new byte[65535];
		DatagramPacket megaDatagramPacket = new DatagramPacket(megaBuffer, 0);
		System.out.println("Server running");
		while (true) {
			megaDatagramPacket.setLength(megaBuffer.length);
			try {
				socket.receive(megaDatagramPacket);
				Communication.handleRequest(socket, megaDatagramPacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
