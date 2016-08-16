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
btn_passportAdd                            xpath                              //input[contains(@id,'btnAddPassport')]
txt_amount                                 xpath                              //*[@id='page-subtotal']/p
chk_archive                                xpath                              .//span[contains(text(),'Accts of Chem Res Mbr Web Archive')]/..//input[@title='Add to Membership']
txt_invoiceValue                           xpath                              .//*[@id='lblInvoice']
list_productName                           css                              .inv-details>span:nth-child(2)>span
list_productAmount                        xpath                              .//*[@class='inv-details']/following-sibling::td[1]/span
txt_welcome									css								#welcome-msg>span>h2>span
txt_taxAmount                              xpath                              .//*[@id='lblTax']
txt_shippingAmount                         xpath                              .//*[@id='lblShipping']
btn_printOrderReceipt                      xpath                              .//*[@id='print-invoice']/span/input\
txt_recordNumber                           id                                 ValueTextBox0
btn_search                                 id                                 ButtonSearch
lnk_moreTab                                id                                 ProfileTabMenuImage_TS0
lnk_subscriptionTab                        xpath                              .//*[@class='ProfileTitleLight']//a[text()='Subscriptions']
lnk_activeSubscription                     xpath                              (.//*[@class='icon-chevron-down'])[1]
td_subscription				   xpath                              //td[contains(text(),'${productName}')]/..//td[contains(text(),'${productAmount}')]
btn_printReceipt                           xpath                              .//*[@id='print-invoice']/span/input
img_spinner                                 css                           #__UPIMG
txt_pdf_productName							xpath							.//xhtml:div[contains(text(),'ACS Member E-Passport: 250')]

===========================================================================================================================================