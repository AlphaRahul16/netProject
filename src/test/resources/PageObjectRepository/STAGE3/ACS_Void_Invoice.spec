Page Title: BatchProcessing

#Object Definitions
======================================================================================================================================
btn_invoiceAction                              xpath                             //div[@id='F1_InvoiceAction']//td[${index}]/a/img 
link_addbatch                                  xpath                             //div[@class='AddEditLinkDiv']/a/img
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
======================================================================================================================================