/// <reference path="_references.js" />

// ----- utility functions:

var mouse_inside_slider = false;
var row_clicked = false;
var mouse_inside_overflow = false;

function GetTop(element) {
    //AAG NS:4697 - fixing positioning of relative links
    if (element != null) {
        return ($(element).position().top + ($(element).css("position") == null || ($(element).css("position").toLowerCase() != "absolute" && $(element).css("position").toLowerCase() != "static") ? 0 : GetTop(element.offsetParent)));
    }
 /*{ return element.offsetTop + GetTop(element.offsetParent); }*/
    return 0;
}

function GetLeft(element) {
    //AAG NS:4697 -fixing positioning of relative links
    if (element != null) {
        return ($(element).position().left + ($(element).css("position") == null || ($(element).css("position").toLowerCase() != "absolute" && $(element).css("position").toLowerCase() != "static") ? 0 : GetLeft(element.offsetParent)));
    }
    /*{ return element.offsetLeft + GetLeft(element.offsetParent); }*/
    return 0;
}

function FindControl(id) {
    if (id != null && id != "") { return document.getElementById(id); }
    return null;
}

function FindControls(tagName) {
    if (tagName != null && tagName != "") { return document.getElementsByTagName(tagName); }
    return null;
}

function HideControls(tagName, partialId) {
    if (tagName != null && tagName != "") {
        var a = FindControls(tagName.toUpperCase());
        for (var i = 0; i < a.length; i++) {
            if (partialId != null && partialId != "" && a[i].id != null && a[i].id.indexOf(partialId) >= 0) { a[i].style.display = "none"; }
            else if (partialId == null || partialId == "") { a[i].style.display = "none"; }
        }
    }
}

function GotoFavorite(url, defaultName) {
    var e = FindControl("__APPLICATIONPATH");
    if (e != null) {
        var appPath = e.value;
        if (url.toLowerCase() == "__addfavorite") {
            var url = document.location.href;
            url = url.replace(document.location.protocol + '//', ''); //RJ, bug# 8896, error when client has url similar to http://iweb.client.com/iweb
            url = url.substring(url.toLowerCase().indexOf(appPath.toLowerCase()) + appPath.length);
            if (defaultName != null)
                OpenNewWindow(appPath + "/forms/DynamicEditModal.aspx?Action=Add&FormKey=73045ddb-956f-4dd3-bbef-fc28bda182ae&fav_url=" + escape(url) + "&fav_name=" + defaultName);
            else
                OpenNewWindow(appPath + "/forms/DynamicEditModal.aspx?Action=Add&FormKey=73045ddb-956f-4dd3-bbef-fc28bda182ae&fav_url=" + escape(url));
        }
        else if (url != "" && url.toLowerCase() != "javascript:void();" && url.toLowerCase() != "javascript:void(0);") { document.location.href = appPath + url; }
    }
    else { alert("An error occured.\n\nError: \"ApplicationPath\" can not be evaluated.\n\nPlease refresh the page and try again."); }
}

function expandCollapse(divId) {
    var e = FindControl("__APPLICATIONPATH");
    if (e != null) {
        var appPath = e.value;
        var div = FindControl(divId);
        if (div != null) {
            if (event.srcElement != null && event.srcElement.tagName.toLowerCase() == "img") { event.srcElement.src = appPath + "/images/" + ((div.style.display == "inline") ? "expand1.gif" : "collapse1.gif"); }
            div.style.display = (div.style.display == "inline") ? "none" : "inline";
        }
    }
    else { alert("An error occured.\n\nError: \"ApplicationPath\" can not be evaluated.\n\nPlease refresh the page and try again."); }
}

function gotoUrl(myselect) {
    if (myselect != null && myselect.value != "") { window.location.assign(myselect.value); }
}

function gotoUrlNew(myselect) {
    if (myselect != null && myselect.value != "") {
        openDialogUrl(screen.width / 2, screen.height / 2, myselect);
        myselect.value = "";
    }
}

function openDialogUrl(width, height, myselect) {
    if (myselect != null && myselect.value != "") {
        DialogWindow = null;
        DialogWindow = new Object();
        if (!DialogWindow.win || (DialogWindow.win && DialogWindow.win.closed)) {
            DialogWindow.url = myselect.value;
            DialogWindow.width = width;
            DialogWindow.height = height;
            DialogWindow.name = (new Date()).getSeconds().toString();
            DialogWindow.left = (screen.width - width) / 2;
            DialogWindow.top = (screen.height - height) / 2;
            var attr = "left=" + DialogWindow.left + ",top=" + DialogWindow.top + ",resizable=yes,status=yes,scrollbars=yes,width=" + DialogWindow.width + ",height=" + DialogWindow.height;
            DialogWindow.win = window.open(DialogWindow.url, DialogWindow.name, attr);
        }
        DialogWindow.win.focus();
    }
}

function noDialogWindowIsOpen() {
    try { return (!window.DialogWindow || DialogWindow == null || DialogWindow.win == null || DialogWindow.win.closed); }
    catch (ex) { return false; }
}

function PostBackPersonas(eventTarget, eventArgument) {
    var et = FindControl("__EVENTTARGET");
    var ea = FindControl("__EVENTARGUMENT");
    if (et != null && ea != null) {
        et.value = eventTarget;
        ea.value = eventArgument;
        document.forms[0].submit();
    }
}

// ----- shadow boxes:

function CreateShadowBox(id, l, t, w, h) {
    var menu = FindControl(id);
    if (menu != null && menu.style.display != "none" && menu.offsetWidth > 1 && menu.offsetHeight > 1) {
        var img = FindControl(id + "__ShadowBox");
        if (img == null) {
            img = document.createElement("IMG");
            img.id = id + "__ShadowBox";
            img.className = "menuShadow";
            img.src = "";
            img.border = 0;
            img.style.position = "absolute";
            img.style.display = "none";
            if (menu.style.zIndex == 0) { img.style.zIndex = 59999; }
            else { img.style.zIndex = menu.style.zIndex - 1; }
            document.body.appendChild(img);
        }
        img.style.left = l + 3;
        img.style.top = t + 3;
        img.style.width = w;
        img.style.height = h;
        img.style.clip = "rect(auto auto auto auto)";
        img.style.display = "block";
    }
}

function ResizeShadowBox(id, l, t, w, h, bClip) {
    var img = FindControl(id + "__ShadowBox");
    if (img != null) {
        img.style.left = l + 3;
        img.style.top = t + 3;
        img.style.width = w;
        img.style.height = h;
        if (bClip) { img.style.clip = "rect(" + (54 - t) + "," + w + "," + h + ",0)"; }
    }
}

function RemoveShadowBox(id) {
    if (id != null && id != "") {
        if (id == "all") { HideControls("IMG", "__ShadowBox"); }
        else {
            var img = FindControl(id + "__ShadowBox");
            if (img != null) { img.style.display = "none"; }
            // else { RemoveShadowBox("all"); }
        }
    }
    // else { RemoveShadowBox("all"); }
}

