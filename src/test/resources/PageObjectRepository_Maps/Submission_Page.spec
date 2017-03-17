Page Title: MAPS_Submission_Page

#Object Definitions
======================================================================================================================================
iframe                             xpath                            //div[@id='cke_${index}_contents']//iframe
txt_title                          xpath                            //body[contains(@class,'cke_editable cke')]
btn_saveContinue                   id                               saveAndContinueBtn
btn_selectImage                    id                               ABSTRACT_IMAGE_FILE
btn_uploadImage                    id                               uploadImageBtn
select_symposiumType               id                               ${dropdownType}_combobox
list_authors                       css                              #authors>tbody>tr>td:nth-child(3)
btn_showAffiliations               css                              #authors>tbody>tr:nth-child(1)>td:nth-child(3)>div>p>a
======================================================================================================================================