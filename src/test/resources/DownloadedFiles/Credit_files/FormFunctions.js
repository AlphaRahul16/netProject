/*

	Please keep the following lines visible, in recognition of my work...

	************************	
	Author: Max Holman <max@blueroo.net>
	Date  : Sun, 21 Jan 2001
	************************

	This functions lets users type in letters to select an option in your SELECT form fields.

	Usually the browser only takes notice of single keystrokes and switches to the first Option that
	begins with that letter.

	This scripts buffers the users input and compares it against the OPTIONs in the SELECT field, 
	choosing the closest match as you type


	Cut and Paste this text into your HTML file or into a separate .js file for inclusion on your site.

	Usage:	<SELECT onKeyPress = "return shiftHighlight(event.keyCode, this);">	

	Platform: Only tested on IE5 (Win) - will not work on Netscape


*/

// H. Stechl - 01/28/2008
// this is breaking type-aheads
//window.onfocus = function anonymous() { window.hasFocus = true; return true; } 
//window.onblur = function anonymous() { window.hasFocus = false; return true; }
//window.document.onmouseover = function anonymous() { if (!window.hasFocus && noDialogWindowIsOpen()) window.focus(); }
// eom 01/28/2008


var timerid     = null;
var matchString = "";
var mseconds    = 1500;	// Length of time before search string is reset
var msecondsshort    = 500;	// Length of time before search string is reset
// H. Stechl 06/05/2006
var utilitystring = ""; // maybe used as a temporary variable...
//
var bConfirmNavAway = 1;
var bTimeout = true;
var nSessionTimerid = null;
var sessionTimeout = null;
var DialogWindow = null;
var nOriginalWidth = 0;
var nOriginalHeight = 0;
var bLoadTimeAdded = false;

var aDialogs = new Array();
var bDroppedControl = false;
var bOverLookUpFrame = false;
var oFileIframe = null;

function noDialogWindowIsOpen()
{
    try { return (!window.DialogWindow || DialogWindow == null || DialogWindow.win == null || DialogWindow.win.closed); }
    catch(ex) { return false; }
}

function expandDropDownList(e)
{
	// H. Stechl 10/30/2007
	// IE 6, causes a postback as soon as the user clicks on the dropdown
	if (navigator.userAgent.indexOf("MSIE")>-1 && parseFloat(navigator.appVersion.split("MSIE")[1])>=7)
	{
		if (e != null && e.tagName == "SELECT" 
            && (e.attributes["_isInFocus"] == null || e.attributes["_isInFocus"].value.toString() != "1"))
		{
			var w = e.offsetWidth;
		    var szStyleWidth = e.style.width;
			var z = e.style.zIndex;
            //AAG 9/24/2012 do not perform position logic when in table layout
			var bAddPosition = true;
			// H. Stechl 01/21/2008
			// during WebForm_AutoFocus, width is 0 and causes dropdownlist to disappear.
			if (w>0)
			{
                if (e.offsetParent != null && e.offsetParent.tagName.toLowerCase() == "td" && e.offsetParent.style.display != "relative" && e.offsetParent.style.display != "absolute") {
                    bAddPosition = !(e.offsetParent.id.startsWith("CELL_") || ($(e.offsetParent).attr("name") != null && $(e.offsetParent).attr("name").indexOf("CELL_") > 0));

                    if (bAddPosition) {
                    var pw = e.offsetParent.offsetWidth;
                    e.offsetParent.style.width = pw;
                }
                }
                
				e.setAttribute("_position", e.style.position);
                if (bAddPosition) {
				e.style.position = "absolute";

				e.style.width = "";
                }
				if (e.offsetWidth <= w)
				{
				    if (!bAddPosition) {
				        e.setAttribute("_width", szStyleWidth);
				        e.setAttribute("_zindex", z);
				        e.setAttribute("_isInFocus", "1");
				    }
				    else {
					e.style.width = w + "px";
					e.style.position = e.attributes["_position"].value.toString();
					e.setAttribute("_position", "");
				        
				    }
                    e.setAttribute("_isInFocus", "0");
				}
				else
				{
				    e.setAttribute("_width", szStyleWidth);//w + "px");
					e.style.zIndex = 100000;
					e.setAttribute("_zindex", z);
                    e.setAttribute("_isInFocus", "1");
				}
			}
		}
    }
    return true;
}

function collapseDropDownList(e)
{
	// H. Stechl 10/30/2007
	// IE 6, causes a postback as soon as the user clicks on the dropdown
	 if (navigator.userAgent.indexOf("MSIE")>-1 && parseFloat(navigator.appVersion.split("MSIE")[1])>=7)
	{
		if (e != null && e.tagName == "SELECT" && e.attributes["_width"] != null && e.attributes["_zindex"] != null && e.attributes["_position"] != null)
		{
			e.style.width = e.attributes["_width"].value.toString();
			e.style.zIndex = e.attributes["_zindex"].value.toString();
			e.style.position = e.attributes["_position"].value.toString();
			e.setAttribute("_width", "");
			e.setAttribute("_zindex", "");
			e.setAttribute("_position", "");
            e.setAttribute("_isInFocus", "0");
		}
    }
    return true;
}
function shiftHighlight(keyCode,targ)
{
    //// navid 11/07/06: return if tab or enter key
    //if (keyCode==9 || keyCode==13) { return true; }
    
	//keyVal      = String.fromCharCode(keyCode); // Convert ASCII Code to a string
	//matchString = matchString + keyVal; // Add to previously typed characters

	//elementCnt  = targ.length - 1;	// Calculate length of array -1

	//for (i = elementCnt; i > 0; i--)
	//{
	//	selectText = targ.options[i].text.toLowerCase(); // convert text in SELECT to lower case
	//	if (selectText.substr(0,matchString.length) == 	matchString.toLowerCase())
	//	{
	//		targ.options[i].selected = true; // Make the relevant OPTION selected
	//	}
	//}
	//clearTimeout(timerid); // Clear the timeout
	//timerid = setTimeout('matchString = ""',mseconds); // Set a new timeout to reset the key press string
	
	//return false; // to prevent IE from doing its own highlight switching
}

function FormFocusFirst(oForm, szName)
{
	if (oForm)
	{
		var oElement=null;
		
		if (szName!=null && szName!="")
		{
			oElement = oForm.elements[szName];
		}
		else
		{
			for (i = 0; i < oForm.elements.length; i++)
			{
				oElement = oForm.elements[i] ;
				if (!oElement.isDisabled) //ajs 12/9/02
				{
					if (oElement.type == 'text' || oElement.type == 'textarea' || oElement.type == 'select-one' || oElement.type == 'checkbox' || oElement.type == 'select-multiple' || oElement.type == 'radio')
					{
						break;
					}
				}
			}
		}
		if (oElement != null && oElement.type != 'hidden' && !oElement.isDisabled && oElement.style.display != 'none') // ajs 12/9/02
		{
			try { oElement.focus(); } catch(ex) {} // navid 09/06/06
		}
	}
}

function TabStrip_Next(oTS)
{
	if (oTS.selectedIndex < oTS.numTabs)
		oTS.selectedIndex++;
}

function TabStrip_Previous(oTS)
{
	if (oTS.selectedIndex > 0)
		oTS.selectedIndex--;
}


function expandCollapseDiv(divId, imageId, szExpand, szCollapse, szExpandAlt, szCollapseAlt)
{
	var div,img,oMultiChildAttributes
	
	if (document.all)
	{
		div = document.all[divId];
		img = document.all[imageId];
		oMultiChildAttributes = document.all["MultiChildAttributes"];
	}
	else if (document.getElementById)
	{
		div = document.getElementById(divId);
		img = document.getElementById(imageId); //BugFix by psherman (10-12-2006): replaced incorrect element ID
		oMultiChildAttributes = document.getElementById("MultiChildAttributes");
	}
		
	if (div != null && img != null)
	{
		if($(div).parent('td').length > 0 && $(div).parent('td').parent('tr').length > 0)
        {
            var parentRow = $(div).parent('td').parent('tr');

            if(parentRow.css("display")=="none")
                parentRow.css("display","");
            else
                parentRow.css("display","none");
        }
        if (div.style.display == "none") 
        {
            //AAG NS:7245 in chrome colspan on td is ignored if inline is inherited 
            if ($.browser.msie)
                div.style.display = "inline";
            else
                div.style.display = "";//AAG NS:10329 removed inline display causing bad alignment on open and postback
			img.src = szCollapse;
			if (szCollapseAlt!=null)
			{img.alt = szCollapseAlt;}
		}
		else
		{
			div.style.display = "none";		
			img.src = szExpand;	
			if (szExpandAlt!=null)
			{img.alt = szExpandAlt;}
		}
		if (oMultiChildAttributes != null)
		{
			szAttribute = "["+divId+"]";
			szAttributes = oMultiChildAttributes.value;
			if (div.style.display == "inline" || div.style.display == null || div.style.display == "")
			{
				if (szAttributes.indexOf(szAttribute) < 0)
					szAttributes += szAttribute;
			}
			else
			{
				if (szAttributes.indexOf(szAttribute) >= 0)
					szAttributes = szAttributes.replace(szAttribute,"");
			}
			oMultiChildAttributes.value = szAttributes;
		}
	}
}
function KeyPressMasked(szMaskType)
{
// Supported Mask Types:
//	Currency
//	Date
//	Phone
//	Integer
//	Float
//	Alpha

	// H. Stechl 11/16/2007
	// quick fix, make sure window.event exists
	if (typeof( window.event ) != "undefined")
	{
		if (szMaskType != "")
		{
			szChar = String.fromCharCode(window.event.keyCode)
			szMaskType = szMaskType.toLowerCase();
			if (szMaskType == "currency")
			{
				if ((szChar < "0" || szChar > "9") && "$,.-".indexOf(szChar) < 0)
					window.event.keyCode = 0;
			}
			else if (szMaskType == "date")
			{
				if ((szChar < "0" || szChar > "9") && szChar != "/")
					window.event.keyCode = 0;
			}
			else if (szMaskType == "phone")
			{
				if ((szChar < "0" || szChar > "9") && "()-/".indexOf(szChar) < 0)
					window.event.keyCode = 0;
			}
			else if (szMaskType == "integer")
			{
				if ((szChar < "0" || szChar > "9") && szChar != "-")
					window.event.keyCode = 0;
			}
			else if (szMaskType == "float")
			{
				if ((szChar < "0" || szChar > "9") && ".-".indexOf(szChar) < 0)
					window.event.keyCode = 0;
			}
			else if (szMaskType == "alpha")
			{
				if ((szChar < "a" || szChar > "z") && (szChar < "A" || szChar > "Z"))
					window.event.keyCode = 0;
			}
			else if(szMaskType == "number")
			{
				if((szChar < "0" || szChar > "9") && "$,.-".indexOf(szChar) < 0)
				   window.event.keyCode = 0;
			}
		}
	}
}

function ListGridRowClicked()
{
	szHref = event.srcRow.children[0].children[0].href;
	szHref = szHref.substr(szHref.indexOf("'")+1);
	szHref = szHref.substr(0,szHref.indexOf("'"));
	__doPostBack(szHref,'');
}

function ConfirmDelete()
{
	if (!confirm("Are you sure you want to delete?"))
	{
		// H. Stechl 11/16/2007
		// quick fix, make sure window.event exists
		if (typeof( event ) != "undefined")
		{
			if (event)
				{event.returnValue = false;}
		}
		return false;
	}
	return true;
}

function Confirm(msg)
{
	if (!confirm(msg))
	{
		// H. Stechl 11/16/2007
		// quick fix, make sure window.event exists
		if (typeof( event ) != "undefined")
		{
			if (event)
				{event.returnValue = false;}
		}
		return false;
	}
	return true;
}

function DeleteChildRow(szFormKey,szRowKey)
{
	if (ConfirmDelete())
	{
		if (document.forms[0].MultiChildAttributes != null)
		{
			document.forms[0].MultiChildAttributes.value += "DCKey["+szFormKey+"]DCRKey["+szRowKey+"]";
			document.forms[0].submit();
		}
		/*
		szURL = window.document.URL;
		if (szURL.indexOf("&DCKey=") > 0)
			szURL = szURL.substr(0,szURL.indexOf("&DCKey="));
		szURL += "&DCKey="+szFormKey+"&DCRKey="+szRowKey;
		window.document.URL = szURL;
		*/
	}
}

function ImageSrcChange(szImageSrc)
{
	var oImage = null;
	if (event.srcElement.children[0] != null && event.srcElement[0].tagName == "IMG")
		oImage = event.srcElement[0];
	else if (event.srcElement.parentElement != null && event.srcElement.parentElement.children[0] != null && event.srcElement.parentElement.children[0].tagName == "IMG")
		oImage = event.srcElement.parentElement.children[0];
	if (oImage != null)
	{
		oImage.src = szImageSrc;	
	}
}

function TextLimit(field, maxlen)
{
	if (field.value.length > maxlen) 
	{
		field.value = field.value.substring(0, maxlen);
		alert('your input has been truncated!');
	}
}

function checkField (objField, cInputMask) 
{
	InputMaskDelimiters = "/"
	bDelimiter = false;
	if( objField.value != "")
	{
		for (var i=0; i<objField.value.length; i++)
		{
			cChar = objField.value.charAt(i)
			if (InputMaskDelimiters.indexOf(cChar)>-1)
			{
				bDelimiter = true;
				break;
			}
		}

		if ( !bDelimiter )
		{
			objField.value = reformatInputMask(objField.value, cInputMask, InputMaskDelimiters)
		}
	}
	return true;
}

