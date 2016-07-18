package kr.ac.sungkul.network.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer {
	private final static int SERVER_PORT = 3000;

	public static void main(String[] args) {
		ServerSocket serverSocket =null;
		try {
			// 1. 서버 소켓 생성
			serverSocket = new ServerSocket();

			// 2. 바인딩 - 서버의 ip주소와 port를 바인딩
			InetAddress inetAddress = InetAddress.getLocalHost();// 자꾸 아이피 쓰기
																	// 귀찬으니깐 adr
																	// 가져와서
																	// ↓아래에서 이용
			String serverAddress = inetAddress.getHostAddress();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(serverAddress, SERVER_PORT);// 아이피와	 포트번호 둘다 들어감
			serverSocket.bind(inetSocketAddress);
			System.out.println("[server] bind: " + serverAddress + " :" + SERVER_PORT);

			// 3. accept 클라이언트로 부터 연결(요청)대기
			Socket socket = serverSocket.accept();// blocking 됨 - 밑으로 내려가지 앉고
													// 기다리고 있는것

			// 4. 연결 성공
			InetSocketAddress remoteAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			String remoteHostAddress = remoteAddress.getAddress().getHostAddress();
			int remoteHostPort = remoteAddress.getPort();
			System.out.println(
					"[server] 연결 성공 from" + remoteAddress.getAddress().getHostAddress() + remoteAddress.getPort());

			try {
				
				// 5. IOStream
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();

				// 6.데이터 읽기
				byte[] buffer = new byte[256];
				int readBytes = is.read(buffer); // blocked
				if (readBytes <= -1) { // 클라이언트가 연결을 끊었다.(정상종료)
					System.out.println("[server] closed by client");
					return;
				}
				String data = new String(buffer, 0, readBytes, "utf-8");
				
				System.out.println("[server] recieved :" + data);

				// 7. 데이터 쓰기
				os.write(data.getBytes("utf-8"));
				
				
			} catch (SocketException e){
				System.out.println("[server] 비정상적으로 클라이언트가 연결을 끊었습니다.");
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				// 8. 데이터 통신 소켓 닫기
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 9. 서버 소켓 닫기
				if (serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
