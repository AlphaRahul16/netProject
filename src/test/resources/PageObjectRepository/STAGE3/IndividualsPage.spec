Page Title: IndividualsPage

#Object Definitions
======================================================================================================================================
inp_fieldName               xpath    //span[starts-with(text(),'${fieldName}')]/../following-sibling::td//input
btn_Go                      xpath    //input[@id='ButtonSearch']
txt_userEmail                xpath    //a[text()='${email}']
txt_memberDetails           xpath    //*[@id='F1_cxa_mailing_label_html']
txt_additionalInfo           xpath    //span[text()='${infoValue}']
btn_memberShip              xpath    //span[text()='${membershipName}']/preceding-sibling::a//i[@class='icon-chevron-down']
btn_memberShipAACT          xpath    //span[text()='${membershipName}']/../a[1]
img_moreMenu                 id       ProfileTabMenuImage_TS0
link_moreMenuName           xpath    //a[@title='${menuNames}']
txt_divisionPubName        xpath    //td[contains(text(),'${divisionPubName}')]
txt_numberOfyears          xpath    //td[contains(text(),'Total Years of Service')]/following-sibling::td
txt_invoiceValue           xpath    //td[@id='UP24']
txt_invoiceAACT            xpath       //td[@id='UP25']
txt_priceValue              xpath     //td[contains(text(),'${productName}')]/following-sibling::td[1]
txt_quantity               xpath     //td[contains(text(),'${productName}')]/following-sibling::td[2]
txt_total                    xpath     //td[contains(text(),'${productName}')]/following-sibling::td[3]
txt_payment                 xpath      //td[contains(text(),'${productName}')]/following-sibling::td[4]
txt_balance                 xpath     //td[contains(text(),'${productName}')]/following-sibling::td[5]
btn_invoiceAtMembership     xpath              //td[starts-with(text(),'Active')]/preceding-sibling::td[3]/a
lnk_lastName                id            F1_HYPERLINK_4
link_pageLink               css             .DataFormChildDataGridPagerLink:nth-child(${pageNumber})
list_pageLink               css             .DataFormChildDataGridPagerLink
txt_contactId               xpath     //*[contains(text(),'contact id:')]/preceding-sibling::span
img_aactMember              xpath      //span[contains(.,'aact member:')]/following-sibling::span/img[@title='New Image']
img_member                  xpath      //span[contains(.,'aact member:')]/following-sibling::span/img[@title='Non Member']
img_noMemberBenefits        xpath       //span[contains(.,'aact member:')]/following-sibling::span/img[@title='No Member Benefits']
img_spinner                xpath          //*[contains(@src,'updating.gif')]
txt_termStartDate           xpath     //td[contains(text(),'${productName}')]/following-sibling::td[10]
txt_termEndDate            xpath      //td[contains(text(),'${productName}')]/following-sibling::td[11]
lnk_pages                  xpath               //tr[@class='pager']/td/a
btn_invoicearrow           css               .iconpro-circle-arrow-right
list_rowsInSubscription                   xpath             (//th/a)[2]/../../following-sibling::tr
txt_subscriptionName                      xpath       (//th/a)[2]/../../following-sibling::tr[${rowNumber}]//td[5]
txt_subscriptionPrice                     xpath       (//th/a)[2]/../../following-sibling::tr[${rowNumber}]//td[6]
txt_subscriptionStartDate                 xpath       (//th/a)[2]/../../following-sibling::tr[${rowNumber}]//td[7]
txt_subscriptionEndDate                   xpath       (//th/a)[2]/../../following-sibling::tr[${rowNumber}]//td[8]
<<<<<<< HEAD
txt_subscriptionIssueFulfilled           xpath       (//th/a)[2]/../../following-sibling::tr[${rowNumber}]//td[9]
txt_subscriptionStartIssue               xpath       (//th/a)[2]/../../following-sibling::tr[${rowNumber}]//td[10]
btnArrowProdName                         xpath       //td[contains(text(),'${prodName}')][1]/preceding-sibling::td[2]//i
inp_fieldSelect                          xpath       //span[starts-with(text(),'${fieldName}')]/../following-sibling::td//select
txt_NominatorName                        xpath       .//*[@id='dgDynamicList']/tbody/tr/td[contains(text(),'${NomineeName}')]/following-sibling::td[4][contains(text(),'Submitted')]/following-sibling::td[3]    
=======
txt_subscriptionIssueFulfilled           xpath        (//th/a)[2]/../../following-sibling::tr[${rowNumber}]//td[9]
txt_subscriptionStartIssue               xpath        (//th/a)[2]/../../following-sibling::tr[${rowNumber}]//td[10]
btnArrowProdName                         xpath        //td[contains(text(),'${prodName}')][1]/preceding-sibling::td[2]//i
list_emailAddressType                    xpath         //tbody/tr/td[4]
txt_emailID                             xpath          //tbody/tr[${rowNumber}]/td[5]
link_addRecordEmail                     xpath          //a[@title='Add Record: e-mail addresses']
select_emailType                        id               eml_type
inp_emailAddress                        id              eml_address
btn_saveButton                          id             ButtonSave
>>>>>>> c3f9ab4913633609558b26e34f7505e48521dd34
======================================================================================================================================
