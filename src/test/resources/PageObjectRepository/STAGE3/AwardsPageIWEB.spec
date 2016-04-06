Page Title: AwardsPageIWEB

#Object Definitions
========================================================================================================================================
txt_startDateRounds                       xpath               //td[starts-with(text(),'Round ${round number}')]/following-sibling::td[1]
txt_endDateRounds                         xpath               //td[starts-with(text(),'Round ${round number}')]/following-sibling::td[2]
link_editRecord                           xpath               //td[starts-with(text(),'Round ${roundNumber}')]/preceding-sibling::td[1]/a[@title='edit record']
txt_editRoundPage                         xpath               //span[text()='Edit - Award Stage/round']
inp_editStartEndDate                      id                  aws_${start_end}_date
btn_saveButton                            id                  ButtonSave
list_nominees                             xpath               //img[contains(@src,'img_chkmk')]/../preceding-sibling::td[5]/a
list_paging                               xpath               //table[@class='DataFormChildTABLE']//table//tr[1]//a
link_paging                               xpath               //table[@class='DataFormChildTABLE']//table//tr[1]//a[starts-with(text(),'${page no}')]
img_spinner                               xpath               //*[contains(@src,'updating.gif')]
list_rounds                               xpath               (//table[@class='DataFormChildTABLE']//tr[3]//tbody[1])[1]/tr/td[4]
btn_addRounds_judges                      xpath               //a[@title='Add Record: ${tabName}']/i
lbl_addRound                              xpath               span[text()='Add - Award Stage/round']
inp_editRound_Judge                       xpath              //label[text()='${labeltext}:']/preceding-sibling::input
list_awardJudge                           xpath              (//table[@class='DataFormChildTABLE'])[2]//a[@title='edit record']
list_selectRoundNumber                    id                  awj_aws_key
btn_search                                classname           LookUpHyperLink
img_goToJudge                             xpath               //td[contains(text(),'@')]/preceding-sibling::td[7]//img
========================================================================================================================================
