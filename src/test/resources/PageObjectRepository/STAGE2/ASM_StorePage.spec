Page Title: ASM_StorePage

#Object Definitions
=================================================================================================================================
inp_search                     id                 search-store
btn_search                     id                 search-submit
txt_searchList                 xpath              //div[@id='pnlCategoryList']/h3
lnk_logIn                      xpath              //a[text()='Log In']
inp_userName                   classname          input-user-name
inp_password                   classname           input-password
btn_verify               	   xpath               //input[@value='Verify']
lnk_logOut                     xpath               //a[text()='Log Out']
lnk_secondFeatureItem          xpath               (//div[@class='pnlProductListing']/a/img)[2]
btn_addToCart                  xpath               //input[contains(@id,'btnAddToCart')]
btn_proceedToCheckoutTop    xpath               //input[contains(@id,'btnCheckout2')]
rad_ShippingAdd                xpath               (//label[contains(text(),'${billingAddress}')]/preceding-sibling::input)[1]
inp_shippingAddressFields      xpath               (//input[contains(@id,'${fieldName}')])[1]
list_shippingState             xpath               (//select[contains(@id,'ddlState')])[1]
list_shippingCountry           xpath               (//select[contains(@id,'ddlCountry')])[1]
btn_continue                   xpath               //input[contains(@id,'btnContinue')]
hd_storeHeading                xpath               //h1[text()='Payment Information']
rad_billingAdd                 xpath               (//label[contains(text(),'${billingAddress}')]/preceding-sibling::input)[2]
inp_billingAddressFields       xpath               (//input[contains(@id,'${fieldName}')])[2]
list_billingState             xpath               (//select[contains(@id,'ddlState')])[2]
list_billingCountry           xpath               (//select[contains(@id,'ddlCountry')])[2]
chk_newPhone                  xpath                //input[contains(@id,'NewPhone')]
inp_phone_email               xpath                //span[contains(text(),'${phoneOrEmail}:')]/../following-sibling::div/input
list_paymentInfo               xpath               //select[contains(@id,'ddl${paymentInfo}')]
inp_paymentInfo               xpath                //input[contains(@id,'${paymentInfo}')]
list_phone                    xpath                //select[contains(@id,'Phone')]
hd_secureCheckout             xpath                //h1[contains(text(),'Secure Checkout')]
txt_price                     css                  .spnPriceValue
lnk_searched_product          css                  .pnlProductListing>img[id*='imgProduct']+div>a
txt_box_Quantity              xpath                 //div[@class='Attributes']/span/following-sibling::input
txt_description               css                   .StoreCartDescriptionColumn>a[id*='lnkDescription']                  
txt_shoppingCart              css                   .LayoutCell>div>h1
txt_store_cart_column         xpath                 //table[@class='StoreCart']/tbody/tr/th[contains(text(),'Price')]/../following-sibling::tr/td/div[contains(@class,'Price')]/span
txt_priceValue                css                   .StoreCartPriceColumn>span
txt_quantity                  css                   .StoreCartQtyColumn>input
txt_discount                  xpath                  (//div[@class='StoreCartTotalColumn']/span)[1]
txt_total                     xpath                  (//div[@class='StoreCartTotalColumn']/span)[2]
txt_store_Summary             xpath                  //span[contains(text(),'${value}')]/../../following-sibling::td/div[@class='StoreCartSummaryValue']/span
inp_shipping_adress           xpath                  (//span[contains(text(),'${value}')]/../following-sibling::div[1]/input)[1]
inp_country                   xpath                  //span[contains(text(),'Country')]/../following-sibling::div/select/option[@selected]
txt_order_shipping_summary    xpath                  //span[contains(text(),'${value}')]/../../following-sibling::td/div[@class='StoreCartSummaryValue']/span
btn_placeOrder                css                    input[value='Place Your Order']
txt_summary_priceValue        css                    .StoreSummaryPriceColumn>span
inp_dropdown                  xpath                  //span[contains(text(),'${value}')]/../following-sibling::div/select/option[@selected]
txt_summary_quantity          css                    .StoreSummaryQtyColumn>span
txt_summary_discount          xpath                  //div[@class='StoreCartTotalColumn']/span
txt_summary_total             xpath                  //div[@class='StoreSummaryTotalColumn']/span
txt_summary_description       css                      .StoreSummaryDescriptionColumn>span
msg_thankyou                  css                    .StoreWrapper>div>.StoreMainHeading
lnk_print_receipt             css                     a[href*='ReportPDF']
invoice_number                xpath                   .//*[@id='pageContainer1']/xhtml:div[2]/xhtml:div[54]
txt_mailing_address           xpath                   //span[@class='DataFormTextBox' and contains(@id,'mailing_label')]
btn_newShip                   xpath                    //input[@value='NewShip']
=================================================================================================================================