Page Title: MAPS_Submission_Page

#Object Definitions
======================================================================================================================================
txt_header							xpath							//h2[contains(text(),'${header}')]
radiobtn_role						xpath							//span[contains(text(),'${role}')]/preceding-sibling::input
btn_select							xpath							//a[contains(text(),'${text}')]
img_Loader							xpath							//img[contains(@src,'preloader')]
txt_reportType						xpath							//span[contains(text(),'${text}')]
txt_abstractTitle					xpath							//div[@role='presentation']/span[contains(text(),'${title}')]
lnk_reviewerScoreReport				xpath							//a[contains(text(),'${text}')]
input_filter						xpath							(//div[contains(text(),'${text}')]/../following-sibling::td//input)[1]
comboBox_reviewPage					xpath							//div[contains(text(),'${text}')]/../following-sibling::td/div[contains(@role,'combobox')]
listItem							xpath							//div[contains(@role,'listitem')]
btn_ImportExportExcel				xpath							//button[contains(text(),'${text}')]
list_ImportExportExcel				xpath							//div[contains(@class,'menu-list')]
drpdown_role						xpath							//tr[@role='presentation']//input[@name='role']
btn_expandIcon						xpath							//td[@align='right']//button
btn_massUpdate						xpath							//button[text()='${btnName}']
table_ReviewerScoreReport			xpath							//table[contains(@class,'row-table')]
table_pageination					xpath							//div[contains(@class,'panel-bbar')]//tr[contains(@class,'left-row')]
input_SaveGridConfig				xpath							//label[contains(text(),'Name')]/following-sibling::div//input
======================================================================================================================================