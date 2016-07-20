package kr.ac.sungkyul.network.echo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer3 {
	private static final int SERVER_PORT = 3000;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;

		// 1. 소켓 만들기
		try {
			serverSocket = new ServerSocket();

			// 2. 바인딩

			InetAddress inetaddress = InetAddress.getLocalHost();
			String address = inetaddress.getHostAddress();
			InetSocketAddress inetsocketaddress = new InetSocketAddress(address, SERVER_PORT);
			serverSocket.bind(inetsocketaddress);

			System.out.println("[echo server]binding" + address + ":" + SERVER_PORT);
		
			while (true) {
				// 3.연결
				Socket socket = serverSocket.accept();
				
				EchoServer3ReceiveThread thread = new EchoServer3ReceiveThread(socket);
				thread.start();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(serverSocket !=null && serverSocket.isClosed()== false){
				serverSocket.close();}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