function reformat (s)
{   
	var arg;
    var sPos = 0;
    var resultString = "";
	var nStringLength = s.length
    for (var i = 1; i < reformat.arguments.length ; i++) {
		if (sPos <= nStringLength)
		{
			arg = reformat.arguments[i];
			if (i % 2 == 1) resultString += arg;
			else
			{
				resultString += s.substring(sPos, sPos + arg);
				sPos += arg;
			}
		}
    }

    return resultString;
}

function stripCharsInBag (s, bag)
{   var i;
    var returnString = "";

    for (i = 0; i < s.length; i++)
    {   
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }

    return returnString;
}




function reformatInputMask (cValue,cInputMask, InputMaskDelimiters)
{   
	creformatFunction = "reformat (cValue, "
	nChar = 0
	for (i=0;i<cInputMask.length;i++)
	{
		if (InputMaskDelimiters.indexOf(cInputMask.charAt(i))==-1)
		{
			nChar=nChar+1
			if (i==0) //first character
				{creformatFunction = creformatFunction + "'', "}
			else
				{
				if (nConcatMask)
					{creformatFunction = creformatFunction + "', "}
				}				
			nConcatMask = false
		}	
		else
		{
			if (i==0)
				{creformatFunction = creformatFunction + "'"+cInputMask.charAt(i)}
			else
			{
				if (nChar==0)
				{
					if (nConcatMask)
						{creformatFunction = creformatFunction + cInputMask.charAt(i)}
					else
						{creformatFunction = creformatFunction + "'"+cInputMask.charAt(i)}
				}
				else
				{
					if (nConcatMask)
						{creformatFunction = creformatFunction + cInputMask.charAt(i)}
					else
						{creformatFunction = creformatFunction + " "+nChar+", '"+cInputMask.charAt(i)}
				}
			}
			nConcatMask = true
			nChar = 0
		}
	}
	if (nChar > 0)
	{
		creformatFunction = creformatFunction + " "+nChar+", "
	}
	//remove last two characters ", " and add close parenthesis at the end.
	creformatFunction = creformatFunction.substring(0,creformatFunction.length-2)
	creformatFunction = creformatFunction + ")"
	//alert(creformatFunction)
return ( eval(creformatFunction) )
}



function CheckCreditDebit()
{
	oSrcElement = event.srcElement;
	iIndex = oSrcElement.name.indexOf('JDG');
	iIndex = oSrcElement.name.indexOf('_', iIndex+1); 
	iIndex = oSrcElement.name.indexOf('_', iIndex+1); 
	oRowID = oSrcElement.name.substr(iIndex+1,1); 
	if ( oSrcElement.name.indexOf('JournalDataGrid') >= 0 &&
		oSrcElement.type == 'text' &&
		(oSrcElement.name.indexOf('Debit') >= 0 || oSrcElement.name.indexOf('Credit') >= 0) &&
		oSrcElement.value > 0 )
	{
		if (oSrcElement.name.indexOf('Debit') >= 0) Opposite = 'Credit';
		if (oSrcElement.name.indexOf('Credit') >= 0) Opposite = 'Debit';
		for (i = 0; i < document.forms[0].elements.length; i++)
		{
			oElement = document.forms[0].elements[i];
			if (oElement.type == 'text' &&
				oElement.name.indexOf('JournalDataGrid') >= 0 && 
				oElement.name.indexOf('JDG_'+Opposite+'_' + oRowID) >= 0 )
			{    
				document.forms[0].elements[i].disabled = true;
				if (Opposite == 'Credit')
					document.forms[0].elements[i+1].focus();
				if (Opposite == 'Debit')
					document.forms[0].elements[i+2].focus();
				break;
			}
		}
	}
}

function SetGrandChildFormCookie(szDivID, szName, szDictionary)
{
    var oDiv = document.getElementById(szDivID);
    if (oDiv != null)
    {
        var szValue = "0";
        if (oDiv.style.display.toLowerCase() == "inline")
            szValue = "1";
        SetCookie(szName, szValue, szDictionary);
    }
}

function SetCookie(szName, szValue, szDictionary)
{
	szValue = escape(szValue);
	
	if (szDictionary==null)
	{
		document.cookie = szName + "=" + szValue + "; expires=Fri, 31 Dec 2099 23:59:59 GMT;";
	}
	else
	{
		// delete old cookie if exists
		DelCookie(szName);
		if (document.cookie.indexOf(szDictionary)==-1)
			SetCookie(szDictionary,'');
		var szNameValuePairs = GetCookie(szDictionary,szDictionary);
		if (szNameValuePairs==null || szNameValuePairs=="undefined")
			szNameValuePairs = '';
		var aNameValuePairs = szNameValuePairs.split('&');
		var aNameValuePair;
		var bExists=false;
		for (var i=0; i<aNameValuePairs.length; i++)
		{
			aNameValuePair = aNameValuePairs[i].split('=');
			if (aNameValuePair[0]==szName)
			{
				bExists = true;
				aNameValuePair[1]=szValue;
				aNameValuePairs[i] = aNameValuePair[0]+'='+aNameValuePair[1];
				break;
			}
		}
		if (!bExists)
		{
			aNameValuePairs[aNameValuePairs.length] = szName + '=' + szValue;
		}
		szNameValuePairs = null;
		for (var i=0; i<aNameValuePairs.length; i++)
		{
			if (i==0)
				szNameValuePairs = "";
			else
				szNameValuePairs = szNameValuePairs + '&'
				
			szNameValuePairs = szNameValuePairs + aNameValuePairs[i];
		}
		document.cookie = szDictionary+'='+szNameValuePairs;
	}
	
	if (0==1){}
	var szDummy = GetCookie(szName, szDictionary);
	if (0==1){}
}

function GetCookie(szName, szDictionary)
{
	//debugger;
	// cookies are separated by semicolons
	var szReturnValue = null;
	var aCookies = document.cookie.split("; ");
	
	if (szDictionary==null)
	{
		for (var i=0; i < aCookies.length; i++)
		{
			// a name/value pair (a crumb) is separated by an equal sign
			var aCookie = aCookies[i].split("=");
			if (szName == aCookie[0]) 
				szReturnValue = unescape(aCookie[1]);
		}
	}
	else
	{
		for (var i=0; i < aCookies.length; i++)
		{
			if ( szDictionary==aCookies[i].substring(0, aCookies[i].indexOf('=')) )
			{
				if (szName==szDictionary) //prevents multiple escaping
				{
					szReturnValue = aCookies[i].substring(aCookies[i].indexOf('=')+1);
					break;
				}
				else
				{
					var szCookieNameValuePairs = aCookies[i].substring(aCookies[i].indexOf('=')+1);
					var aNameValuePairs = szCookieNameValuePairs.split('&');
					for (var j=0; j < aNameValuePairs.length; j++)
					{
						var aCookie = aNameValuePairs[j].split('=')
						if (szName == aCookie[0]) 
						{
							szReturnValue = unescape(aCookie[1]);
							break;
						}
					}
				}
				if (szReturnValue != null)
					break;
			}
		}
	}
	return szReturnValue;
}

function DelCookie(szName)
{
	date = new Date();
	document.cookie = szName + "=" + "; expires=" + date.toGMTString();
}


function GetParentWindow()
{

    var parentwin = opener;
    if (parentwin==null)
    {
        if (parent.aDialogs < 2)
            parentwin = parent;
        else
            parentwin = $('#iframe'+(parent.aDialogs.length - 1), parent.document)[0].contentWindow;
    }
    return parentwin;
}

// do all the magic here..
function CloseWindow(jqd)
{
    if (self != parent)
        top.CloseWindow(self);
    else if (jqd)
    {
        var nDialogID = aDialogs[aDialogs.length-1];
        $("#div"+ nDialogID).dialog('close'); 
    }
    else if (self==top)
	    close();

}

function ParentWindowRefresh(szURL)
{
    var parentwin = GetParentWindow();
	if (parentwin)
	{
	    //AAG8/13/2013 remove if else. first true condition wins
	    if (window.document.URL.toLowerCase().indexOf("reloadparent=") >= 0) {
	        parentwin.location.reload(true);
	        return;
	    }

	    if (window.document.URL.toLowerCase().indexOf("restart=") >= 0) {
			parentwin.location.href = "startpage.aspx";
	        return;
	    }

	    if (window.document.URL.toLowerCase().indexOf("redirect=") >= 0) {
	        parentwin.location.href = window.document.URL.substring(window.document.URL.indexOf("Redirect=") + 9);
	        return;
	    }
		    //STH 4/30/13 NS:4769 (3854) Do not do a post back if "NoReload" is in the URL
	    if (window.document.URL.toLowerCase().indexOf("noreload=") >= 0)
	        return; // Do nothing!
	    //this needs to be in a try catch block. __doPostBack is not always accessible
	    try {
	        if (parentwin.__doPostBack) //AAG 8/12/2013 do post back may not be accessible from parentwin in IE
		{
			if (window.document.URL.indexOf("UpdateAll=") >= 0)
	                parentwin.__doPostBack("ZZZZZZZZ", "");
			else
	                parentwin.__doPostBack("XXXXXXXX", "");
	            return;
		}
	    }
	    catch (ex) { }

	    if (parentwin.document.forms[0]) {
			parentwin.document.forms[0].submit();
	        return;
	    }
	    if (szURL != "") {
			parentwin.location.assign(szURL);
	        return;
	    }//AAG 8/13/2013
	    if (window.document.URL.indexOf("NoReload") < 0)
			parentwin.location.reload(false);
	}
}

// H. Stechl - add options to dropdown from opener
function DropDownAddOption(oDropDown, szValue, szText)
{
	oDropDown.options[oDropDown.length] = new Option((szText==null)?szValue:szText, szValue);
}

function getRadioButtonListValue(szRadioListID) {
    var oRadioList = document.getElementsByName(szRadioListID);
    var szValue = "";
    if(!oRadioList) {
        oRadioList = document.getElementById(szRadioListID);
    }
    if(oRadioList ) {
        for(var idx = 0; idx < oRadioList.length; idx++) {
            if(oRadioList[idx].checked) {
                szValue = oRadioList[idx].value;
                break;
            }
        }
    }
    return szValue;
}

function postBackDropDownListEdit(szDropDownListID)
{
    if(__doPostBack) 
        __doPostBack(szDropDownListID, ''); 
    else 
        document.forms[0].submit();
    
    return true; 
}
function postBackDropDownListAdd(szDropDownListID, szNewKey)
{
    oDropDownList = document.getElementById(szDropDownListID);
    DropDownAddOption(oDropDownList, szNewKey, 'Loading new item...');
    oDropDownList.options.selectedIndex=oDropDownList.options.length-1; 
    if(__doPostBack) 
        __doPostBack(szDropDownListID, '');
    else 
        document.forms[0].submit();
    return true;
}

function OpenNewWindow_WithClose(szURL)
{
	close();
	openDialog(szURL,50,50);
}

function OpenNewWindow(szURL, newBrowser, reloadTopOnClose)
{
	//window.onfocus = "reload();";
	//openDialog(szURL,screen.width/2,screen.height/2);
    //debugger
   
    
   if (szURL != null && szURL.toLowerCase().indexOf('md_dynamic_designer.aspx') > -1) {
        clearTimeout(nSessionTimerid);
        openDialog(szURL, (screen.availWidth - 100), (screen.availHeight - 100));
    }
   else if (newBrowser || (szURL && szURL.toLowerCase().indexOf('md_dynamic_formdesign.aspx') > -1))
   {
       szURL = checkModalUrl(szURL);
       openDialog(szURL, 50, 50);
   }
   else
       openJQueryDialog(szURL, null, reloadTopOnClose);

}

function OpenNewWindowAlert(szURL, szAlert)
{
	if (szAlert != "")
		AlertDialog(szAlert);
	openDialog(szURL,50,50);
}

