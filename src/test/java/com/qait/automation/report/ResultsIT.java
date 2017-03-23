package com.qait.automation.report;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;


public class ResultsIT extends ReformatTestFile {

    String today = new Date().toString();
    String resultOfRun = null;
    String host = "smtp.gmail.com";
    String from = "automation.resultsqait@gmail.com";
    String password = "QaitAutomation";
    int port = 25;
    String failureResults = "";
    String skippedResults = "";
    String passedResult = "";
    boolean sendResults = false;
    YamlReader util = new YamlReader();
    final String projectName = "ACS Society";
    private String totaltest;
    private String passedResults,executiontime;
	private String testname;
	private String xmlPath = "./target/surefire-reports/testng-results.xml";
    public static int count = 0;
    
    NumberFormat integerFormat = NumberFormat.getIntegerInstance();
    @BeforeClass
    void setupMailConfig() {
    	YamlReader.setYamlFilePath();
    }
    public ResultsIT(String testname) {
    	this.testname = testname;
	}

    @Test
    public void changeTimeStamp() throws IOException  {
    	System.out.println("Testname "+testname);
    	if(testname.contains("Default"))
    	{
    	 testname=System.getProperty("testngxml", System.getProperty("test")).replace(".xml", "").trim();
    	}
    	System.out.println("Testname "+testname);
   
    	//String html = readLargerTextFile("./target/surefire-reports/"+this.getClass().getSimpleName()+".html");
       String html = readLargerTextFile("./target/surefire-reports/emailable-report.html");
        String logspath="./Acslogs/"+DateUtil.getCurrentdateInStringWithGivenFormate("dd MMM yyyy")+"/"
        +testname+" "+DateUtil.getCurrentdateInStringWithGivenFormate("HH_mm_ss_a")+"_emailable-report.html";
        html = replacealltimestamp(html);
        writeLargerTextFile("./target/surefire-reports/emailable-report.html", html);
        System.out.println("=============Path==================="+logspath);
       writeLargerTextFile(logspath, html);
    }
    

