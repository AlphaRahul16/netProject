Page Title: MAPS_Submission_Page

#Object Definitions
======================================================================================================================================
lnk_createSubmission                                xpath                            //a[contains(text(),'${linkName}')]
txt_pageHeader                                      css                              .page-header>h1
iframe                                              xpath                            //div[@id='cke_${index}_contents']//iframe
txt_title                                           xpath                            //body[contains(@class,'cke_editable cke')]
btn_saveContinue                                    xpath                            //div[contains(@class,'row-fluid')]//a[contains(text(),'Save & Continue')]
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
btn_addAuthor                                       css                             #table_authors_inst>div>a[href*='author']
txt_AuthorsearchResults                             css                             td[data-label='{$value}']
txt_successAlert                                    css                             .alert-success
txt_Tablesections_Status                            xpath                           //table[@id='${Sectionname}']//td[@data-label='Status']  
tbl_section                                         id                              table_${value}    
txt_activeProgramName                               xpath                           //input[contains(@id,'${value}')]/../../following-sibling::td[1]
sel_Drafts_options                                  xpath                           //table[@id='${sectionName}']//tr[last()]//td[contains(text(),'${programName}')]/preceding-sibling::td/select/option
list_authors                                        css                              #authors>tbody>tr>td:nth-child(3)
select_symposiumType                                css                              #symposia_title_combobox>option:nth-child(2)
btn_saveandContinueFooter                           xpath                            //div[@class='modal-footer']//a[contains(text(),'${value}')]
txt_popupHeader                                     id                               myModalLabel
lnk_submissionSteps                                 xpath                            //a[contains(@class,'left-side-menu-item') and contains(text(),'${linkName}')]
radio_disclosures                                   xpath                            //h3[text()='${headerName}']/..//label[@class='radio'][${index}]
chckbox_disclosures                                 xpath                            //h3[text()='${label}']/..//label[@class='checkbox']/input
btn_submit                                          id                               submit_btn
img_chkCompletedStep                                css                              #Step${index}>i
select_submissionAction                             xpath                            //td[@data-label='Type' and text()='${programName}']/preceding-sibling::td[5]//select
btn_draftStatus                                     xpath                            //span[text()='${btnName}']
select_editDraft                                    xpath                            //table[@id='${sectionName}']//tr[last()]//td[contains(text(),'${programName}')]/preceding-sibling::td/select
txt_abstractStatus                                  xpath                            (//table[@id='${subs}']//tr[last()]//td[contains(text(),'${title}')]/following-sibling::td)[${index}]
btn_edit                                            xpath                            //h3[@id='Step${stepNumber}']/following-sibling::a
txt_reviewAnswer                                    xpath                            //h3[@id='Step${stepNumber}']/../following-sibling::div//strong[text()='${fieldName}']/../following-sibling::td
txt_abstractStatus                                  xpath                            (//table[@id='${subs}']//tr[last()]//td[contains(text(),'%{title}')]/following-sibling::td)[#{index}]
==========================================================================================================================================================================================