// ----- context (popup) menu:

var bContextMenuDelay = false;
var nContextMenuDelayTime = 0;
var nContextMenuTimerId = null;
var sLastContextMenuId = "";
var sLastContextMenuParentLinkId = "";
var bContextMenuOpenToRight = true;
var bContextMenuOpenToBottom = true;

function ShowHideContextMenu(element, url, id, delayTime, widthAdj, heightAdj, openToRight, openToBottom, isParent) {
    if (noDialogWindowIsOpen()) {
        var menu = null;
        if ((id == null || id == "") && sLastContextMenuId != "") { id = sLastContextMenuId; }
        if (id != "") { menu = FindControl(id); }
        if (menu != null) {
            if (menu.style.display == "none") { ShowContextMenu(element, url, id, delayTime, widthAdj, heightAdj, openToRight, openToBottom, isParent); }
            else if (menu.style.display == "block") { CloseContextMenu(id); }
        }
    }
}

function ShowContextMenu(element, url, id, delayTime, widthAdj, heightAdj, openToRight, openToBottom, isParent) {
    if (noDialogWindowIsOpen()) {
        var menu = null;
        if ((id == null || id == "") && sLastContextMenuId != "") { id = sLastContextMenuId; }
        if (id != "") { menu = FindControl(id); }
        if (element == null && sLastContextMenuParentLinkId != "") { element = FindControl(sLastContextMenuParentLinkId); }
        if (menu != null && element != null && element.id != null && element.id != "") {
            ContextMenuStopDelay();
            if (sLastContextMenuId != "" && sLastContextMenuId != id) { CloseContextMenu(sLastContextMenuId); }
            if (delayTime != null && delayTime != "") { nContextMenuDelayTime = parseInt(delayTime); }
            if (isParent != null && isParent == true) {
                url += ((url.indexOf("?") < 0) ? "?" : "&") + "WidthAdj=" + widthAdj + "&HeightAdj=" + heightAdj;
                var bParentIdSet = false;
                if (openToRight == null) { openToRight = bContextMenuOpenToRight; }
                if (openToRight != null && openToRight == false) {
                    url += ((url.indexOf("?") < 0) ? "?" : "&") + "MoveToLeft=1&ParentId=" + element.id;
                    bContextMenuOpenToRight = false;
                    bParentIdSet = true;
                }
                else { bContextMenuOpenToRight = true; }
                if (openToBottom == null) { openToBottom = bContextMenuOpenToBottom; }
                if (openToBottom != null && openToBottom == false) {
                    url += ((url.indexOf("?") < 0) ? "?" : "&") + "MoveToTop=1";
                    if (!bParentIdSet) { url += "&ParentId=" + element.id; }
                    bContextMenuOpenToBottom = false;
                }
                else { bContextMenuOpenToBottom = true; }
                menu.style.display = "block";
                if (menu.tagName.toLowerCase() == "iframe") {
                    if (url != null && url != "" && (menu.src.toLowerCase() != url.toLowerCase()
			            || (menu.style.width == "" || menu.style.width == "0px" || menu.style.width == "1px"
			            || menu.style.height == "" || menu.style.height == "0px" || menu.style.height == "1px"))) {
                        menu.src = url;
                        if (url.toLowerCase().indexOf("&noresize=1") < 0) {
                            menu.style.width = "1px";
                            menu.style.height = "1px";
                            PositionContextMenu(menu, element, widthAdj, heightAdj);
                        }
                        else {
                            menu.style.width = menu.offsetWidth + "px";
                            menu.style.height = menu.offsetHeight + "px";
                        }
                    }
                    else if (menu.contentWindow && menu.contentWindow != null
			            && menu.contentWindow.document != null && menu.contentWindow.document.forms[0] != null) {
                        if (navigator.userAgent.toLowerCase().indexOf("msie") > 0) {
                            if (menu.offsetWidth != menu.contentWindow.document.forms[0].scrollWidth
			                    || menu.offsetHeight != menu.contentWindow.document.forms[0].scrollHeight) {
                                menu.style.width = menu.contentWindow.document.forms[0].scrollWidth + "px";
                                menu.style.height = menu.contentWindow.document.forms[0].scrollHeight + "px";
                                PositionContextMenu(menu, element, widthAdj, heightAdj);
                            }
                        }
                        else {
                            if (menu.offsetWidth != menu.contentWindow.document.forms[0].offsetWidth
			                    || menu.offsetHeight != menu.contentWindow.document.forms[0].offsetHeight) {
                                menu.style.width = menu.contentWindow.document.forms[0].offsetWidth + "px";
                                menu.style.height = menu.contentWindow.document.forms[0].offsetHeight + "px";
                                PositionContextMenu(menu, element, widthAdj, heightAdj);
                            }
                        }
                    }
                }
                else {
                    if (menu.style.width == "") { menu.style.width = menu.offsetWidth + "px"; }
                    if (menu.style.height == "") { menu.style.height = menu.offsetHeight + "px"; }
                    if (url.toLowerCase().indexOf("noresize=1") < 0) { PositionContextMenu(menu, element, widthAdj, heightAdj); }
                }
                // CreateShadowBox(id, GetLeft(menu), GetTop(menu), parseInt(menu.style.width), parseInt(menu.style.height));
            }
            sLastContextMenuId = id;
            sLastContextMenuParentLinkId = element.id;
        }
    }
}

function PositionContextMenu(menu, element, widthAdj, heightAdj) {
    if (menu != null && element != null) {
        if (widthAdj == null || widthAdj == "") { widthAdj = 0; }
        if (heightAdj == null || heightAdj == "") { heightAdj = 0; }
        if (!bContextMenuOpenToRight) { menu.style.left = (GetLeft(element) + parseInt(widthAdj) + element.offsetWidth - parseInt(menu.style.width)) + "px"; }
        else { menu.style.left = (GetLeft(element) + parseInt(widthAdj)) + "px"; }
        if (!bContextMenuOpenToBottom) { menu.style.top = (GetTop(element) + parseInt(heightAdj) - parseInt(menu.style.height)) + "px"; }
        else { menu.style.top = (GetTop(element) + parseInt(heightAdj) + element.offsetHeight) + "px"; }
        if (GetTop(menu) < 0) { menu.style.top = "0px"; }
        if (GetLeft(menu) < 0) { menu.style.left = "0px"; }
        if (GetTop(menu) + menu.offsetHeight > document.body.offsetHeight - 25) { menu.style.top = (document.body.offsetHeight - menu.offsetHeight - 25).toString() + "px"; }
        if (GetLeft(menu) + menu.offsetWidth > document.body.offsetWidth - 25) { menu.style.left = (document.body.offsetWidth - menu.offsetWidth - 25).toString() + "px"; }
    }
}

