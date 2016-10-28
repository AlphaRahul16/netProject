Page Title: education and employment

#Object Definitions
===========================================================================================================================
txt_memberName          xpath   //div[@class='name']/span[2]
txt_userEmail           xpath   //span[text()='Email:']/following-sibling::span
txt_technicalDivision   xpath   //a[text()='ACS Technical Divisions']/../../following-sibling::tr//span[contains(text(),'${divisionName}')]
txt_publication         xpath   //a[text()='ACS Publications']/../../following-sibling::tr//span[contains(text(),'${publication}')]
list_creditCardType     xpath   //select[@id='ddlCreditCardType']
inp_creditCardHoldNo    xpath   //input[@id='tbCardholderName']
inp_creditCardNumber    xpath   //input[@id='tbCreditCardNumber']
list_ExpirationMonth    xpath   //select[@id='ddlExpirationMonth']
list_ExpirationYear     xpath   //select[@id='ddlExpirationYear']
inp_CcvNumber           xpath   //input[@id='tbCcvNumber']
inp_AtTestStatement     xpath   //input[@id='chkAttestStatement']
btn_submitBottom        id      btnSubmitBottom
btn_cancelPopup         xpath   //input[@value='Cancel']
txt_productName         xpath   //tr[@class='national-membership-items']/td[1]/span[1]
txt_amount              xpath   //span[contains(text(),'${productName}')]/../following-sibling::td[@class='amount']/span
txt_cen_LSProduct       xpath   //span[contains(text(),'${cenProductName}')]
rad_multiYear           xpath   //input[contains(@id,'rd${TwoOrThree}Year')]
txt_Tax                 xpath   //span[contains(text(),'${productName}')]/../following-sibling::td[@class='tax']/span
txt_shipping            xpath   //span[contains(text(),'${productName}')]/../following-sibling::td[@class='shipping']/span
txt_paymentError        xpath   //div[@class='error']
txt_quantity            xpath    //span[contains(text(),'${productName}')]/../following-sibling::td[@class='qty']/span
list_Totalvalues        xpath    //tr/td[${totalName}]/span
txt_total               xpath    //th[text()='${total}']/following-sibling::td/span
txt_taxTotal            xpath     //th/span[starts-with(text(),'${total}')]/../following-sibling::td/span
txt_multiYearWait       xpath    //img[contains(@alt,'Processing')]
div_multiYear           xpath    //strong[text()='International members save on your exchange rate and transfer fees!']
list_deliveryOptions     xpath       //select[@id='ddlChemMatters']/option
list_deliveryMethods     id       ddlChemMatters 
list_AACTNationalMem     xpath    //td[@class='category']/span[1]  
select_currency          xpath      //select[contains(@id,'CurrencySelector_ddlCurrencies')]
btn_payInINR            xpath   //input[contains(@id,'btnSubmit')]
hd_confirmCurrencyPayment  xpath    //h4[text()='Confirm Currency Payment']
btn_paymentMethod          xpath      //label[contains(.,'${payment method name}')]/input
btn_paymentType          xpath      //input[@value='${button name}']
img_paymentLoader        xpath      //img[contains(@src,'progress.gif')]
chk_agreeTermsAndCondition   id          idAcceptFlag
btn_paymentContinue          id        btnPayment
txt_GCSOMAmemberName        xpath   //*[@class='name']/span[1]
======================================================================================================================================