function openJQueryDialog(url, TheOpener, reloadTopOnClose)
{
    if (self != top) { top.openJQueryDialog(url, self, reloadTopOnClose); return; }
    
    if (TheOpener == null)
        TheOpener = self;
    
    url = checkModalUrl(url);

    var nDialogID = aDialogs.length + 1;
    aDialogs.push(nDialogID);

    var newDiv = document.createElement('div');
    newDiv.setAttribute('id', 'div'+nDialogID);
    newDiv.setAttribute('title', '&nbsp;');
	newDiv.setAttribute('style', 'overflow: hidden;');
    document.body.appendChild(newDiv);

    $('#div' + nDialogID).dialog({
        autoOpen: true,
        //height: "auto",
		//width: "auto",
        resize: "auto",
        modal: true,
        closeOnEscape: true,
        //hide: "explode",
        close: function(event, ui) { 
                            var nDialogID = aDialogs.pop();
                            dialIframe = $('#iframe' + nDialogID, document)[0];
                            //AAG 1/23/2012 preventive chqange based on errors received on resize. will do a null check
                            if(dialIframe != null)
                            dialIframe.src = '';
                            dialDiv = $('#div' + nDialogID, document)[0];
                            //AAG 1/23/2012 preventive chqange based on errors received on resize. will do a null check
                            if(dialDiv != null)
                                dialDiv.parentNode.removeChild(dialDiv);
                
                            if (reloadTopOnClose)
                                top.location.reload();
                            }
							,
        resize: function(event, ui) { 
                            dialIframe = $('#iframe' + nDialogID, document)[0];
                            //AAG 1/23/2012 received JS errors after a dialog was closed, somehow this continued to fire
                            if(dialIframe != null)
                            {
                            dialIframe.style.height = this.style.height;
                            dialIframe.style.width = this.style.width;
                            }
        },
        dragStart: function (event, ui) {
                        $('#iframe' + nDialogID, this).each(function() {
                            $('<div class="ui-draggable-iframeFix" style="background: #FFF;"></div>')
                                .css({
                                    width: '100%', height: '100%',
                                    position: 'absolute', opacity: '0', zIndex: 1000, overflowX: 'hidden'
                                    })
                                .css($(this).position())
                                .appendTo($(this).offsetParent());
                        });
        },
        dragStop: function (event, ui) {
                        $("div.ui-draggable-iframeFix").each(function() {
                            this.parentNode.removeChild(this); }); //Remove frame helpers
        },
        resizeStart: function (event, ui) {
                        $('#iframe' + nDialogID, this).each(function() {
                            $('<div class="ui-draggable-iframeFix" style="background: #FFF;"></div>')
                                .css({
                                    width: '100%', height: '100%',
                                    position: 'absolute', opacity: '0', zIndex: 1000, overflowX: 'hidden'
                                    })
                                .css($(this).position())
                                .appendTo($(this).offsetParent());
                        });
        },
        resizeStop: function (event, ui) {
                        $("div.ui-draggable-iframeFix").each(function() {
                            this.parentNode.removeChild(this); }); //Remove frame helpers
        }

    }).parent().draggable("option", { containment: false });

    var newIframe;
//        if ($.browser.msie)
//            newIframe = document.createElement('<iframe name="iframe'+nDialogID+'">');
//        else
		newIframe = document.createElement('iframe');
    newIframe.setAttribute('id', 'iframe'+nDialogID);
    newIframe.setAttribute('name', 'iframe'+nDialogID);
    newIframe.setAttribute('scrolling', 'auto');
    newIframe.setAttribute('frameBorder', '0');
    //newIframe.setAttribute('allowTransparency', 'true');
    //if (url != null && !$.browser.webkit && !$.browser.safari)
        newIframe.setAttribute('src', url);
    //else
    //    newIframe.setAttribute('src', "./javascript:void(0);");
    newIframe.setAttribute('style', 'display:none;padding:0;margin:0;');
    newDiv.appendChild(newIframe);

    //AAG 1/7/2013 NS:9596 Prevent dialog from increasing in size on postbacks
    var nCurrentDialogWidth = -1;
    var nCurrentDialogHeight = -1;

    $('#iframe'+ nDialogID).load( 
        function () {
            //AAG NS:8190 reload be fore i frame is loaded so its size and location is correct. AAG 10/23/2012 this is interfering with redirect in IE and Firefox 
            //if(!this.loaded && ($.browser.webkit || $.browser.safari)){
            //    this.loaded = true;
            //    this.src = url;
            //    //this.contentWindow.location.reload();
		    //    //setTimeout('$(\'#iframe'+ nDialogID+'\')[0].contentWindow.location.reload()', 0);
		    //} else {

            var iFrameDoc;

            try {
                iFrameDoc = this.contentWindow.document || this.contentDocument || this.document;
            } catch(e) {
                ;
            }
            if (!iFrameDoc)
                return;

            try {
                var onloadattr = iFrameDoc.body.getAttribute('onload').toString();
                if (onloadattr.indexOf('close()') > -1)
                    CloseWindow(newDiv);
            } catch(ex) {
                ;
            }

            if (iFrameDoc.loaded) return;
            iFrameDoc.loaded = true;
            this.style.display = 'inline';

            if (this.loaded) {
                this.style.height = '300px';
                this.style.width = '300px';
            }
            var w = iFrameDoc.documentElement.scrollWidth;
            var h = iFrameDoc.documentElement.scrollHeight;
            if (w < iFrameDoc.body.scrollWidth) {
                w = iFrameDoc.body.scrollWidth;
                h = iFrameDoc.body.scrollHeight;
            }

            //AAG 1/7/2013 NS:9596 Prevent dialog from increasing in size on postbacks
            if (nCurrentDialogWidth != -1) {
                w = nCurrentDialogWidth;
            }
            else if (w > nCurrentDialogWidth) {
                w = w + 30;
                nCurrentDialogWidth = w;
            }
            //AAG NS:11523 account for footer that may block buttons
            if ((h + ($('#footer').length > 0?$('#footer').height():0)) > (nCurrentDialogHeight + ($('#footer').length > 0?$('#footer').height():0))) {
                h = h + 30;                    
                nCurrentDialogHeight = h;
            } else if (nCurrentDialogHeight != -1) {
                h = nCurrentDialogHeight;
            }

            if (this.loaded) {
                if (navigator.userAgent.indexOf('Opera') > -1) // and then there was one.
                    w = w - 30;

            }

            this.style.height = (h) + 'px';
            this.style.width = (w) + 'px';

            try {
                this.contentWindow.opener = TheOpener;
            } catch(e) {
                ;
            }
            //AAG NS:11523 account for footer that may block buttons
            if ($('#footer').length > 0)
                h = h + $('#footer').height();

            // cache selectors
            var $dialog = $('#div' + nDialogID), // modal/jquery-ui dialog
                $this = $(this); // current iframe

            $dialog.dialog('option', 'title', iFrameDoc.title);
            $dialog.dialog('option', 'width', w + 20);

            if ($($this, $dialog).length === 1) { // then current iframe (this) is a child of the dialog
                // use css instead of dialog method to set height of the dialog based on iframe contents
                $dialog.css('height', $this.height() + 'px');
                // check if dialog width should be modified
                var iframeWidth = $this.contents().width();
                if ($this.width() < iframeWidth) {// then expand dialog width to display it's contents
                    // iframe width
                    $this.width(iframeWidth);
                    // dialog width with extra spacing based on existing sizes
                    $dialog.dialog('option', 'width', iframeWidth + (parseInt($dialog.css('paddingLeft'), 10) * 2));
                }
            }
            else {
                // original code - use dialog method to set height
                $dialog.dialog('option', 'height', h + 25);
            }

            $dialog.dialog('option', 'position', 'center');

            this.contentWindow.scrollTo(0, 0);
            //}
        }
	);
    
    $('#iframe'+ nDialogID).unload( 
        function () {
			try { this.contentWindow.opener = null; } catch (e) {;}
			this.setAttribute('src', '');
			this.style.height = '300px';
			this.style.width = '300px';
        }
	);
}

function openDialog(url, width, height)
{
	DialogWindow = null;
	DialogWindow = new Object();
    bConfirmNavAway = 0;
	if (!DialogWindow.win || (DialogWindow.win && DialogWindow.win.closed))
	{
		//DialogWindow.returnFunc = returnFunc;
		//DialogWindow.returnedValue = "";
		//DialogWindow.args = args;
		DialogWindow.url = url;
		DialogWindow.width = width;
		DialogWindow.height = height;
		// H. Stechl 08/14/2006
		// ensure unique window name
		// DialogWindow.name = (new Date()).getSeconds().toString();
		dDate = new Date();
		DialogWindow.name = dDate.getMinutes().toString() + dDate.getSeconds();
		//
		
		if (url.indexOf("ReportStart.aspx")>0)
		{
		    DialogWindow.width = 800;
		    DialogWindow.height =600;
		    DialogWindow.left = ((screen.width - width) / 2)- 400;
		    DialogWindow.top = ((screen.height - height) / 2) - 300;
		    
		    if (DialogWindow.left < 0)
		        DialogWindow.left =0;
		        
		    if (DialogWindow.top < 0)
		        DialogWindow.top =0;		    
		}
		else
		{
		    // in case of a dialog always tie up coordinates to a parent window
		    if (window.parent != undefined)
		    {
                DialogWindow.left = window.screenLeft;
                DialogWindow.top = window.screenTop;
		    }
		    else
		    {
		        DialogWindow.left = (window.screen.availWidth / 2) - width; //5000; //(screen.width - width) / 2;
		        DialogWindow.top = (window.screen.availHeight /2) - height; //5000; // (screen.height - height) / 2;
		    }
		}
		var attr = "left=" + DialogWindow.left + ",top=" + 
			DialogWindow.top + ",resizable=yes,status=yes,scrollbars=yes,width=" 
			+ DialogWindow.width + ",height=" + DialogWindow.height;
			
			
			
		DialogWindow.win = window.open(DialogWindow.url, DialogWindow.name, attr);
		var nLeftOffset = DialogWindow.left-DialogWindow.win.screenLeft;
		var nTopOffset = DialogWindow.top-DialogWindow.win.screenTop;
		SetCookie("WindowTopLeftOffset",nTopOffset+","+nLeftOffset,'netForumModalWindow');
		//setTimeout("CheckWindowCoorinatesTimer()",3000);
	}
	DialogWindow.win.focus()
}

function openDialogAbsolute(url, width, height)
{
	DialogWindow = null;
	DialogWindow = new Object();
	if (!DialogWindow.win || (DialogWindow.win && DialogWindow.win.closed))
	{
		DialogWindow.url = url;
		DialogWindow.width = width;
		DialogWindow.height = height;
		DialogWindow.name = (new Date()).getSeconds().toString();
		DialogWindow.left = (screen.width - width) / 2;
		DialogWindow.top = (screen.height - height) / 2;
		var attr = "left=" + DialogWindow.left + ",top=" + 
			DialogWindow.top + ",resizable=yes,status=yes,scrollbars=yes,width=" 
			+ DialogWindow.width + ",height=" + DialogWindow.height;
		DialogWindow.win = window.open(DialogWindow.url, DialogWindow.name, attr);
	}
	DialogWindow.win.focus()
	//setTimeout("CheckWindowCoorinatesTimer()",3000);
}

function CheckWindowCoorinatesTimer()
{
	//debugger
	try
	{
		if (DialogWindow && DialogWindow.win && DialogWindow.win.screenTop)
		{
			//nTop = Math.abs(DialogWindow.win.screenTop);
			//nLeft = Math.abs(DialogWindow.win.screenLeft);
			nTop = DialogWindow.win.screenTop;
			nLeft = DialogWindow.win.screenLeft;
			if (nTop > screen.height || nLeft > screen.width)
			{
				if (nTop > screen.height)
					nTop = 100;
				if (nLeft > screen.width)
					nLeft = 100;
				//DialogWindow.win.moveTo(nLeft,nTop); //eliminate jumping window on dual monitor systems
			}
		}
	}
	catch (e)
	{
		// window must have been closed by user before timer interval, do nothing
	}
}

function WindowBlockEvents()
{
	window.onfocus = WindowCheckModal
}

function WindowCheckModal()
{	
	if (DialogWindow != null)
	{
		if (DialogWindow.win!=null)
		{ 
			if (typeof DialogWindow.win.closed != 'unknown')
			{
				if (!DialogWindow.win.closed)
					{ DialogWindow.win.focus(); }
			}
		}
	}
}

function DialogBlockParent()
{
	if (opener) 
	{
		try
		{		
			opener.WindowBlockEvents();
		}
		catch (e)
		{
		}

	}
}

function SetWindowSize(bRemember)
{
	if (window.top==window.self) {
		if (! bRemember || ! ResizeByCookie())
		{
			w = document.documentElement.scrollWidth;
			h = document.documentElement.scrollHeight;
			if (document.body.scrollWidth > w)
			{
				w = document.body.scrollWidth;
				h = document.body.scrollHeight;
			}
			w += 40;
			h += 60;
			if (w > screen.width-50)
			w = screen.width-50;
			if (h > screen.height-50)
			h = screen.height-50;
			window.resizeTo(w,h);
			window.moveTo(((screen.availWidth-w) / 2),((screen.availHeight-h) / 2));
			window.scrollTo(0,0);
		}
    }
}

function ResizeByCookie()
{
	var bResized = false;
	var szCookie = GetCookieName(document.location.search);
	
	if (szCookie == "")
		szCookie = GetFileName(document.location.pathname);
	
	if (szCookie != "")
	{
		var szCoordinates = GetCookie(szCookie, 'netForumModalWindow');
		if (szCoordinates != null)
		{
		
			var nTopOffset = 0;
			var nLeftOffset = 0;
			var szOffsetCookie = GetCookie("WindowTopLeftOffset", 'netForumModalWindow');
			if (szOffsetCookie != null)
			{
				var aOffsets = szOffsetCookie.split(",");
				nTopOffset = (isNaN(parseInt(aOffsets[0])) ? 1 : nTopOffset);
				nLeftOffset = (isNaN(parseInt(aOffsets[1])) ? 1 : nLeftOffset);
				
				// TJW, 11/14/2005, fix from OD team to make sure windows don't resize too small
                if(nTopOffset>0) nTopOffset=-30;
                if(nLeftOffset>0) nLeftOffset=-4;
                // TJW, 11/14/2005, END
			}
			
			var aCoordinates = szCoordinates.split(",");
			var nTop = parseInt(aCoordinates[0])+nTopOffset;
			nTop = isNaN(nTop) ? 0 : nTop;
			var nLeft = parseInt(aCoordinates[1])+nLeftOffset;
			nLeft = isNaN(nLeft) ? 0 : nLeft;
		
			// TJW, 11/14/2005, fix from OD team to make sure windows don't resize too small
            if(nTop < 0 || nTop > window.screen.availHeight) nTop=100;
            if(nLeft < 0 /*|| nLeft>window.screen.availWidth*/) nLeft = 100; 
            // TJW, 11/14/2005, END 
			
			// H. Stechl 07/31/2006
			// bug # 5862 hold on to previous size
			//nOriginalHeight = parseInt(aCoordinates[2]);
			//nOriginalWidth = parseInt(aCoordinates[3]);
			
			var nHeight = parseInt(aCoordinates[2])-(nTopOffset*2);
			var nWidth = parseInt(aCoordinates[3])-(nLeftOffset*2);

			window.resizeTo(nWidth,nHeight);
			window.moveTo(nLeft,nTop);

            var adjustWidth, adjustHeight, screenLeft, screenTop;
            if (navigator.appName == "Netscape")
            {
			    adjustWidth = parseInt(aCoordinates[3]) - parseInt(window.outerWidth) ;
			    adjustHeight = parseInt(aCoordinates[2]) - parseInt(window.outerHeight);
            }
            else
            {
			    adjustWidth = parseInt(aCoordinates[3]) - parseInt(document.body.offsetWidth);
			    adjustHeight = parseInt(aCoordinates[2]) - parseInt(document.body.offsetHeight);
			}

			//adjust to the exactly saved size
			window.resizeBy(adjustWidth, adjustHeight);
			
			// adjust to make sure the window is moved to the exact position saved in cookie
			var screenLeft = (window.screenLeft == undefined ? window.screenX : window.screenLeft);
			var screenTop = (window.screenTop == undefined ? window.screenY : window.screenTop);
			var adjustLeft = nLeft - parseInt(screenLeft);
			var adjustTop = nTop - parseInt(screenTop);
			window.moveBy(adjustLeft, adjustTop);

			window.scrollTo(0,0);
			bResized = true;
		}
	}
	return bResized;
}