function HideContextMenu(id) {
    ContextMenuStartDelay(id);
}

function CloseContextMenu(id) {
    if (id != null && id.toLowerCase() == "all") {
        //RemoveShadowBox(id);
        HideControls("IFRAME", "menu_");
        HideControls("DIV", "menu_");
    }
    else {
        var menu = null;
        if ((id == null || id == "") && sLastContextMenuId != "") { id = sLastContextMenuId; }
        if (id != null && id != "") { menu = FindControl(id); }
        if (menu != null) {
            if (menu.contentWindow && menu.contentWindow != null && menu.contentWindow.CloseContextMenu) { menu.contentWindow.CloseContextMenu("all"); }
            //   RemoveShadowBox(id);
            menu.style.display = "none";
        }
    }
}

function ContextMenuOnKeyPress(element, url, id, delayTime, widthAdj, heightAdj, openToRight, openToBottom, isParent) {
    var c = window.event ? event.keyCode : evt.which;
    if (c != null && c == 32) {
        var menu = null;
        if (id != null && id != "") { menu = FindControl(id); }
        if (menu != null) {

            if (id != sLastContextMenuId) { CloseContextMenu(sLastContextMenuId); }
            if (menu.style.display == "none") { ShowContextMenu(element, url, id, delayTime, widthAdj, heightAdj, openToRight, openToBottom, isParent); }
            else if (menu.style.display == "block") { CloseContextMenu(id); }
        }
    }
}

function ContextMenuStartDelay(id) {
    if (id == null || id == "") { id = sLastContextMenuId; }
    if (id != "") {
        if (bContextMenuDelay == true) {
            ContextMenuStopDelay();
            CloseContextMenu(id);
        }
        else {
            bContextMenuDelay = true;
            nContextMenuTimerId = setTimeout("ContextMenuStartDelay('" + id + "');", nContextMenuDelayTime);
        }
    }
}

function ContextMenuStopDelay() {
    bContextMenuDelay = false;
    clearTimeout(nContextMenuTimerId);
}

//JS for the top menu
/*$(document).ready(*/function TopMenuDisplay(szDelay, szEffect,szShowEvent) {
    /*
    $("#moduleItemMenuBox a[name]").tooltip({ effect: szEffect,
    position: 'bottom left',
    slideOffset: 0,
    predelay: szDelay,
    slideFade: true,
    direction: 'down',
    offset: [-28, 149],
    def: "mouseover,mouseout",
    tooltip: "mouseover,mouseout"
    });
    
    $("#helpButtonBox a[name]").tooltip({ effect: szEffect,
    position: 'bottom left',
    slideOffset: 0,
    predelay: szDelay,
    slideFade: true,
    direction: 'down',
    offset: [0, 149],
    def: "mouseover,mouseout",
    tooltip: "mouseover,mouseout"
    });
    */
    //$("#moduleItemMenuBox a[name]").tipsy({ customMenu: true, effect: szEffect, horizontalMargin: -25, verticalMargin: -25, speed: szDelay, showEvent: szShowEvent });
    //$("#HelpMenuLink").tipsy({ customMenu: true, effect: szEffect, horizontalMargin: 0, verticalMargin: 0, speed: szDelay, showEvent: szShowEvent });
    //$("#UserRoleLink").tipsy({ customMenu: true, effect: szEffect, horizontalMargin: -400, verticalMargin: 0, speed: szDelay, showEvent: szShowEvent });
    //$("td.TopUIItemMenuCell a").tipsy({ customMenu: true, effect: szEffect, horizontalMargin: -800, verticalMargin: -75, speed: szDelay, showEvent: szShowEvent });
    //$(".dropdown-toggle").tipsy({ customMenu: true, effect: szEffect, horizontalMargin: -800, verticalMargin: 0, speed: szDelay, showEvent: szShowEvent });
    // $(".dropdowntoggleright").tipsy({ customMenu: true, effect: szEffect, horizontalMargin: 0, verticalMargin: 0, speed: szDelay, showEvent: szShowEvent });
    $('.dropdown-menu .nocloseonclick').click(function (e) { e.stopPropagation(); });
    
    

} //);

$(function () {
    hideleftNav();
    if ($("#accordion").length > 0) {
        var itemKey = getQuerystring('ItemKey');
        if (itemKey == null || itemKey == '' || itemKey == 'undefined') {
            $("#accordion").accordion({ collapsible: true, icons: false, heightStyle: "content", active: 0 });
            $("#accordion").attr("style", "display:block;");
        } else {
            $("#accordion").accordion({ collapsible: true, icons: false, heightStyle: "content", active: "h3[id='" + itemKey + "']" });
            $("#accordion").attr("style", "display:block;");

            if ($(".antiscroll-inner").length && $("h3[id='" + itemKey + "']").offset() != null && $("h3[id='" + itemKey + "']").offset() != 'undefined')
                $(".antiscroll-inner").animate({ scrollTop: $("h3[id='" + itemKey + "']").offset().top - $(".antiscroll-inner").offset().top + $(".antiscroll-inner").scrollTop() });
        }
    }
    if ($("[rel='tooltip']").length > 0)
        $("[rel='tooltip']").tooltip();
    setBreadcrumbs();
    
    //set focus for supersearch textbox
    //$('#searchAnchor').click(function () { $('#SuperSearchField').focus(); });
});

function hideleftNav() {
    
    var collapseleftui = getQuerystring('CollapseLeftUI');
    if (collapseleftui != null && collapseleftui != 'undefined' && collapseleftui == 'yes') {
        $('body').addClass('sidebar_hidden');
    
    }
}

function setBreadcrumbs()
{
    try {
        //get module link from modulelist
        var moduleLnk = $('#breadCrumb #bcModule a');
        var moduleValue = moduleLnk.attr('title');
        var modHref = $("#moduleList li a").filter(function () {
            return $(this).text() === '' + moduleValue + '';
        }).attr('href');
        if(modHref!=null && modHref!='undefined')
            moduleLnk.attr('href', modHref);

        //get group item link
        var bcgrpItemLnk = $('#breadCrumb #bcGroupItem a');
        var grpItemValue = bcgrpItemLnk.attr('title');
        var grpItemHeader = $("#accordion h3").filter(function () {
            grpContextValue = $(this).html().replace(/&nbsp;/g, ' ');
            return grpContextValue === grpItemValue;
            //return $(this).text() === '' + grpItemValue + '';            
        });
        //get first group item link
        var grpItemHref = grpItemHeader.next('div').find('li a').attr('href');
        if (bcgrpItemLnk.length && grpItemHref != null && grpItemHref != 'undefined') {
            bcgrpItemLnk.attr('href', grpItemHref);
        }
        
        //alert(grpItemLink);

    } catch(e) {

        //TODO: remove hyperlink
    } 
    
}


