package kr.ac.sungkyul.thread;

public class ThreadEx {

	public static void main(String[] args) {
		DigitThread thread1 = new DigitThread(); //thread 생성 
		DigitThread thread2= new DigitThread();
		AlphabetThread thread3 = new AlphabetThread();
		Thread thread4 = new Thread(new UpperCaseAlphabetRunnableImpl());//
		
		
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
	}

}
