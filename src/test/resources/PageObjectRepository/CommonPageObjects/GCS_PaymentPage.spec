Page Title: GCS_PaymentPage

#Object Definitions
================================================================================================================================
rad_paymentType                                css                             input[name='paymentType'][value='${value}']
btn_continue                                   xpath                           //button[contains(text(),'Continue')]
inp_billingInfo                                css                             input[placeholder*='${value}'] 
inp_paymentDetails                             xpath                           (//label[contains(text(),'${value}')]/following-sibling::div/input[1])[2]
drpdwn_expMonth                                css                             #${intialValue}cMonth
drpdwn_expYear                                 css                             #${intialValue}cYear
btn_payNow                                     css                             #paymentProcess
rad_majorBankId                                css                             #majorBankID
drpdwn_BankName                                css                             #bankID
inp_CardNumber                                 xpath                           //label[contains(text(),'${value}')]/following-sibling::div/input[1]
inp_submit                                     css                             input[value='${value}']

================================================================================================================================