//JS for the left side menu
/*$(document).ready(*/function LeftSlideMenuDisplay(szDelay, szEffect, szShowEvent) {
    //$("#leftSidebar img[name]").tooltip({ effect: szEffect, tipClass: "tooltip2", position: 'bottom', slideOffset: 0, predelay: szDelay, slideFade: true, direction: 'right', offset: [-206, 0] });
    //$("#leftSidebar img[name]").tipsy({ customMenu: true, effect: szEffect, horizontalMargin: 0, verticalMargin: -75, direction: 'right', speed: szDelay, showEvent: szShowEvent });
} //);

//JS for the plus sign expand menu in the top right corner
/*$(*/function ExpandModuleMenuButtonDisplay() {
    $("#moduleItemMenuButtonBox img").click(function () {
        if ($('.tooltipTopUIItemsMenuTable').is(":hidden")) {
            $('.tooltipTopUIItemsMenuTable').show();
            $(this).addClass("activeImage");
            if (bConfirmNavAway != null) bConfirmNavAway = 0;
        }
        else {
            $('.tooltipTopUIItemsMenuTable').hide();
            $(this).removeClass("activeImage");
            if (bConfirmNavAway != null) 
                bConfirmNavAway = 0;
        }
    }, function () {
        $('.tooltipTopUIItemsMenuTable').hide();
    });
    //AAG NS:2490 NF 2011 10/19/2011 
    $('.tooltipTopUIItemsMenuTable').hover(function() {
        mouse_inside_overflow = true;
    },
        function() {
            mouse_inside_overflow = false;
        }
    );

        $("body").mouseup(function () {
            if (!mouse_inside_overflow && !$('.tooltipTopUIItemsMenuTable').is(":hidden")) {
                $('.tooltipTopUIItemsMenuTable').hide();
                $('#moduleItemMenuButtonBox img').removeClass("activeImage");
                if (bConfirmNavAway != null) 
                    bConfirmNavAway = 0;    
            }
        
    });
} //);


/*$(*/function HideDisplayTopDiv() {    
    //$("td.TopUIItemMenuCell").click(function () {

    //    if ($('div:first', this).is(":hidden"))
    //    { $('div:first', this).show('fast'); if (bConfirmNavAway != null) bConfirmNavAway = 0; }
    //    else {
    //        if (bConfirmNavAway != null && !$(this).is('a')) bConfirmNavAway = 0;
    //        $('div:first', this).hide('fast');
    //    }
    //}, function () {
    //    $('div:first', this).hide(); if (bConfirmNavAway != null) bConfirmNavAway = 0;
    //});
   $("td.TopUIItemMenuCell:has(div)").find("a:first").append("&nbsp;&raquo; ");
} //);

//JS for the top module sliding menu
/*$(document).ready(*/function TopMenuActionHide() {
$(".topMenuAction").click(function () {
    if ($("#openCloseIdentifier").is(":hidden")) {
        /* AAG if Using standards mode in IE9 and 4px difference nolonger exists comment out if protion below 
        if (window.navigator.appName == "Microsoft Internet Explorer") {
        $("#slider").animate({

        marginTop: (($("#slider").height() + 4) * -1)
        }, 500);
        }
        else {
        */
        //AAG NS:4766 need to code for XP version of IE8
        if ($.browser.msie && $.browser.version == "8.0")
            $("#slider").css("marginTop", (($("#slider").height()) * -1));
        else
            $("#slider").animate({

                marginTop: (($("#slider").height()) * -1)
            }, 500);
        // }
        $("#openCloseIdentifier").show();
        //setTimeout("$(\"#sliderWrap\").css(\'zIndex\', 1);", 500);
    }
    else {
        //$("#sliderWrap").css('zIndex', 9000);
        //AAG NS:4766 need to code for XP version of IE8
        if ($.browser.msie && $.browser.version == "8.0")
            $("#slider").css("marginTop", "0px");
        else
            $("#slider").animate({
                    marginTop: "0px"
                }, 500);
        $("#openCloseIdentifier").hide();
    }
});
            //AAG BUG# 12635 netFORUM 2010.01.01 11/29/2010  
            $('#slider').hover(function () {
            mouse_inside_slider = true;
            },
            function () {
            mouse_inside_slider = false;
            });
            $("body").mouseup(function () {

                if (!mouse_inside_slider) {
                    /* AAG if Using standards mode in IE9 and 4px difference nolonger exists comment out if protion below 
                    if (window.navigator.appName == "Microsoft Internet Explorer") {
                    $("#slider").animate({

                    marginTop: (($("#slider").height() + 4) * -1)
                    }, 500);
                    }
                    else {
                    */
                    //AAG NS:4766 need to code for XP version of IE8
                    if ($.browser.msie && $.browser.version == "8.0")
                        $("#slider").css("marginTop", (($("#slider").height()) * -1));
                    else
                        $("#slider").animate({

                                marginTop: (($("#slider").height()) * -1)
                            }, 500);
                    $(".topMenuAction").removeClass("topMenuActionActive");
                    // }
                    $("#openCloseIdentifier").show();
                }
            });


} //);


/*$(document).ready(*/function TopMenuActionDisplay() {
    $(".topMenuAction").click(function () {
        $(this).toggleClass("topMenuActionActive");
        return false;
    });
} //);

function getQuerystring(key) {
    var query = window.location.search.substring(1);
    //alert(query);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0].toLowerCase() == key.toLowerCase()) {
            return pair[1];
        }
    }
}

