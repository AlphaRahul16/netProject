Page Title: MAPS_Admin_Page

#Object Definitions
======================================================================================================================================

lnk_leftPanelInstructions				xpath				//div[contains(text(),'${name}')]
inp_usrDetails                          xpath               //p[contains(text(),'${name}')]/../following-sibling::td/input
drpdwn_usrDetails                       xpath               //p[contains(text(),'${name}')]/../following-sibling::td/select
inp_roles_chkbox                        xpath               //p[text()=' ${RoleName}  ']/../preceding-sibling::td/input
inp_searchField  					    xpath			    //input[@name='${text}']
lnk_defaultInstructions					xpath			    //b[contains(text(),'${label}')]
txt_titleDescription					xpath				//p[text()='${titleName}']/ancestor::tr/following-sibling::tr//span[@class='pagecontents']
img_searchButton                        xpath                //img[contains(@src,'${imagename}.gif')]
txt_confim_msg                          xpath                (//p[contains(text(),'${msg}')])[1]
lst_options_drpdown                     xpath                //p[contains(text(),'${name}')]/../following-sibling::td/select/option
table_report                            xpath                //table[@align='CENTER']/tbody
lnk_controlId                           css                  P[class='tablecontents']>a
chkbox_selectRole                       xpath                //p[text()='${RoleName}']/../preceding-sibling::td/input
btn_editTitle                           id                   editBodyBtn
chkbox_chked_activate                   xpath                //td[3]/input[@type='CHECKBOX'][@checked]
txt_chked_txtBox_name                   xpath                //input[@type='CHECKBOX'][@checked]/../preceding-sibling::td[2]//a
txt_status                              xpath                //a[contains(text(),'${rolename}')]/../../following-sibling::td[1]//font
chkbox_inactiveRole                     xpath                //a[contains(text(),'${rolename}')]/../../following-sibling::td[2]//input
inp_templateName                        xpath                //p[contains(text(),'Template Name')]/input
lnk_templateName                        xpath                //a[contains(text(),'${text}')]
txt_emaillog_tbl_headings               css                  .headercells>.pagecontents>b
select_email_template                   css                  select[name='${text}']
drpdwn_selectRole						css					select[name*='XIK_SELECTED_ROLE_ID']
drpdwn_copyRole							css					select[name*='XIK_ROLE_ID_TO_COPY_FROM']
btn_go									css					img[src*='/images/buttons/go.gif']
txt_searchField							css					input[name*='ABSTRACT_SEARCH_FIRST_NAME']
list_searchTitle						xpath				(//p/a[contains(@href,'popWindow')])[1]
list_titleWithDraftStatus				xpath				//p[contains(.,'Draft')]/../..//td[2]/p/a[contains(@href,'popWindow')]
lnk_abstractTitle						xpath				//a[contains(.,'${value}')]
btn_editBody							id					editBodyBtn
iframe_title							xpath				//div[@id='cke_1_contents']/iframe
txt_editTitle                           xpath               //body[contains(@class,'cke_editable cke')]
lnk_ReviewSubmit						xpath				//a[contains(.,'Review & Submit')]
btn_finish								xpath				//a[contains(.,'Finish')]
lnk_withdraw							xpath				//a[contains(.,'Withdraw')]
lnk_unwithdraw							xpath				//a[contains(.,'Unwithdraw')]
list_abstractID							xpath				//a[contains(.,'Withdraw')]/../../../td[1]
list_abstractIDUnwithdraw				xpath				//a[contains(.,'Unwithdraw')]/../../../td[1]
txt_abstractID							xpath				//td[@data-label='ID' and contains(.,'${abstractID}')]/../td[contains(.,'Withdrawn')]
txt_abstractIDUnwithdraw				xpath				//td[@data-label='ID' and contains(.,'${abstractID}')]/../td[contains(.,'Under Review')]
img_startANewDataExport					xpath				//img[@src='/images/buttons/start_a_new_data_export.gif']
txt_exportName							xpath				//input[@name='EXPORT_LOCATION_NAME']
btn_exportGo							xpath				//input[@src='images/buttons/go.gif']
list_exportName							xpath				//span[contains(.,'${value}')]
lnk_dataExport							xpath				(//div[contains(.,'${value}')])[4]
img_abstractSearch						xpath				//img[contains(@src,'search.gif')]
lnk_username							css					.dropdown-toggle.modify-account-lnk
==========================================================================================================================================================================================
