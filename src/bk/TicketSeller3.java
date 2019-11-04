package bk;

public class TicketSeller3 implements Runnable{
	
	public int agent;
	public static int numTicketToSell;
	
	@Override
	public void run()
	{
		while(numTicketToSell>0)
		{
			System.out.println("agent:"+agent+" sells:"+numTicketToSell+" ticket");
			numTicketToSell--;
		}
		System.out.println("agent:"+agent+"all done!");
	}


	public static void main(String[] args) {
		
		int numAgent=10;
		int numTicket=150;
		for(int agent=1;agent<=numAgent;agent++)
		{
			TicketSeller3 thisSeller = new TicketSeller3();
			thisSeller.agent=agent;
			thisSeller.numTicketToSell=numTicket;
			Thread thread = new Thread(thisSeller);
			thread.start();
		}
		
	}
}