function GetCookieName(szQueryString)
{
	var szCookie = "";
	if (szQueryString.indexOf("WizardKey=") >= 0)
		szCookie = szQueryString.substr(szQueryString.indexOf("WizardKey=")+10);
	else if (szQueryString.indexOf("FormKey") >= 0)
		szCookie = szQueryString.substr(szQueryString.indexOf("FormKey=")+8);
	if (szCookie != "")
	{
		if (szCookie.indexOf("&") >= 0)
			szCookie = szCookie.substring(0,szCookie.indexOf("&"));
	}
	szCookie = szCookie.substring(0,8);
	return szCookie;
}

function GetFileName(szQueryString)
{
	var szCookie = "";
	if (szQueryString.indexOf(".") >= 0)
		szCookie = szQueryString.substr(0,szQueryString.indexOf("."));
	while (szCookie.indexOf("/") >= 0)
		szCookie = szCookie.substr(szCookie.indexOf("/")+1);
	if (szCookie != "")
		szCookie = "FORM_"+szCookie;
	return szCookie;
}

function BeforeUnloadWindow()
{
	//debugger
	var szCookie = GetCookieName(document.location.search);
	if (szCookie == "")
		szCookie = GetFileName(document.location.pathname);
	if (szCookie != "")
	{
		// H. Stechl 07/31/2006
		// bug # 5862 use original height if increased by less than 10 pixels
		// prevents ever-increasing browser height.
		//var nWidth = document.body.offsetWidth;
		//var nHeight = document.body.offsetHeight;
		//if ( (nHeight > nOriginalHeight) && (nHeight - nOriginalHeight) < 21) // increase from 11 to 21 for ie7
		//	nHeight = nOriginalHeight;
		//var szCookieValue = window.screenTop+","+window.screenLeft+","+nHeight+","+nWidth;

	    var szCookieValue;
	    if (navigator.appName == "Netscape")
	    	szCookieValue = window.screenY + "," + window.screenX + "," + window.outerHeight + "," + window.outerWidth;
	    else
	    	szCookieValue = window.screenTop + "," + window.screenLeft + "," + document.body.offsetHeight + "," + document.body.offsetWidth;
		
		//((window.screenLeft > window.screen.availWidth) ? (window.screenLeft - window.screen.availWidth) : window.screenLeft)
		SetCookie(szCookie,szCookieValue,'netForumModalWindow');
	}
}

function Report_Preview(szReportKey)
{
	openDialogAbsolute("ReportPreview.aspx?ReportKey="+szReportKey,800,600);
}

/*
function Report_Run(szReportKey)
{
	openDialogAbsolute("ReportStart.aspx?ReportKey="+szReportKey,200,200);
}
*/

function Report_Run(szReportKey)
{
	Report_Run(szReportKey,null);
}

function Report_Run(szReportKey, szParam)
{
  Report_Run(szReportKey,szParam,null);
}

function Report_Run(szReportKey, szParam, szAParams)
{
  Report_Run(szReportKey,szParam,szAParams,null);
}

function Report_Run(szReportKey,szParam,szAdditionalParams,szDelivery)
{
   //debugger;
       
   //SB, 11/03/2005, MRS
   var szOutputFormat = "";
   //var oDropDown = document.getElementById("DDL"+szReportKey);
    
   //if (oDropDown != null)
   //{
		//szOutputFormat = oDropDown.options[oDropDown.selectedIndex].value;
		
		if (szDelivery==null)
		    szDelivery = "0";
		    
		if (szAdditionalParams==null)
		{
			//openDialogAbsolute("ReportStart.aspx?ReportKey="+szReportKey,200,200);
			
			//SB, 04/05/2006, Fixes Bug# 5294			
			if (szParam == "True")
			{
				openDialogAbsolute("ReportStart.aspx?ReportKey="+szReportKey+"&bReportCentral=true&ReportSubscription="+szDelivery, 200, 200);
			}
			else if (szParam == "False")
			{
			    openDialogAbsolute("ReportStart.aspx?ReportKey="+szReportKey+"&bReportCentral=true&ReportSubscription="+szDelivery, 800, 600);		
			}
			else
			{
			    openDialogAbsolute("ReportStart.aspx?ReportKey="+szReportKey+"&bReportCentral=true&ReportSubscription="+szDelivery, 200, 200);
			}
			//SB, 04/05/2006			    
		}
		else
		{
			//openDialogAbsolute("ReportStart.aspx?ReportKey="+szReportKey+szAdditonalParams,200,200);
			openDialogAbsolute("ReportStart.aspx?ReportKey="+szReportKey+szAdditionalParams+"&bReportCentral=true&ReportSubscription="+szDelivery, 200, 200);			
		}
   //}   
   //SB, 11/03/2005, end
}

function ConfirmDialog(szMessage, szButtonID)
{
	if (window.confirm(szMessage))
	{
		if (document.forms[0].action)
			document.forms[0].action += "&Confirm=ByPass";

		if (szButtonID==null || szButtonID=='')
			szButtonID='ButtonSave';

		oButton = eval("document.getElementById('"+szButtonID+"')");
		
		if (!oButton)
			oButton = eval('document.forms[0].'+szButtonID);
		
		if (oButton)
			oButton.click();
	}
}

function ClearConfirmByPass()
{
	if (document.forms[0].action && document.forms[0].action.indexOf("&Confirm=ByPass") > 0)
		document.forms[0].action = document.forms[0].action.replace("&Confirm=ByPass","");
}

function AlertDialog(szMessage)
{
	window.alert(szMessage);
}

//adds HideUI=Yes when a OpenNewWindow is called. This is a work-around
// to make sure StyleModal.css is used for modal windows instead of
// Style.css to address the issue where the TOPUI/Sidebar would collapse
// when a modal window is opened.
function checkModalUrl(szUrl) {

    if (szUrl != null && szUrl.indexOf('HideUI=') < 0
       && szUrl.indexOf('Modal=') < 0 && szUrl.indexOf('bModal=') < 0) {
        if (szUrl.indexOf('?') == -1)
            szUrl += '?HideUI=Yes';
        else {
            szUrl += '&HideUI=Yes';
        }
    }
    return szUrl;
}


//s sterian - in order to remove header added by internet explorer to innerHTML in iframe
//				(for RichTextBox control)
 
var differenceUrl2P ='',differenceUrl1P ='',differenceUrl='',differenceUrl4P='',differenceUrlAnchor='';


function ReplaceUrlHeaders(textHtml,checkbPres)
{
	var innText=new String();	
	innText = textHtml;
	
	//SRI --Bug# 11386 - 06/14/2010 
    //NXN and ssterian -- resolve issue with relative path causing the script to get stuck in infinite loop 
	if (differenceUrl2P =='..')
		differenceUrl2P ='';

	if (differenceUrl4P =='../..')
		differenceUrl4P ='';
	
	if (checkbPres != null && checkbPres.checked == true)
	{//replace only anchors
	    if (differenceUrlAnchor != '')
	    	while (innText.indexOf(differenceUrlAnchor+'#',0)!=-1)	    		
		        innText = innText.replace(differenceUrlAnchor+'#','#');	   
		     
		return innText;
    }

	if (differenceUrlAnchor != '')					 									
		while (innText.indexOf(differenceUrlAnchor,0)!=-1)
			innText = innText.replace(differenceUrlAnchor,'');	
	if (differenceUrl1P != '')
		while (innText.indexOf(differenceUrl1P,0)!=-1)
			innText = innText.replace(differenceUrl1P,'');
	if (differenceUrl2P != '')
		while (innText.indexOf(differenceUrl2P,0)!=-1)
			innText = innText.replace(differenceUrl2P,'..');
	if (differenceUrl4P != '')
		while (innText.indexOf(differenceUrl4P,0)!=-1)
			innText = innText.replace(differenceUrl4P,'../..');
	if (differenceUrl != '')										 									
		while (innText.indexOf(differenceUrl,0)!=-1)
			innText = innText.replace(differenceUrl,'');	 

	return innText;	
}

function OnLoadGrabUrlDiff(iframeObj)
{
	var c=0;var testHtml=new String();	
	
	iframeObj.document.body.innerHTML ="<A href='/TxTTT.aspx'>Link</A>xxx0"+
	"<A href='UxUUU.aspx'>Link</A>yyy0<A href='../VxVVV.aspx'>Link</A>"+
	"zzz0<a href='../../WxWWW.aspx'>Link</a>www0<a href='#QxQQQ'>Link</a>";
	
	testHtml=iframeObj.document.body.innerHTML;
	
	c = testHtml.indexOf('/TxTTT.aspx');
	if (c>0)
		differenceUrl=testHtml.substr(9,c-9);		
	b  = testHtml.indexOf('xxx0');
	testHtml = testHtml.substr(b+4);	
		
	c = testHtml.indexOf('UxUUU.aspx');
	if (c>0)
		differenceUrl1P=testHtml.substr(9,c-9); 		
	b  = testHtml.indexOf('yyy0');
	testHtml = testHtml.substr(b+4);			
	c = testHtml.indexOf('/VxVVV.aspx');		
	if (c>0)
		differenceUrl2P=testHtml.substr(9,c-9); 		
	
	b  = testHtml.indexOf('zzz0');
	testHtml = testHtml.substr(b+4);		
	c = testHtml.indexOf('/WxWWW.aspx');
	if (c>0)
	 differenceUrl4P=testHtml.substr(9,c-9);	
	 
	b  = testHtml.indexOf('www0');	
	testHtml = testHtml.substr(b+4);		
	c = testHtml.indexOf('#QxQQQ');		
	if (c>0)
	 differenceUrlAnchor=testHtml.substr(9,c-9);	 
}


function goback()
{
	history.go(-1);
}

var new_win;

function open_window(url,win_name,w,h,scroll)
{
	var settings;
	settings+='"toolbar=0, directories=0, menubar=0,"';
	if(scroll!=null)
	{
		settings+='" scrollbars=yes, resizable=1,"';
	}
	else
	{
		settings+='" scrollbars=0, resizable=0,"';
	}
	settings+='"status=0, width='+w+', height='+h+'"';
	close_window();
	var new_win = window.open(url, win_name, settings);
	new_win.focus();
}

function close_window()
{
	if(new_win  && !new_win.closed)
	{
		new_win.close();
	}
}

function arg_length(f,len,number)
{
	if(len!=number) {document.write("function "+f+"called with "+len+" arguments, but expected "+number+"!");return 0;} else {return 1;}
}

function not_empty(par)
{
	if ((par!=null)&&(par!="")) return 1;
	if ((par==null)||(par=="")) return 0;
}

function space(par1,par2)
{
	if(not_empty(par1)&&not_empty(par2)) return 1;
	if(!not_empty(par1)||!not_empty(par2)) return 0;
}

function StartProcess(id,s,wdth,hght,iFrameID)
{
	var features;
	var width;
	var heigth;
	
	if(wdth==null)
	    width = 200;
	   else
	    width = wdth;
	    
	if (hght ==null)
	    height = 100;
	   else
	    height = hght;

/*  Need to resolve z-Index issues on profile forms before can use every where  
    if(iFrameID == null && document.getElementById("DataFormProgressFrame") != null)
    iFrameID = "DataFormProgressFrame";*/

    //AAG 03/16/2011 netFORUM 2011 FUND-19
    if(iFrameID == null || iFrameID == 'undefined')
    {
	features = "toolbar=no,directories=no,menubar=no,scrollbars=no,resizable=no,height="+height+",width="+width;
	window.open('ProgressBar.aspx?id=' + id +'&s=' + s, 'Progress', features);
    }
    else
    {
        var _frame = document.getElementById(iFrameID);
        if(_frame != null)
        {
            _frame.src ='ProgressBar.aspx?id=' + id +'&s=' + s;
            _frame.height = height;            
            _frame.width = width;

        }
    }
}

