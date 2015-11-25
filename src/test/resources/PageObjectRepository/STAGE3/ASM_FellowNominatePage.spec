Page Title: ASM_FellowNominatePage

#Object Definitions
=======================================================================================================================================================================================
inp_username                                                xpath                            //input[contains(@id,'Credential1')]
inp_password                                                xpath                            //input[contains(@id,'Credential2')]
btn_login                                                   id                               btnLogin
txt_loginErrorMessage                                       xpath                            //p/span[contains(@id,'Login_lblMessage')]
hdng_welcome                                                xpath                            //legend[text()='Contact Information']
btn_startNomination                                         xpath                            //input[contains(@id,'btnStart')]
btn_confirm                                                 xpath                            //input[contains(@id,'btnConfirm')]
rad_designateYourNomination                                 xpath                            //label[text()='${type}']/preceding-sibling::input
btn_countinueWithNomination                                 id                               btnContinueEligibility
btn_confirmSelection                                        xpath                            //a[text()='Confirm']
rad_searchNominee                                           xpath                             //input[@id='sBy${searchType}']
inp_enterName_numberToSearch                                xpath                              //div[contains(@class,'searchby${searchType}')]//input[1]
img_search                                                  id                                imgSearchName
btn_findMember                                              xpath                              //input[@name='findbymembernum']
txt_searchErrorMsg                                          xpath                               //div[@class='cantfind']/p
btn_logOut                                                  css                                 .login-link>li>a
btn_firstNominee                                            id                                  0
btn_saveAnContinue                                          id                                btnContinue
tab_currentTab                                              xpath                              //li[@class='current']/span
fieldset_currentNomination                                  id                                ${education}
inp_nominationField                                         xpath                             //input[contains(@id,'${FieldName}')]
list_selectNominationField                                  xpath                             //select[contains(@id,'${Degree}')]
btn_nominationSave                                          id                                ${education}Save
btn_AddMore                                                 xpath                             //input[contains(@id,'AddMore')]
txt_errorMessage                                            classname                         errMsg
lnk_nominationChecklist                                     xpath                             //a[text()='${nominationChecklistName}']
inp_professionalHistory                                     id                                txt${Employer}
inp_description                                             xpath                             //body[@id='tinymce']
inp_searchName                                              id                                txtNameNom${1}
img_searchNominator                                         id                                imgSearchName${1}
list_selectMember                                           xpath                            //b[text()='Secondary Nominator #${nominatorNumber}']/../../following-sibling::div[2]//input[@value='Select Member']
rad_searchNumber                                            id                                rbByNumberNom${1}
inp_searchByNumber                                          id                                 txtNumberNom${1}
btn_findMemberNominator                                     id                                 btnFindByNumberNom${1}
list_delete                                                 xpath                              //a[text()='Delete']
btn_deleteCofirm                                            xpath                              //input[contains(@id,'btnDeleteConfirm')]
link_uploadPrimaryNominator                                 id                                 hlBrowsePrimary
link_uploadSecondary1Nominator                              id                                 hlBrowseSecondary1
link_uploadSecondary2Nominator                              id                                 hlBrowseSecondary2
link_uploadAttestationNominator                             id                                 hlBrowseAttestation
img_uploadFile                                              classname                          file-upload-progress-img
link_deletePrimaryNominator                                 xpath                                 //b[text()='Primary Nominator']/../../following-sibling::div//a[@id='hlDelete']
link_deleteSecondary1Nominator                              xpath                                //b[text()='Secondary Nominator #1']/../../following-sibling::div//a[@id='hlDelete']
link_deleteSecondary2Nominator                              xpath                                 //b[text()='Secondary Nominator #2']/../../following-sibling::div//a[@id='hlDelete']
link_deleteAttestationNominator                             xpath                                 //b[text()='Letter of Attestation']/../../following-sibling::div//a[@id='hlDelete']
link_deleteResumeNominator                                  xpath                                 //b[text()='Resume/CV']/../../following-sibling::div//a[@id='hlDelete']
link_deleteSupplementryMaterialNominator                    xpath                                 //b[text()='Supplementary Material']/../../following-sibling::div//a[@id='hlDelete']
link_uploadResumeNominator                                  id                                  aBrowse
link_uploadSupplementryMaterialNominator                    id                                  aBrowse
link_nextPage                                               classname                           next
tab_current                                                 xpath                               //li[@class='current']/span
btn_keepSession                                             xpath                               //a[text()='Keep Session']
btn_submitContinue                                          xpath                               //input[contains(@id,'btnContinueBtm')]
chk_submitNomination                                        id                                  chkNomSubAck    
btn_submitNomination                                        id                                  btnSubmitNomination   
btn_saveSubmit                                              xpath                               //input[contains(@id,'btnSave')]
txt_successNomination                                       classname                           success-msg
inp_upload                                                  xpath                               //input[@id='fuName']
txt_allFieldsRequired                                       xpath                               //span[text()='All Fields Required']
txt_startNominationDiv                                      classname                            action-bar-green
=======================================================================================================================================================================================