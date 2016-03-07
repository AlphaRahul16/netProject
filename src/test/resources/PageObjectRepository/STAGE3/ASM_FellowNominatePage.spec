Page Title: ASM_FellowNominatePage

#Object Definitions
=======================================================================================================================================================================================
inp_username                                                xpath                            //input[contains(@id,'Credential1')]
inp_password                                                xpath                            //input[contains(@id,'Credential2')]
btn_login                                                   css                                 input[id*='btnLogin']
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
iframe1_Awards                                              id                                citation_field_1_value_ifr
iframe2_Awards                                              id                                citation_field_2_value_ifr
txtarea_awards                                              css                               #tinymce
btn_underline                                               css                               .mceIcon.mce_underline
btn_italic                                                  css                                .mceIcon.mce_italic
txt_dashboardHeadings                                       xpath                                //legend[contains(text(),'${value}')]
rad_localsectionArea                                        xpath                              //label[text()='${value}']/preceding-sibling::input/following-sibling::ul//input
btn_CancelEligibity                                         id                                 btnCancelEligibility
rad_codeConductYes                                          id                                 cocFlagYes
rad_codeConductNo                                           id                                 cocFlagNo
txt_citationNumber                                          xpath                              //a[text()='${value}']/following-sibling::span
btn_ReturnToDashboard                                       xpath                              //input[@value='Return to the Dashboard']
txt_NomineeName1                                            css                                .name
rad_lastNameMemNumber                                       css                                input[value*='rbLogin1']
txt_codeOfConductValue                                      xpath                              //a[contains(text(),'Value')]/../../following-sibling::tr/td[5]
btn_detailsMenuAACT                                         xpath                               //span[text()='${menuName}']/../a[1]
icon_up                                                     xpath                               //span[contains(text(),'${value}')]/preceding-sibling::a/i[@class='icon-chevron-up']
txt_fellowIwebDetails                                       xpath                                (//th/a)[2]/../../following-sibling::tr[1]//td[${coloumnNumber}]
frameGeneral                                                xpath                               //iframe[@id='txt${value}Desc_ifr']
lnk_viewNominations                                         xpath                              //h5[contains(text(),'${value}')]/following-sibling::ul//a[contains(text(),'View')]
lnk_editNominations                                         xpath                              //h5[contains(text(),'${value}')]/following-sibling::ul//a[contains(text(),'Edit')]
lnk_printPDF                                                xpath                               //h5[contains(text(),'Individual Nomination')]/following-sibling::ul//a[contains(text(),'Print PDF')]
btn_codeofconduct_checked                                   css                                input[type='radio'][checked]
btn_previewNomination                                       id                                  btnPreviewNomination
btn_view                                                    css                                 input[value='View']
btn_home                                                    classname                           home
txt_errmsg_renomination                                     css                                 span[id*='CantFind']
btn_nominatorAdressConfirm                                  id                                  submitInfo                
=======================================================================================================================================================================================