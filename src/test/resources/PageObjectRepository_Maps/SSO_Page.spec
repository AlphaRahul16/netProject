Page Title: MAPS_SSO_Page

#Object Definitions
======================================================================================================================================
txt_MAPS_title                         classname                          header-title
inp_fields                             css                                input[name='${value}']
btn_Login                              css                                .help-block>a[href*='WELCOME']
btn_nav_Heading                        xpath                              //li[@class='nav-link']/a[contains(text(),'${headingName}')]
tab_homePage                           xpath                              //li[@class='active nav-link']//a[contains(text(),'${value}')]
tab_accountProfile                     css                                .dropdown-toggle.modify-account-lnk
lnk_userInfo                           xpath                              //a[contains(text(),'${infoType}')]
lnk_leftUserInfo                       xpath                              //a[contains(@class,'left-side-menu-item') and contains(text(),'${value}')]
txt_infoHeader                         css                                .span9>h1
======================================================================================================================================
                