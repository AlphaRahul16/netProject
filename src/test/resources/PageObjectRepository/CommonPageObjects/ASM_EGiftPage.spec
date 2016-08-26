Page Title: ASM_EGiftPage

#Object Definitions
============================================================================================================================
txt_currentTab                                    css                                .current>span
rad_memberCategories                              xpath                              (//input[@id='rbPrice'])[1]
inp_FirstName                                     xpath                              //input[contains(@id,'txtFirstName')]
inp_LastName                                      xpath                              //input[contains(@id,'txtLastName')]
inp_Email                                         xpath                              //input[contains(@id,'txtEmail')]
btn_continue                                      xpath                              //a[contains(@id,'lbContinue')]
txtAr_personalMessage                             xpath                              //textarea[contains(@id,'txtMessage')]
txt_errorMessage                                  xpath                              //span[contains(@id,'lblErrorMsg')]
list_cardType                                     xpath                              //select[contains(@id,'ddlCreditCardType')]
inp_cardHolderName                                id                                 tbCardholderName
inp_cardNumber                                    id                                 tbCreditCardNumber
list_expMonth                                     id                                 ddlExpirationMonth
list_expYear                                      xpath                              //select[contains(@id,'ddlExpirationYear')]
inp_cvvNumber                                     id                                 tbCcvNumber
chk_termsAndCondition                             xpath                              //input[contains(@id,'cbTerms')]
inp_submit                                        id                                 btnSubmit
btn_printReceipt                                  id                                 topPrintButton
inp_purchaseAnotherGift                           xpath                              //input[contains(@id,'lbDashboard')]
txt_PrintReceiptTitle                             xpath                              //xhtml:title
============================================================================================================================