Page Title: MembershipPage

#Object Definitions
==============================================================================================================================================
hd_sideBar                                          xpath                  //a[text()='${Query Membership}']
hd_page                                             xpath                   //span[@class='PageTitle']
list_existingQuery                                   id                        SavedQueriesDropDownList
img_spinner                                          css                  #__UPIMG
table_query                                          id                    DisplayConditionsTable
btn_runQuery                                         id                   SaveGoButton
img_firstInactiveRegularMember                       css                 #dgDynamicList > tbody > tr:nth-child(3) > td:nth-child(1) > a
link_customerName                                    id                    F1_HYPERLINK_4
txt_memberStatus                                     xpath                   //label[contains(text(),'member status')]/following-sibling::span
list_mbrStatus                                       xpath                  //a[contains(text(),'Mbr Status')]/../../following-sibling::tr/td[6]
img_cross                                           xpath                  //img[@title='${memberDetailName}']
btn_menuItems                                     xpath                    //a[contains(@title,'${menuName}')]/i
img_orderEntry                                      xpath                   //img[contains(@alt,'Order Entry')]
lnk_selectProduct                                   id                      HYPERLINK_17
txt_menuItems                                      id                      HYPERLINK_20
list_association                                    id                     mbr_asn_code
list_memberType                                     id                     mbr_mbt_key
list_memberPackage                                  id                     mbr_pak_prd_key
list_jobTitle                                        id                    mbr_ttl_key_ext
list_industry                                         id                    mbr_sin_key_ext
inp_industryUpdateDate                              id                     mbr_sin_last_update_date_ext
inp_jobTitleUpdateDate                               id                     mbr_jfn_last_update_date_ext
btn_saveAndFinish                                    id                    Bottom_0
txt_itemsAdded                                       xpath                   //a[text()='${itemName}']
list_batch                                          id                        inv_bat_key
list_PaymentType                                     id                       inv_orig_trans_type
list_paymentMethod                                   id                       pin_apm_key
inp_cardNumber                                       id                       pin_cc_number
list_expireDate                                      id                       pin_cc_expire
inp_cvvNumber                                        id                       pin_cc_security_code
txt_rejoinDateForActive                              xpath                    //td[contains(text(),'Active')]/following-sibling::td[2]
img_ticked                                           xpath                    //img[@id='F1_IMAGE_${index}']
list_billingAdd                                    xpath                      //select[@id='inv_cxa_key']/option      
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
table_queryResult                                css                             #DataFormTable  
btn_saveForBillingAdd                             id                            ButtonSave   
table_lineItems                                   id                         UPDATEGRIDCONTROL_DOD_Invoice_DetailGRIDDIV 
frame_selectProduct                              id                             menu_a83665ae18eb43488c5d83ce5f6027f8  
list_billAddress                                 id                         inv_cxa_key    
txt_loadOnExistingQueryLabel                      id                            LoadQueryLabel   
inp_customerId                                    xpath                        //input[contains(@id,'QueryAsk')]        
btn_askGo                                         id                            ButtonAskGo
lnk_invoice_number                                 xpath                        //table[@id='dgDynamicList']/tbody/tr[not(@class)]/td[3][contains(text(),'${value}')]
lnk_first_invoice_number                           xpath                       (//table[@id='dgDynamicList']/tbody/tr[not(@class)]/td[3])[1] 
==============================================================================================================================================