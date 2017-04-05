Page Title: BatchProcessing

#Object Definitions
======================================================================================================================================
btn_edit                                    css                            img[alt*='edit batch']
txt_batchDetailsSummary                     xpath                          //b[contains(text(),'${value}')]/../following-sibling::td
btn_ForProcesingBatch                       css                            #${'idname'}
txt_closeDateAndSaleRequest                 xpath                          //label[contains(text(),'${value}')]/following-sibling::span[1]
txt_postedClosedUserDate                    css                            span[id*='${value}_user']
txt_postDate                                css                            span[id*='post_date']
btn_save                                    id                             ButtonSave
inp_controlTotal                            css                            input[id*='control_total']
inp_controlCount                            css                            input[id*='control_trx_count']
txt_BatchTotal                              css                            span[id*='BatchTotal']
txt_BatchCount                              id                             TEXT_2
iframe1                                     id                             iframe1
drpdwn_securitygroup                        xpath                          //label[contains(text(),'${value}')]/following-sibling::select
inp_batchAddFields                          xpath                          //label[contains(text(),'${value}')]/following-sibling::input
inp_batchEnterField                         xpath                         //span[contains(text(),'${batchName}')]/../following-sibling::td//input
drodown_batchSearchCriteria                 xpath                          //span[contains(text(),'${batchName}')]/../following-sibling::td//select
inp_batchAdvanceView                        xpath                          //label[contains(text(),'${value}')]/preceding-sibling::input
drpdwn_period                               id                             ValueDropDownList2
chkbox_RefundCC                             css                            input[id*='RefundDataGrid'][type='checkbox'][checked='checked']
txt_Refundamount                            css                            td>input[id*='RefundDataGrid'][type='text']
btn_ftpReport                               id                              ACSRefundReportButton
tbl_RefundTotal                             xpath                           //td[contains(text(),'${value}')]/following-sibling::td[1]
txt_processPercentage                       xpath                          (//td[@class='ProgressBarMessage'])[1]
form_progressBar                            id                             ProgressBar_ProgressBarBackgroundDiv 
txt_processPercentage                       xpath                          (//td[@class='ProgressBarMessage'])[1]
form_progressBar                            id                             ProgressBar_ProgressBarBackgroundDiv 
======================================================================================================================================