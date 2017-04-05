
Page Title: MAPS_Submission_Page

#Object Definitions
======================================================================================================================================
txt_userrole							classname							pagecontents
rdbtn_userrole                          xpath                               //span[contains(text(),'${value}')]/preceding-sibling::input
lnk_selButton                           xpath                              //a[contains(text(),'${linkName}')]
btn_navPanel                            xpath                              //span[contains(text(),'${linkName}')]
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
listItem_programField				    xpath						     	//div[contains(@class,'combo-list-item')]
======================================================================================================================================