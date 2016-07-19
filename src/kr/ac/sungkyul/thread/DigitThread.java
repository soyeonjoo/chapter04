package kr.ac.sungkyul.thread;

public class DigitThread extends Thread {

	@Override
	public void run() {
		for(int i = 0; i<9;i++){
			System.out.print(i);
			try {
				Thread.sleep(1000);//10초
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.run();
	}



}
