Page Title: education and employment

#Object Definitions
====================================================================================
rad_currentStudent        xpath    //input[@id='rbCurrentlyStudent${studentAvailability}']
inp_universityName        xpath    //input[@id='txtUniversity']
rad_majorInChemistry      xpath    //input[@id='rbMajoringInChemistry${majorInChemistry}']
list_Degree               xpath    (//select[@id='ddlDegreeType'])[${count}]
list_major                xpath    (//select[@id='ddlMajor'])[${count}]
list_month                xpath    (//select[@id='ddlGraduationMonth'])[${count}]
list_year                 xpath    (//select[@id='ddlGraduationYear'])[${count}]
rad_chemistryTeacher      xpath    //input[@id='rbChemTeacher${No}']
rad_summerMaillingAdd     xpath    //input[@id='rbSummerMailing${yes}']
list_seasonMonthFrom      id       ddlMonthFrom
list_seasonDayFrom        id       ddlDayFrom
list_seasonMonthTo        id       ddlMonthTo
list_seasonDayTo          id       ddlDayTo
inp_address               xpath     //input[@id='txtAddress1']
inp_city                  xpath     //input[@id='txtCity']
list_country              xpath     //select[@id='ddlCountry']
list_state                xpath     //select[@id='ddlState']
inp_zipCode               xpath     //input[@id='txtZipCode']
inp_employerName          xpath     //input[@id='txtEmployerName']
list_industryType         xpath     //select[@id='ddlIndustry']
list_occupationTitle      xpath     //select[@id='ddlOccupationTitle']
btn_addAnotherDegree      xpath     //a[text()='Add Another Degree']
txt_summerMailingAdd      xpath      //div[@id='summerMailingAddressControls']
list_chemistryMajorValues  xpath     //tr[@class='chem-degree-item-row']
lbl_warnings              xpath     //span[@id='lblError' or @id='lblWarningMsg']
txtAr_professionalExp     xpath     //textarea[@id='txtarProfessionalExperience']
lbl_chemistryTeacher      xpath     //b[text()='Are you a pre-college chemistry teacher?']
====================================================================================