function formatInput(element,format)
{
	if(element!=null && element.tagName=="INPUT" && element.type=="text" && format!=null)
	{
		var s=element.value;
		var tmpStr = s;
		var f=format.toLowerCase();
		switch(f)
		{
			case "integer":
			{
				var n=s.length;
				s=s.replace(/[^0-9]/g,"");
				if(element.value!=s) element.value=s;
				if(s.length<n)
				{
					if(!confirm("The entered number contains invalid characters and they have been removed.\nPlease review the number and click OK to continue or Cancel to modify."))
					{
						try{element.focus();} catch(x){}
						return false;
					}
				}
				break;
			}
			case "float":
			{
				var n=s.length;
				s=s.replace(/[^0-9\.\,]/g,"");
				var m=s.length;
				s=s.replace(/[^0-9\.]/g,"");
				if(s!="")
				{
					if(s.indexOf(".")<0) s+=".00";
					var a=s.split(".");
					s=a[0]+".";
					for(var i=1; i<a.length; i++) s+=a[i];
					a=s.split("");
					if(a[0] && a[0]==".") a[0]="0.";
					else if(a[a.length-1] && a[a.length-1]==".") a[a.length-1]=".00";
					s=a.join("");
				}
				if(element.value!=s) element.value=s;
				if(m<n)
				{
					if(!confirm("The entered number contains invalid characters and they have been removed.\nPlease review the number and click OK to continue or Cancel to modify."))
					{
						try{element.focus();} catch(x){}
						return false;
					}
				}
				break;
			}
			case "currency":
			{
				var n=s.length;
				s=s.replace(/[^0-9\.\$\,]/g,"");
				var m=s.length;
				s=s.replace(/[^0-9\.]/g,"");
				if(s!="")
				{
					var a=s.split(".");
					s=a[0]+".";
					for(var i=1; i<a.length; i++) s+=a[i];
					s=""+(Math.round(parseFloat(s)*100)/100);
					a=s.split("");
					if(a[0] && a[0]==".") a[0]="0.";
					else if(a[a.length-1] && a[a.length-1]==".") a[a.length-1]=".00";
					s=a.join("");
					a=s.split(".");
					if(a[1])
					{
						if(a[1].length==0) a[1]="00";
						else if(a[1].length==1) a[1]+="0";
					}
					else a[1]="00";
					s=a.join(".");
					s="$"+s;
				}
				if(element.value!=s) element.value=s;
				if(m<n)
				{
					if(!confirm("The entered currency value contains invalid characters and they have been removed.\nPlease review the value and click OK to continue or Cancel to modify."))
					{
						try{element.focus();} catch(x){}
						return false;
					}
				}
				break;
			}
			case "time":
			{
				s=s.toLowerCase().replace(/[^0-9\:\a\p\m]/g,"");
				var t="";
				if(s.indexOf("am")>0) t="am";
				else if(s.indexOf("pm")>0) t="pm";
				s=s.replace(/[^0-9\:]/g,"");
				var a=s.split(":");
				if(a.length!=2)
				{
					if(tmpStr!="")
					{
						alert("The entered time is invalid and will be removed!\nPlease re-enter a valid time.");
						element.value="";
						try{element.focus();} catch(x){}
						return false;
					}
				}
				else
				{
					for(var i=0; i<a.length; i++)
					{
						if(a[i].length<2)
						{
							a[i]="00"+a[i];
							a[i]=a[i].substring(a[i].length-2);
						}
						else if(a[i].length>2) a[i]=a[i].substring(0,2);
					}
					if(t!="")
					{
						if(parseInt(a[0])>12) a[0]="12";
					}
					else if(t=="")
					{
						if(parseInt(a[0])>23) a[0]="00";
					}
					if(parseInt(a[1])>59) a[1]="00";
					s=a.join(":");
					if(t!="") s+=" "+t;
					if(element.value!=s) element.value=s;
				}
				break;
			}
			case "date":
			{
				s=s.replace(/[^0-9\/]/g,"");
				if(s.length>10) s=s.substring(0,10);
				var a=s.split("/");
				if(a.length!=3)
				{
					if(s!="")
					{
						alert("The entered date is invalid and will be removed!\nPlease re-enter a valid date.");
						element.value="";
						try{element.focus();} catch(x){}
						return false;
					}
				}
				else
				{
					for(var i=0; i<a.length; i++)
					{
						if(a[i].length<2)
						{
							a[i]="00"+a[i];
							a[i]=a[i].substring(a[i].length-2);
						}
						else if(i<2 && a[i].length>2) a[i]=a[i].substring(0,2);
						else if(i==2 && a[i].length>4) a[i]=a[i].substring(0,4);
					}
					if(parseInt(a[0])>12) a[0]="12";
					if(parseInt(a[1])>31) a[1]="31";
					s=a.join("/");
					if(element.value!=s) element.value=s;
				}
				break;
			}
			case "phone":
			{
				s=s.replace(/[^0-9]/g,"");
				var n=s.length;
				if(s.length==10 || (s.length==11 && s.substring(0,1)=="1"))
				{
					var b=false;
					if(s.substring(0,1)=="1")
					{
						b=true;
						s=s.substring(1);
					}
					var a=s.split("");
					var n=a.length;
					for(var i=0; i<n; i++)
					{
						switch(i)
						{
							case 0: a[i]="("+a[i]; break;
							case 2: if(a[i+1]!=null) a[i]+=") "; break;
							case 5: if(a[i+1]!=null) a[i]+="-"; break;
						}
					}
					s=a.join("");
					if(b) s="1 "+s;
				}
				if(s.length==7) s=s.substring(0,3)+"-"+s.substring(3);
				if(n!=0 && (n<7 || (n>11 && s.substring(0,1)=="1") || (n>10 && s.substring(0,1)!="1")))
				{
					if(!confirm("The entered phone number does not seem valid.\nPlease review the number and click OK to continue or Cancel to modify."))
					{
						try{element.focus();} catch(x){}
						return false;
					}
				}
				else if(element.value!=s) element.value=s;
				break;
			}
			case "alphanumeric":
			{
				var n=s.length;
				s=s.replace(/[^\w]/g,"");
				if(element.value!=s) element.value=s;
				if(n>s.length) alert("Invalid or special characters have been removed from your entry.\nOnly alpha-numeric characters and underscores \"_\" (no spaces) are allowed.");
				break;
			}
			case "initialuppercase":
			{
				var a=s.split("");
				var n=a.length;
				for(var i=0; i<n; i++)
				{
					if(i==0) a[i]=a[i].toUpperCase();
					else if(a[i]==" " && a[i+1]!=null) a[i+1]=a[i+1].toUpperCase();
				}
				s=a.join("");
				if(element.value!=s) element.value=s;
				break;
			}
			case "fulluppercase":
			{
				s=s.toUpperCase();
				if(element.value!=s) element.value=s;
				break;
			}
			case "fulllowercase":
			{
				s=s.toLowerCase();
				if(element.value!=s) element.value=s;
				break;
			}
		}
	}
}

function fadeOpacity(id, opacStart, opacEnd, millisec) 
{ 
    if (millisec==null)
		millisec=200;
    var speed = Math.round(millisec / 100); 
    var timer = 0; 

    if(opacStart > opacEnd) { 
        for(i = opacStart; i >= opacEnd; i--) { 
            setTimeout("changeOpac(" + i + ",'" + id + "')",(timer * speed)); 
            timer++; 
        } 
    } else if(opacStart < opacEnd) { 
        for(i = opacStart; i <= opacEnd; i++) 
            { 
            setTimeout("changeOpac(" + i + ",'" + id + "')",(timer * speed)); 
            timer++; 
        } 
    } 
} 

function changeOpac(opacity, id, bDisappear) 
{ 
    var object = document.getElementById(id).style; 

	if (object != null)
	{
	    if (opacity != 0 && object.display != 'block') {
	        object.display = 'block';
	        growToFitWidth();
	    }

		object.filter = "alpha(opacity=" + opacity + ")"; 
		
		if (opacity == 0 && object.display != 'none') {
		    object.display = 'none';
		    restoreWidth();
		}

    }
}

// NS 13464 - Increase the width of the dialog to fit to the lookup and restore it when the lookup is hidden
var dialogOriginalWidth;
var dialogGrowToWidth;
function growToFitWidth() { 
    if (!window.name) return;
    var iframe = $('#' + window.name, window.parent.document);
    if (iframe.length) {
        // Get original width, to restore to this value later
        var currentWidth = iframe.width();
        if (currentWidth != dialogGrowToWidth) {
            dialogOriginalWidth = currentWidth;
            // Calculate the new width and grow to it
            if (!dialogGrowToWidth) {
                dialogGrowToWidth = iframe[0].contentWindow.document.documentElement.scrollWidth;
                iframe.width(dialogGrowToWidth);
                iframe.parents('div[role="dialog"]').width('auto');
            }
        }
    }
}
function restoreWidth() {
    if (dialogOriginalWidth) {
        var iframe = $('#' + window.name, window.parent.document);
        if (iframe.length && iframe.width() > dialogOriginalWidth) {
            // Restore the original width and clean variables
            iframe.width(dialogOriginalWidth);
            dialogGrowToWidth = null;
            dialogOriginalWidth = null;
        }
    }
}

function fadeHeight(id, heightStart, heightEnd, millisec) 
{
    if (millisec==null)
		millisec=250;
    var speed = Math.round(millisec / 100); 
    var timer = 0; 

    if(heightStart > heightEnd) { 
        for(i = heightStart; i >= heightEnd; i--) { 
            setTimeout("changeHeight(" + i + ",'" + id + "')",(timer * speed)); 
            timer++;
        } 
    } else if(heightStart < heightEnd) { 
        for(i = heightStart; i <= heightEnd; i++) 
            { 
            setTimeout("changeHeight(" + i + ",'" + id + "')",(timer * speed)); 
            timer++; 
        } 
    } 
} 

function changeHeight(height, id) 
{ 
    var object = document.getElementById(id).style;
    if (object != null)
    {
		if (height!=0 && object.display != 'block')
			object.display = 'block';

		object.height = (height); 

		if (height==0 && object.display != 'none')
			object.display = 'none';
	}
} 



function fadeWidth(id, widthStart, widthEnd, millisec) 
{ 
    if (millisec==null)
		millisec=250;
    var speed = Math.round(millisec / 100); 
    var timer = 0; 

    if(widthStart > widthEnd) { 
        for(i = widthStart; i >= widthEnd; i--) { 
            setTimeout("changeWidth(" + i + ",'" + id + "')",(timer * speed)); 
            timer++; 
        } 
    } else if(widthStart < widthEnd) { 
        for(i = widthStart; i <= widthEnd; i++) 
            { 
            setTimeout("changeWidth(" + i + ",'" + id + "')",(timer * speed)); 
            timer++; 
        } 
    } 
} 

function changeWidth(width, id) { 
    var object = document.getElementById(id).style; 
    if (object != null)
    {
		if (width!=0 && object.display != 'block')
			object.display = 'block';

	    object.width = (width); 

		if (width==0 && object.display != 'none')
			object.display = 'none';

	}
} 

// H. Stechl 07/27/2006
// control which button/image is activated.
function CheckForEnter(szButtonOrImage,evt)
{
	//RJ, bug# 11743 
	// Get current event object
    evt = (!evt && window.event) ? window.event : evt;
   
	if (evt)
	{
		// Get KeyCode
        var keyCode = 0;
        if (evt.which) 
        {
            keyCode = evt.which;
        } 
        else 
        {
            keyCode = evt.keyCode;
        }
		if (keyCode==13)
		{
			oButtonOrImage = document.getElementById(szButtonOrImage);
			if (oButtonOrImage != null)
			{
                document.forms[0].onkeypress=null;
				evt.keyCode = 0;
				evt.cancelBubble = true;
                oButtonOrImage.focus();
				oButtonOrImage.click();
				return false; // Don't accept the keypress
			}
		}
	}
	return true;
}

function RTBCleanupMSWord(szIframeId)
{
    if (szIframeId != null && szIframeId != "")
    {
        var oIframe = document.getElementById(szIframeId);
        if (oIframe != null)
        {
            var oDocument = oIframe.contentWindow.document;
            var aElements = oDocument.getElementsByTagName("*");
            for (var i = 0; i < aElements.length; i++)
            {
                var e = aElements[i];
                if (e.className.toLowerCase().indexOf("mso")>=0)
                    e.removeAttribute("className","",0);
                if (e.style.cssText.toLowerCase().indexOf("mso")>=0)
                {
                    var szCssText = "";
                    var aStyles = e.style.cssText.split(";");
                    for (var j = 0; j < aStyles.length; j++)
                    {
                        if (aStyles[j].toLowerCase().indexOf("mso")<0)
                            szCssText += aStyles[j] + ";";
                    }
                    e.style.cssText = szCssText;
                }
                e.removeAttribute("lang","",0);
                e.removeAttribute("stylw","",0);
            }
            var html = oDocument.body.innerHTML;
            html = html.replace(/\r/g,"");
            html = html.replace(/\n/g,"");
            html = html.replace(/\t/g," ");
            html = html.replace(/<\\?\??xml[^>]>/gi,"");
            html = html.replace(/(\&lt\;)?\\?\??|\W?|\w?xml[^(\&gt\;)](\&gt\;)?/gi,"");
            html = html.replace(/<\/?\w+:[^>]*>/gi,"");
            html = html.replace(/<p>&nbsp;<\/p>/gi,"<br/><br/>");
            html = html.replace(/[ ]+/g," ");
            html = html.replace(/<(\/)?strong>/ig,"<$1b> ");
            html = html.replace(/<(\/)?em>/ig,"<$1i> ");
            html = html.replace(/[��]/gi,"\"")
	        html = html.replace(/[��]/gi,"'")
            html = html.replace(/^\s/i,"");
            html = html.replace(/\s$/i,"");
            html = html.replace(/<o:[pP]>&nbsp;<\/o:[pP]>/gi,"");
            html = html.replace(/<font>([^<>]+)<\/font>/gi,"$1");
            html = html.replace(/<span>([^<>]+)<\/span>/gi,"$1");
	        //html = html.replace(/<p([^>])*>(&nbsp;)*\s*<\/p>/gi,"")
	        //html = html.replace(/<span([^>])*>(&nbsp;)*\s*<\/span>/gi,"")
	        try { html = html.replace(/<st1:.*?>/gi,""); } 
            catch(ex) { html = html.replace(/<st1:.*>/gi,""); }
            oDocument.body.innerHTML = html;
        }
    }
}
//AAG MISC-06 netFORUM 2010 Session timeout warning message
function SessionTimeoutWarningDisplay(iTimeout) {
    sessionTimeout = iTimeout;
    // H. Stechl 12/23/2012
    // clear previous timeout before setting a new one.
    if (GetParentWindow() && (GetParentWindow().SessionTimeoutWarningDisplayClear != null))
        GetParentWindow().SessionTimeoutWarningDisplayClear();
    SessionTimeoutWarningDisplayClear();
    iTimeout = iTimeout * 60000;
    var displayMssageTime = 120000;
    nSessionTimerid = setTimeout("SessionTimeoutConfirm()", (iTimeout - displayMssageTime));
}
function SessionTimeoutWarningDisplayClear() {
    clearTimeout(nSessionTimerid);
}

