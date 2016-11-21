Page Title: AddMember_IWEB

#Object Definitions
=============================================================================================================================================
list_individualType                         id                            ind_int_code
list_country                                id                            adr_country
inp_postalCode                              id                            adr_post_code
list_state                                  id                            adr_state
btn_save                                    id                            ButtonSave
inp_middleName                              id                            ind_mid_name
inp_lastName                                id                            ind_last_name
inp_addressLine1                            id                            adr_line1
img_spinner                                 css                           #__UPIMG
list_phnCountry                             id                            phn_cty_key
list_addVerifyCountry                       id                            DropDownListCountry_adr
inp_addVerify                               xpath                         //span[text()='${address values}:']/../following-sibling::td/input
btn_addVerifySave                           id                            SaveButton
list_state_ver		                        id                            DropDownListState_adr
list_country_ver							id							  DropDownListCountry_adr
inp_memberDetailInAdd                       xpath                         (//label[text()='${detailName}:']/preceding-sibling::input)[1]
inp_memberDetailCityState                   xpath                         (//label[text()='${detailName}:']/following-sibling::input)[1]
=============================================================================================================================================