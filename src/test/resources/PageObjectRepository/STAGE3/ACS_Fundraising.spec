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
txt_giftType                       xpath                   //label[text()='gift type:']/preceding-sibling::select//option[@selected='selected']
inp_batchName                      xpath                   //input[@title='batch name']
btn_save                           id                      ButtonSave
txt_listData                       xpath                   (//span[text()='${tabName}']/parent::td/parent::tr/following-sibling::tr//tr[not(contains(@style,'none'))]//td[%{index1}])[#{index2}]
======================================================================================================================================
