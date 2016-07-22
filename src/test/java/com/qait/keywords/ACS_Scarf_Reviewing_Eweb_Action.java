package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class ACS_Scarf_Reviewing_Eweb_Action extends ASCSocietyGenericPage{

	WebDriver driver;
	static String pagename = "Scarf_Reviewing";
	int timeOut=60;
	
	public ACS_Scarf_Reviewing_Eweb_Action(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
	}
	
	public int verifyChapterOnTheReviewPageAndClickOnreviewButton(String chapterName){
		boolean flag=false;
		int i;
		isElementDisplayed("list_ChapterList");
		for(i=2;i<=elements("list_ChapterList").size();i++){
			if(chapterName.equalsIgnoreCase(element("txt_ChapterName",String.valueOf(i),String.valueOf(1)).getText().trim())){
				flag=true;
				break;
			}
		}
		Assert.assertTrue(flag,"ASSERT FAILED : Chapter Name does not exist in the list\n");
		logMessage("ASSERT PASSED : Chapter Name exist in the list\n");
		return i;
	}
	
	public void verifyChapterStatus(String status,int index){
		isElementDisplayed("txt_ChapterName",String.valueOf(index),String.valueOf(2));
		Assert.assertEquals(element("txt_ChapterName",String.valueOf(index),String.valueOf(2)).getText().trim()
				, status,"ASSERT FAILED : The status of the chapter is not "+status+"\n");
		logMessage("ASSERT PASSED : The status of the chapter is "+status+"\n");
	}
	
	public void selectChapterReviewImage(int index){
		isElementDisplayed("txt_ChapterName",String.valueOf(index),String.valueOf(5));
        element("txt_ChapterName",String.valueOf(index),String.valueOf(5)).click();
        logMessage("STEP : Review button is clicked\n");
	}
	
	public void enterRating(String rating,String sectionName){
		logMessage("*********Entering review comments for "+sectionName+" section*********\n");
		if(!sectionName.equalsIgnoreCase("Overall Report Assessment")){
		selectProvidedTextFromDropDown(element("list_ratingOptions"), rating);
		logMessage("STEP : Rating value entered as "+rating+"\n");
		}
		else
			System.out.println("no rating for overall report assessment section");;
	}
	
	public void enterComments(String comments){
		wait.hardWait(2);
		switchToFrame(element("lnk_iframe"));
		isElementDisplayed("txt_commentsTextbox");
		element("txt_commentsTextbox").click();
		element("txt_commentsTextbox").clear();
		logMessage("STEP : Comments entered as "+comments+"\n");
		element("txt_commentsTextbox").sendKeys(comments);
		switchToDefaultContent();
	}
	
	public void enterCommentsForSections(String sectionName,String comments){
		if(sectionName.equalsIgnoreCase("Service")||sectionName.equalsIgnoreCase("Professional Development")||sectionName.equalsIgnoreCase("Chapter Development")){
			clickOnCannedAnswersButton();
			enterCommentsViaCannedAnswers();
		}
		else{
			comments=comments+System.currentTimeMillis();
			enterComments(comments);
		}
	}
	
	public void clickOnNextButton(){
		isElementDisplayed("btn_next");
		element("btn_next").click();
		logMessage("STEP : Clicked on Next Button\n");
	}
	
	public void enterCommentsViaCannedAnswers(){
		isElementDisplayed("txt_cannedAnswers");
		logMessage("STEP : Canned Answer entered as "+element("txt_cannedAnswers").getText().trim()+"\n");
		element("txt_cannedAnswers").click();		
	}
	
	public void clickOnCannedAnswersButton(){
		isElementDisplayed("btn_cannedAnswers");
		element("btn_cannedAnswers").click();
		logMessage("STEP : Clicked on Canned Answers button\n");
	}
	
	public void verifySectionName(String sectionName){
		isElementDisplayed("heading_sectionName",sectionName);
		logMessage("ASSERT PASSED : Section name "+sectionName+" is verified\n");
	}
	
	public void enterOverallRating(String rating){
		isElementDisplayed("list_overallRating","select");
		selectProvidedTextFromDropDown(element("list_overallRating","select"),rating);
		logMessage("STEP : Overall rating entered is "+rating+"\n");
		wait.waitForPageToLoadCompletely();
	}
	
	public void clickOnSubmitButton(){
		isElementDisplayed("btn_submit");
		element("btn_submit").click();
		logMessage("STEP : Clicked on Submit button\n");
	}
	
	public void enterOverallReview(String review){
		isElementDisplayed("list_overallRating","textarea");
		element("list_overallRating","textarea").click();
		element("list_overallRating","textarea").clear();
		element("list_overallRating","textarea").sendKeys(review);
		logMessage("STEP : Overall review entered as "+review+"\n");
	}
	
	public void clickOnReturnToDashboardButton(){
		isElementDisplayed("btn_returnToDashboard");
		element("btn_returnToDashboard").click();
		logMessage("STEP : Clicked on Return To Dashboard button\n");
	}
}
