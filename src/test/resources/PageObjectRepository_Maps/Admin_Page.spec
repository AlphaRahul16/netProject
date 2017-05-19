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
inp_roles_chkbox                        xpath                //p[text()=' ${RoleName}  ']/../preceding-sibling::td/input
img_searchButton                        xpath                //img[contains(@src,'search.gif')]
==========================================================================================================================================================================================