function SessionTimeoutConfirm() {
    //setTimeout("AutoAbandonSession()", 120000);

    //  var szConfirmDialogDiv = " <div id=\"SessionTimeoutDialog\"><div id=\"dialog\" title=\"Session Warning\"> <Label id=\"LabelSessionTimeout\" CSSClass=\"DataFormLabel\">";
    // szConfirmDialogDiv += "Your session is about to expire! <br /> click ok to continue.</Label><BR /> <Button id=\"Button\" CSSClass=\"DataFormButton\" onclick=\"CloseSessionDialog(); __doPostBack(\'XXXXXXXX\',\'\'); \" >Ok! </Button>";
    //  szConfirmDialogDiv += "</div></div>";

    //  document.writeln(szConfirmDialogDiv);

    OpenSessionDialog();
    if(bTimeout == null)
        bTimeout=true;

    setTimeout("if(bTimeout && opener == null){bConfirmNavAway=0; AutoAbandonSession(); }else if(bTimeout && opener != null){bConfirmNavAway=0; AutoAbandonSession();} else{setTimeout('bTimeout=true;SessionTimeoutConfirm();', 1200000);}", 115000);
    /*
    if(window.confirm("Your session is about to expire! click ok to continue"))
    {
    __doPostBack("XXXXXXXX","");        
    }
    else
    {
    } 
    */
}

var modal = "<div id='SessionDialog'><p>Your Session is about to expire! click ok to continue.</p></div>";
function ShowSessionDialog() {
    $(modal).dialog({
        autoOpen: false,
        width: 600,
        dialogClass: "confirm",
        closeOnEscape: false,
        open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
        buttons: {
            "Ok": function () {
                  CloseSessionDialog();
                bTimeout = false;
                //__doPostBack('XXXXXXXX','');      
                $.post(document.getElementById("__APPLICATIONPATH").value + "/Ping.aspx");
                SessionTimeoutWarningDisplay(sessionTimeout);
            }
        }
    });
}

var modalExpired = "<div id='SessionDialogExp'><p>Your Session has expired, please click OK to continue.</p></div>";
function ShowSessionExpiredDialog() {
    $(modalExpired).dialog({
        autoOpen: false,
        width: 600,
        dialogClass: "confirm",
        closeOnEscape: false,
        open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
        buttons: {
            "Ok": function () {
                  CloseSessionDialog();
                  bTimeout = false
                //__doPostBack('XXXXXXXX','');                
                window.location.href = document.getElementById("__APPLICATIONPATH").value + "/StartPage.aspx";
            }
        }
    });
    $('#SessionDialogExp').dialog('open');
}

function OpenSessionDialog() {
    $('#SessionDialog').dialog('open');
    // $("#SessionTimeoutDialog").dialog("open");
}

function CloseSessionDialog() {
    $('#SessionDialog').dialog('close');
    // $("#SessionTimeoutDialog").dialog("close");
}

function AutoAbandonSession() {
    //changed redirect from AbandonSession to Logout (which will handle session).
    //window.location.href = "../StartPage.aspx";
    CloseSessionDialog();
    ShowSessionExpiredDialog();
}

function GetPageLoadTime()
{
    
    if(!bLoadTimeAdded && document.getElementById('LabelPageLoadTime') != null)
        document.getElementById('LabelPageLoadTime').innerHTML += GetCookie('LastPageLoadTime','netForum'); 
    else if(!bLoadTimeAdded && document.getElementById('BottomPane') != null)
        document.getElementById('BottomPane').innerHTML += '<UL><LI> <span id=\"LabelPageLoadTime\">Page Load Time: '+GetCookie('LastPageLoadTime','netForum')+'</span></LI></UL>'; 

        bLoadTimeAdded = true;
}


function NavigationWarningConfirm(e)
{
   

    if(bConfirmNavAway == 1 && (theForm.__EVENTTARGET.value == null || theForm.__EVENTTARGET.value == "" || theForm.__EVENTTARGET.value == "ButtonCancel"))
    {
               
        return "You have not saved!";
        /* if(window.confirm(""))
        {
          
        }
        else
        {
           // __doPostBack("","");
        }
        */
    }
    else
    {

    bConfirmNavAway =1;
    }

}

//AAG MISC-06 netFORUM 2010 DateTextBox Type Ahead Suggest Calendar Functions  
// Created To have readable and debuggable code
// Bug# 12024 08/17/2010  
function DateTextOnFocus(szControlId, szValue,szBaseID,szIFrameID,szConfigPath,szAutoPostback)
{
    clearTimeout(timerid); 
    if(utilitystring!='') 
    { 
      document.getElementById(utilitystring).offsetParent.style.zIndex=998; 
      document.getElementById(utilitystring).style.display='none'; 
    } 
    
    utilitystring= szIFrameID ;
    //lets make cleaner debuggable JS code
    //var szTimeoutCommands = 'if(document.getElementById(\''+ szNewIframeID+'\').style.display==\'none\') document.getElementById(\'' + szNewBaseID + '\').style.zIndex=9999; fadeOpacity(\'' + szNewIframeID + '\',0,100); document.getElementById(\'' + szNewIframeID + '\').src=\'' + szConfigPath + '?typeaheadsuggest=yes&field='+this.id+' (this.AutoPostBack) ? "&PostbackOpener=Yes" : "") + "&value='+this.value+'\'';
    
    timerid= setTimeout('CalendarTypeAheadDisplay(\''+szControlId+'\',\''+szValue+'\',\''+szBaseID+'\',\''+szIFrameID+'\',\''+szConfigPath+'\',\''+szAutoPostback+'\');' ,150);     
    clearTimeout(timerid);

    return true;
}

function DateTextOnKeyUp(szNewID, szControlID, szValue, szBaseID, szFrameID, szConfigPath, szAutoPostBack)
{
    clearTimeout(timerid);    
    if (document.getElementById(szNewID).value.length>=0) 
    { 
        timerid=setTimeout('CalendarRefresh(\''+szNewID+'\',\''+szControlID+'\',\''+szValue+'\',\''+szBaseID+'\',\''+szFrameID+'\',\''+szConfigPath+'\',\''+szAutoPostBack+'\');' ,150);
    }

    return true;
}

function DateTextOnBlur(szControlId, szValue, szBaseID, szIFrameID, szConfigPath, szAutoPostBack)
{
    clearTimeout(timerid);
    timerid=setTimeout('CalendarHide(\''+szControlId+'\',\''+szValue+'\',\''+szBaseID+'\',\''+szIFrameID+'\',\''+szConfigPath+'\',\''+szAutoPostBack+'\');',150);
    timerid=setTimeout('CalendarHideWithParent(\''+szControlId+'\',\''+szValue+'\',\''+szBaseID+'\',\''+szIFrameID+'\',\''+szConfigPath+'\',\''+szAutoPostBack+'\');',150);
}

function CalendarHide(szControlID,szValue,szBaseID,szFrameID, szConfigPath, szAutoPostBack)
{
   if (document.getElementById(szFrameID).style.display!='none') 
   {
      document.getElementById(szBaseID).style.zIndex=998; 
   }
   
   if(navigator.appName != "Microsoft Internet Explorer")
   {   
     fadeOpacity(szFrameID,100,0,0);
   }
   else
   {
     fadeOpacity(szFrameID,100,0);
   }
}

function CalendarHideWithParent(szControlID, szValue, szBaseID, szFrameID, szConfigPath, szAutoPostBack)
{
   if (parent != null && parent.document.getElementById(szFrameID) != null && parent.document.getElementById(szFrameID).style.display!='none') 
   {
      document.getElementById(szBaseID).style.zIndex=998; 
   }
   
   if(navigator.appName != "Microsoft Internet Explorer")
   {   
     fadeOpacity(szFrameID,100,0,0);
   }
   else
   {
     fadeOpacity(szFrameID,100,0);
   }
}

function CalendarRefresh(szNewID, szControlID, szValue, szBaseID, szFrameID, szConfigPath, szAutoPostBack)
{
   if(document.getElementById(szBaseID).style != null && document.getElementById(szFrameID).style.display=='none') 
   { 
      document.getElementById(szBaseID).style.zIndex=9999; 
   }
    
   fadeOpacity(szFrameID,0,100,0); 

   var szIFrameSRC = szConfigPath + '?typeaheadsuggest=yes&field='+szControlID;

   if(szAutoPostBack == 'true' || szAutoPostBack == '1')
   {
        szIFrameSRC += '&PostbackOpener=Yes';
   }

   szIFrameSRC += '&value='+szValue;
    
   document.getElementById(szFrameID).src=szIFrameSRC;
   //'" + szConfigPath + "?typeaheadsuggest=yes" + "&field='+this.id+'" + ((this.AutoPostBack) ? "&PostbackOpener=Yes" : "") + "&value='+this.value+'\\'
}

function CalendarTypeAheadDisplay(szControlId,szValue,szBaseID,szFrameID,szConfigPath,szAutoPostBack)
{
     if(document.getElementById(szFrameID).style != null && document.getElementById(szFrameID).style.display == 'none')
     {
        document.getElementById(szBaseID).style.zIndex=99999;
     }

     fadeOpacity(szFrameID,0,100,0);

     var szIFrameSRC = szConfigPath + '?typeaheadsuggest=yes&field='+szControlId; //this.Id

     if(szAutoPostBack == 'true' || szAutoPostBack == '1')
     {
        szIFrameSRC += '&PostbackOpener=Yes';
     }

     szIFrameSRC += '&value='+szValue; //this.value

     document.getElementById(szFrameID).src=szIFrameSRC;
}

function SetDownloadExpireDate()
{
    if(document.getElementById('prd_download_available_days') == null && document.getElementById('ivd_download_days_override') == null)
        return;

    var nPrdDays = 0;
    var nIvdOverrideDays = 0;

    if(document.getElementById('prd_download_available_days') != null)
        nPrdDays = parseInt(document.getElementById('prd_download_available_days').innerHTML);

    if(document.getElementById('ivd_download_days_override') != null)
        nIvdOverrideDays = parseInt(document.getElementById('ivd_download_days_override').value);

    var dtAddDate = new Date(document.getElementById('ivd_add_date').innerHTML);
    var dtExpireDate;

    if(nIvdOverrideDays > 0)
    {
        var nIvdOverrideMilliSec = nIvdOverrideDays*86400000;
        dtExpireDate = dtAddDate.getTime() + nIvdOverrideMilliSec+86400000;
    }
    else
    {
        var nPrdMilliSec = nPrdDays*86400000;
        dtExpireDate = dtAddDate.getTime() + nPrdMilliSec +86400000;                
    }
    dtExpireDate = new Date(dtExpireDate);

    document.getElementById('IvdDownloadExpireDate').innerHTML = (dtExpireDate.getMonth()+1)+'/'+dtExpireDate.getDate()+'/'+dtExpireDate.getFullYear();
}

function SetDownloadsLeft()
{
    if(document.getElementById('prd_download_available_count') == null && document.getElementById('ivd_download_limit_override') == null)
        return;

    var nPrdCount = 0;
    var nIvdOverrideLimit = 0;

    if(document.getElementById('prd_download_available_count') != null)
        nPrdCount = parseInt(document.getElementById('prd_download_available_count').innerHTML);
    if(isNaN(nPrdCount) || nPrdCount == null)
        nPrdCount = 0;

    if(document.getElementById('ivd_download_limit_override') != null)
        nIvdOverrideLimit = parseInt(document.getElementById('ivd_download_limit_override').value);

        if(isNaN(nIvdOverrideLimit) || nIvdOverrideLimit == 0)
            nIvdOverrideLimit = 0;

    var nNumOfDownloads = parseInt(document.getElementById('ivd_download_count').innerHTML);

    if(isNaN(nNumOfDownloads) || nNumOfDownloads == null)
        nNumOfDownloads = 0;
    var nDownloadsLeft;

    if(nIvdOverrideLimit > 0)
        nDownloadsLeft = nIvdOverrideLimit - nNumOfDownloads;
    else
        nDownloadsLeft = nPrdCount - nNumOfDownloads; 
    
     if(nDownloadsLeft < 0)
        nDownloadsLeft = 0;

    document.getElementById('IvdDownloadsLeft').innerHTML =nDownloadsLeft;
}

