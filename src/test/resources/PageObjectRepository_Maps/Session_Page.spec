
Page Title: MAPS_Submission_Page

#Object Definitions
======================================================================================================================================
txt_userrole							classname							pagecontents
rdbtn_userrole                          xpath                               //span[contains(text(),'${value}')]/preceding-sibling::input
lnk_selButton                           xpath                              //a[contains(text(),'${linkName}')]
btn_navPanel                            xpath                              //span[contains(text(),'${linkName}')]
lnk_sessionTypes                        xpath                              //span[@class='x-tree3-node-text' and contains(text(),'${value}')]
heading_sectionName                     xpath                              (//span[text()='${headingName}'])[${index}]
btn_saveAndEdit                         xpath                              (//a[contains(text(),'${lnkname}')])[${index}]
inp_roomName                            xpath                              //label[contains(text(),'${headingName}')]/..//input[@type='text']
chkbox_room                             xpath                              //label[contains(text(),'${label}')]/..//input[@type='checkbox']
select_role                             xpath                              //div[text()='${role}']
inp_saveGridFilters                     xpath                              (//div[contains(@class,'x-form-field-wrap') and @role="combobox"])[${index}]//input
btn_arrow                               xpath                               //div[contains(@qtip,"${label}")]//a
iframe                                  id                                  com.scholarone.s1agxt.s1agxt
img_Loader							    xpath							    //img[contains(@src,'preloader')]
btn_refresh                             xpath                               (//button[@class='x-btn-text']/img)[6]
txt_programTableData					xpath								//td[contains(@class,'${className}')]/div
inp_programField						id									${fieldName}-input
dropdown_programField					css									#${fieldName}-input+img
listItem_programField					xpath								//div[contains(@class,'combo-list-item')]
btn_Types								xpath								//div[@class='x-window-bl']//button[contains(text(),'${text}')]
txt_instruction							xpath								//div[contains(text(),'${text}')]
radioBtn_sessionType					xpath								//label[contains(text(),'${text}')]/preceding-sibling::input
inp_sessionType							xpath								//input[@name='${text}']
tabledata_type							xpath								//div[contains(text(),'${text1}')]/../following-sibling::td/div[contains(text(),'${text2}')]
list_table								xpath								//td[@role='gridcell']
img_processbar							xpath								//div[contains(@class,'progress-bar')]
inp_addHost								xpath								//div[@gxt-dindex='${fieldName}']/input
inp_filtertext                          xpath                               //div[contains(@class,'x-menu-list')]//input
lnk_filters                             xpath                               //a[contains(@class,'x-menu-item') and contains(text(),'${dropdownOptions}')]
txt_tableData                           xpath                               (//div[contains(@class,'grid3-body')])[${index1}]//table//td[${index2}]/div
img_dropDown                            xpath                               (//div[contains(@class,'x-form-field-wrap') and @role="combobox"])[${index}]//img
txt_tableResult                         xpath                               //td[contains(@class,'x-grid3-td-${label}')]//div[text()='${value}']
lst_column                              xpath                               //div[@role='listitem' and text()='${columnName}']
btn_add                                 xpath                               //td[@class='x-toolbar-left']//button[text()='${btnName}']
chkbox_column                           xpath                               //div[text()='${roomName}']/../preceding-sibling::td[${index}]//div[@class='x-grid3-row-checker']
btn_add_column                          xpath                               //div[text()='${roomName}']/../preceding-sibling::td[1]//div[@class='x-grid3-row-expander']
btn_recordsname                         xpath                               (//div[@class='x-grid3-row-checker'])[${recordnumber}]/../../following-sibling::td[${index}]/div
txt_totalRecords                        xpath                               //div[@class='x-grid3-row-checker']
chkbox_records                          xpath                               (//div[@class='x-grid3-row-checker'])[${recordnumber}]
listItem_SymposiumType					xpath								//div[contains(@class,'${className}') and contains(text(),'${value}')]
txt_hostDetails							xpath								//td[contains(@class,'${value}')]/div
txt_dropField							css								    .x-grid3-scroller
img_loading								xpath								//div[contains(text(),'${text}')]
btn_remove								xpath								(//button[text()='${btnName}'])[${index}]
btn_SessionTopic                        xpath                               //label[contains(.,'${value}')]/preceding-sibling::input
txt_searchResults                       css                                 .x-grid3-col-owner_${value}
txt_alertAddOwner                       css                                 .ext-mb-content>span
table_columnDate                        css                                 .x-grid3-${columnName}
inp_fileupload							xpath								(//label[contains(text(),'${text}')]/following-sibling::div//input)[${index}]
list_symposia							xpath								//table[contains(@class,'row-table')]
drpDown_sympType						xpath								//span[contains(text(),'${label}')]/../following-sibling::div//img
date_currentDate                        xpath                               //td[contains(@class,'x-date-today')]
tbl_contents                            xpath                                //td[contains(text(),'${text}')]
txt_chckdColumnData                     xpath                               (//div[@class='x-grid3-row-checker'])[${index1}]/../../following-sibling::td[${index2}]
column_headers                          xpath                                //div[contains(@qtip,'${columnName}')]
checked_columnHeadings                  xpath                                //a[contains(text(),'Columns')]/../../../following-sibling::div[2]//a[contains(@class,'x-menu-checked')]
txt_sortingOrder                        xpath                                //div[contains(text(),'${columnName}')]/../following-sibling::td[contains(@class,"x-grid3-td-F_SORT_DIR")]
img_editColumnHeading                   xpath                                //img[contains(@src,'pencil_small.png')]/..
input_filter							xpath								(//div[contains(text(),'${text}')]/../following-sibling::td//input)[${index}]
input_label								xpath								//label[contains(text(),'${label}')]/following-sibling::div//${tagName}
txt_label								xpath								//label[contains(text(),'${label}')]
input_area								xpath								//span[contains(text(),'${label}')]/parent::label/following-sibling::div//${tagName}
listitem_dropdown						xpath								//div[text()='${text}']/following-sibling::div[text()='${Value}']
lable_checkbox							xpath								//span[contains(text(),'${label}')]/parent::label/following-sibling::div//${tagName}[@type='checkbox']
label_listbox							xpath								//span[contains(text(),'${label}')]/parent::label/following-sibling::div//${tagName}[@role='listbox']
txt_deletedValue						xpath								//span[contains(text(),'${text}')]/../following-sibling::div//div[@class='grid3-scroller']//tbody//div[contains(text(),'${dataToBEdeleted}')]
drpdown_Symposium						xpath								//label[contains(text(),'${text}')]/following-sibling::div//img[contains(@class,'arrow')]
txt_SchedulerGrid						xpath								//div[contains(@class,'${className}')]
drpDown_meetingDay						xpath								//div[contains(text(),'${label}')]/../following-sibling::td//img
input_editableColumn                    xpath                               //div[contains(@class,'x-grid3-col-${columnName}')]
table_abstracts                         css                                 .x-panel-mc
top_scroller                            xpath                               //div[contains(@class,"x-menu-scroller-top")]
list_abstracts                          xpath                               //div[contains(@class,'${className}')]//div[contains(@class,'x-unselectable-single')]
txt_session								xpath								//div[contains(@class,'view-item')]/font
txt_controlId							xpath								//td[contains(@class,'${className}')]//u[contains(text(),'${text}')]
row_withdraw       						xpath        						//font[contains(text(),'${fontText}')]/following-sibling::u[contains(text(),'${text}')]
inp_sessionAbbrev                       xpath                               //label[contains(text(),'${label}')]/..//input[@type='text']
inp_sessionNotes						xpath								//label[contains(text(),'${label}')]/following-sibling::div//textarea
ipt_div									xpath								//label[contains(text(),'${label}')]/following-sibling::div//div
inp_label								xpath								//label[contains(text(),'${label}')]
inp_FnclCosponsor						xpath								//span[contains(text(),'${label}')]/parent::label/following-sibling::div//textarea
inp_sessionTrack						xpath								//span[contains(text(),'${label}')]/parent::label/following-sibling::div//input[@type='text']
lstItm_drpdwn							xpath								//div[contains(@class,'combo-list-item') and text()='${value}']
chkbox_Newsworthy						xpath								//span[contains(text(),'${label}')]/parent::label/following-sibling::div//input[@type='checkbox']
lstbox_Themes							xpath								//span[contains(text(),'${label}')]/parent::label/following-sibling::div//div[@role='listbox']
row_withdraw							xpath								//font[contains(text(),'${fontText}')]/following-sibling::u[contains(text(),'${text}')]
input_popup								xpath								(//label[contains(text(),'${label}')]/parent::td/following-sibling::td/..//input)[${index}]
btn_lnkTxt								xpath								//label[contains(text(),'${text}')]/parent::td/following-sibling::td/..//a[text()='${linkText}']
img_processbar							xpath								//div[contains(@class,'progress-bar')]
txt_rowData                             xpath                               //div[contains(@class,'x-grid3-col-F_SORT_FIELD')]/..
inp_assignDuration						xpath								//label[contains(.,'${text}')]/../following-sibling::td//input[@type='text']
txt_linkEmail							xpath								//td[contains(@class,'${className}')]//u\
inp_editColumnData                      xpath                               //div[contains(@class,'x-editor')]//input[contains(@class,'x-form-field x-form-text')]
txt_emptyTable							css									.x-grid-empty
img_expandTab                           xpath                               //span[text()='${tabName}']/..//div[contains(@class,'x-panel-toolbar x-component')]
lnk_eventName                           xpath                               //div[@qtip='${eventName}']
txt_eventInfo                           xpath                               //b[text()='${fieldName}:']/../../td[2]
txt_sessionItinerary                    xpath                               //div[@class='x-panel x-component']//div[@class='x-tree3-node-ct']//span[@class='x-tree3-node-text']
#txt_colName						    xpath								(//colgroup[@role='presentation']/following-sibling::tbody//td//span)[${index}]
txt_AllColName							xpath								//a[contains(@class,'x-menu-item')]
txt_popUpmsg							xpath								//p[contains(text(),'${msg}')]
select_presentationType                 id                                  ${dropdownType}_combobox
select_symposiumType                    css                                 #symposia_title_combobox>option:nth-child(2)
txt_filterData                          xpath                               (//img[contains(@class,'x-menu-item-icon')]/..//div//input)[${index}]
input_editableColumnindex               xpath                               (//div[contains(@class,'x-grid3-col-${columnName}')])[${index}]
txt_displayedCol						xpath								//a[contains(@class,'x-menu-checked')]
btn_sessionTypes                        xpath    							//button[contains(text(),'${text}')]
btn_cancel                              xpath                               //div[@class='x-plain-bwrap']//td[${index}]//table
======================================================================================================================================