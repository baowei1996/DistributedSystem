package bk;

public class TicketSeller1{
	
	public void SellTicket(int agent,int numTicketToSell)
	{
		while(numTicketToSell>0)
		{
			System.out.println("agent:"+agent+"sells a ticket");
			numTicketToSell--;
		}
	}

	public static void main(String[] args) {
		TicketSeller1 s=new TicketSeller1();
		int numAgent=10;
		int numTicket=150;
		for(int agent=1;agent<=numAgent;agent++)
		{
			s.SellTicket(agent,numTicket/numAgent);
		}
		
	}
}
