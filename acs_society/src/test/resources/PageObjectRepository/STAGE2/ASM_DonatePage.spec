Page Title: ASM_DonatePage

#Object Definitions
=============================================================================================================================================================
inp_DonateProgram                             xpath                       //span[text()='${donateName}']/../following-sibling::div[@class='donate']/input
btn_login                                     xpath                       //a[text()='Log In']
rad_acsId                                     xpath                       //input[contains(@id,'Login4')]
inp_username                                  xpath                       //input[contains(@id,'Credential1')]
inp_password                                  xpath                       //input[contains(@id,'Credential2')]
btn_verify                                    xpath                       //input[contains(@id,'btnLogin')]
btn_continue                                  xpath                       //input[contains(@id,'cmdContinue')]
inp_otherProgram                              id                          txtOtherProgramName
inp_otherAmount                               xpath                       //input[contains(@id,'OtherAmount')]
btn_logout                                    xpath                       //a[text()='Log Out']
btn_continueAsGuest                           id                          btnNonMember
fieldSet_form                                 xpath                       //div[@id='selectNonMember']/fieldset[@class='form']
inp_fieldName                                 xpath                       //input[contains(@id,'NM${fieldName}')]
list_fieldName                                xpath                       //select[contains(@id,'NM${fieldName}')]
txt_loginErrorMessage                         xpath                       //p/span[contains(@id,'lblMessage')]
txt_errorMessage                              id                          lblError
inp_giftToSomene                              xpath                       //input[@id='giftToSomeone']
chk_honor_memory                              id                           ${honor_memory}  
inp_honor_memory                              id                          in${honor_memory}Of    
inp_recipientEmail                            id                          cardEmail        
list_cardType                                 xpath                       //select[contains(@id,'CardType')]        
inp_cardHolderName                            xpath                       //input[contains(@id,'CCName')]  
inp_cardNumber                                xpath                      //input[contains(@id,'CCNumber')]
inp_CVVNumber                                 xpath                      //input[contains(@id,'CVVNumber')]     
list_expirationDate                           xpath                      //select[contains(@id,'ccExpDate')]
list_expirationYear                           xpath                      //select[contains(@id,'ccExpYear')]
rad_sendCardType                              id                          by${Email_Postal_Nothing}
inp_to                                        id                          cardTo
inp_postalMailInfo                            xpath                       //input[contains(@id,'Post${postalMailInfo}')]
list_postalMailInfo                           xpath                       //select[contains(@id,'Post${postalMailInfo}')]
txt_creditCardError                           xpath                       //span[contains(@id,'CreditCard_lblError')]
=============================================================================================================================================================