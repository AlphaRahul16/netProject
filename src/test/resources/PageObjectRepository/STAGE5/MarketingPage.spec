Page Title: MarketingPage

#Object Definitions
============================================================================================================================

txt_titleName                  xpath                          //span[contains(text(),'Add - Mailing List (edit/add)')]
inptxt_mailingListName         xpath                         .//*[@id='mls_name']
drpdwn_mailingListType         xpath                         .//*[@id='mls_type_code']//option[@value='${listType}']
chk_showOnline                 id                             mls_show_online
txt_startDate                  css                            input[id*='start_date_ext']
txt_endDate                    css                            input[id*='end_date_ext']
frame                          css                            #iframe1
btn_save                       id                             ButtonSave
txt_listName                   xpath                          //td[contains(text(),'${listName}')]
btn_searchLookup               id                             Look_Up_cst_sort_name_dn
lnk_next                       xpath                         //a[text()='Next >']
txt_name                       xpath                        //span[text()='name:']//following-sibling::span[@id='Span_cst_sort_name_dn__UP']//input[@id='cst_sort_name_dn']
btn_ArrowProdName              xpath                        (//td[contains(text(),'${userName}')][1]/preceding-sibling::td[1]//i)[3]
btn_iconOnAdditionalInfo       xpath                        //a/img[@alt='${icon name}']
btn_listTypeInComm.Pref        xpath                         //span[text()='${list type}']//following-sibling::input[1]
txt_listInComm.Pref            xpath                         //label[text()='${user name}']
chk_listInComm.Pref            xpath                         //label[text()='${user name}']//../input
btn_cancelInComm.Pref          id                            CommCancel
arrow_selectListType           xpath                        ((//td[contains(text(),'${list type}')])[1]/preceding-sibling::td/a)[1]
list_allMailsInListType        xpath                         //span[text()='${list type}']/../following-sibling::div//span/input
list_categoriesInMailingList   xpath                        (//table[@class='table'])[1]//tr[position()>2]/td[4]
list_pageLinks                 xpath                        //a[@class='DataFormChildDataGridPagerLink']
link_page                      xpath                        //a[starts-with(text(),'${page no}')]
img_spinner                    id                           __UPIMG
chk_showOnlineInListCategory   id                           mlt_show_online_ext
btn_cancelEditMailingList      id                           ButtonCancel
============================================================================================================================