function InitTabSort() {

    $(".ProfileTabSortable, .ProfileTabMenuTable").addClass('connectedSortable');    
    $(".ProfileTabSortable, .ProfileTabMenuTable").sortable({
        distance: 10,
        opacity: .6,
        helper: 'original',
        //cancel: 'input',
        connectWith: '.connectedSortable',
        items: '.ProfileTitleContainer, .ProfileTitleContainerLight',
        zIndex: 999999,
        tolerance: 'pointer',
        forceHelperSize:true,
        forcePlaceHolderSize: false,
        appendTo: 'body',
        cursor: "move",
        cursorAt: { top: 0 },
        start: function (event, ui) {
            //AAG NS:5186 add float left so there is room to drop the tab
            //ui.helper.addClass("ProfileTitleContainerLight");
            $(".ProfileTabSortable, .ProfileTabMenuTable").css("float", "left");
            $(".ProfileTitleContainer").css("display", "inline-block");
            //$('.ProfileTitleContainer, .ProfileTitleContainerLight').css("float", "right");
            //$('.ProfileTitleContainer, .ProfileTitleContainerLight').css("height", "25px");
            //$('.ProfileTitleContainer, .ProfileTitleContainerLight').css("width", "100px");
            //ui.helper.css("height", "25px");
            //ui.helper.css("width", "100px");
            if ($("a[data-menu=\"MoreLinkMenu\"]").length > 0 && $("#MoreLinkMenu").is(":hidden"))
            {
                $("a[data-menu=\"MoreLinkMenu\"]").trigger("click");
            }
        },
        stop: function (event, ui) {
            //AAG NS:5186 remove the float left
            $(".ProfileTabSortable, .ProfileTabMenuTable").css("float", "auto");
           //$('.ProfileTitleContainer, .ProfileTitleContainerLight').css("float", "auto");
            //$('.ProfileTitleContainer, .ProfileTitleContainerLight').css("height", "auto");
            //$('.ProfileTitleContainer, .ProfileTitleContainerLight').css("width", "auto");
            //AAG NS:5186 enabled false no longer works we bust call disabled instead
            //$(this).sortable("enabled",false);
           // $(this).sortable("disable");
            SaveNewSort(event,ui,$(this));
           
        }
        });

        //$(".ProfileTabMenuTable").sortable({        
        //distance: 10,
        //opacity: .6,
        //helper: 'original',
        ////connectWith: '.ProfileTabSortable',
        //accept: ':not(table)',
        //zIndex: 999999,
        //tolerance: 'pointer',
        //forcePlaceHolderSize: true,
        //appendTo: 'body',
        //start: function (event,ui){
        //    if($("img[data-menu=\"MoreLinkMenu\"]").length > 0 )
        //    {
        //        $("img[data-menu=\"MoreLinkMenu\"]").click();
        //    }
        //},
        //stop: function (event,ui){
        //    //AAG NS:5186 enabled false no longer works we bust call disabled instead
        //    //$(this).sortable("enabled",false);
        //   // $(this).sortable("disable");
        //    if($('.ProfileTabSortable').length > 0 )
        //        $('.ProfileTabSortable').width("");
            
        //} 
        //});    

    $(".ProfileTabSortable, .ProfileTabMenuTable").sortable().disableSelection();



} 

function InitTabChildFormSort()
{
    $(".ProfileTabContent").sortable({
     distance: 10,
     opacity: .6,
     helper: 'original',
     greedy: true,
     zIndex: 999999,
     tolerance: 'pointer',
     start: function (event,ui){ 
         if ($("a[data-menu=\"MoreLinkMenu\"]").length > 0 && $("#MoreLinkMenu").is(":hidden")) {
             $("a[data-menu=\"MoreLinkMenu\"]").trigger("click");
         }
     },
     stop: function (event,ui){
         //AAG NS:5186 enabled false no longer works we bust call disabled instead
         //$(this).sortable("enabled",false);
         $(this).sortable("disable");
     if($("#MoreLinkMenu").length > 0 )
        {
            $("#MoreLinkMenu").hide();
        }
        if(bDroppedControl)
        {
            bDroppedControl = false;
            return;
        }
     var szProfileUpdateUrl = "DynamicProfileHandler.ashx?Command=ChildFormOrderUpdate&Tab="+$(this).attr("id");
     var szChildForm = ui.item.attr("id");
     var szPrevChildForm ="";
     var szNextChildForm = "";

     if(ui.item[0].previousElementSibling != null && ui.item[0].previousElementSibling.id != null)
        szPrevChildForm = ui.item[0].previousElementSibling.id.toString();

     if(szPrevChildForm == "" && ui.item[0].previousSibling != null && ui.item[0].previousSibling.id)
        szPrevChildForm = ui.item[0].previousSibling.id.toString();

     if(ui.item[0].nextElementSibling != null && ui.item[0].nextElementSibling.id != null)
        szNextChildForm = ui.item[0].nextElementSibling.id.toString();

     if(szNextChildForm == "" && ui.item[0].nextSibling != null && ui.item[0].nextSibling.id != null)
        szNextChildForm = ui.item[0].nextSibling.id.toString();

      var szDetailKey = szChildForm.split(':');
      var szPrevDetailKey = szPrevChildForm.split(':');
      var szNextDetailKey = szNextChildForm.split(':');

      if(szDetailKey.length < 1)
          return;

      var szDpdKey = szDetailKey[0].replace("CF_", "");
      var szChildFormKey="";
      if(szDetailKey.length == 3)
         szChildFormKey = szDetailKey[1];
      var nPrevOrderNo = 0.0000;
      var nNextOrderNo  = 0.0000;

      if (szPrevDetailKey.length > 2)
          nPrevOrderNo = parseFloat(szPrevDetailKey[2]);
      if (szNextDetailKey.length > 2)
          nNextOrderNo = parseFloat(szNextDetailKey[2]);

      if (nPrevOrderNo == 0)
          nPrevOrderNo = nNextOrderNo - 100;
      if (nNextOrderNo == 0)
          nNextOrderNo = nPrevOrderNo + 100;
      var nOrderNumber = ((nPrevOrderNo + nNextOrderNo)/parseFloat(2));

      if(szDetailKey.length == 3)
          ui.item.attr("id",szDetailKey[0]+":"+szDetailKey[1]+":"+nOrderNumber);
      
      var szDduKey ="";
      
      if(szDetailKey.length == 4)
      {
        szDduKey = szDetailKey[3];
        ui.item.attr("id",ui.item.attr("id")+":"+szDduKey);
      } 

     szProfileUpdateUrl +="&DetailKey="+szDpdKey+"&ChildForm="+szChildFormKey+"&Order="+nOrderNumber+"&Ddu="+szDduKey;
     sendServerInstruction(szProfileUpdateUrl);
     
     ProfileRefresh();
     }

    });

     $(".ProfileTabContent").sortable().disableSelection();
}


function SaveNewSort(event,ui,szContainer)
{
        if($("#MoreLinkMenu").length > 0 )
        {
            $("#MoreLinkMenu").hide();
        }

        if(bDroppedControl)
        {
            bDroppedControl = false;
            return;
        }
        var szProfileUpdateUrl = "DynamicProfileHandler.ashx?Command=TabOrderUpdate";

        if(szContainer.find("input").length > 0)
            szProfileUpdateUrl += "&Form="+szContainer.find("input").attr("id").replace("HiddenProfileTabs","");
        else
            szProfileUpdateUrl += "&Form="+szContainer.attr("id").replace("TABMENU","");
        
        var bIsOverflowTab = (ui.item.attr("id").toLowerCase().indexOf("overflowtableft_") >= 0);

        var szProfileTab ="";
        
        if(!bIsOverflowTab)
         szProfileTab = ui.item.attr("id").replace("TabLeft","");
        else
         szProfileTab = ui.item.attr("id").replace("OverflowTabLeft_","");
        var szTabKey = szProfileTab.split(':');

        var szPrevTab ="";
        var szNextTab = "";

        if(ui.item[0].previousElementSibling != null)
            szPrevTab = ui.item[0].previousElementSibling.id.toString();

        if(szPrevTab == "" && ui.item[0].previousSibling != null)
            szPrevTab = ui.item[0].previousSibling.id.toString();
     
        if(ui.item[0].nextElementSibling != null)
            szNextTab = ui.item[0].nextElementSibling.id.toString();

        if(szNextTab == "" && ui.item[0].nextSibling != null)
            szNextTab = ui.item[0].nextSibling.id.toString();

        if(szPrevTab =="" && ui.item.parent().attr("id").substring(0,7)=="TABMENU")
        {
            szPrevTab = $(".ProfileTabSortable").find('.ProfileTitleContainerLight:last').attr("id").toString();
        }

        if(szNextTab =="" && ui.item.parent().attr("id").substring(0,7) != "TABMENU" && !bIsOverflowTab)
        {
           szNextTab = $(".ProfileTabMenuTable").find('.ProfileTitleContainerLight:first').attr("id").toString();
        }
      
        if(bIsOverflowTab && szNextTab == "") 
            return;

        var szPrevDetailKey = szPrevTab.split(':');
        var szNextDetailKey = szNextTab.split(':');
        var nPrevOrderNo = 0.0000;
        var nNextOrderNo  = 0.0000;

        if (szPrevDetailKey.length > 1)
            nPrevOrderNo = parseFloat(szPrevDetailKey[1]);
        if (szNextDetailKey.length > 1)
            nNextOrderNo = parseFloat(szNextDetailKey[1]);

        if (nPrevOrderNo == 0)
            nPrevOrderNo = nNextOrderNo - 100;
        if (nNextOrderNo == 0)
            nNextOrderNo = nPrevOrderNo + 100;
        var nOrderNumber = ((nPrevOrderNo + nNextOrderNo)/parseFloat(2));

        if(szTabKey.length >= 2)
        {
            if(!bIsOverflowTab && szTabKey[1] > nPrevOrderNo && szTabKey[1] < nNextOrderNo)
                return;

            ui.item.attr("id",szTabKey[0]+":"+nOrderNumber);
        }
        
        var szCaption="";
        if(ui.item[0].firstChild != null && ui.item[0].firstChild.firstChild != null)
        {
            if(ui.item[0].firstChild.firstChild.title != null)
             szCaption = ui.item[0].firstChild.firstChild.title;
             else if(ui.item[0].firstChild.firstChild.toString() != "[object Text]")
             szCaption = ui.item[0].firstChild.firstChild.toString();
             else szCaption = ui.item[0].firstChild.firstChild.nodeValue;
        }

        var szDpuKey = "";
        if((bIsOverflowTab && szTabKey.length == 2) || (szTabKey.length == 3))
        {
            if(!bIsOverflowTab)
            szDpuKey = szTabKey[2];
            else
            szDpuKey = szTabKey[1];
            ui.item.attr("id",ui.item.attr("id")+":"+szDpuKey);
        }

       

        szProfileUpdateUrl += "&Tab="+szTabKey[0]+"&Caption="+szCaption+"&Order="+nOrderNumber+"&Dpu="+szDpuKey;
        
        sendServerInstruction(szProfileUpdateUrl);

     ProfileRefresh();

}

function InitChildFormDrop(){
    $(".ProfileTitleContainerLight").droppable({
    accept: '.ProfileTabContentDetail',
    greedy: true,
    tolerance: 'pointer',
    drop: function(event,ui){
    var szTabId = $(this).attr("id").replace("TabLeft","");
    var szChildFormId = ui.draggable.attr("id").replace("CF_", "");
    var nChildFormOrder ="AddChildForm"; //Putting the Child Form below baseline child Forms
    var szProfileUpdateUrl = "DynamicProfileHandler.ashx?Command=ChildFormOrderUpdate";
    var szTabKey = szTabId.split(":");
    var szDetailKey = szChildFormId.split(':');
    
    if(szDetailKey.length < 1)
        return;

    var szDpdKey = szDetailKey[0].replace("CF_", "");
    szProfileUpdateUrl +="&DetailKey="+szDetailKey[0];
     
    if(szTabKey.length >= 1)
        szProfileUpdateUrl += "&Tab="+szTabKey[0];
    else
        return;

    var szChildFormKey="";
    if(szDetailKey.length >= 3)
       szChildFormKey = szDetailKey[1];

    var szDduKey ="";
      
    if(szDetailKey.length >= 4)    
        szDduKey = szDetailKey[3];

    szProfileUpdateUrl +="&ChildForm="+szChildFormKey+"&Order="+nChildFormOrder+"&Ddu="+szDduKey;
    sendServerInstruction(szProfileUpdateUrl);
    bDroppedControl =true;
    }
    });

    $(".ProfileDeleteDiv").droppable({
    accept: '.ProfileTabContentDetail, .ProfileTitleContainerLight',
    greedy: true,
    tolerance: 'pointer',
    drop: function(event,ui){
    bDroppedControl =true;
    var szControlClass = ui.draggable.attr("class");
    var warningDiv = document.createElement('div');                        
                        warningDiv.setAttribute('title', '&nbsp;');
		                warningDiv.setAttribute('style', 'overflow: hidden; display: none');

    switch(szControlClass.toLowerCase()){
        case "profiletabcontentdetail":
              warningDiv.setAttribute('id', 'div_delete_warning_CF');
              warningDiv.innerHTML = "<div class=\"DataFormDivFormBackground black\"><B><font color=\"red\">Warning!</font></B><BR /><BR /> You are are about to delete this child form.</div>";
              document.body.appendChild(warningDiv);
              $("#div_delete_warning_CF").dialog({
                autoOpen: true,
                height: "auto",
			    width: "auto",
                modal: true,
                closeOnEscape: true,
                resizable: false,
                buttons: {
                    "Continue": function(){
                         PesonalizeDeleteChildForm(ui.draggable);
                         $(this).dialog("close");
                         ProfileRefresh();
                    },
                    "Cancel": function(){
                        $(this).dialog("close");
                         ProfileRefresh();
                    }
                }
              });
              break;
         case "profiletitlecontainerlight ui-droppable":
              warningDiv.setAttribute('id', 'div_delete_warning_TAB');
              warningDiv.innerHTML = "<div class=\"DataFormDivFormBackground black\"><B><font color=\"red\">Warning!</font></B><BR /><BR /> You are are about to delete this tab.</div>";
              document.body.appendChild(warningDiv);
              $("#div_delete_warning_TAB").dialog({
                autoOpen: true,
                height: "auto",
			    width: "auto",
                modal: true,
                closeOnEscape: true,
                resizable: false,
                buttons: {
                    "Continue": function(){
                         PesonalizeDeleteTab(ui.draggable);
                         $(this).dialog("close");
                         ProfileRefresh();
                    },
                    "Cancel": function(){
                        $(this).dialog("close");
                         ProfileRefresh();
                    }
                }
              });
              break;   

            default: 
            warningDiv.innerHTML = "<div class=\"DataFormDivFormBackground black\">Sorry, this cannot be deleted.</div>";
              warningDiv.setAttribute('id', 'div_delete_warning');
              document.body.appendChild(warningDiv);
              $("#div_delete_warning").dialog({
                autoOpen: true,
                height: "150px",
			    width: "100px",
                modal: true,
                closeOnEscape: true,
                resizable: false,
                buttons: {
                    "OK": function(){
                        $(this).dialog("close");
                         ProfileRefresh();
                    }
                }
              });
              break;   
             
    }
    }
     
    });

}

