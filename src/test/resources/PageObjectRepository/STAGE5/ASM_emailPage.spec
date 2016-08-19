Page Title: ASM_emailPage

#Object Definitions
=======================================================================================================================================================================================
inp_username                        xpath                            //input[contains(@id,'Credential1')]
inp_password                        xpath                            //input[contains(@id,'Credential2')]
btn_login                            id                               btnLogin
txt_loginErrorMessage                xpath                            //p/span[contains(@id,'lblMessage')]
lnk_editAccount                      css                              #editAcctLink
txt_selectCategory                    id                               welcome
lnk_change                          xpath                             (//button[text()='subscribe'])[1]/../preceding-sibling::div[@class='manage-email']//a[text()='change']
txt_ACSProductName                  xpath                             (//button[text()='subscribe'])[1]/../preceding-sibling::div//h2
drpdwn_selectEmail                  xpath                             (//select[@class='email'])[1]
btn_subscribe                       xpath                             (//button[text()='subscribe'])[1]
btn_ACSSubscribe_unsubscribe        xpath                              //h2[text()='${product name}']/../following-sibling::div//button
list_subscribe                      css                             .subscribe-btn
btn_unsubscribeAll                  css                              .unsubscribe-all-btn
btn_unsubscribeConfirm               css                              #unsubscribe-btn-confirm
btn_newMail                         xpath                              (//button[text()='subscribe'])[1]/../preceding-sibling::div//a[text()='new']
inp_newEmail                        xpath                               (//button[text()='subscribe'])[1]/../preceding-sibling::div//input[@class='new-email-input']
btn_addNewMail                      xpath                               (//button[text()='subscribe'])[1]/../preceding-sibling::div//a[text()='add']
btn_cancel                          id                              overlay-btn-cancel
txt_addedEmail                      xpath                              //h2[contains(text(),'${productName}')]/../following-sibling::div//span[@class='send-to-address']
txt_failMessage                     id                                 fail-message
chk_selectAll                       xpath                              //div[@class='select-all']//input
txt_selectedProductName             xpath                               //div[@class='select-all']//input/../../preceding-sibling::h3
txt_categoryName                    xpath                              //h3[text()='${productName}']/..
subCategory                        xpath                               //div[@data-category-id='${categoryId}' AND contains(@id,'subscription')]
txt_subscribeToAll                 xpath                               //div[@data-category-id='${categoryId}']/div[3]
btn_newsletterAction           xpath                         //div/h2[text()='${list name}']//..//following-sibling::div[4]/button
btn_newslettedHeading          xpath                         //div/h2[text()='${list name}']
list_mailingListCategory            css                       div[id*=category]>h3
=======================================================================================================================================================================================