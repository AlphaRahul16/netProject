Page Title: FundProfilePage_Iweb

#Object Definitions
==================================================================================================================================
btn_removeDonation                     css                    .iconpro-remove
btn_addDonationAmount                  xpath                  //span[contains(text(),'${value}')]/../following-sibling::td/a/i[@class='iconpro-circle-plus']
input_DonationDetails                  xpath                  //label[contains(text(),'${value}')]/preceding-sibling::input
chkbox_defaultprice                    css                    span[title*='default price?']>input
btn_saveDetails                        id                     ButtonSave
iframeMessageMenu                      css                    #iframe1
btn_save                               css                    #ButtonSave
txt_fundNameByOrder                    xpath                  //table[@id='UP2']/tbody/tr/td[6][contains(text(),'${value}')]/preceding-sibling::td[@align='right']

==================================================================================================================================