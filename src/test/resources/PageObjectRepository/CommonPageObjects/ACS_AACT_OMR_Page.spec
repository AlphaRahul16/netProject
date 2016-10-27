Page Title: ACS_AACT_OMR_Page

#Object Definitions
=========================================================================================================================================================
chk_atAboutYouPage                    xpath                //label[text()='${infoType}']/preceding-sibling::input
txt_paymentError                      xpath                //div[contains(text(),'Payment has failed, please confirm your information and try again')]
rad_memberType                        xpath                //input[starts-with(@id,'rbApplicant${memberType}')]
txt_selectedDetail                   xpath                //select[@id='ddl${detailName}']/option[@selected='selected']
list_cardInfo         		         id                    ddl${infoType}
inp_cardInfo          		         id                 	tb${infoType}

btn_byValue							xpath				 //input[contains(@value,'${btnName}')]
link_contact							xpath				//a[contains(text(),'${value}')]
inp_editEmail							css				    #${value}
title_header							css					.${value}
drpdown_chemMatters						xpath				//div[contains(text(),'${label}')]/following-sibling::select
lbl_memberType							xpath				//div[contains(@class,'left_col')]/div[3]/div[3]/span[2]/span
chked_labelsOnUpdateAboutYou			css					input[id*='${text}'][checked='checked']
unchked_label							css					input[id*='${text}']
txt_label								css					input[id*='${text}'][checked='checked'] + label

=========================================================================================================================================================