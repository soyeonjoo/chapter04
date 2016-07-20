package kr.ac.sungkyul.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class ChatServerThread extends Thread {
	private String nickname;
	private Socket socket;
	public List<PrintWriter> list;

	public ChatServerThread(Socket socket, List<PrintWriter> list) {
		this.socket = socket;
		this.list = list;
	}

	@Override
	public void run() { // 두개 처리하기위해
		// 1. remote host information
		InetSocketAddress remoteAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
		String remoteHostAddress = remoteAddress.getAddress().getHostAddress();
		int remoteHostPort = remoteAddress.getPort();
		ChatServer.log("연결 port" + remoteHostAddress + ":" + remoteHostPort);

		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			// 줄단위로 읽는다.
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),
					true);
			String request = null;
			// 3. 요청 처리

			while (true) {
				request = br.readLine();
				if (request == null) {
					ChatServer.log("Closed by client");
					break;
				}
				
				// 4. 프로토콜 분석  방나간거, 채팅, 입장
				String[] tokens = request.split(":");

				if ("join".equals(tokens[0])) {
					doJoin(tokens[1], pw);

				} else if ("message".equals(tokens[0])) {
					doMessage(tokens[1]);

				} else if ("quit".equals(tokens[0])) {
					doQuit(pw);
				} else {
					ChatServer.log("에러 : 알수 없는 요청(" + tokens[0] + ")");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void doJoin(String nickname, PrintWriter writer) {// join ok 도 필요
		this.nickname = nickname;

		// 데이타 보내기
		String data = nickname + "님이 참여하였습니다.";
		broadcast(data);

		// write pool에 저장
		addPrintWriter(writer);
		
		//에크ㅡ 전송
//		writer.println("join: ok");
//		writer.flush();
//		//ChatServer.log("join:ok");
		
	}

	private void doMessage(String message) {
		//this.list=
				
		String data = nickname + ":"+message;
		broadcast(data);
	
	}

	public void doQuit(PrintWriter writer) {
		removeWriter(writer);
		
		String data = nickname + "님이 퇴장 하였습니다.";
		broadcast(data);
		
	 }
	private void removeWriter(PrintWriter writer){
		synchronized (list) {
			list.remove(writer);
		}
	}

	private void addPrintWriter(PrintWriter writer) {
		synchronized (list) {
			list.add(writer);
		}
	}

	private void broadcast(String data) {
		synchronized (list) {
			int count = list.size();
			for (int i = 0;i<count;i++) {
				PrintWriter printWriter = list.get(i);
				printWriter.println(data);
				printWriter.flush();

			}
		}

		/*
		 * String msgBroadcast = nickname +"sladl dlqwkd"
		 * synhtonized(lietWiter); for(print Writer pw : list ) { if ( 비교하기)
		 * pw.p }list.add(writer)
		 */

		// 백터쓰기 빼면서 골라올수있기떄문에
	}

}// 클래스 끝
/*
 * 
 * 
 * 
 * 
 * 
 * 
 * 

 */

// request = "join:둘리"