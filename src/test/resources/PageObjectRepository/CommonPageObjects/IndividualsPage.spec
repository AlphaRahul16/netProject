Page Title: IndividualsPage

#Object Definitions
======================================================================================================================================
inp_fieldName                             xpath                //span[starts-with(text(),'${fieldName}')]/../following-sibling::td//input
inp_expiredate				              xpath		           //*[@id='AskAtRunTimeTable']//input
select_member				              xpath		          .//*[@id='AskAtRunTimeTable']//strong[contains(text(),'${field}')]/../..//select[@class='DataFormDropDownList']
btn_Go                                    xpath                //input[@id='ButtonSearch']
btn_askGo                                 xpath                 //input[@id='ButtonAskGo']
txt_userEmail                             xpath                //a[text()='${email}']
txt_memberDetails                         xpath                //*[@id='F1_cxa_mailing_label_html']
txt_additionalInfo                        xpath                //span[text()='${infoValue}']
btn_memberShip                            xpath                //span[text()='${membershipName}']/preceding-sibling::a//i[@class='icon-chevron-down']
btn_memberShipAACT                        xpath                //span[text()='${membershipName}']/../a[1]
img_moreMenu                              id                   ProfileTabMenuImage_TS0
link_moreMenuName                         xpath                //a[@title='${menuNames}']
txt_divisionPubName                       xpath                //td[contains(text(),'${divisionPubName}')]
txt_numberOfyears                         xpath                //td[contains(text(),'Total Years of Service')]/following-sibling::td
txt_invoiceValue                          xpath                //td[@id='UP24']
txt_invoiceAACT                           xpath                //td[@id='UP25']
txt_priceValue                            xpath                //td[contains(text(),'${productName}')]/following-sibling::td[1]
txt_quantity                              xpath                //td[contains(text(),'${productName}')]/following-sibling::td[2]
txt_total                                 xpath                //td[contains(text(),'${productName}')]/following-sibling::td[3]
txt_payment                               xpath                //td[contains(text(),'${productName}')]/following-sibling::td[4]
txt_balance                               xpath                //td[contains(text(),'${productName}')]/following-sibling::td[5]
btn_invoiceAtMembership                   xpath                //td[starts-with(text(),'Active')]/preceding-sibling::td[3]/a
lnk_lastName                              id                   F1_HYPERLINK_4
link_pageLink                             css                  .DataFormChildDataGridPagerLink:nth-child(${pageNumber})
list_pageLink                             css                  .DataFormChildDataGridPagerLink
txt_contactId                             xpath                //*[contains(text(),'contact id:')]/preceding-sibling::span
img_aactMember                            xpath                //span[contains(.,'aact member:')]/following-sibling::span/img[@title='New Image']
img_member                                xpath                //span[contains(.,'aact member:')]/following-sibling::span/img[@title='Non Member']
img_noMemberBenefits                      xpath                //span[contains(.,'aact member:')]/following-sibling::span/img[@title='No Member Benefits']
img_spinner                               xpath                //*[contains(@src,'updating.gif')]
txt_termStartDate                         xpath                //td[contains(text(),'${productName}')]/following-sibling::td[10]
txt_termEndDate                           xpath                //td[contains(text(),'${productName}')]/following-sibling::td[11]
lnk_pages                                 xpath                //tr[@class='pager']/td/a
btn_invoicearrow                          css                  .iconpro-circle-arrow-right
list_rowsInSubscription                   xpath                (//th/a)[2]/../../following-sibling::tr
txt_subscriptionName                      xpath                (//th/a)[2]/../../following-sibling::tr[${rowNumber}]//td[5]
txt_subscriptionPrice                     xpath                (//th/a)[2]/../../following-sibling::tr[${rowNumber}]//td[6]
txt_subscriptionStartDate                 xpath                (//th/a)[2]/../../following-sibling::tr[${rowNumber}]//td[7]
txt_subscriptionEndDate                   xpath                (//th/a)[2]/../../following-sibling::tr[${rowNumber}]//td[8]
txt_subscriptionIssueFulfilled            xpath                (//th/a)[2]/../../following-sibling::tr[${rowNumber}]//td[9]
txt_subscriptionStartIssue                xpath                (//th/a)[2]/../../following-sibling::tr[${rowNumber}]//td[10]
btnArrowProdName                          xpath                //td[contains(text(),'${prodName}')][1]/preceding-sibling::td[2]//i
inp_fieldSelect                           xpath                //span[starts-with(text(),'${fieldName}')]/../following-sibling::td//select
txt_NominatorName                         xpath                .//*[@id='dgDynamicList']/tbody/tr/td[contains(text(),'${NomineeName}')]/following-sibling::td[4][contains(text(),'Submitted')]/following-sibling::td[3]    
list_emailAddressType                     xpath                //tbody/tr/td[4]
txt_emailID                               xpath                //tbody/tr[${rowNumber}]/td[5]
link_addRecordEmail                       xpath                //a[@title='Add Record: e-mail addresses']
select_emailType                          id                   eml_type
inp_emailAddress                          id                   eml_address
btn_saveButton                            id                   ButtonSave
txt_tableNominatorEntry                   xpath                //a[contains(text(),'${NominatorName}')]/../following-sibling::td[2]
txt_tableEntryArrow                       xpath                //a[contains(text(),'${NominatorName}')]/../preceding-sibling::td//i[@class='iconpro-circle-arrow-right']
txt_citationAwards                        css                  div[id*='citation']>pre
txt_supporterNameAwardsNomination         xpath                (//th/a)[2]/../../following-sibling::tr[${rowNumber}]//td[4]
btn_entryReceivedAwards                   xpath                //a[contains(text(),'Entry Received')]
lnk_awardsSupporterDoc                    xpath                (//th/a)[2]/../../following-sibling::tr[1]//td[5]/a
lnk_awardsLettersDoc                      xpath                 //td[contains(text(),'${letterName}')]/preceding-sibling::td[1]/a
btn_editAwardsEntry                       css                   img[alt*='edit']
txtarea_CitationRecommedation             xpath                 //label[contains(text(),'${value}')]/preceding-sibling::textarea
inp_presentposition                       xpath                 //label[contains(text(),'present position')]/preceding-sibling::input
drpdwn_industrytype                       css                   select[id*='${value}']>option[selected]
txt_gotorecordrenewal                     xpath                 (//th/a)[2]/../../following-sibling::tr[${rowNumber}]//td[3]/a/i 
link_editEmail                            xpath                 (//a[@title='edit record']/i)[${1}]
list_individualMem						  xpath				    //span[contains(text(),'individual memberships')]/parent::td/parent::tr/following-sibling::tr/td/div/table/tbody/tr[not(contains(@style,'none'))]//td[11]
individualmem_data					      xpath					(//span[contains(text(),'individual memberships')]/parent::td/parent::tr/following-sibling::tr/td/div/table/tbody/tr[not(contains(@style,'none'))]//td[11]/..//td[11])[${value}]/..//td[${field}]
btn_scarfReviewerUserList                 xpath                 (//td[starts-with(text(),'${fullname}')]/following-sibling::td[1][contains(text(),'${currentYear}')]/preceding-sibling::td//img)[1]
txt_memberDetailsForChapter               xpath                 (//span[text()='${name}']/parent::td/parent::tr/following-sibling::tr/td/div/table/tbody/tr[not(contains(@style,'none'))]//td[%{index1}])[#{index2}]
list_memberDetails                        xpath                 //span[text()='${tabName}']/parent::td/parent::tr/following-sibling::tr/td/div/table/tbody/tr[not(contains(@style,'none'))]
btn_plusIcon                              xpath                 (//a[contains(@title,'${tabName}')]/i)[${index}]  
inp_addressDetails                        xpath                 //input[contains(@title,'${field}')]
select_state                              id                    adr_state
select_addressType                        id                    cxa_adt_key
chkbox_primary                            xpath                 //span[contains(@title,'${field}')]//input
img_primary                               xpath                 (//span[text()='${name}']/parent::td/parent::tr/following-sibling::tr/td/div/table/tbody/tr[not(contains(@style,'none'))]//td[%{index1}])[#{index2}]//img
img_override                              xpath                 //td[contains(text(),'${field}')]/following-sibling::td[10]/img
img_demoGraphics                          css                    img[alt*='${buttonName}']
inp_bpa_info                              xpath                 //label[text()='${field}:']/preceding-sibling::span//input
btn_saveRapidFormData                     css                   input[value*='Save']
txt_updatedLogsBPA                        xpath                //td[starts-with(text(),'${productName}')]/following-sibling::td[1]
img_activeMember                          xpath                 //td[starts-with(text(),'${value}')]/preceding-sibling::td//img

#Chapter Relationships

iframe1                                   css                   #iframe1                       
drpdwn_relationshipType                   xpath                 //label[text()='${field}:']/preceding-sibling::select
drpdwn_options                            xpath                 //label[text()='${field}:']/preceding-sibling::select/option
inp_startDate                             id                    cxc_start_date
txt_active_chapters                       xpath                 //td[text()='active']/preceding-sibling::td[4]
tab_pencilButton                          xpath                 //td[contains(text(),'${field}')][1]/preceding-sibling::td//i[@class='iconpro-pencil']

======================================================================================================================================
