package bk;
import java.util.concurrent.Semaphore;

public class TicketSeller5 implements Runnable{
	
	public int agent;
	public static int numTicketToSell=150;
//	public static String lock;
	public static Semaphore semaphore;  
	
	@Override
	public void run()
	{
		try
		{	
			while(true)
			{
				int num=0;
				semaphore.acquire();
				if(numTicketToSell>0)
				{
					numTicketToSell--;
					num=numTicketToSell;
				}
				else
				{
					semaphore.release();
					break;	
				}
				semaphore.release();
				System.out.println("agent:"+agent+" sells:"+num+" ticket");	
			}
			
		}
		catch(Exception e)
		{
		}
		System.out.println("agent:"+agent+"all done!");
	}


	public static void main(String[] args) {
		
		int numAgent=10;
		//int numTicket=150;
//		String lock="mylock";
		Semaphore semaphore = new Semaphore(1); 
		for(int agent=1;agent<=numAgent;agent++)
		{
			TicketSeller5 thisSeller = new TicketSeller5();
			thisSeller.agent=agent;
			//thisSeller.numTicketToSell=numTicket;
//			thisSeller.lock=lock;
			thisSeller.semaphore=semaphore;
			Thread thread = new Thread(thisSeller);
			thread.start();
		}
		
	}
}
