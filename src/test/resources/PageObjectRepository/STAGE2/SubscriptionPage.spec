Page Title: SubscriptionPage

#Object Definitions
====================================================================================================================================================================================
hd_subscriptionFulfillment         css                            #ui-accordion-accordion-panel-5+h3
link_subTabSidebar                 xpath                            //a[text()='${linkName}']
hd_subscriptionBatch               id                               ChildDivDataFormHeader
chk_subscription                   xpath                            //label[text()='${subscriptionName}']/preceding-sibling::input
btn_save                           id                               ButtonSave
icon_printReports                  xpath                            //span[text()='print reports']
txt_firstPreviewStatusInList       xpath                           //tr[3]/td[11]
txt_firstTskStartTimeInList        xpath                           //tr[3]/td[5]
inp_tskStartTime                   id                               sfg_task_start_time
link_firstSubsTask                 css                              #dgDynamicList > tbody > tr:nth-child(3) > td:nth-child(1) > a
label_subscriptionDetail           xpath                            //label[text()='${detailName}']/preceding-sibling::span
btn_commitPreviewButton            id                              F1_ACSCommitPreviewButton
inp_commitStartDate                  id                              sfg_commit_start_date_ext
inp_commitStartTime                 id                              sfg_commit_start_time_ext
list_fulfillmentType                 id                              sfg_type_ext
btn_childFormdata                       xpath                           //span[text()='${subscriptionDataType}']/preceding-sibling::a//i[@class='icon-chevron-down']
subscriptionFulfillmentBacthSummary    xpath                       //span[contains(text(),'subscription fulfillment batch summary')]/preceding-sibling::a[2]/i
td_subscriptionRow                     xpath                      //td[contains(text(),'${prodName}')]
label_fulfillmentType                  xpath                            //label[text()='${detailName}']/following-sibling::span
btn_memberShip                         xpath                       //span[text()='${membershipName}']/preceding-sibling::a//i[@class='icon-chevron-down']
link_pages                              classname                     DataFormChildDataGridPagerLink
list_startDate                           xpath                      //td[contains(text(),'${productName}')]/following-sibling::td[2]
link_pageLink                            css                        .DataFormChildDataGridPagerLink:nth-child(${pageNumber})
txt_subName                              xpath                       //label[text()='sub name:']/preceding-sibling::div/span
txt_fulfillmentDate                      xpath                      //td[contains(text(),'${subname}')]/following-sibling::td[7]
list_pages                              xpath                     //a[@class='DataFormChildDataGridPagerLink']
img_spinner                               xpath                      //*[contains(@src,'updating.gif')]
btn_goToRecord                            xpath                   (//td[contains(text(),'${prodName}')]/preceding-sibling::td[2]//a/i[@class='iconpro-circle-arrow-right'])[${index}]
rows_table                           xpath                         ((//table[contains(@id,'ChildTable')])[1]//tr[last()])[1]//table//tr
lnk_arrow                            xpath                         ((//table[contains(@id,'ChildTable')])[1]//tr[last()])[1]//table//tr[${index}]//td[3]//i
txt_subscriptionName                 xpath                         ((//table[contains(@id,'ChildTable')])[1]//tr[last()])[1]//table//tr[${index}]//td[5]
txt_startDateInTable                 xpath                         ((//table[contains(@id,'ChildTable')])[1]//tr[last()])[1]//table//tr[${index}]//td[7]
txt_issueInTable                 xpath                         ((//table[contains(@id,'ChildTable')])[1]//tr[last()])[1]//table//tr[${index}]//td[9]
btn_detailsMenuAACT               xpath                        //span[text()='${menuName}']/../a[1]
====================================================================================================================================================================================