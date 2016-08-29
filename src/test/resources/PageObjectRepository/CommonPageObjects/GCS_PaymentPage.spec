Page Title: GCS_PaymentPage

#Object Definitions
================================================================================================================================
rad_paymentType                                css                             input[name='paymentType'][value='${value}']
btn_continue                                   xpath                           //button[contains(text(),'Continue')]
inp_billingInfo                                css                             input[placeholder*='${value}'] 
inp_paymentDetails                             xpath                           //label[contains(text(),'${value}')]/following-sibling::div/input
drpdwn_expMonth                                css                             #ccMonth
drpdwn_expYear                                 css                             #ccYear
btn_payNow                                     css                             #paymentProcess
               
================================================================================================================================
