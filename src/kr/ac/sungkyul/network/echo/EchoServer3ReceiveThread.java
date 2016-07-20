package kr.ac.sungkyul.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer3ReceiveThread extends Thread {
	private Socket socket;

	public EchoServer3ReceiveThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		InetSocketAddress remoteAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
		String remoteHostAddress = remoteAddress.getAddress().getHostAddress();
		int remoteHostPort = remoteAddress.getPort();
		consoleLog("연결 port" + remoteHostAddress + remoteHostPort);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			String data=null;
			while (true) {

				 data = br.readLine();

				if (data == null) {
					consoleLog("Closed by client ");
					return;

				}
				consoleLog("received :" + data);
				pw.println(data);
				pw.flush();
			}
		}catch(SocketException e ){
			consoleLog("비정상적으로 클라이언트가 연결을 끊었습니다."+e);
		}
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void consoleLog(String message) {
		System.out.println("[echo server Thread#" + getId() + "]" + message);
	}

}