function PesonalizeDeleteChildForm(szChildForm)
{
     var oChildForm = $('table [id*="'+szChildForm+'"]');
     var szProfileUpdateUrl = "DynamicProfileHandler.ashx?Command=ChildFormOrderDelete&Tab="+($('.ProfileTabContent').attr("id").replace("TAB_",""));
     var szChildForm = oChildForm.attr("id");

      var szDetailKey = szChildForm.split(':');

      if(szDetailKey.length < 1)
          return;

      var szDpdKey = szDetailKey[0].replace("CF_", "");
      var szChildFormKey="";
      if(szDetailKey.length >= 3)
         szChildFormKey = szDetailKey[1];

      var nOrderNumber = szDetailKey[2];
      
      var szDduKey ="";
      
      if(szDetailKey.length == 4)
      {
        szDduKey = szDetailKey[3];
      } 

     szProfileUpdateUrl +="&DetailKey="+szDpdKey+"&ChildForm="+szChildFormKey+"&Order="+nOrderNumber+"&Ddu="+szDduKey;
     sendServerInstruction(szProfileUpdateUrl);

}

function PesonalizeDeleteTab(szTab,szCaption,szRow,szDpuKey)
{

    if(!Confirm('Warning! You are about to delete the '+szCaption+' Tab. Continue?'))
        return;

        var szProfileUpdateUrl = "DynamicProfileHandler.ashx?Command=TabOrderDelete";

        var szFormKey = "";
        var szRequest = window.location.search.replace("?","").split("&");
        if(szRequest == null || szRequest.length == 0 )
            return;

        for(var i = 0; i < szRequest.length; i++)
        {
            if(szRequest[i].toLowerCase().startsWith("formkey=") && szRequest[i].split("=").length > 1)
                szFormKey = szRequest[i].split("=")[1];
        }

        if(szFormKey == "")
            return;
      
        szProfileUpdateUrl += "&Form="+szFormKey+ "&Tab="+szTab+"&Caption="+szCaption+"&Order="+szRow+"&Dpu="+szDpuKey;
        
        sendServerInstruction(szProfileUpdateUrl);

}

function ResetPersonalize(szResetType,szResetKey)
{
    if(szResetType == null || szResetType == "")
        return;

    var szWarningMsg = "";
    var szKeyValue = "";

    if(szResetType == "Tabs")
    {
        szWarningMsg = "This will set all baseline tabs to their original order. Are you sure?";
        szKeyValue = "&Form="+szResetKey;
    }
    
    if(szResetType == "ChildForms")
    {
        szWarningMsg = "This will restore all child forms for the current tab by:<BR /> <UL ><LI>Removing any child forms which have been added</LI> <LI>Adding back any child forms which have been removed</LI> <LI>reverting the ordering</LI></UL><BR /> Are you sure you would like to continue?";
        szKeyValue = "&Tab="+szResetKey;
    }
    if(szResetType == "Restore") {
        szWarningMsg = "This will restore all child forms on every tab (does <b><font color=\"red\">not</font></b> include user added tabs) by:<BR /> <UL><LI>removing child forms which have been added</LI> <LI>adding back child forms which have been removed</LI> <LI>reverting the ordering back to its original order</LI></UL><BR /> This will also revert the ordering of the tabs themselves and add back any tabs which have been removed. Are you sure you would like to continue?";
        szKeyValue = "&Form="+szResetKey;
    }
    if(szResetType == "All") {
        szWarningMsg = "This will restore all child forms on every tab (including user added tabs) by:<BR /> <UL><LI>removing child forms which have been added</LI> <LI>adding back child forms which have been removed</LI> <LI>reverting the ordering back to its original order</LI></UL><BR /> This will also revert the ordering of the tabs themselves and add back any tabs which have been removed. Are you sure you would like to continue?";
        szKeyValue = "&Form="+szResetKey;
    }

    if(szWarningMsg != "")
    {

        var warningDiv = document.createElement('div');
                        warningDiv.setAttribute('id', 'div_reset_warning'+szResetType);
                        warningDiv.setAttribute('title', '&nbsp;');
		                warningDiv.setAttribute('style', 'overflow: hidden; display: none');
                        warningDiv.innerHTML = "<div class=\"DataFormDivFormBackground black\">"+szWarningMsg+"</div>";
                        document.body.appendChild(warningDiv);

       switch(szResetType) {
            case "Restore":
                $('#div_reset_warning'+szResetType).dialog({
                                autoOpen: true,
                                height: "auto",
			                    width: "600px",
                                modal: true,
                                closeOnEscape: true,
                                resizable: false,
                                buttons: {
                                    "Continue": function(){ $(this).dialog("close");
                                                        $(this).attr('style','overflow: hidden;')
                                                        var szResetUrl = "DynamicProfileHandler.ashx?Command=ResetAll&CustomTabs=false"+szKeyValue;
                                                        sendServerInstruction(szResetUrl);
                                                       ProfileRefresh();
                                                    },

                                    "Cancel": function (){ 
                                                    $(this).dialog("close");
                                                    $(this).attr('style','overflow: hidden;')
                                                    ProfileRefresh();
                                                    }

                                 }

                       });
                       break;
       case "All":
       $('#div_reset_warning'+szResetType).dialog({
                                autoOpen: true,
                                height: "auto",
			                    width: "600px",
                                modal: true,
                                closeOnEscape: true,
                                resizable: false,
                                buttons: {
                                    "Continue": function(){ $(this).dialog("close");
                                                        var szResetUrl = "DynamicProfileHandler.ashx?Command=ResetAll&CustomTabs=true"+szKeyValue;
                                                        sendServerInstruction(szResetUrl);
                                        $(this).attr('style', 'overflow: hidden;');
                                                       ProfileRefresh();
                                                     },

                                    "Cancel": function (){ 
                                                    $(this).dialog("close");
                                        $(this).attr('style', 'overflow: hidden;');
                                                    ProfileRefresh();
                                                    }

                                 }

                       });
                       break;
       default:
           $('#div_reset_warning'+szResetType).dialog({
                autoOpen: true,
                height: "auto",
			    width: "600px",
                modal: true,
                closeOnEscape: true,
                resizable: false,
                buttons: {
                    "Continue": function (){
                        
                            var szResetUrl = "DynamicProfileHandler.ashx?Command=Reset" + szResetType + szKeyValue;
                            sendServerInstruction(szResetUrl);
                        $(this).attr('style', 'overflow: hidden;');
                                           ProfileRefresh();
                        
                         
                       $(this).dialog("close");
                    },
                    "Cancel": function (){ 
                        $(this).dialog("close");
                        $(this).attr('style', 'overflow: hidden;');
                          ProfileRefresh();
                    }
                }
                 
           });
           break;
           }
           
                                          
             
                   
                        
    }
        
}

function sendServerInstruction(url)
{
var xmlhttp;
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }

xmlhttp.open("GET", url, true);
xmlhttp.send();
}

function PrepareTooltip(szId,szEffect,szPosition,szPreDelay,szDirection,nOffsetX, nOffsetY, szShowEvent)
{
    $(szId).tipsy({
    customMenu: true,
    effect : szEffect,
    speed: szPreDelay,
    direction: szDirection,
    horizontalMargin: nOffsetX,
    verticalMargin: nOffsetY,
    showEvent: szShowEvent
    });
}

function ProfileRefresh(){
 try{
      WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions("XXXXXXXX", "", true, "", "", false, true));
    }
catch(ex)
    {
      __doPostBack('','');
    }
}


//AAG NS:2590 logic for form submit
function InitializeFormSubmitBehavior(szDefaultButtonId)
{
    $('input[type="text"]').keydown(function(e){
        if($(this).attr('onkeydown') != null && $(this).attr('onkeydown') != "" && $(this).attr('onkeydown') != 'undefind')
            return true;
        else
        {
            if((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13))
            {
                $('#' + szDefaultButtonId).click();
                return false;
            }
        }
      
    });
} 
//AAG NS:944 preventing empty popup windows when files are downloaded
function OpenNewFile(szFilePath) {
    
    if(szFilePath == null || szFilePath == "")
        return;
    
    if(oFileIframe == null) {
        oFileIframe = document.createElement("iframe");
        oFileIframe.setAttribute('id', 'FileDownloadFrame');
        oFileIframe.setAttribute('name', 'FileDownLoadFrame');
        oFileIframe.setAttribute('style', 'display:none;padding:0;margin:0;');
        oFileIframe.setAttribute('scrolling', 'no');
        oFileIframe.setAttribute('frameBorder', '0');
        oFileIframe.setAttribute('src', szFilePath);
        document.body.appendChild(oFileIframe);
    }
    else {
        oFileIframe.setAttribute('src', szFilePath);
    }
    
}

function ExpandableDialogShow(oExpandLink) {

   
    var oExpandable = $(oExpandLink).parent().children(".expand-div");
    if (oExpandable.length >= 1) {
        ExpandableDialogHide();
        oExpandable.appendTo(top.document.body);
        oExpandable.addClass('expand-active');
        $(oExpandable.css({ position: 'fixed', top: 100, left: 100, width: 'auto', height: 'auto', zIndex: 999999, display: 'inline-block' })).draggable({
            containment: false,
            dragStart: function (event, ui) {
                $('iframe', this).each(function () {
                    $('<div class="ui-draggable-iframeFix" style="background: #FFF;"></div>').css({
                        width: '100%',
                        height: '100%',
                        position: 'absolute',
                        opacity: '0',
                        zIndex: 1000,
                        overflowX: 'hidden'
                    }).appendTo($(this).offsetParent());
                });
            },
            dragStop: function (event, ui) {
                $("div.ui-draggable-iframeFix").each(function () {
                    this.parentNode.removeChild(this);
                }); //Remove frame helpers
            }
        }).resizable();
        oExpandable.find('div').css('display', '').attr('unselectable', 'off');

    }
    else {
        oExpandable = $(oExpandLink).parent().children(".expand-auto");

        if (oExpandable.length == 1) {
            if (oExpandable.is(":hidden"))
                oExpandable.show();
            else
                oExpandable.hide();

        }
    }
}

function ExpandableDialogHide(bDoPostBack) {

    var oExpandDropdown = $('.expand-auto');
    if (oExpandDropdown.length > 0) {
        oExpandDropdown.hide();

        if (bDoPostBack)
            __doPostBack(oExpandDropdown.attr('id'), '');
    }

    


    var oContainerDiv = $('.expand-active');
    if (oContainerDiv.length >= 1) {
        var szContainerId = oContainerDiv.attr('id');
        if (szContainerId == null || szContainerId.split("_").length < 2)
            return;
        var szExpandNo = szContainerId.split("_")[1];

        var oOriginalBody = document.body;

        if (aDialogs != null && aDialogs.length >= 1)
            oOriginalBody = $('#div' + aDialogs[(aDialogs.length - 1)]).find('iframe')[0].contentDocument.body;

        oOriginalContainer = $(oOriginalBody).find('#ExpandDiv_' + szExpandNo);
        if (oOriginalContainer != null) {
            oContainerDiv.appendTo(oOriginalContainer);
            oContainerDiv.css('display', 'none');
            var oNoPostControl = oContainerDiv.find('.no-post-back');
            if (bDoPostBack && oNoPostControl.length == 0)
                __doPostBack('ExpandDiv_' + szExpandNo, '');
        }
    }

    
}
//12304 - if a wizard button is clicked, the other submit buttons should be disabled
function disableSubmitButtons() {
    var inputFields = document.getElementsByTagName('input');

    for (var i = 0; i < inputFields.length; i++) {
        if (inputFields[i].type == 'submit') {
            inputFields[i].disabled = 'disabled';
        }
    }

}