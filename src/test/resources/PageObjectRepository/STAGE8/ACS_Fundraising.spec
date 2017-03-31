Page Title: IndividualsPage

#Object Definitions
======================================================================================================================================
txtbox_constituent                 xpath                   //input[@id='ValueTextBox${index}']
table_rows                         xpath                   //table[@id='dgDynamicList']//tr
txt_firstGiftDate                  xpath                   //table[@id='dgDynamicList']//tr[${index1}]//td[${index2}]
txt_giftInformation                xpath                   //label[text()='${field}:']/preceding-sibling::span
btn_addGift                        xpath                   //img[@alt='${btnName}'] 
list_fundraisingCode               xpath                   //label[text()='${field}:']/preceding-sibling::select
txt_giftDate                       xpath                   //label[text()='gift date:']/preceding-sibling::span//input
inp_giftAmount                     xpath                   //label[text()='${field}:']/preceding-sibling::input
label_giftDate                     xpath                   //label[text()='notes:']/preceding-sibling::textarea
table_form                         css                     #DesignedDiv
txt_giftType                       xpath                   //label[text()='${field}:']/preceding-sibling::select//option[@selected='selected']
inp_batchName                      xpath                   //input[@title='batch name']
btn_save                           id                      ButtonSave
txt_listData                       xpath                   (//span[text()='${tabName}']/parent::td/parent::tr/following-sibling::tr//tr[not(contains(@style,'none'))]//td[%{index1}])[#{index2}]
inp_noOfInstallments               id                      ord_num_of_installments
txt_paymentDate                    id                      ord_first_process_date
list_batchCreditPage			   id					   inv_bat_key
txt_calendarDate                   xpath                   //a[contains(@class,'ui-state-active')]
list_tableRows                     xpath                   //span[text()='${tabName}']/parent::td/parent::tr/following-sibling::tr//tr[not(contains(@style,'none'))]
txt_pledgeDate                     xpath                   //span[text()='${tabName}']/../../following-sibling::tr//tr[not(contains(@style,'none'))]//td[contains(text(),'${date}')]
lst_pledgeRow                      xpath                   //span[text()='${tabName}']/../../following-sibling::tr//tr[not(contains(@style,'none'))]
btn_pledgeFolder                   xpath                   //span[text()='${tabName}']/../../following-sibling::tr//tr[not(contains(@style,'none'))]//td[contains(text(),'${date}')]/..//td//img
list_folderTable                   xpath                   //td[@class='DataFormDivDefault']//tr
txt_folderData                     xpath                   //td[@class='DataFormDivDefault']//tr[${index1}]//td[${index2}]
======================================================================================================================================
