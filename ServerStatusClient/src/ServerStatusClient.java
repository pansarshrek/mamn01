import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class ServerStatusClient {

	public static void main(String[] args) {
		try {
			DatagramSocket ds = new DatagramSocket();
			
			byte[] buffer = new byte[65535];
			buffer[0] = 9;
			DatagramPacket dp = new DatagramPacket(buffer, 1);
			dp.setAddress(InetAddress.getByName("pansarshrek.se"));
			dp.setPort(13337);
			ds.send(dp);
			dp.setLength(buffer.length);
			ds.receive(dp);
			DataInputStream dos = new DataInputStream(new ByteArrayInputStream(dp.getData()));
			System.out.println(dos.readUTF());
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
