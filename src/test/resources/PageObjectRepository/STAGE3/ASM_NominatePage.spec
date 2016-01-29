Page Title: ASM_NominatePage

#Object Definitions
=================================================================================================================================
inp_username                                  xpath                       //input[contains(@id,'Credential1')]
inp_password                                  xpath                       //input[contains(@id,'Credential2')]
btn_verify                                    xpath                       //input[contains(@id,'btnLogin')]
rad_acsId                                     id                          13f8d605_6358_42dc_8d75_43b725bac2e3_rblVerifyMethod_1
txt_loginErrorMessage                         classname                   errorMessage
btn_confirm                                   id                          submitInfo
img_spinner                                   xpath                       //img[contains(@src,'loading.gif')]
img_spinnerOnSearch                           xpath                       //img[contains(@src,'updating.gif')]
span_spinnerOnUpload                          classname                   qq-upload-spinner
btn_arrow                                     id                          showAll
li_awards                                     xpath                       //li[@class='ui-menu-item']/a
inp_createNewNomination                       id                          createNomination
rad_knowTheirName                             xpath                       //div[@id='memberSearch_searchNominee']/input[2]
inp_findByName                                id                          memberName_searchNominee
btn_selectNomineeSupport1                     xpath                       (//input[@class='selectNominee'])[1]
btn_selectNomineeSupport2                     xpath                       (//input[@class='selectNominee'])[10] 
tab_currentTab                                xpath                       //span[@class='progressStepOn']  
inp_nomineePosition                           xpath                       //div[@id='nomineePosition']/input  
rad_safeLabPractices                          xpath                       //div[@id='safetyProtocol']/input[3]  
rad_discussedAwardNomination                  xpath                       //div[@id='discussedWithNominee']/input[2] 
rad_yearOfExperience                          xpath                       //div[@id='experienceFlag']//input[1] 
list_selectDiscipline                         name                        n01_nominee_a58_key_ext     
btn_continue                                  xpath                       //input[@class='saveData']
btn_confirm_PrepareNomination                 xpath                       //button[text()='Confirm'] 
txtAr_suggestedCitation                       id                          n01_citation  
txtAr_recommendation                          id                          n01_recommendation_text_ext
inp_recommendationUploads                     xpath                       (//div[@class='qq-upload-button'])[1]
inp_pubsAndPatentsUploads                     xpath                       (//div[@class='qq-upload-button'])[2]
inp_biographyUploads                          xpath                       (//div[@class='qq-upload-button'])[3]
inp_supporter1Uploads                         xpath                       (//div[@class='qq-upload-button'])[4]
inp_supporter2Uploads                         xpath                       (//div[@class='qq-upload-button'])[5]
inp_support1IKnowThierName                    xpath                       (//input[@class='memberSearchOption'])[2]
inp_support2IKnowThierName                    xpath                       (//input[@class='memberSearchOption'])[4]
inp_support1FindByName                        xpath                       (//input[contains(@id,'memberName_searchSupporter')])[1]
inp_support2FindByName                        xpath                       (//input[contains(@id,'memberName_searchSupporter')])[2]
rad_uploadRecommendation                      xpath                       //input[@class='recommendationOption'][1]
link_remove                                   xpath                       //div[@id='${nominationName}']//a[@class='removeDocument']
btn_submit                                    id                          submit
txt_nominationSubmitted                       xpath                       //span[text()='Nomination Submitted']
btn_cancel                                    classname                   cancel
link_removeIncompleteSubmission               xpath                       //a[@class='removeNominee']
txt_nominationRemoved                         xpath                       //span[text()='Nomination Removed']
txt_patentQuestion                            id                          patentQuestion
list_patentDate_day                           id                          patentDate_day
list_patentDate_month                         id                          patentDate_month
list_patentDate_year                          id                          patentDate_year
inp_patentName                                id                          n04_patent_name
inp_patentNumber                              css                         #patentNumber>input
inp_patentDescription                         id                          n04_patent_description
=================================================================================================================================