package com.qait.tests;


import java.util.ArrayList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import javax.xml.ws.LogicalMessage;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;

public class Test123 {

	static int nextYear;
	Date date;
	private static final String VOICENAME = "kevin16";
	int count;
	int a = 10;
	String s1;
	static String s2;
	
	
		/*String date="05/04/2016";*/

	public static void main(String args[]){
//		
//	String memberPackage="ACS : Regular Member : Regular Member Dues C&EN-Print", amount="Balance Due: $197.00";
//    System.out.println(memberPackage.split(":",3)[2]);
//    String i=amount.split("\\$")[1];
//    int num=0;
//    double d= Double.parseDouble(i);
//    System.out.println(d);
//    memberPackage="Regular Member Dues C&EN-Print";
//    String member="Regular Member Dues C&EN-Print 3Y";
//    String Year="1 Year";
//    Year=Year.split(" ")[0];
//	int newYear=Integer.parseInt(Year); 
//    System.out.println(newYear+"-------");
//    char ar2[];
//    ar2=member.toCharArray();
//    for(int k=0;k<ar2.length;k++){
//    	if(ar2[k]=='2'||ar2[k]=='3')
//    		 num = ar2[k]-48;
//    }
//    System.out.println("*********** "+num);
//    String ar[],ar1[];
//    ar=memberPackage.split(" ",8);
////    ar1=member.split(" ",8);
////    if(ar.length==ar1.length)
////    	System.out.println("true");
////    else
////    	System.out.println("false");
//    for(int j=0;j<ar.length;j++)
//    	System.out.println(ar[j]+"----"+member.contains(ar[j]));
////    System.out.println(member.contains(memberPackage));
//    
//    int index= member.indexOf("3Y");
//    System.out.println(member.substring(index,index+1));
//	}
//		String code="Dr Peter Christopher Ellingson Procter & Gamble WHBC, FCIC Mailbox 103 6110 Center Hill Ave Cincinnati, OH 45224-1706";
//		String ar[];
//		ar=code.split(" ");
//		System.out.println(ar[ar.length-1]);
//		for(int i=0;i<ar.length;i++)
//			System.out.println(ar[i]);
		String str="https://C00616:Zx605%4095@iwebtest.acs.org/NFStage3/iweb";
		Reporter.log("\nThe application url is :- " + str.replace(str.split("@")[0], "https://").replace("@", ""),true);
		

	}
	
}

//	public static void main(String...a){
//		String date="05/04/2016";

//		System.out.println(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/YYYY"));
//		String d=DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/YYYY");
//		Date d1=DateUtil.convertStringToDate(d,"MM/dd/YYYY");
//		String d2=new SimpleDateFormat("MM/dd/YYYY").format(d1);
//		System.out.println(d2);

		
		/*String baseurl = "https://iwebtest.acs.org/YBStage1/iWeb";
		String uPs="C00616:Zx605@95";
		String abc=baseurl.replaceAll("https://iwebtest",
				"https://"+uPs.replaceAll("@", "%40")
						+"@"
						+ "iwebtest");*/
		
//		System.out.println(baseurl.replaceAll("https://iwebtest",
//				"https:" + YamlReader.getYamlValue("Authentication.userName")+":"
//						+ YamlReader.getYamlValue("Authentication.password").replaceAll("@", "%40") + "@"
//						+ "//iwebtest"));
		/*System.out.println(DateUtil.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"),"MM/dd/yyyy"));
		System.out.println( DateUtil.convertStringToDate(date,"MM/dd/yyyy"));
		System.out.println(DateUtil.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"),"MM/dd/yyyy").compareTo(
				   DateUtil.convertStringToDate(date,"MM/dd/yyyy")));*/
		
//		
//		System.out.println(DateUtil.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"),"MM/dd/yyyy"));
//		System.out.println( DateUtil.convertStringToDate(date,"MM/dd/yyyy"));
//		System.out.println(DateUtil.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"),"MM/dd/yyyy").compareTo(
//				   DateUtil.convertStringToDate(date,"MM/dd/yyyy")));
//		

//		String startDateString = "06/27/2007";
//	    DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
//	    Date startDate;
//	    try {
//	        startDate = df.parse(startDateString);
//	        String newDateString = df.format(startDate);
//	        System.out.println(newDateString);
//	    } catch (ParseException e) {
//	        e.printStackTrace();
//	    }
		//Date d1=DateUtil.convertStringToDate(d,"d/M/YYYY");
