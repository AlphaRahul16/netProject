Page Title: AwardsPageIWEB

#Object Definitions
========================================================================================================================================
txt_startDateRounds                       xpath               //td[starts-with(text(),'Round ${round number}')]/following-sibling::td[1]
txt_endDateRounds                         xpath               //td[starts-with(text(),'Round ${round number}')]/following-sibling::td[2]
link_editRecord                           xpath               //td[starts-with(text(),'Round ${roundNumber}')]/preceding-sibling::td[1]/a[@title='edit record']
txt_editRoundPage                         xpath               //span[text()='Edit - Award Stage/round']
inp_editStartEndDate                      id                  aws_${start_end}_date
btn_saveButton                            id                  ButtonSave
list_nominees                             xpath               //i[@class='iconpro-circle-arrow-right']/../../following-sibling::td[1]/a
list_paging                               xpath               //table[@class='DataFormChildTABLE']//table//tr[1]//a
link_paging                               xpath               //table[@class='DataFormChildTABLE']//table//tr[1]//a[starts-with(text(),'${page no}')]
img_spinner                               xpath               //*[contains(@src,'updating.gif')]
list_rounds                               xpath               (//table[@class='DataFormChildTABLE']//tr[3]//tbody[1])[1]/tr/td[4]
btn_addRounds_judges                      xpath               //a[@title='Add Record: ${tabName}']/i
lbl_addRound                              xpath               span[text()='Add - Award Stage/round']
inp_editRound_Judge                       xpath              //label[text()='${labeltext}:']/preceding-sibling::input
list_awardJudge                           xpath              (//table[@class='DataFormChildTABLE'])[2]//td[starts-with(text(),'Round ${1}')]
list_selectRoundNumber                    id                  awj_aws_key
img_goToJudge                             xpath               (//td[contains(text(),'@')]/preceding-sibling::td[7]//img)[${indexNumber}]
link_goToRecord                           xpath               //td[starts-with(text(),'Round ${roundNumber}')]/preceding-sibling::td[1]/a[@title='goto record']
list_judgeNames_awardJudges				  xpath				  //a[@title='goto record']/../following-sibling::td[1]
text_judgeNames_awardJudges				  xpath				  (//a[@title='goto record']/../following-sibling::td[1])[${index}]
list_judgesNames_goToRecord				  xpath				  (//span[contains(text(),'award judge')]/../../following-sibling::tr//a[@title='goto record']/i)[${index}]
list_judgeName_rescused                    xpath               //img[contains(@src,'img_chkmk')]/../preceding-sibling::td[2]
list_judgeName_Judges                     xpath                //a[starts-with(text(),'Name')]/../../following-sibling::tr/td[starts-with(text(),'Round ${roundNumber}')]/preceding-sibling::td[1]
link_goToJudgeRecord                      xpath                (//td[contains(text(),'${judgeName}')]/preceding-sibling::td[1]/a[@title='goto record']/i)[last()]
lnk_judgeProfile                          xpath               //a[contains(text(),'${judgeName}')]
txt_webLogin                              id                  F1_cst_web_login
txt_customerId                            id                  F1_cst_id
lnk_awardName_RoundName                   xpath               //a[text()='${awardName_roundName}']
list_pages                                classname            pager
lnk_pages                                 xpath               //tr[@class='pager']//a
btn_editJudge                             xpath               //img[@alt='edit award judge']
link_judgeName                            id                  F1_HYPERLINK_1
heading_updateScoreAlert                  xpath               //span[text()='Edit - ACS Award Stage Score Update']
lnk_updateScore                           id                  F1_HYPERLINK_2
btn_updateScore                           id                  UpdateScoreForStageButton
txt_updateScoreMessage                    id                  LabelMessage
list_acsAwardEntries                      xpath               //span[text()='acs award judge score']/../../following-sibling::tr//tbody/tr[position()>1]
link_acsAwardEntries                      xpath               //span[text()='acs award judge score']/../../following-sibling::tr//tbody/tr[position()>1]
img_reOpenSubmission                      xpath               //div[@id='F1_ReopenJudging']/input
list_deleteNominee                        classname           iconpro-remove
btn_updateScoreClose                      xpath                //button[text()='Close']
txt_closedStatus                          classname            DataFormLabelErrorMessage
list_stageEntriesRankOne                  xpath                //td[starts-with(text(),'${nomineeName}')]/following-sibling::td[2]
txt_winnerStatusForNomineee               xpath               //td[starts-with(text(),'${nomineeName}')]/following-sibling::td[3]
txt_withLabelOnAwardStageProfile          xpath                  //label[text()='${closed status}']/preceding-sibling::span
btn_editAward                             id                    F1_HYPERLINK_0
chk_closedCheckBox                        id                  aws_closed_flag_ext
list_acsAwardProfilesNomineesWithOnes	  xpath               (//a[contains(@title,'acs award stage - entries in this stage')]/../../following-sibling::tr//tr[position()>1])//td[6][not(starts-with(text(),'0'))]/preceding-sibling::td[2]
list_acsEntriesNotStage_ColWinnerStatus	  xpath				 //a[contains(@title,'acs entries not in this stage')]/../../following-sibling::tr//tr[position()>1]/td[7]
list_acsEntriesNotStage_ColNominee		  xpath		         //a[contains(@title,'acs entries not in this stage')]/../../following-sibling::tr//tr[position()>1]/td[starts-with(text(),'${Winner Status}')]/preceding-sibling::td[3]
btn_detailsMenuAACT                       xpath                 //span[contains(text(),'${value}')]/preceding-sibling::a/i[@class='icon-chevron-down']
img_spinner                               id                     __UPIMG
icon_up                                   xpath                 //span[contains(text(),'${value}')]/preceding-sibling::a/i[@class='icon-chevron-up']
list_links_awardJudgesEditRound           xpath                //span[starts-with(text(),'award judges')]/../../following-sibling::tr//td[starts-with(text(),'Round ${round}')]/preceding-sibling::td[2]//a[@title='edit record']/i
lbl_alreadyExistJudgeMsz                   xpath               //span[contains(text(),'This record already exists and cannot be added.The statement has been terminated.')]
inp_judgeName                             id                   cst_sort_name_dn
btn_cancel                                id                   ButtonCancel 
lbl_stageAwardInEdit                      id                   Caption_aws_stage_code
list_resetWinnerStatus                    id                     aws_winner_aww_key_ext  
btn_editAwards                            id                   F1_HYPERLINK_0   
inp_editDateInEditAwards                  xpath                //label[text()='${labelname}:']/preceding-sibling::span/span/input
btn_plusIconNominee                       xpath                //span[text()="${tabName}"]/../following-sibling::td//a//i
btn_srchNominee                           xpath                (//input[@class='LookUpHyperLink'])[${index}]
inp_entryDate                             xpath                //label[text()='${field}:']/preceding-sibling::span//input
hd_awards                                 xpath                //span[text()='Nominator details']
inp_lookUp                                xpath                (//input[@class='DataFormTextBox LookUp'])[${index}]
inp_lookUpSelect                          xpath                 //input[@class='DataFormTextBox LookUp LookupIsSelected']
btn_deleteAwards                          xpath                //span[text()='award judges']/../../following-sibling::tr//a[@title='delete record']/i
btnList_yellowPointerExpand               xpath                //img[@src='/NFStage3/iweb/images/img_folder_full.gif' and @alt='expand']
btnList_yellowPointerCollapse             xpath                //img[@alt='collapse']
btn_editJudges                            xpath               //img[@alt='collapse']/../../following-sibling::tr/td//tr[position()>1]//i[@class='iconpro-pencil']
btn_editChild                             xpath                //span[text()='${childTabName}']/../../following-sibling::tr//tr[position()>1]//i[@class='iconpro-pencil']
btn_deleteJudge                           id                   ButtonDelete
drpdwn_selectWinnerCategory               id                   awe_aww_key
 img_valid								  xpath					(//span[text()='${childTabName}']/../../following-sibling::tr//tr[position()>1]//img[@src='../images/img_chkmk.gif'])[${index}]
 img_awardClosed                          xpath                //td[starts-with(text(),'Round ${roundNumber}')]/preceding-sibling::td[1]/following-sibling::td//img
========================================================================================================================================
