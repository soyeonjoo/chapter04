package kr.ac.sungkyul.thread;

public class AlphabetThread extends Thread {

//	DigitThread thread1 = new DigitThread(); //thread 생성 
//		
//		thread1.start(); //thread 실행 
		public void run() {
		for(int i = 'a'; i<'z';i++){
			System.out.print((char)i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// 10초 
		}
		}
		
	}

