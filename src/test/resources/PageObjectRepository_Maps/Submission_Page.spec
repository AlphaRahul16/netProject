Page Title: MAPS_Submission_Page

#Object Definitions
======================================================================================================================================
lnk_createSubmission                                xpath                            //a[contains(text(),'${linkName}')]
txt_pageHeader                                      css                              .page-header>h1
iframe                                              xpath                            //div[@id='cke_${index}_contents']//iframe
txt_title                                           xpath                            //body[contains(@class,'cke_editable cke')]
btn_saveContinue                                    id                               saveAndContinueBtn
btn_selectImage                                     id                               ABSTRACT_IMAGE_FILE
btn_uploadImage                                     id                               uploadImageBtn
select_presentationType                             id                               ${dropdownType}_combobox
btn_activeProgram                                   css                              input[id*='XIK_SELECTED_ROLE_ID']:not([disabled])
btn_ContinueProgram                                 css                              .row-fluid>.confirm-submit-btn
btn_popUp_Continue                                  xpath                            //button[contains(text(),'${linkName}')]
btn_showAffiliations                                css                              a[id*='affiliationBtn']
select_affiliations                                 xpath                           (//select[contains(@id,'${fieldName}')])[1]
inp_institution_Fields                              xpath                           //label[contains(text(),'${fieldName}')]/following-sibling::input
txt_createInstitution_dialog                        id                              CreateInstModalLabel
select_Institution_dialog                           css                             select[name*='${name}']                 
list_authors                                        css                              #authors>tbody>tr>td:nth-child(3)
btn_showAffiliations                                css                              #authors>tbody>tr:nth-child(1)>td:nth-child(3)>div>p>a
select_symposiumType                                css                              #symposia_title_combobox>option:nth-child(2)
btn_saveandContinueFooter                           xpath                            //div[@class='modal-footer']//a[contains(text(),'${value}')]
txt_popupHeader                                     id                               myModalLabel
lnk_submissionSteps                                 xpath                            //a[contains(@class,'left-side-menu-item') and contains(text(),'${linkName}')]
radio_disclosures                                   xpath                            //h3[text()='${headerName}']/..//label[@class='radio'][${index}]
chckbox_disclosures                                 xpath                            //h3[text()='${label}']/..//label[@class='checkbox']/input
btn_submit                                          id                               submit_btn
img_chkCompletedStep                                css                              #Step${index}>i
======================================================================================================================================