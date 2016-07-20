package kr.ac.sungkyul.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient2 {
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
			
			Scanner scanner = new Scanner(System.in);

			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			PrintWriter pw = new PrintWriter( new OutputStreamWriter(socket.getOutputStream()), true); 
			
			while (true) {
				
				// 4. 데이터 쓰기
				System.out.print(">>");
				data = scanner.nextLine();
				
				//5. 데이터 읽기
				if (data == null) {
					System.out.println("[celient] close by server  ");
				}
				
				if (data.equals("exit")) {
					break;
				}
				
				pw.println(data );
				pw.flush();
				
				System.out.print("<<");
				System.out.println(br.readLine());
			}
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}

}
