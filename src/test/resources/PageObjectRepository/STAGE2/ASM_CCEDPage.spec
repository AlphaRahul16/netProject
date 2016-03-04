Page Title: ASM_CCEDPage

#Object Definitions
================================================================================================================================
inp_searchType             xpath                            //label[text()='Search by ${searchType}']/preceding-sibling::input
inp_findCCEDCoordinator    id                               ACSFindCCEDCoordinator
img_spinner                xpath                            //img[@src='images/updating.gif']
lnk_contactCordinator      xpath                            //a[contains(text(),'Contact Coordinator')]
lnk_newSearch              xpath                            //a[text()='New Search']
txt_zipCodeErrorMsz        id                               LabelMessage
txt_title                  classname                        pageTitle
inp_zipCode                id                               adr_post_code
lnk_viewCoordinator        xpath                            //td[text()='${localSectionId}']/../td/a
txt_CoordinatorName        xpath                            //td[text()='${name}']
inp_formDetails            xpath                            //label[contains(text(),'${field}')]/preceding-sibling::input
select_state               classname                        DataFormDropDownList
txt_questionsComments      xpath                            //textarea[@title='comments']
btn_submit                 id                               Bottom_0
txt_thankYouMessage        classname                        bodyTXT
================================================================================================================================
