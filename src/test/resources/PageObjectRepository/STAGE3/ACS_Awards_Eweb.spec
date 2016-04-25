Page Title: ASM_CCEDPage

#Object Definitions
================================================================================================================================
hd_welcome                                id                             welcome
rad_lastNameMemberNumber                  xpath                          //input[contains(@id,'rblVerifyMethod_0')]
rad_ACSID                                 xpath                          //input[contains(@id,'rblVerifyMethod_1')]
inp_credential1                           xpath                          //input[contains(@id,'Credential1')]
inp_credential2                           xpath                          //input[contains(@id,'Credential2')]
btn_login                                 xpath                          //input[contains(@id,'btnLogin')]
txt_status                                classname                      highlight
txt_awardName                             xpath                          //h1[text()='${awardName}']
txt_numberOfDaysRemaining                 xpath                           //em[contains(.,'${'number of days'} days remain to submit your ballot')]
txt_totalNominations                      xpath                          //p[text()='${number of nominations} total nominations']
txt_submitBallotDate                      classname                       bluetext
lnk_fiveYearNomineeMemo                   xpath                         //h1[contains(text(),'${award name}')]/preceding-sibling::div[@class='awardrightcol']/div[@class='ranking awardHistory dashboard']/a
inp_viewNominationMaterial                xpath                         //h1[contains(text(),'${award name}')]/preceding-sibling::div[@class='awardrightcol']//input[@value='View Nomination Materials']
tab_currentTab                            classname                       progressStepOn
list_nominees                             xpath                          //tr[@style='font-weight: normal;']/td[1]/input
list_selectedNomineesFirstName            xpath                           //tr[@style='font-weight: bold;']/td[1]
list_selectedNomineesLastName             xpath                           //tr[@style='font-weight: bold;']/td[2]
list_selectedNomineesPrepopulated         xpath                           //tr[@style='font-weight: bold;']/td[1]/input
txt_unselectedNomineeHeader               classname                       info
txt_selectedNomineeHeader                 classname                       nominationsAddedText 
lnk_viewProfile                           xpath                          //td[text()='${nomineeName}']/following-sibling::td/a[text()='View Profile']
img_viewProfileLoader                     xpath                          img[contains(@src,'loadingAnimation')]
txt_viewProfileAwardName                  classname                      awtitle
txt_numberOfPossibleNominees              xpath                          //h1[contains(text(),'${Award name}')]/..//div[@class='awardrightcol']/p[1]
list_nominationsDocuments                 classname                       arrow    
btn_close                                 classname                      closeModal    
btn_rankNominees_save                     classname                      save
drpdwn_rank                               xpath                          //table[@class='nomineesTable fullslots']//tr[${index}]/td[1]/select
txt_nomineeName                           xpath                          //tr[contains(@class,'mover')][${index}]/td[1]
heading_rankAward                         xpath                          //div[@class='ListHeaderTitle' and contains(text(),'Rank Award Nominees')]
dropdown_rank_nominee                     xpath                          //table[@class='nomineesTable fullslots']//tr/td[1]/select
btn_confirmBallot                         xpath                          //input[@class='saveRankings validate']
txt_confirmNomineeName                    xpath                          //table[@class='nomineesTable']//tr[${index}]//td[2]
txt_confirmNomineeRank                    xpath                          //table[@class='nomineesTable']//tr[${index}]//td[1]
txt_confirmBallotPage                     xpath                          //div[@class='ListHeaderTitle' and contains(text(),'My Award Ballot')]
btn_submit_editBallot                     xpath                          //input[@value='${button name}']
txt_ballotConfirmation                    xpath                          //div[@class='ListHeaderTitle' and contains(text(),'Ballot Confirmation')]
txt_confirmationMessage                   xpath                          //div[@class='ListDiv']//div[3]
btn_returnToYourAwardDashboard            id                             save
txt_ballotSubmissionDate                  xpath                          //div[@class='awardrightcol']//p//em
lnk_updateScore                           id                             F1_HYPERLINK_2
img_dashboardLoader                       xpath                          img[@src='images/awards/loading.gif']
================================================================================================================================
