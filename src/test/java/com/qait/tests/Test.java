package com.qait.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.commons.lang3.time.DateUtils;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.DateUtil;
import com.qait.keywords.MembershipPageActions_IWEB;

public class Test {
	static int nextYear;
	Date date;
	private static final String VOICENAME = "kevin16";
	int count;

	@org.testng.annotations.Test(invocationCount = 1)
	public void method() throws ParseException {
		DateFormat df=new SimpleDateFormat("M/d/YYYY");
		df.setTimeZone(TimeZone.getTimeZone("EST5EDT"));
		String date = df.format(new Date());
		System.out.println(date);
//		getcaseIdToExecute

//		String format = "Dues task netFORUM_Dues_Renewal_update_Task_([0-9a-zA-Z])+ scheduled successfully. Upon completion an e-mail will be sent to";
//		Pattern pattern = Pattern.compile(format);
//		String s = "Dues task netFORUM_Dues_Renewal_update_Task_([0-9a-zA-Z])+ scheduled successfully. Upon completion an e-mail will be sent to";
//		System.out
//				.println(pattern
//						.matcher(
//								"Dues task netFORUM_Dues_Renewal_update_Task_7364783bhjb3y48384 scheduled successfully. Upon completion an e-mail will be sent to")
//						.matches());
//		pattern.matcher("Dues task netFORUM_Dues_Renewal_update_Task_7364783bhjb3y48384 scheduled successfully. Upon completion an e-mail will be sent to");
//		boolean match = s
//				.matches("Dues task netFORUM_Dues_Renewal_update_Task_([0-9a-zA-Z])+ scheduled successfully. Upon completion an e-mail will be sent to");
//		System.out.println(match);
		 String currentDate = DateUtil.getCurrentTime("MM/dd/YYYY",
		 "EST5EDT");
		 System.out.println(currentDate);
		 Date dateInDate = DateUtil.convertStringToDate(currentDate,
		 "hh:mm:ss a");
		 Date dateAfterMinutesAdded = DateUtils.addMinutes(dateInDate,
		 Integer.parseInt("4"));
		 SimpleDateFormat sdf = new SimpleDateFormat("h:mm:ss a");
		 String dateWithTimeSlabInString = sdf.format(dateAfterMinutesAdded);
		 System.out.println(dateWithTimeSlabInString);
		// TimeZone timeZone = TimeZone.getTimeZone("EST5EDT");
		// String time = "05:00PM";
		// String s = DateUtil.getCurrentTime("h:mm:ss a", "EST5EDT");
		// System.out.println(s);
		// //System.out.println(getCurrentTime("ss", timeZone));
		// String currentDate = DateUtil.getCurrentTime("MM/d/YYY", "EST5EDT");
		// Date dateInDate = DateUtil.convertStringToDate(time, "hh:mm:ssa");
		// //System.out.println(currentDate);
		// Date date = DateUtils.addMinutes(dateInDate, Integer.parseInt("3"));
		// SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
		// String stringDate = sdf.format(date);
		// System.out.println(stringDate);
		// getCurrentdateInStringWithGivenFormateForTimeZone("dd/MM/YYYY",
		// "EST");
		// getUrlResponseCode("https://C00616:ACS201523@www.ewebtest12.acs.org/NFStage4/eweb/AACTOMATemplate.aspx?Site=AACT_OMA");
		// System.out.println(callURL("https://www.google.com"));
		// getUrlResponseCode("https://www.ewebtest12.acs.org/NFStage4/eweb/ReportPDF.aspx?rpt_key=50C587A9-2F23-4909-9C6E-87A783A246FE&inv_code=16423087");
	}

	// void mySpeak()
	// {
	// Voice voice;
	// VoiceManager vm = VoiceManager.getInstance();
	// voice = vm.getVoice(VOICENAME);
	// voice.allocate();
	// try{
	// voice.speak("Hi Mr Gaur Welcome to VITS. Thanks To choose Us");
	// }catch(Exception e){}
	// }

