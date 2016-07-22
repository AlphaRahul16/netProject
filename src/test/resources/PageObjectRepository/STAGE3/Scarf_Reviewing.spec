Page Title: Scarf_Reviewing

#Object Definitions
=============================================================================================================================================
drpdwn_selectReviewer                           css                        select[id*='ReviewerRole']
drpdwn_Reviwerlist                              css                        select[id*='lstReviewer']
list_reviewerOptions                            css                        select[id*='lstReviewer']>option
txt_AssignedchapterName                         xpath                      (//table[@class='OTIGrid']//td[2])[1]
txt_ChapterName                                 xpath                      //td[@class='LayoutCell']//tbody//tr[${index1}]//td[${index2}]
list_ChapterList                                xpath                      //td[@class='LayoutCell']//tbody//tr
list_ratingOptions                              xpath                      //div[@class='boxed-body-padded']//select
txt_commentsTextbox                             css                         #tinymce
lnk_iframe                                      xpath                      //td[@class='mceIframeContainer mceFirst mceLast']//iframe
btn_next                                        xpath                      (//input[@value='Next'])[2]
btn_cannedAnswers                               xpath                      //a[text()='Canned Answer']
txt_cannedAnswers                               xpath                      //table[@id='gvwCannedAnswers2']//tr[2]//td
heading_sectionName                             xpath                      (//span[contains(text(),"${value}")])[1]
list_overallRating                              xpath                      //td[@class='LayoutCell']//${value}
btn_submit                                      xpath                      //input[@value='Submit']
btn_returnToDashboard                           xpath                      //input[@value='Return to Dashboard']
btn_AssignChapter                               xpath                      //td[contains(text(),'${chaptername}')]/following-sibling::td/input
=============================================================================================================================================