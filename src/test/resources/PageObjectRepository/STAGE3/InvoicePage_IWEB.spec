Page Title: InvoicePage_IWEB

#Object Definitions
====================================================================================================================================
tab_invoice                     xpath                  //a[text()='Invoice']
txt_invoiceMenu                 xpath                  //a[text()='${invoiceMenu}']
inp_invoiceValue                xpath                  //label[contains(text(),'${invoice value}')]/preceding-sibling::span
inp_invoiceCode                 id                     ValueTextBox0
inp_invoiceAACT                 xpath                  //label[contains(text(),'${invoice value}')]/preceding-sibling::span
btn_search                      xpath                  //input[@id='ButtonSearch']
btn_memberShip                  xpath                 //span[text()='${membershipName}']/preceding-sibling::input
txt_code                        xpath                  //td[contains(text(),'${productName}')]//following-sibling::td[1]
txt_priceValue                  xpath                  //td[contains(text(),'${productName}')]//following-sibling::td[2]
txt_quantity                    xpath                  //td[contains(text(),'${productName}')]//following-sibling::td[3]
txt_total                       xpath                  //td[contains(text(),'${productName}')]//following-sibling::td[4]
txt_discount                    xpath                  //td[contains(text(),'${productName}')]//following-sibling::td[5]
txt_balance                     xpath                  //td[contains(text(),'${productName}')]//following-sibling::td[6]
txt_invoiceValues               xpath                 //label[contains(text(),'${invoice value}')]/preceding-sibling::span
btn_detailsMenu                 xpath                 //span[text()='${menuName}']/preceding-sibling::input
btn_detailsMenuAACT             xpath                 //span[text()='${menuName}']/../a[1]
table_description               xpath                 //a/../../following-sibling::tr/td[5]
table_code                      xpath                 //a/../../following-sibling::tr/td[6]
table_priceValue                xpath                 //a/../../following-sibling::tr/td[7]
table_quantity                  xpath                //a/../../following-sibling::tr/td[8]
table_total                     xpath                 //a/../../following-sibling::tr/td[9]
table_discount                  xpath                //a/../../following-sibling::tr/td[10]
table_balance                   xpath                //a/../../following-sibling::tr/td[11]
table_Shipping                  xpath                 .//a/../../following-sibling::tr/td[@id='UP17']
img_spinner                     css                     #__UPIMG
icon_up                         xpath                 //span[contains(text(),'${value}')]/preceding-sibling::a/i[@class='icon-chevron-up']
txt_memberDetail_q              xpath                 //label[text()='${labelname}?']/preceding-sibling::span[1]
txt_memberDetails               xpath                 //label[text()='${labelname}:']/preceding-sibling::span[1]
lnk_batch                       xpath                 //a[contains(text(),'${batchName}')]
txt_invoiceDetailsInTable       xpath                 (//td[starts-with(text(),'${detailValue}')])[1]

====================================================================================================================================

