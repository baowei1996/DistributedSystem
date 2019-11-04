package bk;
import java.util.concurrent.Semaphore;

class BufferChar4
{
	public static Semaphore empty=new Semaphore(8);
	/*改成4代表什么？//如果改成0？//如果改成empty7，full1？//改成empty16，full0？*/
	public static Semaphore full=new Semaphore(0);
	public static char[] buffer=new char[8];
}

class Wite4 implements Runnable{
	
	@Override
	public void run()
	{
		for(int i=0;i<26;i++)
		{
			try
			{
				BufferChar4.empty.acquire();
				BufferChar4.buffer[i%8]=(char)(i+65);
				BufferChar4.full.release();
			}
			catch(Exception e)
			{
			}
		}
	}


}

class Reader4 implements Runnable{

	@Override
	public void run()
	{
		for(int i=0;i<26;i++)
		{
			try
			{
				BufferChar4.full.acquire();
				System.out.println(BufferChar4.buffer[i%8]);
				BufferChar4.empty.release();
			}
			catch(Exception e)
			{
			}
		}
	}

}

public class DownLoad4{
	
		public static void main(String[] args) {
		
			Wite4 w = new Wite4();
			Reader4 r = new Reader4();
			Thread threadw = new Thread(w);
			Thread threadr = new Thread(r);
			threadw.start();
			threadr.start();
	}
}