//		Calendar bcal = Calendar.getInstance();
//	    bcal.add(Calendar.YEAR, -1);
//	    bcal.set(Calendar.DAY_OF_YEAR, 1);
//	    
//	    DateFormat formatter = new SimpleDateFormat("M/d/YYYY");
//	    
//	      System.out.println(formatter.format(bcal.getTime()));
//	    bcal.add(Calendar.YEAR, -1);
//	    bcal.set(Calendar.MONTH, 11);
//	    bcal.set(Calendar.DAY_OF_MONTH, 31);
//	    System.out.println(formatter.format(bcal.getTime()));
	    //	      .contains(formatter.format(bcal.getTime())));"inp_search_max", field).getAttribute("value").trim()




/*	@Test(invocationCount = 1)
>>>>>>> 59252f4434a2c438d3d76e13c8b42fc4d75d269d
	public void avnish() {
		// List<Integer> a = new ArrayList<Integer>();
		// a= ThreadLocalRandom.current()
		// .ints(2, 10).distinct()
		// .limit(10 - 1).boxed().collect(Collectors.toList());
		// System.out.println(DateUtil
		// .getCurrentdateInStringWithGivenFormate("MMM dd, YYYY"));
		// DateUtil.numberOfDaysBetweenTwoDays("MM/d/YYYY", "4/25/2016",
		// "4/27/2016");
		for (int i = 1; i <= 4; i++) {
			s1 = abc(i+"   :    "+s1);
			System.out.println("s1::" + s1);
		}

<<<<<<< HEAD
	}
=======
		System.out.println(DateUtil
				.getCurrentdateInStringWithGivenFormate("MMM dd, YYYY"));
		DateUtil.numberOfDaysBetweenTwoDays("MM/d/YYYY", "4/25/2016",
				"4/27/2016");
	}*/



// // @org.testng.annotations.Test
// public void function1() {
// Reporter.log("<b>Avnish Rawat</b>", true);
// }
//
// // @org.testng.annotations.Test
// // public void method1() {
// // String s =
// //
// "hh jj aa bb cc dd ee ff aa bb cc dd ee ff aa bb cc dd ee ff aa bb cc dd ee ff";
// // split(s, "aa");
// // }
// // public String split(String s, String splitString) {
// // // String size=s.
// // // String[] arr = s.split(splitString);
// //
// // }
// // @org.testng.annotations.Test
// public void method111() {
// // String data1 = "Today, java is object oriented language";
// //
// // String regex = "\\bjava.*object\\b";
// //
// // Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
// // Matcher matcher = pattern.matcher(data1);
// // System.out.println(matcher.find());
//
// Pattern intsOnly = Pattern.compile("\\d*");
// Matcher makeMatch =
// intsOnly.matcher("61 days remain to submit your ballot 0 of 10 nominees ranked");
// makeMatch.find();
// String inputInt = makeMatch.group();
// System.out.println(inputInt);
// }
//
// public static String getCurrentdateInStringWithGivenFormateForTimeZone1(
// String formate, String timeZone) {
// DateFormat df = new SimpleDateFormat(formate);
// df.setTimeZone(TimeZone.getTimeZone(timeZone));
// String date = df.format(new Date());
// return date;
// }
//
// @org.testng.annotations.Test
// public void abc() {
// String format = "M/dd/YYYY";
// String date1 = "6/14/2016";
// String date2 = "4/9/2016";
// final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
// final LocalDate firstDate = LocalDate.parse(date1, formatter);
// final LocalDate secondDate = LocalDate.parse(date2, formatter);
// final long days = ChronoUnit.DAYS.between(firstDate, secondDate);
// System.out.println("Days between: " + days);
// }

// public static void main(String[] args) {
// System.out.println("in main");
// int max=11,min=1,num;
// Random random=new Random();
// List<Integer> list=new ArrayList<Integer>();
// list.add(1);
// while(list.size()<10){
// System.out.println("list size:"+list.size());
// System.out.println("in loop");
// num=random.nextInt((max - min)) + min;
// System.out.println("number is:"+num);
// if(list.contains(num)){
// System.out.println("in if");
// // tinue;
// }
// else{
// System.out.println("in else");
// list.add(num);
// }
// }
// for(int i=0;i<list.size();i++){
// System.out.println("----"+list.get(i));
// }
//
// String name="\n"
// + "hello";
// System.out.println(name.replace("\n", ""));
// }
// }

