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
txt_confim_msg                          xpath                //p[contains(text(),'${msg}')]
lst_options_drpdown                     xpath                //p[contains(text(),'${name}')]/../following-sibling::td/select/option
table_report                            xpath                //table[@align='CENTER']/tbody
lnk_controlId                           css                  P[class='tablecontents']>a
chkbox_selectRole                       xpath                //p[text()='${RoleName}']/../preceding-sibling::td/input
btn_editTitle                           id                   editBodyBtn
chkbox_activate                         xpath                //td[3]/input[@type='CHECKBOX']
chkbox_chked_activate                   xpath                //td[3]/input[@type='CHECKBOX'][@checked]

==========================================================================================================================================================================================
