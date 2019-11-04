package bk;

public class TicketSeller4 implements Runnable{
	
	public int agent;
	public static int numTicketToSell;
	public static String lock;
	
	@Override
	public void run()
	{
		synchronized(lock)
		{
			while(numTicketToSell>0)
			{

					System.out.println("agent:"+agent+" sells:"+numTicketToSell+" ticket");
					numTicketToSell--;
							
				
			}
		}
		System.out.println("agent:"+agent+"all done!");
	}


	public static void main(String[] args) {
		
		int numAgent=10;
		int numTicket=150;
		String lock="mylock";
		for(int agent=1;agent<=numAgent;agent++)
		{
			TicketSeller4 thisSeller = new TicketSeller4();
			thisSeller.agent=agent;
			thisSeller.numTicketToSell=numTicket;
			thisSeller.lock=lock;
			Thread thread = new Thread(thisSeller);
			thread.start();
		}
		
	}
}