function ShowProfileLinks(szDelay, szEffect, szShowEvent) {
    //$("#ProfileHeaderRow a[name]").tooltip({ effect: szEffect, position: 'bottom center', slideOffset: 0, predelay: szDelay, slideFade: true, direction: 'down', offset: [-8, -70] });
    //$(".AlternateProfileRow a[name]").tooltip({ effect: szEffect, position: 'bottom center', slideOffset: 0, predelay: szDelay, slideFade: true, direction: 'down', offset: [0, 76] });
    //$(".TopUIFormLinksLink").tooltip({ effect: 'slide', tipClass: "tooltip", position: 'bottom', slideOffset: 0, predelay: szDelay, slideFade: true, direction: 'right', offset: [0, -30] });
    var nFormHeight = 0;
    var nLinkHeight = 0;
         
    if ($(".ProfileRightDiv").children('a').length > 0) {
        if ($('.DesignedTABLE').attr('height') != null && $('.DesignedTABLE').attr('height').replace('px', '') > 0) {
            nFormHeight = $('.DesignedTABLE').attr('height').replace('px', '');
            if ($('.ProfileRightDiv').children('.FormLinkTip').children('.TopUIFormLinksMenuTable').length > 0)
                $('.ProfileRightDiv').children('.FormLinkTip').children('.TopUIFormLinksMenuTable').css('height', nFormHeight + 'px');

            nLinkHeight = parseInt(nFormHeight / 2)-2;

            if ($('.SlideMenuHeaderDiv').length > 0)
                $('.SlideMenuHeaderDiv').css('height', nLinkHeight + 'px');
            if ($(".ProfileRightDiv").children('a').length > 0)
                $(".ProfileRightDiv").children('a').css('height', nLinkHeight + 'px');
        }//AAG NS:2324 Table layout for Profile Form 
        else if ($('.DesignedTABLE').attr('clientHeight') != null && $('.DesignedTABLE').attr('clientHeight') > 0) {
            nFormHeight = $('.DesignedTABLE').attr('clientHeight');
            if ($('.ProfileRightDiv').children('.FormLinkTip').children('.TopUIFormLinksMenuTable').length > 0)
                $('.ProfileRightDiv').children('.FormLinkTip').children('.TopUIFormLinksMenuTable').css('height', nFormHeight + 'px');

            nLinkHeight = parseInt(nFormHeight / 2) - 2;

            if ($('.SlideMenuHeaderDiv').length > 0)
                $('.SlideMenuHeaderDiv').css('height', nLinkHeight + 'px');
            if ($(".ProfileRightDiv").children('a').length > 0)
                $(".ProfileRightDiv").children('a').css('height', nLinkHeight + 'px');
        }


        var nVerticalMargin = -5;
        $(".ProfileRightDiv .TopUIFormLinksLink").tipsy({ customMenu: true, effect: szEffect, horizontalMargin: -25, verticalMargin: nVerticalMargin, direction: 'right', speed: szDelay, ensureInScreen: false, zIndex: '300', adjustListWidth: true, showEvent: szShowEvent }); //showMoreHeight: nFormHeight
        if ($(".ProfileRightDiv .TopUIFormLinksLink").length > 0) {
            nVerticalMargin = (nLinkHeight * -1) + nVerticalMargin -1;
            if ($('#ReportLinksHeader').length > 0)
                $('#ReportLinksHeader').css('margin-top', nLinkHeight + 'px');
        }
        $(".ProfileRightDiv .TopUIFormReportsLink").tipsy({ customMenu: true, effect: szEffect, horizontalMargin: -25, verticalMargin: nVerticalMargin, direction: 'right', speed: szDelay, ensureInScreen: false, zIndex: '300', adjustListWidth: true, showEvent: szShowEvent }); //, showMoreHeight: nFormHeight
        //$(".SlideMenuHeaderDiv a[data-menu]").tipsy({ customMenu: true, effect: 'toggle', horizontalMargin: -30, verticalMargin: -5, direction: 'right', speed: 0, showEvent: 'click', ensureInScreen: false, showMoreHeight: $('.DesignedTABLE').attr('height').replace('px', ''), zIndex: '300', });
      /*  if (GetCookie('NetForumMenus', 'FormLinks') == "true") {
            $(window).load(function () {
                $(".TopUIFormLinksLink").click();
                $(".FormLinkTip .MenuPin").addClass("Pinned");
            });
        }
    */
    }
    if ($("#ProfileHeaderRow a[name]").length > 0)
        $("#ProfileHeaderRow a[name]").tipsy({ customMenu: true, effect: szEffect, horizontalMargin: 0, verticalMargin: 0, speed: szDelay, showEvent: szShowEvent });
}

function ShowListPageLinks(szDelay, szEffect, szShowEvent) {
    //$("#ListHeaderRow a[name]").tooltip({ effect: szEffect, position: 'bottom center', slideOffset: 0, predelay: szDelay, slideFade: true, direction: 'down', offset: [-8, -70] });
    //$("#ListHeaderRow a[name]").tipsy({ customMenu: true, effect: szEffect, horizontalMargin: 0, verticalMargin: 0, speed: szDelay, showEvent: szShowEvent });
}

/*
function ShowMoreMenu(szDelay, szEffect) {
    $(".ProfileTabTable img[name='more']").tooltip({ effect: szEffect, position: 'top right', slideOffset: 0, predelay: szDelay, slideFade: true, direction: 'right', offset: [10, -4] });
}
*/
/*BUG 12961 02/14/2011 Row Click Replacement for all browsers. courtesy or TL@AJJ.  Added by AAG*/