	public String getCurrentTime(String timeFormat, TimeZone timeZone) {

		/* Specifying the format */
		DateFormat dateFormat = new SimpleDateFormat(timeFormat);
		/* Setting the Timezone */
		Calendar cal = Calendar.getInstance(timeZone);
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

	public static String getCurrentdateInStringWithGivenFormateForTimeZone(
			String formate, String timeZone) {
		DateFormat df = new SimpleDateFormat(formate);
		df.setTimeZone(TimeZone.getTimeZone(timeZone));
		String date = df.format(new Date());
		System.out.println(date);
		return date;
	}

	// public void
	// Step01_TC01_Enter_Invalid_UserName_And_Verify_ASM_Error(String url){
	// // String methodName
	// =Thread.currentThread().getStackTrace()[1].getMethodName();
	// // System.out.println("methodName = " + methodName);
	// //
	// // String[] split=methodName.split("_");
	// // System.out.println(split[1]);
	// // return split[1];
	//
	//
	//
	// }

	public void getUrlResponseCode(String url) {
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};

		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		try {
			URL url1 = new URL(url);
			HttpURLConnection http = (HttpURLConnection) url1.openConnection();
			// int respCode = http.getResponseCode();
			// System.out.println(http.getContent());
			// System.out.println("1");
			// //System.out.println(http.getRequestProperty("Accept"));
			Map<String, List<String>> map = http.getRequestProperties();

			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				System.out.println(entry.getKey() + "/" + entry.getValue());
			}

			// System.out.println("2");
			// System.out.println(http.getResponseMessage());
			// System.out.println("3");
			//
			// System.out.println(respCode);
			http.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String callURL(String myURL) {
		System.out.println("Requeted URL:" + myURL);
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		try {
			URL url = new URL(myURL);
			urlConn = url.openConnection();
			if (urlConn != null)
				urlConn.setReadTimeout(60 * 1000);
			if (urlConn != null && urlConn.getInputStream() != null) {
				in = new InputStreamReader(urlConn.getInputStream(),
						Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(in);
				if (bufferedReader != null) {
					int cp;
					while ((cp = bufferedReader.read()) != -1) {
						sb.append((char) cp);
					}
					bufferedReader.close();
				}
			}
			in.close();
		} catch (Exception e) {
			throw new RuntimeException("Exception while calling URL:" + myURL,
					e);
		}

		return sb.toString();
	}
}

// Calendar cal = Calendar.getInstance();
//
// int year = Calendar.getInstance().get(Calendar.YEAR);
// nextYear=year+1;
// System.out.println(year);
// System.out.println(year%4);
// int month = Calendar.getInstance().get(Calendar.MONTH);
// int date = Calendar.getInstance().get(Calendar.DATE);
// if (year % 4 == 0) {
// System.out.println("1");
// if (month == 2) {
// System.out.println("2");
// if (date < 29) {
// System.out.println("3");
// cal.add(Calendar.DATE, 366);
// Date nextYear = cal.getTime();
//
// SimpleDateFormat formatter = new SimpleDateFormat(
// "M/d/yyyy");
//
// String ourformat = formatter.format(nextYear.getTime());
//
// System.out.println(ourformat);
// } else {
// System.out.println("4");
// cal.add(Calendar.DATE, 365);
// Date nextYear = cal.getTime();
//
// SimpleDateFormat formatter = new SimpleDateFormat(
// "M/d/yyyy");
//
// String ourformat = formatter.format(nextYear.getTime());
//
// System.out.println(ourformat);
// }
// } else if (month < 2) {
// System.out.println("5");
// cal.add(Calendar.DATE, 366);
// Date nextYear = cal.getTime();
//
//
// SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
//
// String ourformat = formatter.format(nextYear.getTime());
//
// System.out.println(ourformat);
// } else {
// System.out.println("6");
// cal.add(Calendar.DATE, 365);
// Date nextYear = cal.getTime();
//
// SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
//
// String ourformat = formatter.format(nextYear.getTime());
//
// System.out.println(ourformat);
// }
//
// } else if (nextYear % 4 == 0) {
// System.out.println("7");
// cal.add(Calendar.DATE, 365);
// int nextYearMonth = Calendar.getInstance().get(Calendar.MONTH);
// cal.add(Calendar.DATE, -365);
// if (nextYearMonth > 2) {
// System.out.println("8");
// cal.add(Calendar.DATE, 366);
// Date nextYear = cal.getTime();
//
// SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
//
// String ourformat = formatter.format(nextYear.getTime());
//
// System.out.println(ourformat);
// } else {
// System.out.println("9");
// cal.add(Calendar.DATE, 365);
// Date nextYear = cal.getTime();
//
// SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
//
// String ourformat = formatter.format(nextYear.getTime());
//
// System.out.println(ourformat);
// }
// }

// Calendar cal = Calendar.getInstance();
// cal.add(Calendar.DATE, 365);
// Date nextYear = cal.getTime();
//
// SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
//
// String ourformat = formatter.format(nextYear.getTime());
//
// System.out.println(ourformat);
//

// String s =
// DateUtil.getCurrentdateInStringWithGivenFormate("M/DD/YYYY");
// System.out.println(s);

// Date date = new Date(System.currentTimeMillis());
// SimpleDateFormat formatter = new SimpleDateFormat("M/d/YYYY");
// String s = formatter.format(date);
// System.out.println(s);

// TimeZone.setDefault(TimeZone.getTimeZone("EST"));
// String date = new SimpleDateFormat("M/d/yyy").format(new Date());
// TimeZone.setDefault(TimeZone.getTimeZone("IST"));
// String date1 = new SimpleDateFormat("M/d/yyy").format(new Date());
// System.out.println(date);
// System.out.println(date1);

// SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyy");
// String dateInString = formatter.format(new Date());
// Date date = formatter.parse(dateInString);
// TimeZone tz = TimeZone.getDefault();
//
//
// SimpleDateFormat sdfAmerica = new
// SimpleDateFormat("dd-M-yyyy hh:mm:ss a");
// TimeZone tzInAmerica = TimeZone.getTimeZone("America/New_York");
// sdfAmerica.setTimeZone(tzInAmerica);
//
// String sDateInAmerica = sdfAmerica.format(date); // Convert to String
// first
// Date dateInAmerica = formatter.parse(sDateInAmerica);
//
// System.out.println("\nTimeZone : " + tzInAmerica.getID() +
// " - " + tzInAmerica.getDisplayName());
// System.out.println("TimeZone : " + tzInAmerica);
// System.out.println("Date (String) : " + sDateInAmerica);
// System.out.println("Date (Object) : " +
// formatter.format(dateInAmerica));
// String baseurl="http://C00525:ACS2015$@dev-iweb12/NFDev6/iWeb";
// int begIndex = baseurl.indexOf("//");
// int endIndex = baseurl.indexOf("@");
// String s1 = baseurl.substring(begIndex + 2, endIndex + 1);
// String s2 = baseurl.replace(s1, "");
// System.out.println(s2);

