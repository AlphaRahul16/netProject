Page Title: IndividualsPage

#Object Definitions
===========================================================================================================
inp_fieldName                    xpath               //span[contains(text(),'${fieldName}')]/../following-sibling::td/input
btn_Go                           xpath               //input[@id='ButtonSearch']
txt_userEmail                     xpath              //a[text()='${email}']
txt_memberDetails                 xpath             //span[@class='DataFormTextBox']
txt_additionalInfo                xpath              //span[text()='${infoValue}']
btn_memberShip                      xpath           //span[text()='${membershipName}']/preceding-sibling::a[2]
img_moreMenu                        xpath           //a[@id='ProfileTabMenuImage_TS0']
link_moreMenuName                 xpath              //a[@title='${menuNames}']
txt_divisionPubName              xpath            //td[contains(text(),'${divisionPubName}')]
txt_divisionPubName_reinstate     xpath            //td[contains(text(),'${invoiceNumber}')]/following-sibling::td[1]
txt_numberOfyears                xpath              //td[contains(text(),'Total Years of Service')]/following-sibling::td
txt_invoiceValue                 xpath              //td[@id='UP25']
txt_priceValue                      xpath          //td[contains(text(),'${productName}')]//following-sibling::td[1]
txt_quantity                       xpath            //td[contains(text(),'${productName}')]//following-sibling::td[2]
txt_total                         xpath               //td[contains(text(),'${productName}')]//following-sibling::td[3]
txt_payment                       xpath              //td[contains(text(),'${productName}')]//following-sibling::td[4]
txt_balance                        xpath                 //td[contains(text(),'${productName}')]//following-sibling::td[5]
txt_termStartDate                 xpath                //td[contains(text(),'${productName}')]/following-sibling::td[10]
txt_termEndDate                    xpath                 //td[contains(text(),'${productName}')]/following-sibling::td[11]
btn_invoiceAtMembership          xpath              //td[starts-with(text(),'Active')]/preceding-sibling::td[3]/a
lnk_lastName                         id            F1_HYPERLINK_4
link_pageLink                      css             .DataFormChildDataGridPagerLink:nth-child(${pageNumber})
list_pageLink                      css             .DataFormChildDataGridPagerLink
===========================================================================================================