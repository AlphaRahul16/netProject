Page Title: MemberShipRenewalPage

#Object Definitions
====================================================================================================================================================================================
btn_plusIconMemSetup                             xpath                          //span[text()='${membershipSetupName}']/../following-sibling::td/a/i
txt_addACSRenewalCycle                           xpath                          //span[text()='Add - ACS Renewal Cycle']
list_expireMonth                                 id                              a33_expire_month
list_renewalYear                                 id                              a33_renewal_year
list_expireYear                                  id                              a33_expire_year
list_renewalLength                               id                              a33_renewal_length
inp_type                                         id                              a33_type
inp_name                                         id                              a33_name
btn_save                                         id                              ButtonSave
list_dropdownAtAddMembershipRenewal              xpath                           //label[text()='${labelName}:']/preceding-sibling::select
list_selectBatch                                 xpath                           //label[text()='${labelName}:']/preceding-sibling::span/select
inp_runTaskDateTime                              id                              tsk_begin_date
txt_renewalDetails                               xpath                           //label[text()='${labelname}:']/preceding-sibling::span[1]
txt_renewalCycleName                             xpath                           //label[text()='${labelname}:']/following-sibling::span[1]
txt_renewalDetail_q                              xpath                           //label[text()='${labelname}?']/preceding-sibling::span[1]
lnk_batch                                        xpath                           //a[contains(text(),'${batchName}')]
btn_memberShipRenewalSubInfo                     xpath                           //span[text()='${membershipName}']/preceding-sibling::a//i[@class='icon-chevron-down']
txt_numberOfRenewal                              xpath                           //table[@class='table']//tr[2]//td[3]
txt_numberOfInvoicesCreated                      xpath                           //table[@class='table']//tr[2]//td[4]
txt_numberOfErrors                               xpath                           //table[@class='table']//tr[2]//td[5]
btn_createRenewalInvoices                        id                              ACSCreateInvoicesScheduleButton
txt_scheduleCreateRenewalInvoice                 xpath                           //span[text()='ACS Schedule Create Renewal Invoices']
inp_startTimeAndDate                             id                              mdp_create_invoice_start_${timeDate}_ext
txt_errorMessage                                 id                              F1_mdp_message
img_spinner                                      css                             #__UPIMG
txt_addMembershipRenewal                         xpath                           //div[text()='Add - Membership Renewal']
txt_Now                                           xpath                           //a[text()='Now']
txt_beginDate                                    id                              tsk_begin_date
txt_noResultsDisplay                             xpath                           //span[contains(text(),'There are no results to display.')]
txt_addACSRenewalCycle                           xpath                           //td[text()='${acsRenewalCycleName}']
hd_acsRenewalCycle                               xpath                           //h3[11]
inp_notifyEmail                                  id                              tsk_email_notify_address
hd_sideBar											xpath						//li/a[text()='${text}']
====================================================================================================================================================================================