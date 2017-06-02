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



==========================================================================================================================================================================================
