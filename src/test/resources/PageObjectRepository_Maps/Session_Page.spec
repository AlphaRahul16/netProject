
Page Title: MAPS_Submission_Page

#Object Definitions
======================================================================================================================================
txt_userrole							classname							pagecontents
rdbtn_userrole                          xpath                               //span[contains(text(),'${value}')]/preceding-sibling::input
lnk_selButton                           xpath                              //a[contains(text(),'${linkName}')]
btn_navPanel                            xpath                              //span[contains(text(),'${linkName}')]
lnk_sessionTypes                        xpath                              //span[@class='x-tree3-node-text' and contains(text(),'${value}')]
heading_sectionName                     xpath                              (//span[text()='${headingName}'])[${index}]
btn_saveAndEdit                         xpath                              (//a[text()='${btnName}'])[${index}]
inp_roomName                            xpath                              //label[text()='${label}']/..//input[@type='text']
chkbox_room                             xpath                              //label[contains(text(),'${label}')]/..//input[@type='checkbox']
select_role                             xpath                              //div[text()='${role}']
drpdwn_filters                          xpath                              (//div[contains(@class,'x-form-field-wrap') and @role="combobox"])[2]
btn_arrow                               xpath                               //div[contains(@qtip,"${label}")]//a
iframe                                  id                                  com.scholarone.s1agxt.s1agxt
img_Loader							    xpath							    //img[contains(@src,'preloader')]
btn_refresh                             css                                .x-btn-text[aria-describedby='x-auto-61']
txt_programTableData					xpath								//td[contains(@class,'${className}')]/div
inp_programField						id									${fieldName}-input
dropdown_programField					css									#${fieldName}-input+img
listItem_programField					xpath								//div[contains(@class,'combo-list-item')]
btn_Types								xpath								//button[contains(text(),'${text}')]
txt_instruction							xpath								//div[contains(text(),'${text}')]
radioBtn_sessionType					xpath								//label[contains(text(),'${text}')]/preceding-sibling::input
inp_sessionType							xpath								//input[@name='${text}']
tabledata_type							xpath								//div[contains(text(),'type30mar2017')]/../following-sibling::td/div[contains(text(),'#FF6600')]
list_table								xpath								//td[@role='gridcell']
img_Loader								xpath								//div[contains(@class,'progress-bar')]
listItem_programField						xpath							//div[contains(@class,'combo-list-item')]
======================================================================================================================================