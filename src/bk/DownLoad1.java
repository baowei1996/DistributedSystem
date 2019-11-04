package bk;
import java.util.concurrent.Semaphore;

class BufferChar1 {
	public static char[] buffer=new char[8];
}

class Witer1 implements Runnable{
	
	@Override
	public void run()
	{
		for(int i=0;i<26;i++)
		{
			BufferChar1.buffer[i%8]=(char)(i+65);
		}
	}


}

class Reader1 implements Runnable{

	@Override
	public void run()
	{
		for(int i=0;i<26;i++)
		{
			System.out.println(BufferChar1.buffer[i%8]);
		}
	}

}

public class DownLoad1{
	
		public static void main(String[] args) {
		
			Witer1 w = new Witer1();
			Reader1 r = new Reader1();
			Thread threadw = new Thread(w);
			Thread threadr = new Thread(r);
			threadw.start();
			threadr.start();
	}
}
