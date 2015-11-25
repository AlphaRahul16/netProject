Page Title: SubscriptionPage

#Object Definitions
===================================================================================================================================
hd_sideBar                         xpath                            //h3[text()='${tabName}']
link_subTabSidebar                 xpath                            //a[text()='${linkName}']
hd_subscriptionBatch               id                               ChildDivDataFormHeader
chk_subscription                   xpath                            //label[text()='${subscriptionName}']/preceding-sibling::input
btn_save                           id                               ButtonSave
icon_printReports                  xpath                            //span[text()='print reports']
txt_firstPreviewStatusInList       xpath                            //tr[3]/td[11]
txt_firstTskStartTimeInList        xpath                            //tr[3]/td[5]
inp_tskStartTime                   id                               sfg_task_start_time
link_firstSubsTask                 css                              #dgDynamicList > tbody > tr:nth-child(3) > td:nth-child(1) > a
label_subscriptionDetail           xpath                            //label[contains(text(),'${detailName}')]/preceding-sibling::span
===================================================================================================================================