Page Title: MembershipPage

#Object Definitions
==============================================================================================================================================
frame_selectProduct                              id                             menu_a83665ae18eb43488c5d83ce5f6027f8  
btn_add                                              id                      inv_cxa_key_oAddHyperLink
list_addressType                                   id                           cxa_adt_key
chk_doNotValidate                                  css                         #adr_no_validation_flag
inp_addressLine                                    id                           adr_line1
inp_city                                            id                         adr_city
inp_country                                       id                           adr_county
list_state                                        id                          adr_state
inp_postalCode                                    id                         adr_post_code
inp_district                                       id                         adr_county
inp_congressional                                id                            adr_cong_dist
inp_province                                      id                         adr_intl_province
inp_mail                                          id                         cxa_mail_stop
btn_saveForBillingAdd                             id                            ButtonSave   
txt_ContactId                                     id                        F1_cst_id
btn_go                                          id                           ButtonSearch
link_subscriptionInSelectProduct                  id                          HYPERLINK_2
lnk_first_invoice_number                           xpath                        (//table[@id='dgDynamicList']/tbody/tr[not(@class)]/td[1]/a/img)[1]
txt_priceOrderEntryLineItmes                     xpath                          //a[starts-with(text(),'${itemName}')]/../following-sibling::td[9]/span
btn_detailsMenuAACT                              xpath                        //span[text()='${menuName}']/../a[1]
heading_transferPackage                          xpath                         //span[text()='Edit - Membership']
btn_transferNow                                  id                            TransferMembershipButtonID
btn_gotorenewal                                  xpath                       (//td[contains(text(),'Active Renewed-No Response')]/preceding-sibling::td[3]//i)[1]
txt_PaymentStatus                                xpath                        //td[contains(text(),'${productName}')]//following-sibling::td[1]
icon_up                                          xpath                         //span[starts-with(text(),'${value}')]/preceding-sibling::a/i[@class='icon-chevron-up']
txt_productPackage                               xpath                        (//th/a)[2]/../../following-sibling::tr[1]//td[4]
txt_ContactId                                     id                        F1_cst_id
arrow_selectMember                              xpath                           (//th/a)[2]/../../following-sibling::tr[${index}]//td[3]//a