function InitializeRowClick() {
    if (window.location.href.toLowerCase().indexOf("formkey=3cf31c16-bf0f-46ee-835f-7fc4f9f1da10") >= 0) //AAG NS:2324 ignore the form designer
        return;
    if (window.location.href.toLowerCase().indexOf('md_dynamic_designer.aspx') >= 0) //AAG NS:2324 ignore the form designer
        return; 
    // These functions replace all instances of TableRowOver.htc //
    $(".DataFormListTdDataGrid tr").live("mouseover", function () {
        if ($(this).hasClass('DataFormListTDDataGridHeader') || $(this).hasClass('DataFormListTDDataGridPager') || $(this).hasClass('DataFormListTDDataFormFooterTR')) {
            $(this).die("mouseover");
        }
        else {
            $(this).children().css('color', 'blue');
            $(this).children().css('background-color', 'white');
            $(this).children().css('cursor', 'pointer');
        }
    });
    $(".DataFormListTdDataGrid tr").live("mouseout", function () {
        if ($(this).hasClass('DataFormListTDDataGridHeader') || $(this).hasClass('DataFormListTDDataGridPager') || $(this).hasClass('DataFormListTDDataFormFooterTR')) {
            $(this).die("mouseout");
        }
        else {
            $(this).children().css('color', '');
            $(this).children().css('background-color', '');
            $(this).children().css('cursor', '');
        }
    });
    $(".DataFormChildDataGrid tr").live("mouseover", function () {
        if ($(this).hasClass('DataFormChildDataGridHeader') || $(this).hasClass('DataFormChildDataGridPager') || $(this).hasClass('DataFormChildDataGridFooterTR') || $(this).find('td img:first').attr('onclick') == null) {
            $(this).die("mouseover");
        }
        else {
            $(this).children().css('color', 'blue');
            $(this).children().css('background-color', 'white');
            $(this).children().css('cursor', 'pointer');
        }
    });
    $(".DataFormChildDataGrid tr").live("mouseout", function () {
        if ($(this).hasClass('DataFormChildDataGridHeader') || $(this).hasClass('DataFormChildDataGridPager') || $(this).hasClass('DataFormChildDataGridFooterTR')) {
            $(this).die("mouseout");
        }
        else {
            $(this).children().css('color', '');
            $(this).children().css('background-color', '');
            $(this).children().css('cursor', '');
        }
    });

    /*AAG NS:2871 Too General, row click logic needs to be much more precise. Start from scratch and expand as needed  
        $(".DataFormListTdDataGrid tr").live("click", function () {
        if ($(this).hasClass('DataFormListTDDataGridHeader') || $(this).hasClass('DataFormListTDDataGridPager') || $(this).hasClass('DataFormListTDDataFormFooterTR')) {
            $(this).die("click");
        }
        else {
            //AAG BUG 12911 Fix for data Grid Rows with check boxes
            if ($(this).parent('tr') != null && $(this).parent('tr').find('td img:first') != null && $(this).parent('tr').find('td img:first').attr('onclick') != null) {
                var rowDest = $(this).parent('tr').find('td img:first').attr('onclick').toString();
                 Fix for Grid rows where the first image is not an edit link 
                if (rowDest.indexOf('OpenNewWindow') >= 1) {
                    if (rowDest.substring(rowDest.indexOf('OpenNewWindow')).indexOf('\'') < 1) {
                        rowDest = rowDest.substr(rowDest.indexOf('\"') + 1);
                        rowDest = rowDest.substr(0, rowDest.indexOf('\"'));
                    }
                    else if (rowDest.substring(rowDest.indexOf('OpenNewWindow')).indexOf('\'') > 1) {
                        rowDest = rowDest.substr(rowDest.indexOf('\'') + 1);
                        rowDest = rowDest.substr(0, rowDest.indexOf('\''));
                    }
            location.href = $(this).find('td a').attr('href');
        }
    });
    $(".DataFormChildDataGrid tr td").live("click", function () {
    if ($(this).parent('tr').hasClass('DataFormChildDataGridHeader') || $(this).parent('tr').hasClass('DataFormChildDataGridPager') || $(this).parent('tr').hasClass('DataFormChildDataGridFooterTR') || $(this).children().attr('onclick') != null || $(this).children().attr('input') != null) {
    $(this).die("click");
    }
    else {
    //AAG BUG 12911 Fix for data Grid Rows with check boxes
    if ($(this).parent('tr') != null && $(this).parent('tr').find('td img:first') != null && $(this).parent('tr').find('td img:first').attr('onclick') != null) {
    var rowDest = $(this).parent('tr').find('td img:first').attr('onclick').toString();
    /* Fix for Grid rows where the first image is not an edit link 
    if (rowDest.indexOf('OpenNewWindow') >= 1) {
    if (rowDest.substring(rowDest.indexOf('OpenNewWindow')).indexOf('\'') < 1) {
    rowDest = rowDest.substr(rowDest.indexOf('\"') + 1);
    rowDest = rowDest.substr(0, rowDest.indexOf('\"'));
    }
    else if (rowDest.substring(rowDest.indexOf('OpenNewWindow')).indexOf('\'') > 1) {
    rowDest = rowDest.substr(rowDest.indexOf('\'') + 1);
    rowDest = rowDest.substr(0, rowDest.indexOf('\''));
    }
                
    OpenNewWindow(rowDest);
    }
    }
    if ($(this).parent('tr') != null && $(this).parent('tr').find('td a:first') != null && $(this).parent('tr').find('td a:first').attr('href') != null)
    window.location.href = $(this).parent('tr').find('td a:first').attr('href').toString();

    }
    });
    
    AAG NS:2871 begining of new row click logic*/
    $("td").click(
    function () {
        if (row_clicked)
            return;

        //AAG NS:4939 do not perform row click logic on headers
        if ($(this).hasClass('DataFormHeaderTD') || $(this).hasClass('DataFormFooterTD'))
            return;
        
    	if ($(this).find("a,input,select,button,img").length <= 0) {
    	    //Earlier version of JQuery did not work with quotes    	    
    		var oCheckbox = $(this).parent().find("input[type='checkbox']:first");
    		var oGotoImg = $(this).parent().find("img[src*='img_goto.gif']:first");
    		var oEditImg = $(this).parent().find("img[src*='img_edit.gif']:first");
    		var oFolderClosedImg = $(this).parent().find("img[src*='img_folder_full.gif']:first");
    		var oFolderOpenImg = $(this).parent().find("img[src*='img_folder_full1.gif']:first");
    		//AAG NS:5345 using children instead of find to restrict to first cell only
    		var oFirstLink = $(this).parent().children("td:first").children("a:first");
    		var oReportGo = $(this).parent().find("img[src*='img_button_go2.gif']:first");
    		var oRowClick = null;

            if (oCheckbox.length == 1)
                oRowClick = oCheckbox;

            if (oRowClick == null && oGotoImg.length == 1)
                oRowClick = oGotoImg;

    	    if (oRowClick == null && oEditImg.length == 1) {
    	        //AAG 3/21/2012 some update grids have edit links in definition as hyper link new to set row click to anchor instead of img
    	        if (oEditImg.parent() != null && oEditImg.parent().is('a'))
    	            oRowClick = oEditImg.parent();
    	        else
    	            oRowClick = oEditImg;
    	    }
    	    if (oRowClick == null && oFolderClosedImg.length == 1)
                oRowClick = oFolderClosedImg;

            if (oRowClick == null && oFolderOpenImg.length == 1)
                oRowClick = oFolderOpenImg;

            if (oRowClick == null && oReportGo.length == 1)
                oRowClick = oReportGo;

            if (oRowClick != null) {
                row_clicked = true;
                //oRowClick.click();
                if (oRowClick.attr("onclick") != null)
                    eval(oRowClick.attr("onclick"));
                else if (oRowClick.attr("href") != null)
                    window.location.href = oRowClick.attr("href");
                else
                    oRowClick.click();
            }

            if (oRowClick == null && oFirstLink.length == 1) {
                row_clicked = true;
                
                if (oFirstLink.attr("href") != undefined && oFirstLink.attr("href") != '') {
                    window.location.href = oFirstLink.attr("href");
                }
            }


        }
        row_clicked = false;
    });
    // end TableRowOver.htc replacement //
    //AAG NS:2286 NF2011 open close child form enhancement

    /*
    $(".module-header").live("click", function (e, ui) {
        e.preventDefault();
        var oOpenCloseButton = $(this).children("a:first");
        if (oOpenCloseButton.length == 1) {
            window.location(oOpenCloseButton.attr("href"));
        }
    });
    */
}

function InitializeHub() {
    
    //enable logging for debugging
    //$.connection.hub.logging = true;
    $.connection.hub.start();
    
    var notificationHub = $.connection.notificationHub;
    notificationHub.client.connected = function () {  };
    notificationHub.client.disconnected = function () { };
    notificationHub.client.showNotification = onShowNotification;
   
}

function onShowNotification(msg) {
        //alert('notification test from:' + msg.From + '\n To:' + msg.To + '\n content:' + msg.Content);
        var msgCount = $('#header-messages-count');
        //increment count by 1
        if (msgCount != null)
        {
            try {
                    msgCount.text(parseInt(msgCount.text(), 10) + 1);
            } catch (e) { }
            if (msgCount.is('.unseen')) 
                msgCount.removeClass('unseen').addClass('seen');
        }
}



