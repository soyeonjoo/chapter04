package kr.ac.sungkul.network.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class TCPClient {

	private static final String SERVER_IP = "220.67.115.228";
	private static final int SERVER_PORT = 3000;
	public static void main(String[] args) {
		Socket socket =null;
		try {
			
		//1. 소켓 생성
		socket = new Socket();
		
		//1.-1 소켓 버퍼 사이즈확인
		int receiveBufferSize =socket.getReceiveBufferSize();
		int sendBufferSize = socket.getSendBufferSize();
		
		System.out.println(receiveBufferSize + ":"+sendBufferSize);
		//1-2 소켓 버퍼사이즈 늘리기 
		socket.setReceiveBufferSize(1024 * 1024 *10);
		socket.setSendBufferSize(1024 * 1024 * 10 );
		
		System.out.println(socket.getReceiveBufferSize() + ":"+socket.getSendBufferSize());
		
		
		//1-3 TCP NODELAY 옵션(Nagle OFF)
		socket.setTcpNoDelay(true); //nagle이 off여야 딜레이가 되지않기 때문에 true
		
		//1-4 so_timeout
		socket.setSoTimeout(5000); //1 = 1ms 5초
		System.out.println("");
		
		
		//2. 서버연결
		InetSocketAddress serverSocketAddress = new InetSocketAddress(SERVER_IP,SERVER_PORT);
		
			socket.connect(serverSocketAddress);
		
		//3. IOStream 받아오기 -소켓에서
		InputStream is = socket.getInputStream();
		OutputStream os = socket.getOutputStream();
		
		
		//4. 데이터 쓰기
		String data = "Hello World\n";
		os.write(data.getBytes("utf-8")); //데이터 보내기 byte로 
		
		//5. 데이터 읽기
		byte[] buffer = new byte[256];
		int readBytes = is.read(buffer); // read는  blocking 상태
		if(readBytes <= -1){//서버가 연결을 끊음
			System.out.println("client] close by server");
		}
		
			data = new String(buffer, 0, readBytes, "utf-8");// 인코딩하려구 
			System.out.println("[client] recieved: "+ data);
		}catch (SocketTimeoutException e){
			System.out.println("[client] 소켓에서 데이터를 읽는데 시간이 지연되었습니다.");
		}
		catch (SocketException e){
			System.out.println("[client]비정상적으로 서버로 부터 연결이 끊어졌습니다...;");
		}
		
		catch (IOException e) {
			
			e.printStackTrace();
		}finally {
			if(socket !=null && socket.isClosed() == false){
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
