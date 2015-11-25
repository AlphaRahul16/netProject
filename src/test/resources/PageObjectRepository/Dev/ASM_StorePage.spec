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
=================================================================================================================================