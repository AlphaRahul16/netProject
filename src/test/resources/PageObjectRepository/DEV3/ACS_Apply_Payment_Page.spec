Page Title: ACS_Apply_Payment_Page

#Object Definitions
===============================================================================================================================================
chk_selectInvoice                   xpath                    //label[text()='${labelName}']/preceding-sibling::span//input[@checked='checked']
btn_next                            id                       Bottom_0
tab_addApplyPayment                 xpath                    //div[@class='current' AND text()='${tabName}']
select_addApplyPayment              xpath                    //label[starts-with(text(),'${dropdownName}')]/preceding-sibling::select
inp_addApplyPayment                 xpath                     //label[starts-with(text(),"${paymentCardInput}")]/preceding-sibling::input
btn_Save                            id                       Bottom_1
select_batchName                    id                       inv_bat_key
img_spinner                         css                     #__UPIMG
===============================================================================================================================================
