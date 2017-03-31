Page Title: ACS_Reports

#Object Definitions
=============================================================================================================================================
drpdwn_modulesAndCategory                       xpath                           //span[contains(text(),'${value}')]/../following-sibling::td/select
txt_reportName                                  xpath                           (//td[text()='${value}'])[1]
btn_goReport                                    xpath                           (//td[text()='${value}']/following-sibling::td[4]//img)[1]
txt_reportResult                                xpath                           //div[text()='${value}']
tbl_report                                      css                             td[id*='ReportCell']
inp_Email                                       xpath                           //span[contains(text(),'${value}')]/../following-sibling::td/input
btn_Go                                          id                              ButtonGo
=============================================================================================================================================