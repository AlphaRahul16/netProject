Page Title: ASM_DonatePage

#Object Definitions
=============================================================================================================================================================
inp_DonateProgram                             xpath                       //span[text()='${donateName}']/../following-sibling::div[@class='donate']/input
btn_login                                     xpath                       //a[text()='Log In']
btn_memlogin                                  css                         #btnMember
rad_acsId                                     xpath                       //input[contains(@id,'Login4')]
inp_username                                  xpath                       //input[contains(@id,'Credential1')]
inp_password                                  xpath                       //input[contains(@id,'Credential2')]
btn_verify                                    xpath                       //input[contains(@id,'btnLogin')]
btn_continue                                  xpath                       //input[contains(@id,'cmdContinue1')]
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
txt_productNames                              xpath                            .//*[@id='dgDynamicList']/tbody/tr/td[3]
txt_product_displayName                       xpath                           (//td[contains(text(),'${productName}')])[1]/following-sibling::td[1]
txt_product_Code                              xpath                             (//td[contains(text(),'${productName}')])[1]/following-sibling::td[2]
txt_totalamount                               css                             #total-amt
txt_totalOnDonationPage                       css                             #lblTotal
txt_PhoneNo                                  xpath                        //label[text()='Phone:']/../span
txt_Email                                     css                          a[href*='mailto']
txt_Address                                   xpath                        .//*[@id='F1_cxa_mailing_label_html']
txtbox_inpfeild                               xpath                        (//label[contains(text(),'${value}')]/following-sibling::input)[1]
drpdwn_country                                xpath                        //*[contains(text(),'${value}')]/following-sibling::select/option[@selected]
txt_inpName                                   xpath                        //label[contains(text(),'Name')]/following-sibling::b/span
txt_confirmDonation_product                   xpath                          //td[@class='program']/span/span
txt_confirmDonation_amount                    xpath                         //td[@class='program']/span/span[contains(text(),'${value}')]/../../following-sibling::td/span
inp_recipientPersonalisedMsg                  id                            cardMessage
txt_thankYouMessage                           css                            .yui-u.first.confirmation-message>p:nth-child(2)
lnk_printReceipt                              css                            .print-receipt>a
txt_confirmationEmailBox                      css                             .yui-g.confirmation-box>p
btn_loginMember                               id                               btnMemberLogin
txt_DonateProgram                             xpath                            //span[text()='${donateName}']
btn_submitPayment                             xpath                       //input[contains(@id,'cmdContinue')]
=============================================================================================================================================================