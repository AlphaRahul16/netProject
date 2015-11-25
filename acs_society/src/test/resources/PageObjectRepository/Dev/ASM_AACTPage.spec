Page Title: ASM_AACTPage

#Object Definitions
=========================================================================================================================================================
txt_invalidEmailFormate                css                 #regexValProspectEmail
list_addressType                       css                 #prospectAddressType>option
inp_schoolWorkInfo                    id                   txt${infoType}
list_schoolWorkInfo                   id                   ddl${infoType}
chk_atAboutYouPage                    xpath                //label[text()='${infoType}']/preceding-sibling::input
txt_paymentError                      xpath                //div[contains(text(),'Payment has failed, please confirm your information and try again')]
=========================================================================================================================================================