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
lnk_fiveYearNomineeMemo                   xpath                          //div[@class='ranking awardHistory dashboard']/a
inp_viewNominationMaterial                xpath                         //input[text()='View Nomination Materials']
tab_currentTab                            classname                       progressStepOn
list_nominees                             classname                       nominee
list_selectedNomineesFirstName            xpath                           //tr[@style='font-weight: bold;']/td[1]
list_selectedNomineesLastName             xpath                           //tr[@style='font-weight: bold;']/td[2]
================================================================================================================================
