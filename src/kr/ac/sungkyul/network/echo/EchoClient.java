package kr.ac.sungkyul.network.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	private final static String SERVER_IP = "220.67.115.228";
	private final static int PORT_NUM = 3000;

	public static void main(String[] args) {
		Socket socket = null;
		String data = null;
		try {
			// 1. 소켓생성
			socket = new Socket();

			// 2. 서버연결
			InetSocketAddress inetSocketAddress = new InetSocketAddress(SERVER_IP, PORT_NUM);
			socket.connect(inetSocketAddress, PORT_NUM);

			// 3. IOStream 받아오기 -소켓에서
			byte[] buffer = new byte[256];

			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			Scanner scanner = new Scanner(System.in);

			while (true) {
				// 4. 데이터 쓰기
				System.out.print(">>");
				data = scanner.nextLine();
				os.write(data.getBytes("utf-8"));
				
				//5. 데이터 읽기
				int readByte = is.read(buffer);
				if (readByte <= 1) {
					System.out.println("  ");
				}
				if (data.equals("exit")) {
					break;
				}
				data = new String(buffer, 0, readByte, "utf-8");
				
				System.out.print("<<");
				System.out.println(data);
			}
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}

}
