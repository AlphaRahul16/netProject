Page Title: MAPS_Review_Page

#Object Definitions
======================================================================================================================================
txt_header							xpath							//h2[contains(text(),'${header}')]
radiobtn_role						xpath							//span[contains(text(),'${role}')]/preceding-sibling::input
btn_select							xpath							//a[contains(text(),'${text}')]
img_Loader							xpath							//img[contains(@src,'preloader')]
txt_reportType						xpath							//span[contains(text(),'${text}')]
txt_abstractTitle					xpath							(//div[@role='presentation']/span[contains(text(),'${title}')])[1]
input_filter						xpath							(//div[contains(text(),'${text}')]/../following-sibling::td//input)[1]
comboBox_reviewPage					xpath							//div[contains(text(),'${text}')]/../following-sibling::td/div[contains(@role,'combobox')]/input
listItem							xpath							//div[contains(@class,'combo-list-item')]
btn_ImportExportExcel				xpath							//button[contains(text(),'${text}')]
list_drpdwnoptions				    css						     	.x-menu-item.x-component
drpdown_role						xpath							//a[contains(text(),'Save/Edit')]/../../preceding-sibling::td[1]//input
btn_expandIcon						xpath							//td[@align='right']//button/img
table_ReviewerScoreReport			xpath							//table[contains(@class,'row-table')]
table_pageination					xpath							//div[contains(@class,'panel-bbar')]//tr[contains(@class,'left-row')]
input_SaveGridConfig				xpath							//label[contains(text(),'${text}')]/following-sibling::div//input
txt_tabledata						xpath							(//table[contains(@class,'row-table')]//u)[1]
img_Loader							xpath							//div[contains(text(),'Loading...')]
img_CrossFilter                     xpath                           (//div[contains(text(),'${text}')]/../following-sibling::td//img)[1]
drpdwn_records                      xpath                           //div[contains(text(),'${text}')]/../preceding-sibling::td[1]//input
img_dropdown						xpath							//tr[@role='presentation']//input[contains(@id,'input')]
list_gripConfig						xpath							//div[contains(@class,'combo-list-item')]
dropdown_sessionDetailType			xpath							//label[contains(text(),'Session Detail Type')]/following-sibling::div/img
txt_sucessMsg						xpath							//p[contains(text(),'${msg}')]
======================================================================================================================================