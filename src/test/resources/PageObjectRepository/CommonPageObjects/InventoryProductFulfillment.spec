Page Title: InventoryPage

#Object Definitions
======================================================================================================================================
link_productOrder					id 						F1_HYPERLINK_5
inp_datefrom						id						datefrom
inp_dateto							id						dateto
btn_search							id						refreshbutton
chkbox_soldproduct					xpath					(.//*[@id='ShipOrdersTable']//td[contains(text(),'${text}')])[1]/../td[2]/input
btn_ship							id						shipbutton
txt_reports							xpath					.//span[contains(text(),'fulfillment reports')]
bttn_continue						xpath					.//input[@value='Continue']
productName_orders					xpath					.//*[@id='ShipOrdersTable_Fulfill']//td[contains(text(),'${name}')]
lineitem_product					xpath					.//*[contains(text(),'${name}')]
icon_reports						id						ACSProductReportButton
======================================================================================================================================
                