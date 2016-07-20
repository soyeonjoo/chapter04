package kr.ac.sungkyul.network.chat;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	private final static int PORT_NUM = 6000;
	public static void main(String[] args) {
		List<PrintWriter> list = new ArrayList<PrintWriter>();
		ServerSocket serverSocket = null;
		try {
			
			 serverSocket = new ServerSocket();
		
			InetAddress inetaddress = InetAddress.getLocalHost();
			String Address = inetaddress.getHostAddress();
			InetSocketAddress socketAddress = new InetSocketAddress(Address,PORT_NUM);
			
			serverSocket.bind(socketAddress);
		
			while(true){
				
				
				Socket socket = serverSocket.accept();
				
				ChatServerThread thread = new ChatServerThread(socket, list);
				thread.start();
				
				 
			}
		
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(serverSocket!=null && serverSocket.isClosed()==false){
				serverSocket.close();}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		

	}

	public static void log(String s) {
		System.out.println(s);
	}
}