function ChangeTemplateBackgroundColor(ColorField, bodyTagId) {

    if (ColorField == null || ColorField == '') return;

    if (bodyTagId == null) {

        if (document.getElementById("CE_cct_body_ID_Frame") != null)
            bodyTagId = "CE_cct_body_ID_Frame";

        if (document.getElementById("cct_body_IFrame") != null)
            bodyTagId = "cct_body_IFrame";
    }

    if (document.getElementById(bodyTagId) != null) {
        var oIFrame = document.getElementById(bodyTagId);
        var oFrameWindow = oIFrame.contentWindow;
        var oFrameDocument = oIFrame.contentDocument;

        if (oFrameWindow != null && oFrameDocument == null) {
            var oDocument = oFrameWindow.document;
            if (oDocument != null) {
                oDocument.body.style.backgroundColor = "#" + ColorField[0].value;
            }
        }

        if (oFrameDocument != null) {
            oFrameDocument.body.style.backgroundColor = "#" + ColorField[0].value;
        }
    }
}

function DefaultTemplateBackground(oTemplateBodyCtrl) {
    var oDefaultBg = document.getElementById("cct_bg_color").value;

    if (oTemplateBodyCtrl == null)
        oTemplateBodyCtrl = document.getElementById("CE_cct_body_ID_Frame");

    if (oTemplateBodyCtrl == null)
        oTemplateBodyCtrl = document.getElementById("cct_body_IFrame");

    if (oTemplateBodyCtrl != null && oDefaultBg != null && oDefaultBg != "") {
        var oFrameWindow = oTemplateBodyCtrl.contentWindow;
        var oFrameDocument = oTemplateBodyCtrl.contentDocument;

        if (oFrameWindow != null && oFrameDocument == null) {
            var oDocument = oFrameWindow.document;

            if (oDocument != null) {
                oDocument.body.style.backgroundColor = "#" + oDefaultBg;
            }
        }

        if (oFrameDocument != null && oFrameDocument.body != null) {
            oFrameDocument.body.style.backgroundColor = "#" + oDefaultBg;
        }

    }
}


function PinMenuClick(szMenu,oLink) {
    var bPinned = GetCookie('NetForumMenus', szMenu);

    if (bPinned == "true") {
        SetCookie('NetForumMenus', false, szMenu);
        $(oLink).removeClass("Pinned");
    }
    else {
        SetCookie('NetForumMenus', true, szMenu);
        $(oLink).addClass("Pinned");
    }
}

// --- sidebar related js 


//* detect touch devices 
function is_touch_device() {
    return !!('ontouchstart' in window);
}
$(document).ready(function () {

    $('body').on('click.collapse.data-api', '[data-toggle=collapse]', function (e) {
        if ($('.nav-collapse').hasClass('in')) {
            $('body').addClass('sidebar_hidden');
        }
    });

    $('.sidebar, .sidebar_switch').click(function (event) {
        event.stopPropagation();
    });

    $('html').click(function () {
        if ($(window).width() < 979 && !$('body').hasClass('sidebar_hidden')) {
            $('body').addClass('sidebar_hidden');
        }
    });

    //* accordion change actions
    $('#side_accordion').on('hidden shown', function () {
        ent_sidebar.make_active();
        ent_sidebar.update_scroll();
    });
    //* resize elements on window resize
    var lastWindowHeight = $(window).height();
    var lastWindowWidth = $(window).width();
    $(window).on("debouncedresize", function () {
        if ($(window).height() != lastWindowHeight || $(window).width() != lastWindowWidth) {
            if ($(window).width() < 979 && $(window).width() != lastWindowWidth) {
                $('body').addClass('sidebar_hidden');
            } else {
                $('body').removeClass('sidebar_hidden');
            }
            lastWindowHeight = $(window).height();
            lastWindowWidth = $(window).width();
            ent_sidebar.update_scroll();
            if (!is_touch_device()) {
            //     $('.sidebar_switch').qtip('hide');
            }
        }
    });
    //* tooltips
    //ent_tips.init();
    //if(!is_touch_device()){
    //    //* popovers
    //    ent_popOver.init();
    //}
    //* sidebar
    ent_sidebar.init();
    ent_sidebar.make_active();
    //* breadcrumbs
    ent_crumbs.init();
    //* pre block prettify
    if (typeof prettyPrint == 'function') {
        prettyPrint();
    }
    //* external links
    //ent_external_links.init();
    //* accordion icons
    //ent_acc_icons.init();
    //* colorbox single
    //ent_colorbox_single.init();
    //* main menu mouseover
    //ent_nav_mouseover.init();
    //* top submenu
    //ent_submenu.init();

    ent_sidebar.make_scroll();
    ent_sidebar.update_scroll();

    //* style switcher
    ent_style_sw.init();
});

ent_sidebar = {
    init: function () {
        // sidebar onload state
        //$('.sidebar_switch').attr('rel', 'tooltip');
        if ($(window).width() > 979) {
            if (!$('body').hasClass('sidebar_hidden')) {
                if ($.cookie != null && $.cookie('ent_sidebar') == "hidden") {
                    $('body').addClass('sidebar_hidden');
                    $('.sidebar_switch').toggleClass('on_switch off_switch').attr('title', 'Show Sidebar');
                }
            } else {
                $('.sidebar_switch').toggleClass('on_switch off_switch').attr('title', 'Show Sidebar');
            }
        } else {
            $('body').addClass('sidebar_hidden');
            $('.sidebar_switch').removeClass('on_switch').addClass('off_switch');
        }

        //ent_sidebar.info_box();
        //* sidebar visibility switch
        $('.sidebar_switch').click(function () {
            $('.sidebar_switch').removeClass('on_switch off_switch');
            if ($('body').hasClass('sidebar_hidden')) {
                $.cookie('ent_sidebar', null);
                $('body').removeClass('sidebar_hidden');
                $('.sidebar_switch').addClass('on_switch').show();
                $('.sidebar_switch').attr('title', "Hide Sidebar");
                if ($('.nav-collapse').hasClass('in')) {
                    $('.nav-collapse').removeClass('in').attr('style', 'height: 0px;');
                }
            } else {
                $.cookie('ent_sidebar', 'hidden');
                $('body').addClass('sidebar_hidden');
                $('.sidebar_switch').addClass('off_switch');
                $('.sidebar_switch').attr('title', "Show Sidebar");
            }
            //ent_sidebar.info_box();
            ent_sidebar.update_scroll();
            $(window).resize();
        });
        //* prevent accordion link click
        $('.sidebar .accordion-toggle').click(function (e) { e.preventDefault() });
    },
    //info_box: function () {
    //    var s_box = $('.sidebar_info');
    //    var s_box_height = s_box.actual('height');
    //    s_box.css({
    //        'height': s_box_height
    //    });
    //    $('.push').height(s_box_height);
    //    $('.sidebar_inner').css({
    //        'margin-bottom': '-' + s_box_height + 'px',
    //        'min-height': '100%'
    //    });
    //},
    make_active: function () {
        var thisAccordion = $('#side_accordion');
        thisAccordion.find('.accordion-heading').removeClass('sdb_h_active');
        var thisHeading = thisAccordion.find('.accordion-body.in').prev('.accordion-heading');
        if (thisHeading.length) {
            thisHeading.addClass('sdb_h_active');
        }
    },
    make_scroll: function () {
        if ($('.antiScroll').length > 0)
            antiScroll = $('.antiScroll').antiscroll().data('antiscroll');
    },
    update_scroll: function () {
        if ($('.antiScroll').length) {
            if ($(window).width() > 979) {
                $('.antiscroll-inner,.antiscroll-content').height($(window).height() - 40);
            } else {
                $('.antiscroll-inner,.antiscroll-content').height('auto');
            }
            if (antiScroll != null && antiScroll.length > 0)
                antiScroll.refresh();
        }
    }
};

