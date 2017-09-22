package com.qait.tests;

import com.qait.automation.utils.DateUtil;

public class TestSample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] ar = DateUtil.getNextDate("day", 30);	
		String date=ar[1] + "/" + ar[2] + "/" + ar[0];

		System.out.println("-----date:"+date);
		System.out.println("-----expected date:"+DateUtil.convertStringToParticularDateFormat(date, "m/d/yyyy"));

		
		// TODO Auto-generated method stub
		String[] ar2 = DateUtil.getNextDate("day", 60);	
		String date2=ar2[1] + "/" + ar2[2] + "/" + ar2[0];

		System.out.println("-----date:"+date2);
		System.out.println("-----expected date:"+DateUtil.convertStringToParticularDateFormat(date2, "m/d/yyyy"));

		String[] ar3 = DateUtil.getNextDate("day", 90);	
		String date3=ar3[1] + "/" + ar3[2] + "/" + ar3[0];

		System.out.println("-----date:"+date3);
		System.out.println("-----expected date:"+DateUtil.convertStringToParticularDateFormat(date3, "m/d/yyyy"));


	}

}
