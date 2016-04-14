package com.qait.automation.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.testng.Reporter;

public class DateUtil {

	static Calendar cal;
	static SimpleDateFormat s;
	String[] arr;
	static Date date;

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
		String day = result.substring(6, 8);
		String[] date = { year, month, day };
		return date;
	}

	public String[] getPreviousDate(String dateModule, int frequency) {
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
			Reporter.log("Step : date value is NA in data sheet\n");
			return null;
		} else {
			Reporter.log("Step : date value in data sheet is invalid\n");
		}
		return null;

	}

	public static String getCurrentdateInStringWithGivenFormate(String formate) {
		String date = new SimpleDateFormat(formate).format(new Date());
		return date;
	}

	public static String getAnyDateForType(String formate,int difference,String type)
	{
		SimpleDateFormat formatter = new SimpleDateFormat(formate);
		Calendar cal = Calendar.getInstance();
		if(type.equalsIgnoreCase("year"))
		{
			
		cal.add(Calendar.YEAR, difference); 
		
		}
		else if(type.equalsIgnoreCase("month"))
		{
			cal.add(Calendar.MONTH, difference);
		}
		else if(type.equalsIgnoreCase("date"))
		{
			cal.add(Calendar.DAY_OF_MONTH, difference);
		}
		String nextYear = formatter.format(cal.getTime());
		
		return nextYear;
	}

	public static String getAddYearWithLessOnedayInStringWithGivenFormate(
			String formate, String yearToAdd,String timeZone) {
		int yearToAddInInteger = Integer.parseInt(yearToAdd);
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DATE, 365 * yearToAddInInteger);
//		Date nextYear = cal.getTime();
//		SimpleDateFormat formatter = new SimpleDateFormat(formate);
//		formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
//		String ourformat = formatter.format(nextYear.getTime());
		
		
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

	public static String getCurrentdateInStringWithGivenFormateForTimeZone(String formate,String timeZone) {
		DateFormat df=new SimpleDateFormat(formate);
		df.setTimeZone(TimeZone.getTimeZone(timeZone));
		String date = df.format(new Date());
		return date;
	}

	public static Date convertStringToDate(String dateString,String formate)
	{
		DateFormat sourceFormat = new SimpleDateFormat(formate);
		try {
			date = sourceFormat.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}


	public static String getCurrentTime(String timeFormat, String timeZone)
	{

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

	public static int getCurrentYear()
	{

		int year = Calendar.getInstance().get(Calendar.YEAR);
		System.out.println("Current Year is : "+year);
		return year;
	}
	
	
	public long numberOfDaysBetweenTwoDays(String date1,String date2,String format){
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		 final LocalDate firstDate = LocalDate.parse(date1, formatter);
	        final LocalDate secondDate = LocalDate.parse(date2, formatter);
	        final long days = ChronoUnit.DAYS.between(firstDate, secondDate);
	        System.out.println("Days between: " + days);
	        return days;
	}


}
