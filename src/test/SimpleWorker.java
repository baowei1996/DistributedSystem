package test;


import DCS.MigrantWorker;
import DCS.WareHouse;

import java.lang.reflect.Method;

public class SimpleWorker extends MigrantWorker
{	
	public WareHouse doTask(WareHouse inhouse)
	{
		String word = inhouse.getString("word");
		System.out.println(word+" from Contractor.");
		return new WareHouse("word", word+" world!");
	}
	
	public static void main(String[] args)
	{
		SimpleWorker mw = new SimpleWorker();
		mw.waitWorking("127.0.0.1",8001,"simpleworker");
//		WareHouse inhouse = new WareHouse("word","hello");
//		try {
//			Method method = Class.forName("test.SimpleWorker").getMethod("doTask", WareHouse.class);
//			WareHouse out = (WareHouse) method.invoke(mw,inhouse);
//			System.out.println(out);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}