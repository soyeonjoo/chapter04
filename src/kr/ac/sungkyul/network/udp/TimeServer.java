package kr.ac.sungkyul.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeServer {//UDP로 시간 보내는 통신
	private static final String SERVER_IP = "220.67.115.228";
	private static final int PORT_NUM = 1000;
	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {

		DatagramSocket socket = null;
		Date now = new Date();
		try {

			// 1. 소켓 생성
			socket = new DatagramSocket(PORT_NUM);

			DatagramPacket receivepacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
			socket.receive(receivepacket);
			String message = new String(receivepacket.getData(), 0, receivepacket.getLength(), StandardCharsets.UTF_8);
			System.out.println(message);
			// 2. 데이터 전송

			if ("요청".equals(message)) {
				System.out.println("연결성공");
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
				String data = format.format(new Date());
				byte[] sendmessage = data.getBytes();
				DatagramPacket sendpacket = new DatagramPacket(sendmessage, sendmessage.length,
						new InetSocketAddress(receivepacket.getAddress(), receivepacket.getPort()));

				socket.send(sendpacket);

			}

		}

		catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
