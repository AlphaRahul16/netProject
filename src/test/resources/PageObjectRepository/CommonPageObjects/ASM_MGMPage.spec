Page Title: ASM_MGMPage

#Object Definitions
=====================================================================================================================================
link_login                                        xpath                                      //a[contains(@id,'Login')]
form_login                                        classname                                  login-form
inp_userName                                      xpath                                      //input[contains(@id,'Credential1')]
inp_password                                      xpath                                      //input[contains(@id,'Credential2')]
btn_login                                         id                                         btnLogin
label_welcomePage                                 xpath                                      //span[contains(@id,'lblMemberName')]
btn_invite                                        xpath                                      //a[@href='#inviteModal']
modal_invite                                      id                                         inviteModal
inp_inviteMemberDetails                           xpath                                      //input[contains(@id,'${memberDetail}')]
btn_sendInvite                                    xpath                                      //input[contains(@id,'btnSend')]
txt_errorMessage                                  xpath                                      //span[contains(@id,'lblMessage')]
list_email                                        id                                         lblNomEmail
txt_nomineeStatus								  css										 #nomineeStatus>span
link_nomineeStatus								  xpath										 //span[text()='${text}']/../../following-sibling::span/a	
link_applyACSmembership								xpath									//a[contains(text(),'${text}')]
=====================================================================================================================================