    @Test(dependsOnMethods = "changeTimeStamp")
    public void sendResultsMail() throws MessagingException, IOException {

        if (true) { // send email is true *************************
            Message message = new MimeMessage(getSession());
            message.addFrom(new InternetAddress[]{(new InternetAddress(from))});
            setMailRecipient(message);
            message.setContent(setAttachment());
            message.setSubject(setMailSubject());
            Session session = getSession();
            Transport transport = session.getTransport("smtps");
            transport.connect(host, from, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        System.out.println("Reports emailed");

    }

   

    private Session getSession() {
    	 Authenticator authenticator = new Authenticator(from, password);
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtps");
        properties.put("mail.smtps.auth", "true");
        properties.setProperty("mail.smtp.submitter", authenticator
                .getPasswordAuthentication().getUserName());
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", String.valueOf(port));
        return Session.getInstance(properties, authenticator);
    }

    @SuppressWarnings("static-access")
    private String setBodyText() throws IOException {
    	String browserValue,tier;
        List<String> failedResultsList = printFailedTestInformation();
        String[] failedResultArray = new String[failedResultsList.size()];
        for (int i = 0; i < failedResultArray.length; i++) {
            failedResultArray[i] = failedResultsList.get(i);
        }
        if(System.getProperty("browser")==null){
        	browserValue= getProperty("./Config.properties", "browser");
        }
        else
        	browserValue=System.getProperty("browser");
        
        if(System.getProperty("tier")==null){
        	tier= getProperty("./Config.properties", "tier");
        }
        else
        	tier=System.getProperty("tier");
        

        String mailtext = "";

        mailtext = "Hi All,<br>";
        mailtext = mailtext
                + "</br><b>" + projectName + " Test Automation Result </b></br><br>";
        mailtext = mailtext
                + "<br><b><font style = Courier, color = green>Test Name: </font></b>"
                + getTestName(); 
        mailtext = mailtext
                + "<br><b><font color = green>Browser: </font></b>"
                + browserValue.toUpperCase();
        
        mailtext = mailtext
                + "<br><b><font color = green>Environment: </font></b>"
                + tier.toUpperCase();
        mailtext = mailtext
                + "<br><b><font color = green>Test Case Executed By: </font></b>"
                + projectName + " Automation Team";
        mailtext = mailtext
                + "<br><b><font color = green>Test Date: </font></b>" + today;
        mailtext = mailtext + "<b>" + testSetResult() + "</b>";
        mailtext = mailtext +"</br>"+writeResultsTableInEmail(xmlPath);
        
        mailtext = mailtext
                + "<br>Note: This is a system generated mail. Please do not reply."
                + "</br>";
        mailtext = mailtext
                + "<br>If you have any queries mail to <a href=mailto:" + from + "?subject=Reply-of-Automation-Status"
                + today.replaceAll(" ", "_") + ">" + projectName + " AUTOMATION </a></br>";
        mailtext = mailtext
                + "<br><br>The detailed test results are given in the attached <i>emailable-report.html</i> </br></br>";
        mailtext = mailtext + "<br><br>Best Regards" + "</br></br>";
        mailtext = mailtext + "<br>" + projectName + " Automation Team" + "</br>";
        

        return mailtext;
    }
    
 
    private String setMailSubject() {

        return (projectName + " Automated Test Results: " + count + " Failures | " + today);
    }

    private void setMailRecipient(Message message) throws AddressException, MessagingException, IOException {

        Map<String, Object> emailMap = YamlReader.getYamlValues("email.recepients");
        for (Object val : emailMap.values()) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    val.toString()));
        }
       message.addRecipient(Message.RecipientType.BCC, new InternetAddress("rahulyadav@qainfotech.com"));

    }

    private Multipart setAttachment() throws MessagingException, IOException {
        // Create the message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();

        // Fill the message
        messageBodyPart.setContent(setBodyText(), "text/html");

        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // Part two is attachment
        messageBodyPart = new MimeBodyPart();
        addAttachment(multipart, messageBodyPart,
                "./target/surefire-reports/emailable-report.html");

        return multipart;
    }

    private static void addAttachment(Multipart multipart,
            MimeBodyPart messageBodyPart, String filename)
            throws MessagingException {
        messageBodyPart = new MimeBodyPart();
        File f = new File(filename);
        DataSource source = new FileDataSource(f);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(f.getName());
        multipart.addBodyPart(messageBodyPart);
    }

    private String getTestName() {
        String test = System.getProperty("test", "null");
        String testsuite = System.getProperty("testsuite", "null");
        String testName;
        if (test != "null") {
            testName = test + " was executed";
            return testName;
        } else if (testsuite != "null") {
            testName = testsuite + "were executed";
            return testName;
        } else {
            testName = "Complete Automated SMOKE Test Suite Executed";
            return testName;
        }
    }

    private String getTextFile() {
        String textFile = "";
        File folder = new File("./target/surefire-reports/");
        String[] fileNames = folder.list();
        int total = 0;
        for (int i = 0; i < fileNames.length; i++) {
            if (fileNames[i].contains(".txt")) {
                total++;
                assert total == 1;
                textFile = fileNames[i];
                System.out.println("Text File Path: " + textFile);
                return textFile;
            }
        }
        return textFile;
    }

    private String testSetResult() throws IOException {
        String messageToBeSent = "";
        String overallRes = "";
        overallRes = parseTestNgXmlFile(xmlPath);
        messageToBeSent = "<br>" + overallRes;
        return messageToBeSent;
    }
    
    private String parseTestNgXmlFile(String filepath) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        Document dom = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            dom = dBuilder.parse(filepath);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        NodeList nodes = dom.getElementsByTagName("testng-results");
        Element ele = (Element) nodes.item(0);
        String msgOutput = "Tests run: ";
        failureResults = ele.getAttribute("failed");
        skippedResults = ele.getAttribute("skipped");
        passedResult = ele.getAttribute("passed");
        NodeList nodes1 = dom.getElementsByTagName("suite");
        Element ele1 = (Element) nodes1.item(0);
        String totalTime = ele1.getAttribute("duration-ms");
        if (Math.round(Double.parseDouble(totalTime) / 1000) > 60) {
            totalTime = String
                    .valueOf(Math.round((Double.parseDouble(totalTime) / 1000) / 60)) + " minutes";
        } else {
            totalTime = String
                    .valueOf(Math.round(Double.parseDouble(totalTime) / 1000)) + " seconds";
        }
        msgOutput = msgOutput + ele.getAttribute("total") + " ,Passed: "
                + passedResult + " ,Failures: " + ele.getAttribute("failed")
                + " ,Skipped: " + ele.getAttribute("skipped")
                + " ,Total Execution Time: " + totalTime;
        return msgOutput;
    }
    
    private String writeResultsTableInEmail(String filepath) 
    {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        Document dom = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            dom = dBuilder.parse(filepath);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String label,msgOutput = "";
        NodeList testsExecuted = dom.getElementsByTagName("test");
        int testcount= testsExecuted.getLength();
  
     	msgOutput =msgOutput = msgOutput + "<style>"+
         		"table {border-collapse: collapse; width: 60%; border: 1px solid black;}th, td {text-align:left;padding: px;border: 1px solid black;}tr:nth-child(even){background-color: #f2f2f2}th {background-color: #4caf50;color: white;}</style><h4><u>Result Summary</u></h4><br><table><tr><th>Test Name</th><th>Total Tests</th><th>Passed</th><th>Skipped</th><th>Failed</th><th>Execution Time</th></tr>";
     	for(int i=0;i<testcount;i++)
    	{
    	 Element ele = (Element)testsExecuted.item(i);
    	label= ele.getAttribute("name");
    	
    	totaltest=toString().valueOf(getExecutionResults(dom,"//test[@name='"+label+"']/class/test-method[not(@is-config)]"));
    	executiontime = ele.getAttribute("duration-ms");
    	passedResult = toString().valueOf(getExecutionResults(dom,"//test[@name='"+label+"']/class/test-method[@status='PASS' and not(@is-config)]"));
    	skippedResults =toString().valueOf(getExecutionResults(dom,"//test[@name='"+label+"']/class/test-method[@status='SKIP']"));
    	failureResults =toString().valueOf(getExecutionResults(dom,"//test[@name='"+label+"']/class/test-method[@status='FAIL']"));
    	
    	msgOutput = msgOutput + "<tr><td id=\"#t\""+i + "\">" + label+ "</td><td font color='white' bgcolor='#0000FF'>"+totaltest+"</td><td bgcolor='#00FF00' font color='white'>"+passedResult+"</td><td bgcolor='#FFFF00' font color='white'>"+skippedResults+"</td><td bgcolor='#FF0000' font color='white'>"+failureResults+"</td><td>"+executiontime+"</td></tr>";
    	}
        msgOutput = msgOutput + "</table>";
        return msgOutput;
    	
    }
    
   public int getExecutionResults( Document dom,String xpath1)
    {
	   int teststatus=0;
    	XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = null;
        NodeList nl = null;
		try {
			System.out.println(xpath1);
			expr = xpath.compile(xpath1);
			nl = (NodeList) expr.evaluate(dom, XPathConstants.NODESET);
			teststatus=nl.getLength();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		
		}
         return teststatus;
    }
   

    private List<String> printFailedTestInformation() {
        String filepath = "./target/surefire-reports/testng-results.xml";
        File file = new File(filepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        Document dom = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            dom = dBuilder.parse(file);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> list = identifyTagsAndTraverseThroguhElements(dom);
        System.out.println("Number of Failed Test Cases:- " + count);
        return list;
    }

    private List<String> identifyTagsAndTraverseThroguhElements(Document dom) {

        List<String> list = new ArrayList<String>();

        NodeList nodes = dom.getElementsByTagName("test-method");
        try {
            NodeList nodesMessage = dom.getElementsByTagName("full-stacktrace");
            for (int i = 0, j = 0; i < nodes.getLength() && j < nodesMessage.getLength(); i++) {

                Element ele1 = (Element) nodes.item(i);
                Element ele2 = (Element) nodesMessage.item(j);

                if (ele1.getAttribute("status").equalsIgnoreCase("FAIL")) {
                    count++;
                    String[] testMethodResonOfFailure = getNameTestReason(ele1, ele2);
                    list.add(testMethodResonOfFailure[0]);
                    list.add(testMethodResonOfFailure[1]);
                    list.add(testMethodResonOfFailure[2]);

                    j++;
                }
            }
        } catch (Exception e) {
            System.out.println("No Failures");
            Reporter.log("No Failures!!");
        }
        return list;

    }

    private String[] getNameTestReason(Element el1, Element el2) {
        String[] returnNameTestReason = new String[3];
        NamedNodeMap name = el1.getParentNode().getParentNode().getAttributes();

        returnNameTestReason[0] = name.getNamedItem("name").toString().replaceAll("name=", "");
        returnNameTestReason[1] = el1.getAttribute("name");
        returnNameTestReason[2] = el2.getTextContent();
        return returnNameTestReason;

    }

    private String giveTable(String[] failedResults) {
        String table = "";
        table = table + "<table border='3'><tbody><tr style='background:red'><th><b>Test Case</b></th>"
                + "<th><b>Test Method</b></th></tr>";

        for (int k = 0; k < failedResults.length; k += 3) {
            table = table + "<tr valign='top'><b>" + failedResults[k] + "</b>" + "<b><td>" + failedResults[k + 1] + "</b></tr>";

        }

        table = table + "</tbody></table>";
        return table;
    }
	
}
