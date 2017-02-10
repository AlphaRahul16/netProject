Page Title: IndividualsPage

#Object Definitions
======================================================================================================================================
inp_fieldName               xpath    //span[contains(text(),'${fieldName}')]/../following-sibling::td/input
btn_Go                      xpath    //input[@id='ButtonSearch']
txt_userEmail                xpath    //a[text()='${email}']
txt_memberDetails           xpath    //span[@class='DataFormTextBox']
txt_additionalInfo           xpath    //span[text()='${infoValue}']
btn_memberShip              xpath    //span[text()='${membershipName}']/preceding-sibling::input
btn_memberShipAACT          xpath    //span[text()='${membershipName}']/../a[1]
img_moreMenu                 id       ProfileTabMenuImage_TS0
link_moreMenuName           xpath    //a[@title='${menuNames}']
txt_divisionPubName        xpath    //td[contains(text(),'${divisionPubName}')]
txt_numberOfyears          xpath    //td[contains(text(),'Total Years of Service')]/following-sibling::td
txt_invoiceValue           xpath    //td[@id='UP24']
txt_invoiceAACT            xpath       //td[@id='UP25']
txt_priceValue              xpath     //td[contains(text(),'${productName}')]//following-sibling::td[1]
txt_quantity               xpath     //td[contains(text(),'${productName}')]//following-sibling::td[2]
txt_total                    xpath     //td[contains(text(),'${productName}')]//following-sibling::td[3]
txt_payment               xpath     //td[contains(text(),'${productName}')]//following-sibling::td[4]
txt_balance                 xpath     //td[contains(text(),'${productName}')]//following-sibling::td[5]
btn_invoiceAtMembership    xpath        //img[@id='UP26']
lnk_lastName                id        F1_HYPERLINK_4
txt_contactId               xpath     //span[contains(text(),'contact id')]/preceding-sibling::span
img_aactMember              xpath      //span[contains(.,'aact member:')]/following-sibling::span/img[@title='New Image']
img_member                  xpath      //span[contains(.,'aact member:')]/following-sibling::span/img[@title='Non Member']
img_noMemberBenefits        xpath       //span[contains(.,'aact member:')]/following-sibling::span/img[@title='No Member Benefits']
img_spinner                xpath          //*[contains(@src,'updating.gif')]
link_pages                 css              *[class*='GridPagerLink']
link_nextPage                css               *[class*='GridPagerLink'] :nth-child(1) 
link_paging                  xpath               //table[@class='DataFormChildTABLE']//table//tr[1]//a[starts-with(text(),'${page no}')]
chk_advancedView             xpath              //span//label[text()='Advanced View']//../input
drpdwn_selectSearchvalue     xpath              //span[starts-with(text(),'${search option}')]/../following-sibling::td/select
txt_enterSearchValue         xpath            //span[starts-with(text(),'${search option}')]/../following-sibling::td/input
txt_individualInfo           id               F1_cxa_mailing_label_html
======================================================================================================================================