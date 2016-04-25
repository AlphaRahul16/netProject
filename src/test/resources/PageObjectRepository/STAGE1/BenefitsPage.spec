Page Title: education and employment

#Object Definitions
=================================================================================================================================
btn_ACStechnicalDivision        xpath    //input[@value='Add ACS Technical Division']
btn_addToMembership            xpath     //span[text()='${divisionName}']/../../preceding-sibling::td/input[3]
btn_save                       xpath     //input[@id='btnSave']
txt_added                      xpath      //span[text()='${divisionName}']/../preceding-sibling::div/span
btn_ACSPublication             xpath     //input[@value='Add ACS Publication']
btn_ACSMemberBenefits          xpath     //input[@value='Add ACS Member Benefits']
txt_technicalDivisionSubtotal  css      .amount
txt_divisionScore              xpath    //span[text()='${divisionName}']/../../following-sibling::td//span[contains(text(),'$')]
txt_isCENPresent               xpath     //label[contains(text(),'How do you want to receive C&EN')]
rad_CENType                    xpath      //label[contains(text(),'${cenType}')]/preceding-sibling::input
rad_dues_cenPackage            xpath      //label[contains(text(),'${packageName}')]/preceding-sibling::input
=================================================================================================================================