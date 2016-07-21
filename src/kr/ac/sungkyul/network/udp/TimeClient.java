package kr.ac.sungkyul.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class TimeClient {//UDP로 시간 보내는 통신
	private static final String SERVER_IP = "220.67.115.228";
	private static final int PORT_NUM = 1000;
	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		DatagramSocket socket = null;

		try {

			// 1. 소켓 보내기
			socket = new DatagramSocket();

			// 2. 요청 보내기
			String request = "요청";
			byte[] requestbyte = request.getBytes();
			DatagramPacket sendData = new DatagramPacket(requestbyte, requestbyte.length,
					new InetSocketAddress(SERVER_IP, PORT_NUM));
			socket.send(sendData);

			// 3. 시간 받아오기

			DatagramPacket receiveData = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
			socket.receive(receiveData);
			String data = new String(receiveData.getData(), 0, receiveData.getLength(), StandardCharsets.UTF_8);
			System.out.println(receiveData);

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
