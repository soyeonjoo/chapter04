package kr.ac.sungkyul.network.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer {

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
			serverSocket.bind(socketaddress);

			// 3. accept 클라이언트로 부터 연결(요청)대기
			Socket socket = serverSocket.accept();

			
				// 4. 연결 성공
				try {
					while (true) {
					// 5. IOStream
					InputStream is = socket.getInputStream();
					OutputStream os = socket.getOutputStream();

					// 6. 데이터읽기
					byte[] buffer = new byte[256];
					int readByte = is.read(buffer);
					if (readByte <= -1) {
						System.out.println("[server] closed by client");
						return;
					}
					String data = new String(buffer, 0, readByte, "utf-8");
					if (data.equals("quit")) {
						System.out.println(" 종료합니다.");
						break;

					}
					System.out.println(">>" + data);

					// 7. 데이터 쓰기
					os.write(data.getBytes("utf-8"));
				}
			} catch (SocketException e) {
				e.printStackTrace();
			} finally {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
