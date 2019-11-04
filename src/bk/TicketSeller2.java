package bk;

public class TicketSeller2 implements Runnable{
	
	public int agent;
	public int numTicketToSell;
	
	@Override
	public void run()
	{
		while(numTicketToSell>0)
		{
			System.out.println("agent:"+agent+"sells a ticket,"+numTicketToSell+" ticket left");
			numTicketToSell--;//原子操作?
		}
		System.out.println("agent:"+agent+"all done!");
	}


	public static void main(String[] args) {
		
		int numAgent=10;
		int numTicket=150;
		for(int agent=1;agent<=numAgent;agent++)
		{
			TicketSeller2 thisSeller = new TicketSeller2();
			thisSeller.agent=agent;
			thisSeller.numTicketToSell=numTicket/numAgent;
			Thread thread = new Thread(thisSeller);
			thread.start();
		}
		
	}
}