// // @org.testng.annotations.Test
// public void test123() {
// Calendar cal = Calendar.getInstance();
// // System.out.println(cal.get);
// String currentMonth = getCurrentdateInStringWithGivenFormateForTimeZone1(
// "M", "EST5EDT");
// String currentDay = getCurrentdateInStringWithGivenFormateForTimeZone1(
// "d", "EST5EDT");
// String currentYear = getCurrentdateInStringWithGivenFormateForTimeZone1(
// "yyyy", "EST5EDT");
// boolean b = ((Integer.parseInt(currentYear)) % 4) == 0;
// Calendar calendar = Calendar.getInstance();
// Date currentDate = calendar.getTime();
// System.out.format("today:      %s\n", currentDate);
// calendar.setTime(currentDate);
// calendar.add(Calendar.YEAR, 1);
// calendar.add(Calendar.DATE, -1);
// Date nextYear = calendar.getTime();
// System.out.format("next year:  %s\n", nextYear);
// }
//
// // @org.testng.annotations.Test(invocationCount = 1)
// public void method() throws ParseException {
// DateFormat df = new SimpleDateFormat("M/d/YYYY");
// df.setTimeZone(TimeZone.getTimeZone("EST5EDT"));
// String date = df.format(new Date());
// System.out.println(date);
// // getcaseIdToExecute
// // String format =
// //
// "Dues task netFORUM_Dues_Renewal_update_Task_([0-9a-zA-Z])+ scheduled successfully. Upon completion an e-mail will be sent to";
// // Pattern pattern = Pattern.compile(format);
// // String s =
// //
// "Dues task netFORUM_Dues_Renewal_update_Task_([0-9a-zA-Z])+ scheduled successfully. Upon completion an e-mail will be sent to";
// // System.out
// // .println(pattern
// // .matcher(
// //
// "Dues task netFORUM_Dues_Renewal_update_Task_7364783bhjb3y48384 scheduled successfully. Upon completion an e-mail will be sent to")
// // .matches());
// //
// pattern.matcher("Dues task netFORUM_Dues_Renewal_update_Task_7364783bhjb3y48384 scheduled successfully. Upon completion an e-mail will be sent to");
// // boolean match = s
// //
// .matches("Dues task netFORUM_Dues_Renewal_update_Task_([0-9a-zA-Z])+ scheduled successfully. Upon completion an e-mail will be sent to");
// // System.out.println(match);
// String currentDate = DateUtil.getCurrentTime("MM/dd/YYYY", "EST5EDT");
// System.out.println(currentDate);
// Date dateInDate = DateUtil.convertStringToDate(currentDate,
// "hh:mm:ss a");
// Date dateAfterMinutesAdded = DateUtils.addMinutes(dateInDate,
// Integer.parseInt("4"));
// SimpleDateFormat sdf = new SimpleDateFormat("h:mm:ss a");
// String dateWithTimeSlabInString = sdf.format(dateAfterMinutesAdded);
// System.out.println(dateWithTimeSlabInString);
// // TimeZone timeZone = TimeZone.getTimeZone("EST5EDT");
// // String time = "05:00PM";
// // String s = DateUtil.getCurrentTime("h:mm:ss a", "EST5EDT");
// // System.out.println(s);
// // //System.out.println(getCurrentTime("ss", timeZone));
// // String currentDate = DateUtil.getCurrentTime("MM/d/YYY", "EST5EDT");
// // Date dateInDate = DateUtil.convertStringToDate(time, "hh:mm:ssa");
// // //System.out.println(currentDate);
// // Date date = DateUtils.addMinutes(dateInDate, Integer.parseInt("3"));
// // SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
// // String stringDate = sdf.format(date);
// // System.out.println(stringDate);
// // getCurrentdateInStringWithGivenFormateForTimeZone("dd/MM/YYYY",
// // "EST");
// //
// getUrlResponseCode("https://C00616:ACS201523@www.ewebtest12.acs.org/NFStage4/eweb/AACTOMATemplate.aspx?Site=AACT_OMA");
// // System.out.println(callURL("https://www.google.com"));
// //
// getUrlResponseCode("https://www.ewebtest12.acs.org/NFStage4/eweb/ReportPDF.aspx?rpt_key=50C587A9-2F23-4909-9C6E-87A783A246FE&inv_code=16423087");
// }
//
// // void mySpeak()
// // {
// // Voice voice;
// // VoiceManager vm = VoiceManager.getInstance();
// // voice = vm.getVoice(VOICENAME);
// // voice.allocate();
// // try{
// // voice.speak("Hi Mr Gaur Welcome to VITS. Thanks To choose Us");
// // }catch(Exception e){}
// // }
//
// public String getCurrentTime(String timeFormat, TimeZone timeZone) {
//
// /* Specifying the format */
// DateFormat dateFormat = new SimpleDateFormat(timeFormat);
// /* Setting the Timezone */
// Calendar cal = Calendar.getInstance(timeZone);
// dateFormat.setTimeZone(cal.getTimeZone());
// cal.add(Calendar.MINUTE, -2);
// /* Picking the time value in the required Format */
// String currentTime = dateFormat.format(cal.getTime());
// DateFormat sourceFormat = new SimpleDateFormat(timeFormat);
// try {
// date = sourceFormat.parse(currentTime);
// } catch (ParseException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// }
// return currentTime;
// }
//
// public static String getCurrentdateInStringWithGivenFormateForTimeZone(
// String formate, String timeZone) {
// DateFormat df = new SimpleDateFormat(formate);
// df.setTimeZone(TimeZone.getTimeZone(timeZone));
// String date = df.format(new Date());
// System.out.println(date);
// return date;
// }
//
// // public void
// // Step01_TC01_Enter_Invalid_UserName_And_Verify_ASM_Error(String url){
// // // String methodName
// // =Thread.currentThread().getStackTrace()[1].getMethodName();
// // // System.out.println("methodName = " + methodName);
// // //
// // // String[] split=methodName.split("_");
// // // System.out.println(split[1]);
// // // return split[1];
// //
// //
// //
// // }
//
// public void getUrlResponseCode(String url) {
// HostnameVerifier allHostsValid = new HostnameVerifier() {
// public boolean verify(String hostname, SSLSession session) {
// return true;
// }
// };
//
// HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
// try {
// URL url1 = new URL(url);
// HttpURLConnection http = (HttpURLConnection) url1.openConnection();
// // int respCode = http.getResponseCode();
// // System.out.println(http.getContent());
// // System.out.println("1");
// // //System.out.println(http.getRequestProperty("Accept"));
// Map<String, List<String>> map = http.getRequestProperties();
//
// for (Map.Entry<String, List<String>> entry : map.entrySet()) {
// System.out.println(entry.getKey() + "/" + entry.getValue());
// }
//
// // System.out.println("2");
// // System.out.println(http.getResponseMessage());
// // System.out.println("3");
// //
// // System.out.println(respCode);
// http.disconnect();
// } catch (IOException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// }
//
// }
//
// public static String callURL(String myURL) {
// System.out.println("Requeted URL:" + myURL);
// StringBuilder sb = new StringBuilder();
// URLConnection urlConn = null;
// InputStreamReader in = null;
// try {
// URL url = new URL(myURL);
// urlConn = url.openConnection();
// if (urlConn != null)
// urlConn.setReadTimeout(60 * 1000);
// if (urlConn != null && urlConn.getInputStream() != null) {
// in = new InputStreamReader(urlConn.getInputStream(),
// Charset.defaultCharset());
// BufferedReader bufferedReader = new BufferedReader(in);
// if (bufferedReader != null) {
// int cp;
// while ((cp = bufferedReader.read()) != -1) {
// sb.append((char) cp);
// }
// bufferedReader.close();
// }
// }
// in.close();
// } catch (Exception e) {
// throw new RuntimeException("Exception while calling URL:" + myURL,
// e);
// }
//
// return sb.toString();
// }
// }
//
// // Calendar cal = Calendar.getInstance();
// //
// // int year = Calendar.getInstance().get(Calendar.YEAR);
// // nextYear=year+1;
// // System.out.println(year);
// // System.out.println(year%4);
// // int month = Calendar.getInstance().get(Calendar.MONTH);
// // int date = Calendar.getInstance().get(Calendar.DATE);
// // if (year % 4 == 0) {
// // System.out.println("1");
// // if (month == 2) {
// // System.out.println("2");
// // if (date < 29) {
// // System.out.println("3");
// // cal.add(Calendar.DATE, 366);
// // Date nextYear = cal.getTime();
// //
// // SimpleDateFormat formatter = new SimpleDateFormat(
// // "M/d/yyyy");
// //
// // String ourformat = formatter.format(nextYear.getTime());
// //
// // System.out.println(ourformat);
// // } else {
// // System.out.println("4");
// // cal.add(Calendar.DATE, 365);
// // Date nextYear = cal.getTime();
// //
// // SimpleDateFormat formatter = new SimpleDateFormat(
// // "M/d/yyyy");
// //
// // String ourformat = formatter.format(nextYear.getTime());
// //
// // System.out.println(ourformat);
// // }
// // } else if (month < 2) {
// // System.out.println("5");
// // cal.add(Calendar.DATE, 366);
// // Date nextYear = cal.getTime();
// //
// //
// // SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
// //
// // String ourformat = formatter.format(nextYear.getTime());
// //
// // System.out.println(ourformat);
// // } else {
// // System.out.println("6");
// // cal.add(Calendar.DATE, 365);
// // Date nextYear = cal.getTime();
// //
// // SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
// //
// // String ourformat = formatter.format(nextYear.getTime());
// //
// // System.out.println(ourformat);
// // }
// //
// // } else if (nextYear % 4 == 0) {
// // System.out.println("7");
// // cal.add(Calendar.DATE, 365);
// // int nextYearMonth = Calendar.getInstance().get(Calendar.MONTH);
// // cal.add(Calendar.DATE, -365);
// // if (nextYearMonth > 2) {
// // System.out.println("8");
// // cal.add(Calendar.DATE, 366);
// // Date nextYear = cal.getTime();
// //
// // SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
// //
// // String ourformat = formatter.format(nextYear.getTime());
// //
// // System.out.println(ourformat);
// // } else {
// // System.out.println("9");
// // cal.add(Calendar.DATE, 365);
// // Date nextYear = cal.getTime();
// //
// // SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
// //
// // String ourformat = formatter.format(nextYear.getTime());
// //
// // System.out.println(ourformat);
// // }
// // }
//
// // Calendar cal = Calendar.getInstance();
// // cal.add(Calendar.DATE, 365);
// // Date nextYear = cal.getTime();
// //
// // SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
// //
// // String ourformat = formatter.format(nextYear.getTime());
// //
// // System.out.println(ourformat);
// //
//
// // String s =
// // DateUtil.getCurrentdateInStringWithGivenFormate("M/DD/YYYY");
// // System.out.println(s);
//
// // Date date = new Date(System.currentTimeMillis());
// // SimpleDateFormat formatter = new SimpleDateFormat("M/d/YYYY");
// // String s = formatter.format(date);
// // System.out.println(s);
//
// // TimeZone.setDefault(TimeZone.getTimeZone("EST"));
// // String date = new SimpleDateFormat("M/d/yyy").format(new Date());
// // TimeZone.setDefault(TimeZone.getTimeZone("IST"));
// // String date1 = new SimpleDateFormat("M/d/yyy").format(new Date());
// // System.out.println(date);
// // System.out.println(date1);
//
// // SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyy");
// // String dateInString = formatter.format(new Date());
// // Date date = formatter.parse(dateInString);
// // TimeZone tz = TimeZone.getDefault();
// //
// //
// // SimpleDateFormat sdfAmerica = new
// // SimpleDateFormat("dd-M-yyyy hh:mm:ss a");
// // TimeZone tzInAmerica = TimeZone.getTimeZone("America/New_York");
// // sdfAmerica.setTimeZone(tzInAmerica);
// //
// // String sDateInAmerica = sdfAmerica.format(date); // Convert to String
// // first
// // Date dateInAmerica = formatter.parse(sDateInAmerica);
// //
// // System.out.println("\nTimeZone : " + tzInAmerica.getID() +
// // " - " + tzInAmerica.getDisplayName());
// // System.out.println("TimeZone : " + tzInAmerica);
// // System.out.println("Date (String) : " + sDateInAmerica);
// // System.out.println("Date (Object) : " +
// // formatter.format(dateInAmerica));
// // String baseurl="http://C00525:ACS2015$@dev-iweb12/NFDev6/iWeb";
// // int begIndex = baseurl.indexOf("//");
// // int endIndex = baseurl.indexOf("@");
// // String s1 = baseurl.substring(begIndex + 2, endIndex + 1);
// // String s2 = baseurl.replace(s1, "");
// // System.out.println(s2);

