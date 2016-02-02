package com.qait.tests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataProvider_GivingApplicationSmoke {
	@org.testng.annotations.DataProvider
	public static Iterator<Object[]> data() {

		List<String> s = com.qait.automation.utils.DataProvider.getGivingData();
		List<Object[]> dataToBeReturned = new ArrayList<Object[]>();
		for (String userData : s) {
			System.out.println("Active Case ID  : " + userData);
			dataToBeReturned.add(new Object[] { userData });

		}
		
		return dataToBeReturned.iterator();
	}
}
