Page Title: Scarf_Reporting

#Object Definitions
=============================================================================================================================================
lnk_StudentChapter                       xpath                       //a[text()="Student Chapter Reporting"]
table_rows                               xpath                       //table[@id='UP1']//tr
txt_current                              xpath                       (//th/a)[2]/../../following-sibling::tr[${index1}]//td[5]
txt_startDate                            xpath                       (//th/a)[2]/../../following-sibling::tr[${index1}]//td[8]
txt_endDate                              xpath                       (//th/a)[2]/../../following-sibling::tr[${index1}]//td[9]
heading_StudentReport                    css                          .header-title
btn_editChapterInfo                      xpath                        //input[@value='Edit Chapter Info']
btn_editChapterOfficers                  css                          #btnEdit
btn_addOfficer                           css                          #btnAddOfficer
btn_searchOfficer                        css                          #btnOfficerSearch
lst_officers                             xpath                        //div[@class='results']//li
btn_selectOfficer                        xpath                        //input[@id='${index}' and @value='Select']
lst_officerRole                          xpath                        //select[@class='officer-role']
btn_save                                 css                          #save
chkbox_primaryContact                    xpath                        //input[@class='officer-primary']
lst_officerName                          xpath                        //li//input[@id='${index}']/../preceding-sibling::div//div[@class='results-name']
txt_officerName                          xpath                        //div[@class='name']
btn_return                               xpath                        //input[@value='Return']
txt_undergraduatesMajoring               xpath                        //input[contains(@id,'txtMajorInChemistryCount')]
txt_chemistryFacultyMembers              xpath                        //input[contains(@id,'txtChemistryFacultyCount')]
btn_saveChapterInfo                      xpath                        //input[@value='Save']
btn_buildReport                          xpath                        //li[@class='${value}']//a
btn_status                               xpath                        //span[text()='${chapterName}']/../../preceding-sibling::div//input[1]
inp_ln_id					             xpath						 .//input[contains(@id,'${field}')]
radio_lastName                           xpath                       //input[@value='rbLogin1']
btn_login                                xpath                       //input[@id='btnLogin']
txt_reportStatus                         xpath                       //strong[text()='Report Status:']/../..//span[2]
lnk_frameAssessment                      xpath                       (//td[@class='mceIframeContainer mceFirst mceLast']/iframe)[${index}]       
txt_txtBox                               id                          tinymce
btn_addEvent                             xpath                        //input[@value='Add Event']
btn_saveAssessment                       xpath                        //input[@id='btnSubmit' and @value='${value}']
lst_selectEvents                         xpath                        //div[@class='event_profile_content']//select[${index}]
txt_eventsInfo                           xpath                        //div[@class='event_profile_content']//input[${index}]
txt_eventDescription                     xpath                        //div[@class='event_profile_content']//textarea[1]
btn_chooseFile                           xpath                        (//a[@id='aBrowse'])[1]
chkbox_greenChemistry                    xpath                        //label[contains(text(),'green chemistry events')]
btn_sectionStatus                        xpath                        //span[text()='${value}']/../..//input[@value='Complete']
chkbox_submitReport                      id                           chkRptSubAck
btn_submitReport                         id                           btnSubmit
heading_reportComplete                   xpath                        //h2[contains(text(),'Report Complete')]
btn_modalSubmit                          xpath                        //div[@class='modal-body']//input[@value='${value}']
heading_confirmReport                    xpath                        //h3[contains(text(),'Confirm Chapter Report')]
=============================================================================================================================================
