Page Title: IndividualsPage

#Object Definitions
======================================================================================================================================
lnk_awardsSupporterDoc                    xpath                (//th/a)[2]/../../following-sibling::tr[1]//td[5]/a
img_look_up							      css	                .glyphicon.iconpro-search
link_pages                               css              *[class*='GridPagerLink']
txt_individualInfo                      id               F1_cxa_mailing_label_html
img_biography                           xpath            //img[@alt='Bio Information']
txt_bioHonors                           id               ind_bio_honors_ext  
btn_cancel                              css              #ButtonCancel        
link_pages                               css              *[class*='GridPagerLink']     
link_nextPage                           css               *[class*='GridPagerLink'] :nth-child(1) 
link_paging                               xpath               //table[@class='DataFormChildTABLE']//table//tr[1]//a[starts-with(text(),'${page no}')]
chk_advancedView                         xpath              //span//label[text()='Advanced View']//../input
drpdwn_selectSearchvalue                 xpath              //span[starts-with(text(),'${search option}')]/../following-sibling::td/select
txt_enterSearchValue                     xpath            //span[starts-with(text(),'${search option}')]/../following-sibling::td/input
======================================================================================================================================
