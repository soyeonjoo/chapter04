package kr.ac.sungkyul.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClientThread extends Thread {
	private BufferedReader br;
	private PrintWriter pw = null;
	private Socket socket;
	Scanner scanner = null;
	
	

	public ChatClientThread(Socket socket, BufferedReader br) {
		this.socket = socket;
		this.br = br;
	}

	@Override
	public void run() {
		try {
			
			while (true) {
				String data =br.readLine();
				if(data ==null){
					break;
				}
				System.out.println(data);

			
				// BufferedReader br = new BufferedReader(new
				// InputStreamReader(socket.getInputStream(),StandardCharsets.UTF_8));
				// PrintWriter pw = new PrintWriter(new
				// OutputStreamWriter(socket.getOutputStream(),StandardCharsets.UTF_8));
				// /* reader를 통해 읽은 데이터 콘솔에 출력하기 (message 처리) */
				// //버퍼로 읽은 거 출력..
				//
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}