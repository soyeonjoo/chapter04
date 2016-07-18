package kr.ac.sungkul.network.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			try {

				System.out.println(">>");
				String host = scanner.nextLine();
				if ("quit".equals(host)) {
					break;
				}
				InetAddress[] inetAddresses = InetAddress.getAllByName(host); // 베열로넘겨준것
																			  // 여러개호스트이름이있을수
																			  // 있다.
				for (InetAddress inetAddress : inetAddresses) {

					System.out.println(inetAddress.getHostAddress());

				}

			} catch (UnknownHostException e) {
				System.out.println("unknown host");
				e.printStackTrace();
			}
		}
		scanner.close();
	}
}
