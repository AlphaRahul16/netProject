Page Title: Login Required

#Object Definitions
================================================================================================================================
inp_ln_id					xpath						.//input[contains(@id,'${field}')]
inp_verify					xpath						.//input[@value='Verify']
header_home					xpath						.//*[@id='hd']//li[contains(text(),'${header}')]
link_header					xpath						.//*[@id='${link}']/a
txt_toptitle				xpath						.//*[@id='TEXT_1']
inp_address2				xpath						.//*[@id='adr_line2']
chkbox_update				xpath						.//*[@id='ind_ics_update_ext']
btn_submit					css							input[value='Submit']
txt_updated_add				xpath						(.//table[@class='LayoutMainTable']//tr[2])[2]/td
inp_honor					xpath						.//*[@id='ind_bio_honors_ext']
txt_updated_honor			xpath						.//b[contains(text(),'${field}')]/../..//td[2]
drpdown_committee			xpath						.//table[@class='cmtInactive']//select
drpdown_img_alert			xpath						(.//center/../../following-sibling::td[1]/select)[1]
drpdown_select				xpath						//select[contains(@id,'_0')]
txt_select_committee		xpath						.//*[@id='div_${value}']//div[contains(@id,'lbl')]
bttn_continue				xpath						.//*[@id='ButtonSave2']
chkbox_understand			xpath						(.//input[@class='ACSCOICheckBox'])[2]
btn_cont					xpath						(.//input[@value='Continue>>'])[2]
modal_popup					xpath						.//*[@id='fancybox-content']
inp_committee				xpath						.//div[@class='cmtInactiveText']/textarea
bttn_return_to_yb			css							img[alt='Return to YellowBook Home']
list_select_committees		xpath						.//div[contains(text(),'Committee Preferences')]/../..//td[2]
txt_currentCommittee        css                         .cmtOrder
lnk_edit                    xpath                       //div[contains(text(),'Committee Preferences')]//span
================================================================================================================================
