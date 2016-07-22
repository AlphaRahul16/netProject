Page Title: ASM_PUBSPage

#Object Definitions
===========================================================================================================================================
inp_userName                               xpath                              //input[contains(@id,'Credential1')]
inp_password                               xpath                              //input[contains(@id,'Credential2')]
btn_verify                                 xpath                              //input[contains(@id,'btnLogin')]
btn_subscriptionAdd                        xpath                              //input[contains(@id,'btnAddSubscription')]
txt_eSubscription                          xpath                              //span[text()='My E-Subscriptions']
btn_add                                    xpath                              (//table[@id='publications']//tr//td/input[3])[1]
txt_added                                  xpath                              //span[text()='Added']/..[@style='display: block;']
btn_save                                   id                                 btnSave
tr_saved                                   xpath                              //table[@class='inv-table']/tbody/tr[4]
list_cardType                              xpath                              //select[contains(@id,'CreditCard_pin_apm_key')]
inp_cardHolderName                         xpath                              //input[contains(@id,'CreditCard_pin_cc_cardholder_name')]
inp_cardNumber                             xpath                              //input[contains(@id,'CreditCard_pin_cc_number')]
inp_CVVNumber                              xpath                              //input[contains(@id,'CreditCard_pin_cc_security_code')]
list_expirationYear                        xpath                              //select[contains(@id,'CreditCard_pin_cc_expire_ccExpYear')]
btn_ConfirmOrder                            xpath                              //input[contains(@id,'btnConfirmOrder')]
btn_placeOrder                             id                                 btnPlaceOrder
txt_receipt                                xpath                              //span[text()='Receipt']
txt_paymentPage                            xpath                              //legend[text()='Billing Information']
===========================================================================================================================================