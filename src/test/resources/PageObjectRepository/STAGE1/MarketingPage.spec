Page Title: MarketingPage

#Object Definitions
============================================================================================================================

txt_titleName                  xpath                          //span[contains(text(),'Add - Mailing List (edit/add)')]
inptxt_mailingListName         xpath                         .//*[@id='mls_name']
drpdwn_mailingListType         xpath                         .//*[@id='mls_type_code']//option[@value='${listType}']
chk_showOnline                 id                             mls_show_online
txt_startDate                  id                            start_date_ext
txt_endDate                    id                            end_date_ext
frame                          css                            #iframe1
btn_save                       id                             ButtonSave
txt_listName                   xpath                          //td[contains(text(),'${listName}')]
btn_searchLookup               id                          Look_Up_cst_sort_name_dn
lnk_next                       xpath                         //a[text()='Next >']
txt_name                       xpath                        //span[text()='name:']//following-sibling::span[@id='Span_cst_sort_name_dn__UP']//input[@id='cst_sort_name_dn']
btn_ArrowProdName              xpath                        (//td[contains(text(),'${userName}')][1]/preceding-sibling::td[1]//i)[3]
btn_iconOnAdditionalInfo       xpath                        //a/img[@alt='${icon name}']
btn_listTypeInComm.Pref        xpath                         //span[text()='${list type}']//following-sibling::input[1]
txt_listInComm.Pref            xpath                         //label[text()='${user name}']
chk_listInComm.Pref            xpath                         //label[text()='${list name}']/../input
btn_cancelInComm.Pref                      id                            CommCancel
list_allMailsInListType        xpath                         //span[text()='${list type}']/../following-sibling::div//span/input
============================================================================================================================
