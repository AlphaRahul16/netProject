Page Title: ACS_AACT_OMR_Page

#Object Definitions
=========================================================================================================================================================
list_cardInfo         		         id                    ddl${infoType}
inp_cardInfo          		         id                 	tb${infoType}
inp_value								id					txt${infotype}
btn_byValue							xpath				 //input[contains(@value,'${btnName}')]
link_contact							xpath				//a[contains(text(),'${value}')]
txt_summaryHeader						xpath				//span[contains(text(),'${value}')]
inp_editEmail							id				    ${value}
title_header							css					.${value}
drpdown_chemMatters						xpath				//div[contains(text(),'${label}')]/following-sibling::select
chked_labelsOnUpdateAboutYou			css					input[id*='${text}'][checked='checked']
unchked_label							css					input[id*='${text}']
txt_label								css					input[id*='${text}']+ label
txt_detailsAboutYou							css				span[id*='${listname}']
inp_editME                               css                  #tbEmail[style*='inline']
inp_expYear								css					 #txtYearsExp[display:inline]
txt_membershipItems						xpath				(//tr[@class='national-membership-items']/td[@class='category']/span)[${index}]
img_procesing							id					chDialog
btn_Emailsave								id					btnEmailSave
=========================================================================================================================================================