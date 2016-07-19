package kr.ac.sungkyul.network.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class RequestHandler extends Thread {
	private Socket socket;

	public RequestHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {

			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// get IOStream
			OutputStream outputStream = socket.getOutputStream();

			// logging Remote Host IP Address & Port
			InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			consoleLog("connected from " + inetSocketAddress.getAddress().getHostAddress() + ":"
					+ inetSocketAddress.getPort());

			String line = null;
			String request = null;

			while (true) {
				line = br.readLine();
				if (line == null || line.equals("")) {
					break;
				}
				if (request == null) {
					request = line;

				}

			}
			consoleLog("request:" + request);
			String[] tokens = request.split(" ");

			if ("GET".equals(tokens[0])) {
				responseStaticResource(outputStream, tokens[1], tokens[2]);
			} else {
				// POST, PUT,DELETE 지원안함 (400 bad request)
				response400Error(outputStream, tokens[2]);
			}

			// thread가 세개 생김!!
			// 예제 응답입니다.
			// 서버 시작과 테스트를 마친 후, 주석 처리 합니다.
			/*
			 * outputStream.write("HTTP/1.1 200 OK\r\n".getBytes("UTF-8"));
			 * outputStream.write("Content-Type:text/html; charset=utf-8\r\n"
			 * .getBytes("UTF-8")); outputStream.write("\r\n".getBytes());
			 * outputStream.write(
			 * "<h1>이 페이지가 잘 보이면 실습과제 SimpleHttpServer를 시작할 준비가 된 것입니다.</h1>"
			 * .getBytes("UTF-8"));
			 */
		} catch (Exception ex) {
			consoleLog("error:" + ex);
		} finally {
			// clean-up
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}

			} catch (IOException ex) {
				consoleLog("error:" + ex);
			}
		}
	}

	public void response404Error(OutputStream outputStream, String protocol) throws IOException {// 나를
																									// 부르는애가
																									// try
																									// catch함
		File file = new File("./webapp/error/404.html");
		byte[] body = Files.readAllBytes(file.toPath());
		outputStream.write((protocol + " 404 File Not Found\r\n").getBytes("utf-8"));
		outputStream.write("Content-Type:Text/html; charset=utf-8\r\n".getBytes("utf-8"));
		outputStream.write("\r\n".getBytes("utf-8"));
		outputStream.write(body);

	}

	public void consoleLog(String message) {
		System.out.println("[RequestHandler#" + getId() + "] " + message);
	}

	private void responseStaticResource(OutputStream outputstream, String url, String protocol) throws IOException {

		if ("/".equals(url)) {
			url = "/index.html";
		}
		File file = new File("./webapp" + url);

		if (file.exists() == false) {
			response404Error(outputstream, protocol);
			return;
		}

		Path path = file.toPath();
		String mime = Files.probeContentType(path);
		byte[] body = Files.readAllBytes(path);

		// byte[] body = Files.readAllBytes(file.toPath());
		outputstream.write((protocol + " 200 OK\r\n").getBytes("utf-8"));
		outputstream.write(("Content-Type:" + mime + "; charset=utf-8\r\n").getBytes("utf-8"));
		outputstream.write("\r\n".getBytes("utf-8"));
		outputstream.write(body);
	}

	private void response400Error(OutputStream outputStream, String protocol)throws IOException {
		// catch함
		File file = new File("./webapp/error/400.html");
		byte[] body = Files.readAllBytes(file.toPath());
		outputStream.write((protocol + " 400 File Not Found\r\n").getBytes("utf-8"));
		outputStream.write("Content-Type:Text/html; charset=utf-8\r\n".getBytes("utf-8"));
		outputStream.write("\r\n".getBytes("utf-8"));
		outputStream.write(body);

	}
}
