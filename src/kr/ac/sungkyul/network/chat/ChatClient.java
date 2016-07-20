package kr.ac.sungkyul.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ChatClient {
	private final static String SERVER_IP = "220.67.115.228";
	private final static int PORT_NUM = 6000;
	private static String nickname = null;
	private static String message = null;
	
	public static void main(String[] args) {
		BufferedReader br=null;
		PrintWriter pw =null;
		Scanner scanner = new Scanner(System.in);

		// 1.소켓 생성
		Socket socket=null;
		
		
		try {
			socket = new Socket();
			// 2. 서버연결
			InetSocketAddress inetSocketAddress = new InetSocketAddress(SERVER_IP, PORT_NUM);
			socket.connect(inetSocketAddress);
			
			
			// 3.reader/ writer 생성
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
			//BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));//키보드 열기
			
			
			
			// 4.join 프로토콜
			System.out.print("닉네임>>");
			nickname = scanner.nextLine();
			pw.println("join:" + nickname);
			pw.flush();
			
			// 5.스레드
			ChatClientThread thread = new ChatClientThread(socket, br);
			thread.start();

			
			while(true){
			
				message = scanner.nextLine();
				if (message.equals("quit")) {
					pw.println("quit");
					pw.flush();
					break;
				} else {
					pw.println("message:"+message);
					pw.flush();

				}
				
		
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(scanner != null){
					scanner.close();
				}
				if(br !=null){
					br.close();
				}
				if(pw != null){
					pw.close();
				}
				if (socket != null && socket.isClosed() != false) {
					socket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}//main // check clientThread..

}
