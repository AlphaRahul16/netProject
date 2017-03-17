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
select_symposiumType                                id                               ${dropdownType}_combobox
btn_activeProgram                                   css                              input[id*='XIK_SELECTED_ROLE_ID']:not([disabled])
btn_ContinueProgram                                 css                              .row-fluid>.confirm-submit-btn
btn_popUp_Continue                                  xpath                            //button[contains(text(),'${linkName}')]
btn_showAffiliations                                css                              a[id*='affiliationBtn']
select_affiliations                                 xpath                           (//select[contains(@id,'${fieldName}')])[1]
inp_institution_Fields                              xpath                           //label[contains(text(),'${fieldName}')]/following-sibling::input
txt_createInstitution_dialog                        id                              CreateInstModalLabel
select_Institution_dialog                           css                             select[name*='${name}']                 
======================================================================================================================================