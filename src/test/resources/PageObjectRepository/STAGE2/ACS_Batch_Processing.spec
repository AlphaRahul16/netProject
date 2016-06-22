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
======================================================================================================================================