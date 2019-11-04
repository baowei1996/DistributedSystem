package bk;
import java.util.concurrent.Semaphore;

class BufferSomething
{
	public static Semaphore[] kuaiZi={new Semaphore(1),new Semaphore(1),new Semaphore(1),
									  new Semaphore(1),new Semaphore(1)};
	//限制了最多允许四个人吃，避免了循环等待
	public static Semaphore allowEat=new Semaphore(4);
}

class Phi implements Runnable
{
	public int phiId;
	 
	@Override
	public void run()
	{
		try
		{	
			for(int i=0;i<5000;i++)
			{
				BufferSomething.allowEat.acquire();
				BufferSomething.kuaiZi[phiId].acquire();
				BufferSomething.kuaiZi[(phiId+1)%5].acquire();
				System.out.println("phi:"+phiId+" eating and stop thinking");
				BufferSomething.kuaiZi[phiId].release();
				BufferSomething.kuaiZi[(phiId+1)%5].release();
				BufferSomething.allowEat.release();
				System.out.println("phi:"+phiId+" eated and start thinking");
			}
		}
		catch(Exception e)
		{
			System.out.println("error!!!");
		}
	}
}

public class PhiProblem{
	
		public static void main(String[] args) {
			
			for(int i=0;i<5;i++)
			{
				Phi phi=new Phi();
				phi.phiId=i;
				Thread phiThread=new Thread(phi);
				phiThread.start();
				
			}
		

	}
}
