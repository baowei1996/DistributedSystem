package bk;
import java.util.concurrent.Semaphore;
import java.util.Random;

class Craft{

	public static int passed=0;
	public static Semaphore requestinspection = new Semaphore(0); 
	public static Semaphore finishinspection = new Semaphore(0); 
	public static Semaphore managerlock = new Semaphore(1); 

}




class Cashier implements Runnable{

	public static int number=0;
	public static Semaphore numberlock=new Semaphore(1);
	public static Semaphore cashierrequested=new Semaphore(0);
	public static Semaphore customers[]={new Semaphore(0),new Semaphore(0),new Semaphore(0),
	                                     new Semaphore(0),new Semaphore(0),new Semaphore(0),
										 new Semaphore(0),new Semaphore(0),new Semaphore(0),
										 new Semaphore(0)};

	@Override
	public void run()
	{
		try
		{	
			for(int i=0;i<10;i++)
			{
				cashierrequested.acquire();
				System.out.println(i+":checked out");
				customers[i].release();
			}
				
			
		}
		catch(Exception e)
		{
			System.out.println("error!!!");
		}
		
	}

}



class Customer implements Runnable{

	public int numCrafts;
	public int customerid;

	@Override 
	public void run()
	{
		try
		{	
			System.out.println(customerid+": customer entry shop");
			for(int i=0;i<numCrafts;i++)
			{
				Service service=new Service();
				service.customerid=customerid;
				Thread serviceThread = new Thread(service);
				serviceThread.start();
				System.out.println(customerid+": customer comsune "+i+" craft,total:"+numCrafts);
			}
			for(int i=0;i<numCrafts;i++)
			{
				Service.servicedone.acquire();
				System.out.println(customerid+": customer get "+i+" craft,total:"+numCrafts);
			}
			System.out.println(customerid+": customer start checkout");
			Cashier.numberlock.acquire();
			int number=Cashier.number++;
			Cashier.numberlock.release();
			Cashier.cashierrequested.release();
			Cashier.customers[number].acquire();
			
		}
		catch(Exception e)
		{
			System.out.println("error!!!");
		}
		
	}

}
class Manager implements Runnable{

	public static int approved=0;
	public static int inspected=0;

	@Override
	public void run()
	{
		try
		{	
			while(approved<HandmadeShop.total)
			{
				Craft.requestinspection.acquire();
				inspected++;
				Craft.passed=new Random().nextInt(2);
				if(Craft.passed==1)
				{
					approved++;
				}
				Craft.finishinspection.release();
				
			}
			
		}
		catch(Exception e)
		{
			System.out.println("error!!!");
		}
	}

}

class Service implements Runnable{
	
	public int customerid;
	public static Semaphore servicedone=new Semaphore(0);

	@Override
	public void run()
	{
		try
		{	
			int passed=0;
			while(passed==0)
			{
				System.out.println("Make Drink for customer:"+customerid);
				Craft.managerlock.acquire();
				Craft.requestinspection.release();
				Craft.finishinspection.acquire();
				passed=Craft.passed;
				Craft.managerlock.release();
				
			}
			servicedone.release();
			
			
		}
		catch(Exception e)
		{
			System.out.println("error!!!");
		}
		
	}

}

public class HandmadeShop{
	
	public static int total=0;
 
	public static void main(String[] args) {
		
	
		for(int i=0;i<10;i++)
		{
			Customer customer=new Customer();
			int numCrafts = new Random().nextInt(4) + 1;
			customer.numCrafts=numCrafts;
			customer.customerid=i;
			Thread customerThread = new Thread(customer);
			customerThread.start();
			total=total+numCrafts;	
		}
		Cashier cashier=new Cashier();
		Thread cashierThread=new Thread(cashier);
		cashierThread.start();
		Manager manager=new Manager();
		Thread managerThread=new Thread(manager);
		managerThread.start();
	}
}
