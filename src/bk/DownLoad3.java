package bk;
import java.util.concurrent.Semaphore;

class BufferChar3
{
	public static Semaphore semaphore=new Semaphore(1);  
	public static char[] buffer=new char[8];
}

class Witer3 implements Runnable{
	
	@Override
	public void run()
	{
		for(int i=0;i<26;i++)
		{
			try
			{
				BufferChar3.semaphore.acquire();
				BufferChar3.buffer[i%8]=(char)(i+65);
				BufferChar3.semaphore.release();
			}
			catch(Exception e)
			{
			}
		}
	}


}

class Reader3 implements Runnable{

	@Override
	public void run()
	{
		for(int i=0;i<26;i++)
		{
			try
			{
				BufferChar3.semaphore.acquire();
				System.out.println(BufferChar3.buffer[i%8]);
				BufferChar3.semaphore.release();
			}
			catch(Exception e)
			{
			}
		}
	}

}

public class DownLoad3{
	
		public static void main(String[] args) {
		
			Witer3 w = new Witer3();
			Reader3 r = new Reader3();
			Thread threadw = new Thread(w);
			Thread threadr = new Thread(r);
			threadw.start();
			threadr.start();
	}
}
