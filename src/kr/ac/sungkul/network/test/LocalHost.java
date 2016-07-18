package kr.ac.sungkul.network.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {

	public static void main(String[] args) {//호스트네임.. - 컴퓨터이름
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String homename = inetAddress.getHostName();
			String hostAddress = inetAddress.getHostAddress();
			byte[] addresses = inetAddress.getAddress(); //바이트 단위로 빼올떄.
			
			
			System.out.println(homename);
			System.out.println(hostAddress);
			
			for( int i= 0 ; i<addresses.length; i++){
				System.out.print(addresses[i]& 0x000000ff); // - 가 나온다.. int로 캐스팅하면 - 가 따라오기 때문에 & 0x000000ff를 써야한다 
				if(i<addresses.length -1 ){
				System.out.print(".");
				}
			}
			
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		}
	}

}
