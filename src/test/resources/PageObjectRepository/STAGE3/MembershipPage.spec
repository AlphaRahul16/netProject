Page Title: MembershipPage

#Object Definitions
==============================================================================================================================================
hd_sideBar                                          xpath                  //a[text()='${Query Membership}']
hd_sideBarOuter                                           xpath                 //h3[text()='${sideBarName}']
hd_page                                             xpath                   //span[@class='PageTitle']
list_existingQuery                                   id                        SavedQueriesDropDownList
img_spinner                                          css                     #__UPIMG
table_query                                          id                    DisplayConditionsTable
btn_runQuery                                         id                   SaveGoButton
img_firstInactiveRegularMember                       css                 #dgDynamicList > tbody > tr:nth-child(3) > td:nth-child(1) > a
link_randomMemberInList                              css                 #dgDynamicList > tbody > tr:nth-child(${randomNumber}) > td:nth-child(1) > a
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
txt_itemsAdded                                      xpath                   //a[text()='${itemName}']
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
table_lineItems                                   id                         UPDATEGRIDCONTROL_DOD_Invoice_DetailGRIDDIV 
frame_selectProduct                              id                             menu_a83665ae18eb43488c5d83ce5f6027f8  
list_billAddress                                 id                         inv_cxa_key   
link_email                                       id                          F1_HYPERLINK_0     
txt_contactId                                     id                        F1_cst_recno
txt_customerAddress                                id                          F1_cxa_mailing_label_html
txt_addressType                                  id                           F1_cxa_adt_key_Display_Text_
label_listMemberShip                             id                          LabelDataFormHeader
list_memberStatus                               xpath                        //a[starts-with(text(),'Member Status')]/../../following-sibling::tr/td[10]
list_joindate                                   xpath                         //td[contains(text(),'active')]/following-sibling::td[1]       
inp_enterDetails                                xpath                         //span[contains(text(),'${detailName}')]/../following-sibling::td/input
btn_go                                          id                           ButtonSearch
btn_editContactInfo                              css                          #F1_HYPERLINK_1
inp_editEmail                                    id                           eml_address
btn_editNameAndAddress                           css                          #F1_HYPERLINK_2
inp_firstName                                    id                          ind_first_name
inp_lastName                                    id                          ind_last_name
txt_numberOfyears                                xpath                         //td[contains(text(),'Total Years of Service')]/following-sibling::td
btn_cancel                                       id                           ButtonCancel
list_memberStatusRenewal                        id                            ValueDropDownList4
txt_renewalContactId                             id                           F1_cst_id
chk_advanceNew                                    id                           ctl10
list_advanceNewDropDown                           xpath                        //span[contains(text(),'${headingName}')]/../following-sibling::td[1]/select
inp_advanceNewInput                               xpath                         //span[contains(text(),'${headingName}')]/../following-sibling::td[2]/input
list_advanceNewInput                               xpath                        //span[contains(text(),'${headingName}')]/../following-sibling::td[2]/select
link_subscriptionInSelectProduct                  id                            HYPERLINK_2
inp_prdCode                                       id                           prd_code
inp_searchDisplayButton                           id                           Look_Up_prc_display_name
inp_displayName                                   id                           prc_display_name
link_itemInLineItems                              xpath                        (//tr/td[4])[last()]/a
lnk_pages                                         xpath                        //tr[@class='pager']/td/a[${randomPages}]
txt_membershipProfileInfo                         xpath                        //label[contains(text(),'${memberInfo}:')]/following-sibling::span
txt_paymentStatus                                 xpath                        //td[text()='Payment Status:']/following-sibling::td
txt_membershipProfileDetails                      xpath                        //label[contains(text(),'${memberInfo}')]/preceding-sibling::span
btn_memberShipSubInfo                             xpath                        //span[text()='${membershipName}']/preceding-sibling::a//i[starts-with(@class,'icon-chevron-')]
txt_productName                                   xpath                        (//table[@class='table']//tr[2]/td[4])[1]
txt_invoiceId                                     xpath                        (//table[@class='table']//tr[2]/td[12])[1]
txt_termStartDate                                 xpath                        (//table[@class='table']//tr[2]/td[14])[1]
txt_termEndDate                                   xpath                        (//table[@class='table']//tr[2]/td[15])[1]
txt_productNameOnPage                             xpath                        (//table[@class='table']//tr[3]/td[4])[1]
txt_invoiceIdOnPage                               xpath                        (//table[@class='table']//tr[3]/td[12])[1]
txt_termStartDateOnPage                           xpath                        (//table[@class='table']//tr[3]/td[14])[1]
txt_termEndDateOnPage                             xpath                        (//table[@class='table']//tr[3]/td[15])[1]
inp_customerId                                    xpath                        //input[contains(@id,'QueryAsk')]
btn_askGo                                         id                            ButtonAskGo
txt_recordNumberAtMemberQuery                    classname                      DataFormLabelRecordCount
txt_loadOnExistingQueryLabel                      id                            LoadQueryLabel
link_pagesAvailable                              classname                      DataFormChildDataGridPagerLink
lnk_invoice_number                                 xpath                        //table[@id='dgDynamicList']/tbody/tr[not(@class)]/td[3][contains(text(),'${value}')]
lnk_first_invoice_number                           xpath                        (//table[@id='dgDynamicList']/tbody/tr[not(@class)]/td[3])[1] 
txt_webLogin                                       id                            F1_cst_web_login
btn_arrowRightCircle                             xpath                          (//i[@class='iconpro-circle-arrow-right'])[1]
link_tabsOnModule                                 xpath                          //a[text()='${value}']
btn_tabs                                            css                        .iconpad[src='../images/puzzle-icon.png']
==============================================================================================================================================