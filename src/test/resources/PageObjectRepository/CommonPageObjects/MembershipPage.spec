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
link_randomMemberFromList                            xpath                (//a[@class='DataFormListTDDataGridItemLink']/img)[${random number}]
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
btn_saveAndFinish                                    css                    input[id='Bottom_0']
txt_itemsAdded                                      xpath                  	//a[text()='${itemName}']
lineitem_product									xpath					.//table[@class='table']//a[contains(text(),'${Product}')]
list_batch                                          id                        inv_bat_key
list_PaymentType                                     id                       inv_orig_trans_type
list_paymentMethod                                   id                       pin_apm_key
btn_go                                              id                           ButtonSearch
inp_cardNumber                                       id                       pin_cc_number
list_expireDate                                      id                       pin_cc_expire
inp_checkNumber                                      id                       pin_check_number
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
txt_ContactId                                    css                        .TinyTXTOrange
txt_customerAddress                                id                          F1_cxa_mailing_label_html
txt_addressType                                  id                           F1_cxa_adt_key_Display_Text_
label_listMemberShip                             id                          LabelDataFormHeader
list_memberStatus                               xpath                        //a[starts-with(text(),'Member Status')]/../../following-sibling::tr/td[10]
list_joindate                                   xpath                         //td[contains(text(),'active')]/following-sibling::td[1]
txt_effectiveDateForActive                       xpath                          //td[starts-with(text(),'Active')]/following-sibling::td[3]       
inp_enterDetails                                xpath                         //span[contains(text(),'${detailName}')]/../following-sibling::td/input
btn_editContactInfo                              css                          #F1_HYPERLINK_1
inp_editEmail                                    id                           eml_address
btn_editNameAndAddress                           css                          #F1_HYPERLINK_2
inp_firstName                                    id                          ind_first_name
inp_lastName                                    id                          ind_last_name
inp_middleName                                  id                           ind_mid_name
txt_numberOfyears                                xpath                         //td[contains(text(),'Years of Service')]/following-sibling::td
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
link_itemInLineItems                              xpath                        //table[@id='UPDATEGRIDCONTROL_DOD_Invoice_Detail_InternalUpdateGrid']//td[4]/a
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
inp_transactionDate                              id                             inv_trx_date
list_memberStatusInAddMembership                 id                             mbr_mbs_key
list_memberRenewalPackage                        id                             mbr_pak_prd_renewal_key
chk_complimentry                                 id                             ACS_complimentary_checkbox
txt_totalPrice                                   id                             mbr_total
list_complimentryRequest                         id                             ACS_complimentary_request_source
txt_priceOrderEntryLineItmes                     xpath                          //a[text()='${itemName}']/../following-sibling::td[9]/span
inp_sourceCode                                   id                             mbr_src_code
list_chapter                                     id                             mbr_chp_cst_key
link_invoiceListHeadings                         xpath                          //a[contains(text(),'${value}')]
link_addMemership								 xpath							//a[text()='add membership']
chk_complimentry_Sub                             id                             ivd_complimentary_flag_ext
list_complimentryReq_Sub                         id                             ivd_a04_key_ext
txt_priceDetailsBelowLineItems                   id                             inv_${detailsName}
list_priceOrderEntryNetBalance                   xpath                          //*[contains(@id,'ivd_${netPriceName}')]
txt_memberType                                   xpath                          //td[starts-with(text(),'${memberType}')]
txt_effectiveDateMemberType                      xpath                          //td[starts-with(text(),'${memberType}')]/following-sibling::td[4]
txt_expireDateMemberType                         xpath                          //td[starts-with(text(),'${memberType}')]/following-sibling::td[5]   
txt_joinDateMemberType                           xpath                          //td[starts-with(text(),'${memberType}')]/following-sibling::td[2]                      
txt_effectiveDate_chapter                        xpath                          //td[starts-with(text(),'${memberType}')]/following-sibling::td[5]   
txt_joinDate_chapter                             xpath                          //td[starts-with(text(),'${memberType}')]/following-sibling::td[3]
txt_expireDate_chapter                           xpath                          //td[starts-with(text(),'${memberType}')]/following-sibling::td[6]
btn_addBatch                                     id                             inv_bat_key_oAddHyperLink
inp_addBatchName                                 id                             bat_code
list_batchSecurityGroup                          id                             gsc_grp_key
drpdwn_memberType                                xpath                        //select[contains(@id,'QueryAsk')]
btn_detailsMenuAACT                              xpath                        //span[text()='${menuName}']/../a[1]/i[@class='icon-chevron-down']
txt_termStartDaterenewal                         xpath                         (//th/a)[2]/../../following-sibling::tr[${rowNumber}]//td[14]
txt_termEndDaterenewal                           xpath                        (//th/a)[2]/../../following-sibling::tr[${rowNumber}]//td[15]
productname_txt									 xpath							(//th/a)[2]/../../following-sibling::tr[${rowNumber}]//td[4]
pricevalue_txt									 xpath							(//th/a)[2]/../../following-sibling::tr[${rowNumber}]//td[5]
heading_queryAskAtRunTime                        xpath                         //span[text()='Query - Ask At Run-Time Values']
list_memberPackage1                               css                           .DataFormDropDownList
btn_goPackage                                    id                             ButtonAskGo
txt_memberInfo                                   xpath                        //label[contains(text(),'${value}')]/following-sibling::span  
btn_mydTransfer                                  xpath                        //a[@id='F1_HYPERLINK_7']/img    
txt_balanceAmount                                xpath                        //table[@id='TransferTable']//span
list_term                                        id                            ord_ptr_key
list_newPackage                                  id                            mbr_pak_prd_renewal_key
heading_transferPackage                          xpath                         //span[text()='Transfer Package']
btn_transferNow                                  id                            TransferMembershipButtonID
btn_gotorenewal                                  xpath                       (//td[contains(text(),'Active Renewed-No Response')]/preceding-sibling::td[3]//i)[1]
txt_PaymentStatus                                xpath                        //td[contains(text(),'${productName}')]//following-sibling::td[1]
icon_up                                          xpath                         //span[contains(text(),'${value}')]/preceding-sibling::a/i[@class='icon-chevron-up']
txt_productPackage                               xpath                        (//th/a)[2]/../../following-sibling::tr[1]//td[4]
btn_transferMem									xpath							.//*[@id='F1_HYPERLINK_6']/img
drpdown_memtype									xpath							.//select[@id='mbr_mbt_key']
drpdown_package									xpath							.//select[@id='mbr_pak_prd_renewal_key']
drpdown_invoice									xpath							.//*[@id='inv_bat_key']
iframe											id								iframe1
table_rows                                      xpath                           //table[@class='table']//tr
txt_current                                     xpath                           (//th/a)[2]/../../following-sibling::tr[${index1}]//td[5]
txt_startDate                                   xpath                           (//th/a)[2]/../../following-sibling::tr[${index1}]//td[8]
txt_endDate                                     xpath                           (//th/a)[2]/../../following-sibling::tr[${index1}]//td[${index2}]
arrow_selectMember                              xpath                           (//th/a)[2]/../../following-sibling::tr[${index}]//td[3]//a
btn_CurrentYearPencil                           xpath                           //td[contains(text(),'Yes')]/preceding-sibling::td//i
inp_dateForReviewModes                          xpath                           //input[@title='${reviewtitle}']
mbr_autoPay                                     css                             img[title*='mbr_auto_pay'][src*='${value}']
txt_prod_code									id								prd_code
txt_prod_name									id								prc_display_name
img_look_up										css							   .LookUpHyperLink
txt_avl_qty										css							#dgDynamicList > tbody > tr:nth-child(${randomNumber}) > td:nth-child(8)
price_txt										css							#dgDynamicList > tbody > tr:nth-child(${randomNumber}) > td:nth-child(7)
th_lookup										xpath						.//*[@id='dgDynamicList']//th/a[contains(text(),'${field}')]
productName_inp									css							#prd_name
inp_checkNumber                                   id                    	  	 pin_check_number
list_orderFrequency                             id                              ord_frequency

##Credit_Page

icon_expand										css								.icon-chevron-down
drpdown_batchNameCreditPage						css								#cdt_bat_key
label_creditAmount								css								#Caption_cdd_amount
inp_creditAmount								css								#cdd_amount
list_creditReason								id								cdt_rec_key
inp_cardHolderName								id								pin_cc_cardholder_name						
inp_nameOnCheck									id								pin_name_on_check
inp_customerName								id								cst_sort_name_dn
list_liabilityExpense							id								cdd_gla_cr_key
btn_preProcess									id								PreProcess
btn_search										id								Look_Up_cst_sort_name_dn
list_batchCreditPage							id								cdt_bat_key
table_header									xpath							//a[contains(text(),'${text}')]
btn_addBatchCredit								css								#cdt_bat_key_oAddHyperLink>img
txt_creditAvailable								xpath							.//*[@id='cst_credit_available']
inp_customerID									css								#cst_id
td_lineItems									xpath						//td/a[contains(text(),'${text}')]/.. /following-sibling::td[${index}]/a
txt_netTotal									xpath							//*[@id='ivd_nettotal']
txt_netBalanceNetForum								xpath							//td/a[contains(text(),'${text}')]/.. /following-sibling::td[9]/span
label_transCode									xpath								.//*[@id='F1_cdt_code']
img_arrow								       css									 #dgDynamicList > tbody > tr:nth-child(${randomNumber}) > td:nth-child(1) > a > img
txt_tableRow										css									#dgDynamicList > tbody > tr:nth-child(${randomNumber}) > td:nth-child(10)
txt_creditAmount								xpath								//td[contains(text(),'${memberType}')]/following-sibling::td[6]
















