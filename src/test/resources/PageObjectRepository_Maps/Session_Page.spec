
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
inp_saveGridFilters                     xpath                              (//div[contains(@class,'x-form-field-wrap') and @role="combobox"])[${index}]//input
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
tabledata_type							xpath								//div[contains(text(),'${text1}')]/../following-sibling::td/div[contains(text(),'${text2}')]
list_table								xpath								//td[@role='gridcell']
img_processbar							xpath								//div[contains(@class,'progress-bar')]
listItem_programField					xpath								//div[contains(@class,'combo-list-item')]
img_processbar							xpath								//div[contains(@class,'progress-bar')]
inp_addHost								xpath								//div[@gxt-dindex='${fieldName}']/input
inp_filtertext                          xpath                               //div[contains(@class,'x-menu-list')]//input
lnk_filters                             xpath                               //a[contains(@class,'x-menu-item') and contains(text(),'${dropdownOptions}')]
txt_tableData                           xpath                               (//div[contains(@class,'grid3-body')])[${index1}]//table//td[${index2}]
img_dropDown                            xpath                               (//div[contains(@class,'x-form-field-wrap') and @role="combobox"])[${index}]//img
inp_addHost						    	xpath							    //div[@gxt-dindex='${fieldName}']/input
txt_tableResult                         xpath                               //td[contains(@class,'x-grid3-td-${label}')]//div[text()='${value}']
lst_column                              xpath                               //div[@role='listitem' and text()='${columnName}']
btn_add                                 xpath                               //td[@class='x-toolbar-left']//button[text()='${btnName}']
chkbox_column                           xpath                               //div[text()='${roomName}']/../preceding-sibling::td[2]//div[@class='x-grid3-row-checker']
btn_recordsname                         xpath                               (//div[@class='x-grid3-row-checker'])[${recordnumber}]/../../following-sibling::td[1]/div
btn_close								xpath								//button[text()='${btnName}']
img_dropDown                            xpath                               (//div[contains(@class,'x-form-field-wrap') and @role="combobox"])[${index}]//img
btn_recordsname                         xpath                               (//div[@class='x-grid3-row-checker'])[${recordnumber}]/../../following-sibling::td[1]/div
chkbox_records                          xpath                               (//div[@class='x-grid3-row-checker'])[${recordnumber}]
listItem_SymposiumType					xpath								//div[contains(@class,'combo-list-item') and contains(text(),'${value}')]
txt_hostDetails							xpath								//td[contains(@class,'${value}')]/div
txt_dropField							xpath								//div[@class='x-grid-empty']
img_loading								xpath								//div[contains(text(),'${text}')]
btn_remove								xpath								(//button[text()='${btnName}'])[${index}]
txt_dropField							xpath								//div[@class='x-grid-empty']
======================================================================================================================================