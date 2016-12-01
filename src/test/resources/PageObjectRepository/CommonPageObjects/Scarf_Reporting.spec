Page Title: Scarf_Reporting

#Object Definitions
=============================================================================================================================================
lnk_StudentChapter                       xpath                       //a[text()="Student Chapter Reporting"]
table_rows                               xpath                       //table[@class='table']//tr
txt_current                              xpath                       (//th/a)[2]/../../following-sibling::tr[${index1}]//td[5]
txt_startDate                            xpath                       (//th/a)[2]/../../following-sibling::tr[${index1}]//td[8]
txt_endDate                              xpath                       (//th/a)[2]/../../following-sibling::tr[${index1}]//td[${index2}]
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
chkbox_greenChemistry                    xpath                        //input[@type='checkbox']
btn_sectionStatus                        xpath                        //span[text()='${value}']/../..//input[@value='Complete']
chkbox_submitReport                      id                           chkRptSubAck
btn_submitReport                         id                           btnSubmit
heading_reportComplete                   xpath                        //h2[contains(text(),'Report Complete')]
btn_modalSubmit                          xpath                        //div[@class='modal-body']//input[@value='${value}']
heading_confirmReport                    xpath                        //h3[contains(text(),'Confirm Chapter Report')]
hd_sideBar                               xpath                        //a[text()='${value}']
tab_sideBar                              xpath                        //h3[normalize-space(text()='${tabName}')][${index}]
txtbox_chapterName                       id                           ValueTextBox0
drpdown_status                           id                           ValueDropDownList2
btn_go                                   id                           ButtonSearch
txt_chapterName                          id                           chp_name
arrow_selectMember                       xpath                        (//th/a)[2]/../../following-sibling::tr[${index}]//td[3]//a
txt_roleList                             xpath                        (//div[@class='role'])[${index}]
btn_removeOfficer                        xpath                         (//div[@class='role'])[${index}]/..//div[@class='remove-officer']
btn_confirmDeletion                      xpath                       //div[@id='deleteDialog']//div[@class='modal-footer']//a[text()='Confirm']
lst_officerRoles                         css                           .role
heading_removeOfficer                    xpath                           //div[@id='deleteDialog']//div[@class='modal-header']//h3
btn_saveEvent                            xpath                          //input[@class='btn btn-blue' and @value='${value}']
txt_iwebUndergrads                       xpath                          //span[@id='F1_sr1_undergraduates']
txt_iwebFacultyCount                     id                            F1_sr1_faculty
txt_iwebReportStatus                     id                            F1_sr1_status
lnk_iwebReportPdf                        css                           #F1_HYPERLINK_2>img
lnk_reportPages                          xpath                          //a[contains(text(),'${value}')]
txt_chapterFacultyAdvisor                xpath                         //span[text()='Chapter Faculty Advisor']/..//span[2] 
txt_reportChapterName                    xpath                         //div[text()='${value}']
txt_eventCategory                        xpath                         //div[@class='event_category']//span[2]
txt_eventIterateByName                   xpath                         //td[contains(text(),'${value}')]/following-sibling::td[2]
btn_saveAndReturn                        css                           input[name*='btnSave']
lst_primaryContact                       css                           .primary input
checkbox_primaryContact                  css                           .primary input[id='${index}']
list_answers                             xpath                         (//th/a)[2]/../../following-sibling::tr
list_pageNos                             xpath                          //td[@id='UP4']//a
list_page                                xpath                          //td[@id='UP4']//a[${index}]
chkbox_notSubmitReport                   xpath                          //input[@id='cbNotSubmitting']
=============================================================================================================================================
