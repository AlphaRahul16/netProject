Page Title: Confirmation Page

#Object Definitions
====================================================================================================================================
btn_printReceipt                 xpath           //input[@value='Print your receipt']
txt_memberDetail                 xpath           //p[@class='${memberDetail}']/span
txt_memberAddress                xpath            //div[@class='address']//span[contains(.,'${value}')]
btn_topPrintButton               id                topPrintButton
pdf_content                      xpath           //xhtml:div[@id='pageContainer1']/xhtml:div[2]/div[contains(.,'${detailvalue}')]
txt_name                         xpath            //span[contains(@id,'${memberName_type}')]
btnPDF_download                  id              download  
====================================================================================================================================