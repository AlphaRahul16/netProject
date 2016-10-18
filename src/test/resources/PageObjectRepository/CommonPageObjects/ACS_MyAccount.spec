Page Title: IndividualsPage

#Object Definitions
======================================================================================================================================
btn_registerMember					xpath					//a[text()='${btnValue}']
input_memberDetails					xpath					//input[@id='${value}']
btn_myAccount                       xpath                   //button[@id='myAccountButton']
list_accountLink                    xpath                   //ul[@id='myAccountMenu']//li//a[text()='${link}']
btn_acsw                            xpath                   //input[contains(@value,'${btnName}')]
btn_addPhone                        xpath                   //label[text()='Home']//..//input[@value='Add Phone']
btn_addAddress                      xpath                   //input[@id='work' and @value='Add Address']
input_email                         xpath                   //input[@id='emailList0.emailAddress']
btn_emailSave                       xpath                   //input[@id='${field}' and @value='Save']
msg_emailSaved                      css                     .emailSussessMsg
inp_homePhone                       xpath                   //label[text()='Home']//..//input[@class='form-control span4 phoneInputTxt']
chkbx_primary                       xpath                   //label[text()='Home']//..//input[@id='chkBoxPrimary']
btn_phoneSave                       xpath                   //input[@id='home' and @value='Save']
inp_changeAddress                   css                     #${value}
chckbox_addressPrimary              css                     #addressPrimayCheckbox
btn_addressSave                     css                     #addressSaveBtnId
msg_addressSave                     css                     #addressError
msg_phoneSave                       xpath                   //label[text()='Home']/..//span[contains(@class,'phoneSuccessMsg')]
img_phonePrimary                    xpath                   //span[text()='Primary']
img_addressPrimary                  xpath                   //div[text()='Primary']
tab_myAccount                       xpath                   //ul//a[text()='${tabName}']
radioBtn_acsMember                  id                      opted-${value}
btn_Save                            xpath                   //input[@value='${btnName}']
inp_loginDetails                    id                      ${data} 
btn_Phone1                          xpath                   //label[text()='Home']//..//input[@value='Change Phone']
btn_ChangeAddress                   xpath                   //p[contains(text(),'You will receive Invoices')]/../../following-sibling::div//input
list_techincalDivisions             xpath                   //form[@id='techdivision']//li        
list_myApplications                 xpath                   //span[text()='${appName}']    
txt_customerId                      xpath                   //div[@id='userInfo']//li[2] 
btn_close                           id                      addressCloseBtnId  
btn_NoThanks                        xpath                   //a[@class='fsrDeclineButton' and text()='No, thanks']      
btn_changePhone                     xpath                   //label[text()='${label}']/..//input[@value='Change Phone']   
======================================================================================================================================