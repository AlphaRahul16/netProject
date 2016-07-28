package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.HashMap;
import java.util.Map;

import org.apache.xalan.templates.ElemNumber;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class ACS_Scarf_Reviewing_Eweb_Action extends ASCSocietyGenericPage{

	WebDriver driver;
	static String pagename = "Scarf_Reviewing";
	Map<String,Map<String,String>> reviewerComments=new HashMap<String,Map<String,String>>();
	Map<String,String> reviewerSections=new HashMap<String,String>();
	int timeOut=60;
	
	public ACS_Scarf_Reviewing_Eweb_Action(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
	}
	
	public int verifyChapterOnTheReviewPageAndClickOnreviewButton(String chapterName,String elem,String elementValue){
		boolean flag=false;
		int i;
		wait.waitForPageToLoadCompletely();
		isElementDisplayed(elem,elementValue);
		for(i=2;i<=elements(elem,elementValue).size();i++){
			if(chapterName.equalsIgnoreCase(element("txt_ChapterName",String.valueOf(i),String.valueOf(1)).getText().trim())){
				flag=true;
				break;
			}
		}
		Assert.assertTrue(flag,"ASSERT FAILED : Chapter Name "+chapterName+" does not exist in the list\n");
		logMessage("ASSERT PASSED : Chapter Name "+chapterName+" exist in the list\n");
		return i;
	}
	
	public int verifySubmittedChapterOnTheReviewPage(String chapterName,String elem,String elementValue){
		boolean flag=false;
		int i;
		wait.waitForPageToLoadCompletely();
		isElementDisplayed(elem,elementValue);
		for(i=2;i<=elements(elem,elementValue).size();i++){
			if(chapterName.equalsIgnoreCase(element("txt_submittedChapter",String.valueOf(i),String.valueOf(1)).getText().trim())){
				flag=true;
				break;
			}
		}
		Assert.assertTrue(flag,"ASSERT FAILED : Chapter Name "+chapterName+" does not exist in the list\n");
		logMessage("ASSERT PASSED : Chapter Name "+chapterName+" exist in the list\n");
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
			reviewerSections.put(sectionName, getCannedAnswersComments());		
		}
		else{
			comments=comments+System.currentTimeMillis();
			enterComments(comments);
			reviewerSections.put(sectionName, comments);		
		}
	}
	
	public String getCannedAnswersComments(){
		wait.hardWait(2);
		switchToFrame(element("lnk_iframe"));
		isElementDisplayed("txt_commentsTextbox");
		String answers=element("txt_commentsTextbox").getText().trim();
		logMessage("STEP : Canned Answers Comments are "+answers+"\n");
		switchToDefaultContent();
		return answers;
	}
	
	public void addReviewerComments(String reviewerMode){	
		reviewerComments.put(reviewerMode,reviewerSections);
		System.out.println("------comments :"+reviewerComments.get(reviewerMode));
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
	
	public void clickOnSubmitButton(String btnValue){
		isElementDisplayed("btn_submit",btnValue);
		element("btn_submit",btnValue).click();
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
	
	public void verifyReviewerTypeWindow(String reviewerType){
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("heading_reviewerType");
		logMessage("ASSERt PASSED : Pop-up window is displayed for selecting Reviewer type\n");
		selectTypeOfReviewer(reviewerType);
	}
	
	public void selectTypeOfReviewer(String reviewerType){  
		isElementDisplayed("btn_fdpReviewer",reviewerType);
		element("btn_fdpReviewer",reviewerType).click();
		logMessage("STEP : Reviewer type selected as "+reviewerType+"\n");
	}
	
	public void enterCommentsForSectionsByFdpReviewer(String sectionName,int index){
		logMessage("*********Entering review comments for "+sectionName+" section*********\n");
		if(sectionName.equalsIgnoreCase("Service")||sectionName.equalsIgnoreCase("Professional Development")){
			clickOnCannedAnswersButton();
			enterCommentsViaCannedAnswers();
		}
		else{
			copyCommentsToFdp(index);
		    verifyRviewerCommentIsCopied(index);
		}
	}
	
	public void copyCommentsToFdp(int index){
		isElementDisplayed("btn_copyComments",String.valueOf(index));
		logMessage("-----comment added----"+element("txt_reveiwerComment",String.valueOf(index)).getText().trim());
		element("btn_copyComments",String.valueOf(index)).click();
	}
	
	public void verifyRviewerCommentIsCopied(int index){
		String expected;
		expected=element("txt_reveiwerComment",String.valueOf(index)).getText().trim();
		switchToFrame(element("lnk_iframe"));
		isElementDisplayed("txt_commentsTextbox");
		Assert.assertEquals(element("txt_commentsTextbox").getText().trim(), expected,
				"ASSERT FAILED : Reviewer comments are not copied properly\n");
		logMessage("ASSERT PASSED : Reviewer comments are copied properly\n");
		switchToDefaultContent();
	}
	
	public void clickOnSubmittedChaptersTab(String tabName){
		wait.hardWait(2);
		isElementDisplayed("tab_chapterStatus",tabName);
		element("tab_chapterStatus",tabName).click();
		logMessage("STEP : Clicked on Submitted Tab\n");
	}
	
	public void enterRatingByGreenChemistryReviewer(String rating){
		isElementDisplayed("list_ratingGreenChemistry");
		selectProvidedTextFromDropDown(element("list_ratingGreenChemistry"), rating);
		logMessage("STEP : Rating selected as :"+rating+"\n");
	}
	
	public void verifyFinalReview(String review,int index){
		isElementDisplayed("txt_ChapterName",String.valueOf(index),String.valueOf(3));
		Assert.assertEquals(element("txt_ChapterName",String.valueOf(index),String.valueOf(3)).getText().trim(), 
				review,"ASSERT FAILED : Final Review by Green Chemistry Reviewer is not "+review+"\n");
		logMessage("ASSERT PASSED : Final Review by Green Chemistry Reviewer is "+review+"\n");
	}
	
	public int verifyChapterOnReviewPageForGCReviewer(String chapterName,String elem,String elementValue){
		boolean flag=false;
		int i;
		wait.waitForPageToLoadCompletely();
		isElementDisplayed(elem,elementValue);
		for(i=2;i<=elements(elem,elementValue).size();i++){
			if(chapterName.equalsIgnoreCase(element("txt_ChapterName",String.valueOf(i),String.valueOf(1)).getText().trim())
					&& element("txt_ChapterName",String.valueOf(i),String.valueOf(2)).getText().trim().equalsIgnoreCase("Not Started")){
				flag=true;
				break;
			}
		}
		Assert.assertTrue(flag,"ASSERT FAILED : Chapter Name does not exist in the list\n");
		logMessage("ASSERT PASSED : Chapter Name exist in the list\n");
		return i;
	}

}
