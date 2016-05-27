package com.qait.tests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Data_Provider_Member_Transfer_Class {

	@org.testng.annotations.DataProvider
	public static Iterator<Object[]> data() {
	
		List<Integer> listOfAllExecutableIds = com.qait.automation.utils.XlsReader.getListOfRowsNumberToBeExecuted("Scenerios");
			List<Object[]> dataToBeReturned = new ArrayList<Object[]>();
		for (Integer userData : listOfAllExecutableIds) {
			dataToBeReturned.add(new Object[] { userData });
		}
		return dataToBeReturned.iterator();
	}
}
