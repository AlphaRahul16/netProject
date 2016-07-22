Page Title: ASM_MeetingPage

#Object Definitions
============================================================================================================================
inp_username                                  xpath                       //input[contains(@id,'Credential1')]
inp_password                                  xpath                       //input[contains(@id,'Credential2')]
btn_verify                                    xpath                       //input[contains(@id,'btnLogin')]
txtarea_specialService                        id                          reg_ada_requirements
list_professionalDiscipline                   id                          reg_professional_discipline_ext
list_conference                               id                          reg_first_green_cen_conference_ext
list_speakerPoster                            id                          reg_conference_contributor_ext
img_spinner                                   xpath                       //img[@src='images/updating.gif']
btn_continue                                  id                          Bottom_1
txt_loginErrorMessage                         xpath                       //p/span[contains(@id,'lblMessage')]
chk_attendeeList                              id                          reg_attendeelist_optout_flag_ext
hd                                            xpath                       //head
body                                          xpath                       //body
chk_advanceRegistration                       xpath                       (//table[@id='US227']//input)[1]
chk_ticketEvents                              xpath                       (//table[@id='US268']//input)[1]
list_cardType                                 id                          pin_apm_key
inp_cardHolderName                            id                          pin_cc_cardholder_name
inp_cardNumber                                id                          pin_cc_number
list_expirationDate                           id                          pin_cc_expire
inp_CCVNumber                                 id                          pin_cc_security_code
txt_labelMessage                              id                          LabelMessage
txt_eventRegistration                         xpath                          //span[contains(text(),'Event Registration')]
============================================================================================================================