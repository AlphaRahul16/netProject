Page Title: education and employment

#Object Definitions
=================================================================================================================================
btn_ACStechnicalDivision       xpath     //input[@value='Add ACS Technical Division']
btn_addToMembership            xpath     //span[text()='${divisionName}']/../../preceding-sibling::td/input[3]
btn_save                       xpath     //input[@id='btnSave']
txt_added                      xpath      //span[text()='${divisionName}']/../../preceding-sibling::td//span
btn_ACSMemberBenefits          xpath     //input[@value='Add ACS Member Benefits']
txt_technicalDivisionSubtotal  css      .amount
txt_divisionScore              xpath    //span[text()='${divisionName}']/../../following-sibling::td//span[contains(text(),'$')]
txt_isCENPresent               xpath     //label[contains(text(),'How do you want to receive C&EN')]
rad_CENType                    xpath      //label[contains(text(),'${cenType}')]/preceding-sibling::input
rad_dues_cenPackage            xpath      //label[contains(text(),'${packageName}')]/preceding-sibling::input
txt_freeDivision               xpath     //span[contains(@id,'lblDivisionMarket')]//strong
txt_technicalDivisionPoints    xpath     //span[contains(@id,'${sectionId}')]//li[${index}]
list_technicalDivisionPoints   xpath     //span[contains(@id,'${sectionId}')]//li
txt_acsPublications            xpath     //p[text()='${benefitText}']
txt_publicationTitle           classname  item-title
btn_saveColor                  id       btnSave
=================================================================================================================================