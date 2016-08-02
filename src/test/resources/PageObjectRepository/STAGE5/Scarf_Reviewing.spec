Page Title: Scarf_Reviewing

#Object Definitions
=============================================================================================================================================
drpdwn_selectReviewer                           css                        select[id*='ReviewerRole']
drpdwn_Reviwerlist                              css                        select[id*='lstReviewer']
txt_AssignedchapterName                         xpath                      (//table[@class='OTIGrid']//td[2])[1]
btn_AssignChapter                               xpath                      //td[contains(text(),'${chaptername}')]/following-sibling::td/input
img_spinner                                     css                        img[scr*='updating']       
tab_QueryName                                   xpath                      (//a[text()='Query'])[2]              
txt_ChapterName                                 xpath                      //td[@class='LayoutCell']//tbody//tr[${index1}]//td[${index2}]
list_ChapterList                                xpath                      //td[@class='${value}']//tbody//tr
list_ratingOptions                              xpath                      //div[@class='boxed-body-padded']//select
txt_commentsTextbox                             css                         #tinymce
lnk_iframe                                      xpath                      //td[@class='mceIframeContainer mceFirst mceLast']//iframe
btn_next                                        xpath                      (//input[@value='Next'])[2]
btn_cannedAnswers                               xpath                      //a[text()='Canned Answer']
txt_cannedAnswers                               xpath                      //table[@id='gvwCannedAnswers2']//tr[2]//td
heading_sectionName                             xpath                      (//span[contains(text(),"${value}")])[1]
list_overallRating                              xpath                      //td[@class='LayoutCell']//${value}
btn_submit                                      xpath                      //input[@value='${value}']
btn_returnToDashboard                           xpath                      //input[@value='Return to Dashboard']
btn_fdpReviewer                                 xpath                      //a[text()='${value}']
heading_reviewerType                            xpath                      //h3[text()='Choose the Dashboard to View']
list_notStartedChapters                         xpath                      //div[@id='${value}']//tbody//tr
btn_copyComments                                xpath                      (//input[@value='Copy to Faculty Decision Panel Comments'])[${index}]
txt_reveiwerComment                             xpath                      //span[@id='txtReviewer${index}Answer']         
tab_chapterStatus                               xpath                      //a[@href='#${value}']
list_ratingGreenChemistry                       xpath                      //span[text()='Please Select a Rating: ']/..//select
txt_submittedChapter                            xpath                      //div[@id='submitted']//tbody//tr[${index1}]//td[${index2}]
list_reviewerNameOptions                        xpath                        //select[contains(@id,'lstReviewer')]//option
txt_answersReview                               xpath                        //td[contains(text(),'${value1}')]/following-sibling::td[1][contains(text(),'${reviewername}')]/following-sibling::td[2]
lnk_PageNumber                                  xpath                       //a[contains(text(),'${value}')]
=============================================================================================================================================