//* breadcrumbs
ent_crumbs = {
    init: function () {
        if ($('#jCrumbs').length) {
            $('#jCrumbs').jBreadCrumb({
                endElementsToLeaveOpen: 0,
                beginingElementsToLeaveOpen: 0,
                timeExpansionAnimation: 500,
                timeCompressionAnimation: 500,
                timeInitialCollapse: 500,
                previewWidth: 30
            });
        }
    }
};

//* external links
//ent_external_links = {
//    init: function () {
//        $("a[href^='http']").not('.thumbnail>a,.ext_disabled').each(function () {
//            $(this).attr('target', '_blank').addClass('external_link');
//        })
//    }
//};

//* accordion icons
//ent_acc_icons = {
//    init: function () {
//        var accordions = $('.main_content .accordion');

//        accordions.find('.accordion-group').each(function () {
//            var acc_active = $(this).find('.accordion-body').filter('.in');
//            acc_active.prev('.accordion-heading').find('.accordion-toggle').addClass('acc-in');
//        });
//        accordions.on('show', function (option) {
//            $(this).find('.accordion-toggle').removeClass('acc-in');
//            $(option.target).prev('.accordion-heading').find('.accordion-toggle').addClass('acc-in');
//        });
//        accordions.on('hide', function (option) {
//            $(option.target).prev('.accordion-heading').find('.accordion-toggle').removeClass('acc-in');
//        });
//    }
//};

//* main menu mouseover
//ent_nav_mouseover = {
//    init: function () {
//        $('header li.dropdown').mouseenter(function () {
//            if ($('body').hasClass('menu_hover')) {
//                $(this).addClass('navHover')
//            }
//        }).mouseleave(function () {
//            if ($('body').hasClass('menu_hover')) {
//                $(this).removeClass('navHover open')
//            }
//        });
//    }
//};

//* single image colorbox
//ent_colorbox_single = {
//    init: function () {
//        if ($('.cbox_single').length) {
//            $('.cbox_single').colorbox({
//                maxWidth: '80%',
//                maxHeight: '80%',
//                opacity: '0.2',
//                fixed: true
//            });
//        }
//    }
//};

//* submenu
//ent_submenu = {
//    init: function () {
//        $('.dropdown-menu li').each(function () {
//            var $this = $(this);
//            if ($this.children('ul').length) {
//                $this.addClass('sub-dropdown');
//                $this.children('ul').addClass('sub-menu');
//            }
//        });

//        $('.sub-dropdown').on('mouseenter', function () {
//            $(this).addClass('active').children('ul').addClass('sub-open');
//        }).on('mouseleave', function () {
//            $(this).removeClass('active').children('ul').removeClass('sub-open');
//        })

//    }
//};

//* style switcher
ent_style_sw = {
    init: function () {
        $('body').append('<a class="ssw_trigger" href="javascript:void(0)"><i class=""></i></a>');
        var defLink = $('#link_theme').clone();


        $('input[name=ssw_sidebar]:first,input[name=ssw_layout]:first,input[name=ssw_menu]:first').attr('checked', true);

        $(".ssw_trigger").click(function () {
            $(".style_switcher").toggle("fast");
            $(this).toggleClass("active");
            return false;
        });


        //* sidebar position
        $('input[name=ssw_sidebar]').click(function () {
            var sidebar_position = $(this).val();
            $('body').removeClass('sidebar_right').addClass(sidebar_position);
            $(window).resize();
        });
        //* menu show
        $('input[name=ssw_menu]').click(function () {
            var menu_show = $(this).val();
            $('body').removeClass('menu_hover').addClass(menu_show);
        });

        //* reset
        $('#resetDefault').click(function () {
            $('body').attr('class', '');
            $('.style_item').removeClass('style_active').filter(':first-child').addClass('style_active');
            $('#link_theme').replaceWith(defLink);
            $('.ssw_trigger').removeClass('active');
            $(".style_switcher").hide();
            return false;
        });

        $('#showCss').on('click', function(e) {
            var themeLink = $('#link_theme').attr('href'),
                bodyClass = $('body').attr('class');
            var contentStyle = '';
            contentStyle = '<div style="padding:20px;background:#fff">';
            if ((themeLink != 'css/blue.css') && (themeLink != undefined)) {
                contentStyle += '<div class="sepH_c"><textarea style="height:20px" class="span5">&lt;link id="link_theme" rel="stylesheet" href="' + themeLink + '"&gt;</textarea><span class="help-block">Find stylesheet with id="link_theme" in document head and replace it with this code.</span></div>';
            }
            if ((bodyClass != '') && (bodyClass != undefined)) {
                contentStyle += '<textarea style="height:20px" class="span5">&lt;body class="' + $('body').attr('class') + '"&gt;</textarea><span class="help-block">Replace body tag with this code.</span>';
            } else {
                contentStyle += '<textarea style="height:20px" class="span5">&lt;body&gt;</textarea>';
            }
            contentStyle += '</div>';
            $.colorbox({
                opacity: '0.2',
                fixed: true,
                html: contentStyle
            });
            e.preventDefault();
        });
    }
};

function getTimestamp(options) {
    var defaults = {
            advance_minutes: 5, // number of minutes to advance the current timestamp
            regex_remove: /\u200E/g // regex value(s) that will be removed from the timestamp
        },
        now = new Date(),
        then = null;

    // merge options without overwriting
    options = $.extend({}, defaults, (options || {}));

    // set basic timestamp value
    now.setMinutes(now.getMinutes() + options.advance_minutes);
    then = (now.getMonth() + 1)
        + '/'
        + now.getDate()
        + '/'
        + now.getFullYear()
        + ' '
        + now.toLocaleTimeString();

    // clean up the timestamp value because toLocaleTimeString can be problematic in different browsers
    then = then.replace(options.regex_remove, '');

    return then;
}