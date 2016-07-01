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
======================================================================================================================================