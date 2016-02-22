Page Title: ASM_NCWPage

#Object Definitions
==============================================================================================
inp_zipCode                xpath                //label[contains(text(),'Zip Code:')]/preceding-sibling::input
inp_findNCWCoordinator     id                   ACSFindNCWCoordinator
img_spinner                xpath                //img[@src='images/updating.gif']
lnk_contactCordinator      xpath                //a[contains(text(),'Contact Coordinator')]
lnk_newSearch              xpath                //a[text()='New Search']
txt_zipCodeErrorMsz        id                   LabelMessage
inp_searchType             xpath                //label[text()='Search by ${searchType}']/preceding-sibling::input
select_state               classname            DataFormDropDownList
txt_title                  classname            pageTitle
lnk_viewCoordinator        xpath                //td[text()='${localSectionId}']/../td/a
txt_CoordinatorName        xpath                //td[text()='${name}']
txt_questionsComments      xpath                //textarea[@title='comments']
inp_formDetails            xpath                //label[contains(text(),'${field}')]/preceding-sibling::input
btn_submit                 id                   Bottom_0
txt_thankYouMessage        classname            bodyTXT
==============================================================================================
