package kr.ac.sungkyul.network.echo;

import java.net.InetSocketAddress;
import java.net.Socket;

public class EchoServer3ReceiveThread extends Thread {
	private Socket socket;

	public EchoServer3ReceiveThread(Socket socket) {
		this.socket=socket;
	}

	@Override
	public void run() {
		InetSocketAddress remoteAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
		String remoteHostAddress = re
	}
	

}
