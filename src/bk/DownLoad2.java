package bk;
import java.util.concurrent.Semaphore;

class BufferChar2 {
	public static char[] buffer=new char[8];
}

class Witer2 implements Runnable{
	
	@Override
	public void run()
	{
		for(int i=0;i<26;i++)
		{
			synchronized(BufferChar2.buffer)
			{
				BufferChar2.buffer[i%8]=(char)(i+65);
			}
		}
	}


}

class Reader2 implements Runnable{

	@Override
	public void run()
	{
		for(int i=0;i<26;i++)
		{
			synchronized(BufferChar2.buffer)
			{
				System.out.println(BufferChar2.buffer[i%8]);
			}
		}
	}

}

public class DownLoad2{
	
		public static void main(String[] args) {
		
			Witer2 w = new Witer2();
			Reader2 r = new Reader2();
			Thread threadw = new Thread(w);
			Thread threadr = new Thread(r);
			threadw.start();
			threadr.start();
	}
}
