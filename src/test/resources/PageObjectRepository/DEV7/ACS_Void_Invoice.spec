Page Title: BatchProcessing

#Object Definitions
======================================================================================================================================
btn_invoiceAction                              xpath                             //div[@id='F1_InvoiceAction']//td[${index}]/a/img 
link_addbatch                                  xpath                             //a[@id='inv_bat_key_oAddHyperLink']/span
txt_batchDetails                               xpath                             (//div[@id='DesignedDiv']//input)[${index}]
drpdwn_securityGroup                           xpath                             //div[@id='DesignedDiv']//select[${index}]
btn_save                                       id                                ButtonSave
table_actions                                  xpath                             //table[@id='DGInvoiceNESTED']//tr
drpdwn_invoiceAction                           xpath                             (//table[@id='DGInvoiceNESTED']//tr)[${index}]//select
table_rows                                     xpath                              //table[@class='table']//tr
iframe                                         xpath                              //iframe[@id="iframe2"]
table_productName                              xpath                              //table[@class='table']//tr//td[${index}]
txt_voidInvoice                                css                                .DataFormLabelErrorMessage
txt_emptyLineItems                             css                                .DataFormFooterTD
img_memberBenefits                             xpath                              //img[@id='F1_IMAGE_${index}']
txt_creditBatchDate                            xpath                              //td[contains(text(),'${value}')]/preceding-sibling::td[3]
txt_creditbatchName                            xpath                              (//th/a)[2]/../../following-sibling::tr/td[8]
txt_CreditTotal                                xpath                              //td[contains(text(),'${value}')]/following-sibling::td[1]
btn_gotoCreditRecord                           xpath                              //td[contains(text(),'${value}')][1]/preceding-sibling::td[5]//i
lnk_batchName                                  xpath                              //a[contains(text(),'${value}')]
inp_creditDate                                  id                                dtb${'type'}Date
btn_searchRefund                                id                                ACSDateRangeButton
img_spinner                                    css                                #__UPIMG
======================================================================================================================================