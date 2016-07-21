package kr.ac.sungkyul.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class EchoClient { //간단한 UDP packet을 통해서 송수신  
	private static final String SERVER_IP = "220.67.115.228";
	private static final int PORT = 1000;// 클라이언트는 소켓생성할때 포트 안써도됨, 보내줄때 같이 보냄
	private static final int BUFFER_SIZE = 1024;
	public static void main(String[] args) {
		Scanner scanner = null;
		DatagramSocket socket = null;
		try {

			// 0.키보드연결
			scanner = new Scanner(System.in);

			// 1. 소켓생성

			socket = new DatagramSocket();

			while (true) {
				// 2.
				System.out.print(">>");
				String message = scanner.nextLine();
				
				if("quit".equals(message)){
					break;
				}
				//3. 데이터 송신
				byte[] sendData = message.getBytes(StandardCharsets.UTF_8);
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
						new InetSocketAddress(SERVER_IP, PORT));// 데이타 , 길이 , 주소

				socket.send(sendPacket);
				
				
				
				//4. 데이터 수신
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);//원래 위에써 sendData써도되는데 이름으로 구분하기위해 데이터펙킷 하나더 생성
				socket.receive(receivePacket);
				String data = new String(receivePacket.getData(),0,receivePacket.getLength(),StandardCharsets.UTF_8);
				System.out.println("<<"+data);
				
				
				
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (socket != null && socket.isClosed() == false) {
				socket.close();
			}
		}

	}

}
