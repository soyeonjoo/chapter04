package kr.ac.sungkyul.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer2 {

	private final static int PORT_NUM = 5000;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;

		try {

			// 1. 소켓 생성
			serverSocket = new ServerSocket();
			InetAddress inetAddresses = InetAddress.getLocalHost();
			String address = inetAddresses.getHostAddress();
			System.out.println("[server]: " + address + "[port]:" + PORT_NUM);

			// 2. 바인딩 - 서버의 ip주소와 port를 바인딩
			InetSocketAddress socketaddress = new InetSocketAddress(address, PORT_NUM);// ip와
																						// 포트넘버를
																						// 합쳐주는
																						// 주소
			serverSocket.bind(socketaddress); // 이 소켓은 연결하는 소켓

			// 3. accept 클라이언트로 부터 연결(요청)대기
			Socket socket = serverSocket.accept(); // 데이터 통신하는 소켓

			
			// 4. 연결 성공
				// 5. IOStream
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
				String data = null;
				
				while (true) {
					
					// 6. 데이터읽기
				    data = br.readLine();//보낼때는 개행을 붙여서 보내고 받을때는 개행을 때서..
					if (data == null) {
						System.out.println("[server] closed by client");
						return;
					}
					if (data.equals("quit")) {
						System.out.println(" 종료합니다.");
						break;

					}
					System.out.println(">>" + data);

					// 7. 데이터 쓰기
					pw.println(data);
					 pw.flush();
				}
		}		
			 catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		 finally {
			try {
				if (serverSocket != null && serverSocket.isClosed() == false) {

					serverSocket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
