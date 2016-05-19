Page Title: ASM_GivingGreenPage

#Object Definitions
=============================================================================================================================================================
div_selectMember                              id                         selectMember
rad_acsId                                     id                          rID
rad_lastnameMemberNumber                      id                         rMemberNum
inp_username                                  xpath                       //input[contains(@id,'Credential1')]
inp_password                                  xpath                       //input[contains(@id,'Password')]
inp_lastName                                  xpath                       //input[contains(@id,'Credential2')]
btn_login                                     id                         btnMemberLogin
rad_amount                                    css                         input.currency[value='${amount}.0000']
btn_continue                                  xpath                       //input[contains(@id,'cmdContinue')]
inp_otherAmount                               id                          otherAmt
rad_otherAmount                               id                          rbOtherAmt
txt_loginErrorMessage                         xpath                       //p/span[contains(@id,'lblMessage')]
btn_logout                                    css                         .login-link>li>a
btn_loginMember                               id                           btnMember
inp_fieldName                                 xpath                       //input[contains(@id,'Mem${fieldName}')]
list_fieldName                                xpath                       //select[contains(@id,'Mem${fieldName}')]
img_loginLoading                              xpath                       //img[contains(@src,'loading1.gif')]
txt_errorMessage                                id                          lblError2
txt_creditCardError                           xpath                       //span[contains(@id,'CreditCard_lblError')]
=============================================================================================================================================================