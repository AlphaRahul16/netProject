package com.qait.automation.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.testng.Reporter;

import net.sourceforge.htmlunit.corejs.javascript.regexp.SubString;

public class DateUtil {

	static Calendar cal;
	static SimpleDateFormat s;
	String[] arr;
	static Date date, date1, date2;

	public static String converttimestamp(Long unixSeconds) {
		Date date;
		date = new Date(unixSeconds);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the
		// format
		// of
		// your
		// date
		String formattedDate = sdf.format(date);
		return formattedDate;
	}

	public static String converttimestamp(String unixSeconds) {
		return converttimestamp(Long.parseLong(unixSeconds));
	}

	public static String[] getNextDate(String dateModule, int frequency) {
		String day;
		cal = Calendar.getInstance();
		s = new SimpleDateFormat("yyyyMMdd");
		if (dateModule.equalsIgnoreCase("day")) {
			cal.add(Calendar.DATE, frequency);
		} else if (dateModule.equalsIgnoreCase("month")) {
			cal.add(Calendar.MONTH, frequency);
		} else {
			cal.add(Calendar.YEAR, frequency);
		}
		String result = s.format(new Date(cal.getTimeInMillis()));
		String year = result.substring(0, 4);
		String month = result.substring(4, 6);
		     day = result.substring(6, 8);
		String[] date = { year, month, day };
		return date;
	}
	
	public static String getTommorrowsDate() {
		  DateFormat formatter = new SimpleDateFormat("MM");
		  SimpleDateFormat monthParse = new SimpleDateFormat("MM");
		  DateFormat dformatter = new SimpleDateFormat("DD");
		  SimpleDateFormat dateParse = new SimpleDateFormat("DD");
		  Calendar cal = Calendar.getInstance();
		  String month = Integer.toString(cal.get(Calendar.MONTH) + 1);
		  String date = Integer.toString(cal.get(Calendar.DATE) + 1);
		  try {
		   month = formatter.format(monthParse.parse(month));
		   date = dformatter.format(dateParse.parse(date));
		  } catch (ParseException e) {
		  }
		  String year = Integer.toString(cal.get(Calendar.YEAR));
		  String calDate = month + "/" + date + "/" + year;
		  return calDate;
		 }

	public static String[] getPreviousDate(String dateModule, int frequency) {
		cal = Calendar.getInstance();
		SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd");
		if (dateModule.equalsIgnoreCase("day")) {
			cal.add(Calendar.DATE, -frequency);
		} else if (dateModule.equalsIgnoreCase("month")) {
			cal.add(Calendar.MONTH, -frequency);
		} else {
			cal.add(Calendar.YEAR, -frequency);
		}
		String result = s.format(new Date(cal.getTimeInMillis()));
		String year = result.substring(0, 4);
		String month = result.substring(4, 6);
		String day = result.substring(6, 8);
		String[] date = { year, month, day };
		return date;
	}

	public String[] getDate(String dateText) {
		arr = new String[3];
		if (dateText.equalsIgnoreCase("Over 1 year ago")) {
			return getPreviousDate("year", 2);
		} else if (dateText.equalsIgnoreCase("Within past year")) {
			return getPreviousDate("year", 1);
		} else if (dateText.equalsIgnoreCase("Within 30 days from now")) {
			return getNextDate("day", 20);
		} else if (dateText.equalsIgnoreCase("Over 30 days from now")) {
			return getNextDate("month", 2);
		} else if (dateText.equalsIgnoreCase("NA")) {
			Reporter.log("STEP : Date value is NA in data sheet\n");
			return null;
		} else {
			Reporter.log("STEP : Date value in data sheet is invalid\n");
		}
		return null;

	}

	public static String getCurrentdateInStringWithGivenFormate(String formate) {
		String date = new SimpleDateFormat(formate).format(new Date());
		return date;
	}

	public static String getAnyDateForType(String formate, int difference,
			String type) {
		SimpleDateFormat formatter = new SimpleDateFormat(formate);
		Calendar cal = Calendar.getInstance();
		if (type.equalsIgnoreCase("year")) {

			cal.add(Calendar.YEAR, difference);

		} else if (type.equalsIgnoreCase("month")) {
			cal.add(Calendar.MONTH, difference);
		} else if (type.equalsIgnoreCase("date")) {
			cal.add(Calendar.DAY_OF_MONTH, difference);
		}
		String nextYear = formatter.format(cal.getTime());

		return nextYear;
	}

	public static String getAddYearWithLessOnedayInStringWithGivenFormate(
			String formate, String yearToAdd, String timeZone) {
		int yearToAddInInteger = Integer.parseInt(yearToAdd);
		// Calendar cal = Calendar.getInstance();
		// cal.add(Calendar.DATE, 365 * yearToAddInInteger);
		// Date nextYear = cal.getTime();
		// SimpleDateFormat formatter = new SimpleDateFormat(formate);
		// formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
		// String ourformat = formatter.format(nextYear.getTime());

		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();
		calendar.setTime(currentDate);
		calendar.add(Calendar.YEAR, yearToAddInInteger);
		calendar.add(Calendar.DATE, -1);
		Date nextYear = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat(formate);
		formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
		String ourformat = formatter.format(nextYear.getTime());
		return ourformat;
	}

	public static String getCurrentdateInStringWithGivenFormateForTimeZone(
			String formate, String timeZone) {
		DateFormat df = new SimpleDateFormat(formate);
		df.setTimeZone(TimeZone.getTimeZone(timeZone));
		String date = df.format(new Date());
		return date;
	}

	public static Date convertStringToDate(String dateString, String formate) {
		DateFormat sourceFormat = new SimpleDateFormat(formate);
		try {
			date = sourceFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static String convertStringToParticularDateFormat(String dateString,String formate){
		try {
			Date date= new SimpleDateFormat(formate).parse(dateString);
	      
			return new SimpleDateFormat(formate).format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String getCurrentTime(String timeFormat, String timeZone) {

		/* Specifying the format */
		DateFormat dateFormat = new SimpleDateFormat(timeFormat);
		/* Setting the Timezone */
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
		dateFormat.setTimeZone(cal.getTimeZone());
		cal.add(Calendar.MINUTE, -2);
		/* Picking the time value in the required Format */
		String currentTime = dateFormat.format(cal.getTime());
		DateFormat sourceFormat = new SimpleDateFormat(timeFormat);
		try {
			date = sourceFormat.parse(currentTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentTime;
	}

	public static int getCurrentYear() {

		int year = Calendar.getInstance().get(Calendar.YEAR);
		System.out.println("Current Year is : " + year);
		return year;
	}

	// public long numberOfDaysBetweenTwoDays(String date1,String date2,String
	// format){
	// final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
	// final LocalDate firstDate = LocalDate.parse(date1, formatter);
	// final LocalDate secondDate = LocalDate.parse(date2, formatter);
	// final long days = ChronoUnit.DAYS.between(firstDate, secondDate);
	// System.out.println("Days between: " + days);
	// return days;
	// }
	public static long numberOfDaysBetweenTwoDays(String dateFormate,
			String startDate, String endDate) {
		System.out.println("startdate :"+startDate);
		System.out.println("end date :"+endDate);
		
		SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
		try {
			date1 = sdf.parse(startDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			date2 = sdf.parse(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long diff1 = Math.round((date2.getTime() - date1.getTime()));

		long diff = Math.round((date2.getTime() - date1.getTime())
				/ (double) 86400000);
		System.out.println("diff :" + diff);
		System.out.println("diff 1" + diff1);

		return diff;
	}
}
