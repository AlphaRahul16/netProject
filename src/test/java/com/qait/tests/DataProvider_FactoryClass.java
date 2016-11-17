package com.qait.tests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataProvider_FactoryClass {

	static String sheetName;

	@org.testng.annotations.DataProvider
	public static Iterator<Object[]> data() {
		List<String> listOfAllExecutableIds = com.qait.automation.utils.DataProvider
				.get(sheetName);
		List<Object[]> dataToBeReturned = new ArrayList<Object[]>();
		for (String userData : listOfAllExecutableIds) {
			dataToBeReturned.add(new Object[] { userData });
		}
		return dataToBeReturned.iterator();
	}
}
