Page Title: Scarf_Reviewing

#Object Definitions
=============================================================================================================================================
drpdwn_selectReviewer                           css                        select[id*='ReviewerRole']
drpdwn_Reviwerlist                              css                        select[id*='lstReviewer']
list_reviewerOptions                            xpath                        //select[contains(@id,'lstReviewer')]//option[${value}]
txt_AssignedchapterName                         xpath                      (//table[@class='OTIGrid']//td[2])[1]
btn_AssignChapter                               xpath                      //td[contains(text(),'${chaptername}')]/following-sibling::td/input
img_spinner                                     css                        img[scr*='updating']       
tab_QueryName                                   xpath                      (//a[text()='Query'])[2]              

=============================================================================================================================================