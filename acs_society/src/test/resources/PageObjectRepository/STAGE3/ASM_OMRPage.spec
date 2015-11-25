Page Title: ASM_OMRPage

#Object Definitions
=================================================================================================================================================
hdng_OMRLogo                                 css                                       .app-logo>h2
rad_ACSid                                    xpath                                     //input[contains(@id,'Login2')]
rad_LNMemNo                                  xpath                                     //input[contains(@id,'Login1')]
rad_LNNoticeNo                               xpath                                     //input[contains(@id,'Login0')]
inp_userName_lastName                        xpath                                     //input[contains(@id,'Credential1')]
inp_password_mem_notice                      xpath                                     //input[contains(@id,'Credential2')]
btn_verify                                   xpath                                     //input[contains(@id,'btnLogin')]
txt_loginErrorMessage                        xpath                                     //span[contains(@id,'lblMessage')]
hdng_welcome                                 id                                        welcome-msg
link_edit                                    xpath                                     //span[@class='edit']/a
inp_updateAddress                            xpath                                     //label[contains(text(),'${filedName}')]/following-sibling::input[1]
btn_save                                     xpath                                     //input[contains(@id,'btnContinue')]
list_updatedAddress                          xpath                                     //p[@class='address']/span[position()>1]
txt_errorMessage                             xpath                                     //p[contains(text(),'${errorMessage}')]
txt_emailAddress                             xpath                                     //span[contains(@id,'lblEmailAddress')]
chk_eula                                     id                                        cbPubSciEula
list_cardType                                 xpath                                     //select[contains(@id,'CardType')]        
inp_cardHolderName                            xpath                                      //input[contains(@id,'holderName')]  
inp_cardNumber                                xpath                                      //input[contains(@id,'CardNumber')]
inp_CVVNumber                                 xpath                                     //input[contains(@id,'CcvNumber')]     
list_expirationDate                           xpath                                     //select[contains(@id,'ExpirationMonth')]
list_expirationYear                           xpath                                     //select[contains(@id,'ExpirationYear')]
btn_continue                                  id                                     btnContinue
txt_invalidCardErrorMessage                   id                                        lblError
btn_submitPayment                             id                                        btnSubmitOmrPayment
txt_navigation                                xpath                                     //ul[@title='Bread Crumbs']/li[3]
chkConfirm                                      id                                     //input[contains(@id,'Confirm')]
rad_No                                        xpath                                    //label[text()='No']/preceding-sibling::input
txt_confirmPage                             xpath                                      //legend[text()='Confirm your information']
=================================================================================================================================================