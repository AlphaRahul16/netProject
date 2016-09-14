//The MIT License

//Copyright (c) 2008 Jason Frame (jason@onehackoranother.com)

//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:

//The above copyright notice and this permission notice shall be included in
//all copies or substantial portions of the Software.

//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
//THE SOFTWARE.

//Copyright (c) 2011 Avectra Inc. All rights reserved.
//Portions of this "Software" have been modified and extended by Avectra Inc.
//Any usage of this modified version of the "Software" is prohibited without
//explicit permission from Avectra Inc.

(function ($) {
    $.fn.tipsy = function (options) {
        options = $.extend({}, $.fn.tipsy.defaults, options);
        var opts = $.fn.tipsy.elementOptions(this, options);
        var selector = $(this).selector;
        return this.each(function () {
            if ($(this).attr('title') || typeof ($(this).attr('data-original-title')) != 'string') {
                $(this).attr('data-original-title', $(this).attr('title') || '').removeAttr('title');
            }
            if (opts.showEvent == '') opts.showEvent = 'hover';

            if (opts.showEvent.toLowerCase() == 'hover') {
                $(this).hover(function (event) {
                    event.preventDefault();
                    $(this).addClass('TipsyMenuActive');
                    var hoverLink = $(this)
                    setTimeout(function () {
                        if ($(hoverLink).hasClass('TipsyMenuActive')) {
                            if (opts.customMenu) {
                                $(selector).each(function () {
                                    var tempTip = $('#' + $(this).attr('data-menu'));

                                    if (opts.adjustListWidth) {
                                        if ($('body').attr('offsetWidth') > 1024) {

                                            if (tempTip.children(opts.showMoreListType).length > 0)
                                                tempTip.children(opts.showMoreListType).css('width', 'auto');

                                            if (tempTip.children(opts.showMoreListType).children(opts.showMoreListItem).length > 0) {
                                                tempTip.children(opts.showMoreListType).children(opts.showMoreListItem).css('width', 'auto');
                                            }

                                        }

                                    }
                                    if (opts.showMoreHeight > 0 && $('#MenuMore' + tempTip.attr('id')).length == 0) {
                                        //var nTipHeight = (tempTip.children(opts.showMoreListType).children(opts.showMoreListItem).text().length / Math.round(tempTip.css('width').replace('px', '') / tempTip.children(opts.showMoreListType).children(opts.showMoreListItem).css('font-size').replace('px', ''))) * 2; //2 accounts for space between links
                                        var nFontHeight = parseInt(tempTip.children(opts.showMoreListType).children(opts.showMoreListItem).css('font-size').replace('px', ''));
                                        var nPadTop = parseInt(tempTip.children(opts.showMoreListType).children(opts.showMoreListItem).css('padding-top').replace('px', ''));
                                        var nPadBottom = parseInt(tempTip.children(opts.showMoreListType).children(opts.showMoreListItem).css('padding-bottom').replace('px', ''));
                                        if (nFontHeight == null)
                                            nFontHeight = 0;
                                        if (nPadBottom == null)
                                            nPadBottom = 0;
                                        if (nPadTop == null)
                                            nPadTop = 0;

                                        var nAccountForWrapping = 0;
                                        var nTotalLines = 0;
                                        tempTip.children(opts.showMoreListType).children(opts.showMoreListItem).each(function () {
                                            nTotalLines = nTotalLines + ($(this).text().length / ($(this).css('width').replace('px', '') / nFontHeight));
                                        });

                                        if (tempTip.children(opts.showMoreListType).children(opts.showMoreListItem).length > 0)
                                            nAccountForWrapping = nTotalLines / tempTip.children(opts.showMoreListType).children(opts.showMoreListItem).length;

                                        if ($('body').attr('offsetWidth') > 1024)
                                            nAccountForWrapping = 2;
                                        var nMoreItemList = Math.round(opts.showMoreHeight / ((nFontHeight + nPadTop + nPadBottom) * nAccountForWrapping));

                                        if (tempTip.children(opts.showMoreListType).children(opts.showMoreListItem).length > nMoreItemList) {
                                            var nStartItem = nMoreItemList+1;
                                            var oNewItem = $('<' + opts.showMoreListItem + '><a href=\"javascript:void(0);\" class="TipsyMenuMoreLink">more...</a><div id=\"MenuMore' + tempTip.attr('id') + '\" class=\"' + tempTip.attr('class') + '\" style=\"display:none\"><' + opts.showMoreListType + ' class=\"' + tempTip.children(opts.showMoreListType).attr('class') + '\"></' + opts.showMoreListType + '></div></' + opts.showMoreListItem + '>').insertBefore(tempTip.children(opts.showMoreListType).children(opts.showMoreListItem)[nStartItem]);
                                            $('.TipsyMenuMoreLink', oNewItem).click(function () {
                                                if ($('#MenuMore' + tempTip.attr('id')).is(':hidden')) {
                                                    $('#MenuMore' + tempTip.attr('id')).show();
                                                }
                                                else
                                                    $('#MenuMore' + tempTip.attr('id')).hide();
                                                return false;
                                            });
                                            var oNewMenu = $('#MenuMore' + tempTip.attr('id') + ' ' + opts.showMoreListType, oNewItem);
                                            for (var nCount = nStartItem + 1; nCount <= tempTip.children(opts.showMoreListType).children(opts.showMoreListItem).length && $(tempTip.children(opts.showMoreListType).children(opts.showMoreListItem)[nCount]).length > 0; nCount) {
                                                $(tempTip.children(opts.showMoreListType).children(opts.showMoreListItem)[nCount]).appendTo(oNewMenu);
                                            }

                                        }
                                    }
                                    if (opts.changeVisibility) { tempTip.css('display', 'none'); }
                                    else if ($.data(tempTip, 'top') && $.data(tempTip, 'left')) {
                                        tempTip.css('top', $.data(tempTip, 'top'));
                                        tempTip.css('left', $.data(tempTip, 'left'));
                                    }
                                    $.data(this, 'tipsy.open', false);
                                });
                            }
                            var self = hoverLink[0];
                            $.data(self, 'tipsy.open', true);
                            var tip = $.data(self, 'tipsy.menu');
                            if (!tip) {
                                if (opts.customMenu) {
                                    tip = $('#' + hoverLink.attr('data-menu'));
                                    if (tip) {
                                        if (!opts.changeVisibility) {
                                            $.data(tip, 'left', tip.css('left'));
                                            $.data(tip, 'top', tip.css('top'));
                                        }
                                        tip.hover(function (event) {
                                            event.preventDefault();
                                            $.data(self, 'tipsy.open', true);
                                            tip.css({ 'display': 'block' });
                                        },
                            function (event) {
                                event.preventDefault();
                                $.data(self, 'tipsy.open', false);
                                setTimeout(function () {
                                    if ($.data(self, 'tipsy.open')) { return; }
                                    if (opts.changeVisibility) {
                                        if (opts.effect == 'fade') { tip.stop().fadeOut(400, function () { tip.css({ 'display': 'none' }); }); }
                                        else { tip.css({ 'display': 'none' }); }
                                    } else {
                                        tip.css('top', $.data(tip, 'top'));
                                        tip.css('left', $.data(tip, 'left'));
                                    }
                                }, opts.speed);
                            });
                                    }
                                } else {
                                    tip = $('<div></div>').addClass("tipsy").append($('<div></div>').addClass("tipsy-inner"));
                                    tip.css({ display: 'none', fontSize: opts.fontSize }).appendTo(document.body);
                                    if (!opts.textWrap) { tip.css({ 'white-space': 'nowrap' }); }
                                    else { tip.css({ 'width': opts.width }); }
                                }
                                $.data(self, 'tipsy.menu', tip);
                            }
                            if (tip) {
                                if (opts.changeVisibility) { tip.css({ 'display': 'none', 'position': 'absolute', 'z-index': opts.zIndex, 'top': 0, 'left': 0, 'right': '', 'bottom': '' }); }
                                if (!opts.customMenu) {
                                    var title;
                                    if (typeof opts.title == 'string') { title = hoverLink.attr(opts.title == 'title' ? 'data-original-title' : opts.title); }
                                    else if (typeof opts.title == 'function') { title = opts.title.call(self); }
                                    tip.find('.tipsy-inner')[opts.html ? 'html' : 'text'](title || opts.fallback);
                                }
                                var pos = $.extend({}, hoverLink.offset(), { width: self.offsetWidth, height: self.offsetHeight });
                                var actualWidth = parseInt(tip.css('width')), actualHeight = parseInt(tip.css('height'));
                                var direction = (typeof opts.direction == 'function') ? opts.direction.call(self) : opts.direction;
                                var slideDirection = 'down';
                                switch (direction) {
                                    case 'down':
                                        tip.css({ 'top': pos.top + pos.height, 'left': pos.left /* + (pos.width / 2) - (actualWidth / 2) */});
                                        if (!opts.customMenu) { tip.addClass('tipsy-north'); }
                                        slideDirection = 'up';
                                        break;
                                    case 'up':
                                        tip.css({ 'top': pos.top - actualHeight, 'left': pos.left /* + (pos.width / 2) - (actualWidth / 2)*/ });
                                        if (!opts.customMenu) { tip.addClass('tipsy-south'); }
                                        slideDirection = 'down';
                                        break;
                                    case 'left':
                                        tip.css({ 'top': pos.top /* + (pos.height / 2) - (actualHeight / 2)*/, 'left': pos.left - actualWidth });
                                        if (!opts.customMenu) { tip.addClass('tipsy-east'); }
                                        slideDirection = 'right';
                                        break;
                                    case 'right':
                                        tip.css({ 'top': pos.top /* + (pos.height / 2) - (actualHeight / 2)*/, 'left': pos.left + pos.width });
                                        if (!opts.customMenu) { tip.addClass('tipsy-west'); }
                                        slideDirection = 'left';
                                        break;
                                }
                                // add horizontal and vertical margins
                                tip.css({
                                    'top': parseInt(tip.css('top')) + parseInt(opts.verticalMargin),
                                    'left': parseInt(tip.css('left')) + parseInt(opts.horizontalMargin)
                                });
                                if (opts.ensureInScreen) {
                                    // makes sure the popup does not extend beyond the screen's size
                                    tip.css({
                                        // AAG NS:11640 'top': (parseInt(tip.css('top')) + parseInt(tip.css('height')) > ($(window).height() + 15) ? ($(window).height() + 15) - parseInt(tip.css('height')) : parseInt(tip.css('top'))),
                                        'top': (parseInt(pos.top) + parseInt(tip.css('height')) > ($(window).height() + 15) ? (pos.right + 15) - parseInt(tip.css('height')) : parseInt(tip.css('top'))),
                                        // LP Changed positioning because popup is in the wrong place when the form grew vertically.
                                        //NS:11640
                                        //'left': (parseInt(tip.css('left')) + parseInt(tip.css('width')) > ($(window).width() - 10) ? ($(window).width() - 10) - parseInt(tip.css('width')) : parseInt(tip.css('left')))
                                        // 2012/06/26, Aeron, NS#7326: the popup was incorrectly positioned when the page grew horizontally.
                                        'left': (parseInt(pos.left) + parseInt(tip.css('width')) > ($(window).width() - 10) ? (pos.left + 12) - parseInt(tip.css('width')) : parseInt(tip.css('left')))
                                    });
                                    // make sure the popup does not move to less than the zero coordinates
                                    tip.css({
                                        'top': (parseInt(tip.css('top')) > 0 ? parseInt(tip.css('top')) : 0),
                                        'left': (parseInt(tip.css('left')) > 0 ? parseInt(tip.css('left')) : 0)
                                    });
                                }
                                if (opts.effect == 'fade') { tip.css({ 'opacity': 0, 'display': 'block' }).animate({ 'opacity': 1, 'duration': opts.speed }); }
                                else {
                                    tip.css({ 'display': 'block' });
                                    if (opts.effect == 'bounce') { tip.effect('bounce'); }
                                    else if (opts.effect == 'slide') { tip.effect('slide', { 'direction': slideDirection }, opts.speed); }
                                }
                            }
                        }
                    }, opts.hoverDelayTime);
                },
            function (event) {
                event.preventDefault();
                $(this).removeClass('TipsyMenuActive');
                var self = this;
                $.data(self, 'tipsy.open', false);
                setTimeout(function () {
                    if ($.data(self, 'tipsy.open')) { return; }
                    var tip = $.data(self, 'tipsy.menu');
                    if (tip) {
                        if (opts.changeVisibility) {
                            if (opts.effect == 'fade') { tip.stop().fadeOut(opts.speed, function () { tip.css({ 'display': 'none' }); }); }
                            else { tip.css({ 'display': 'none' }); }
                        } else {
                            tip.css('top', $.data(tip, 'top'));
                            tip.css('left', $.data(tip, 'left'));
                        }
                    }
                }, opts.speed);
            });
            }
            if (opts.showEvent.toLowerCase() == 'click' || opts.showEvent.toLowerCase() == 'hover') {
                $(this).click(function (event) {
                    //AAG NFEP Custom code for navigation warnings
                    if (bConfirmNavAway != null)
                        bConfirmNavAway = 0;

                    if (!$.data(this, 'tipsy.open')) {
                        $(this).addClass('TipsyMenuActive');
                        if (opts.customMenu) {
                            $(selector).each(function () {
                                var tempTip = $('#' + $(this).attr('data-menu'));

                                if (opts.adjustListWidth) {
                                    if ($('body').attr('offsetWidth') > 1024) {

                                        if (tempTip.children(opts.showMoreListType).length > 0)
                                            tempTip.children(opts.showMoreListType).css('width', 'auto');

                                        if (tempTip.children(opts.showMoreListType).children(opts.showMoreListItem).length > 0) {
                                            tempTip.children(opts.showMoreListType).children(opts.showMoreListItem).css('width', 'auto');
                                        }

                                    }

                                }

                                if (opts.showMoreHeight > 0 && $('#MenuMore' + tempTip.attr('id')).length == 0) {
                                    //var nTipHeight = (tempTip.children(opts.showMoreListType).children(opts.showMoreListItem).text().length / Math.round(tempTip.css('width').replace('px', '') / tempTip.children(opts.showMoreListType).children(opts.showMoreListItem).css('font-size').replace('px', ''))) * 2; //2 accounts for space between links
                                    var nFontHeight = parseInt(tempTip.children(opts.showMoreListType).children(opts.showMoreListItem).css('font-size').replace('px', ''));
                                    var nPadTop = parseInt(tempTip.children(opts.showMoreListType).children(opts.showMoreListItem).css('padding-top').replace('px', ''));
                                    var nPadBottom = parseInt(tempTip.children(opts.showMoreListType).children(opts.showMoreListItem).css('padding-bottom').replace('px', ''));
                                    if (nFontHeight == null)
                                        nFontHeight = 0;
                                    if (nPadBottom == null)
                                        nPadBottom = 0;
                                    if (nPadTop == null)
                                        nPadTop = 0;

                                    var nAccountForWrapping = 0;
                                    var nTotalLines = 0;
                                    tempTip.children(opts.showMoreListType).children(opts.showMoreListItem).each(function () {
                                        nTotalLines = nTotalLines + ($(this).text().length / ($(this).css('width').replace('px', '') / nFontHeight));
                                    });

                                    if (tempTip.children(opts.showMoreListType).children(opts.showMoreListItem).length > 0)
                                        nAccountForWrapping = nTotalLines / tempTip.children(opts.showMoreListType).children(opts.showMoreListItem).length;

                                    if ($('body').attr('offsetWidth') > 1024)
                                        nAccountForWrapping = 2;
                                    var nMoreItemList = Math.round(opts.showMoreHeight / ((nFontHeight + nPadTop + nPadBottom) * nAccountForWrapping));

                                    if (tempTip.children(opts.showMoreListType).children(opts.showMoreListItem).length > nMoreItemList) {
                                        var nStartItem = nMoreItemList +1;
                                        var oNewItem = $('<' + opts.showMoreListItem + '><a href=\"javascript:void(0);\" class="TipsyMenuMoreLink">more...</a><div id=\"MenuMore' + tempTip.attr('id') + '\" class=\"' + tempTip.attr('class') + '\" style=\"display:none\"><' + opts.showMoreListType + ' class=\"' + tempTip.children(opts.showMoreListType).attr('class') + '\"></' + opts.showMoreListType + '></div></' + opts.showMoreListItem + '>').insertBefore(tempTip.children(opts.showMoreListType).children(opts.showMoreListItem)[nStartItem]);
                                        $('.TipsyMenuMoreLink', oNewItem).click(function () {
                                            if ($('#MenuMore' + tempTip.attr('id')).is(':hidden')) {
                                                $('#MenuMore' + tempTip.attr('id')).show();
                                            }
                                            else
                                                $('#MenuMore' + tempTip.attr('id')).hide();
                                            return false;
                                        });
                                        var oNewMenu = $('#MenuMore' + tempTip.attr('id') + ' ' + opts.showMoreListType, oNewItem);
                                        for (var nCount = nStartItem + 1; nCount <= tempTip.children(opts.showMoreListType).children(opts.showMoreListItem).length && $(tempTip.children(opts.showMoreListType).children(opts.showMoreListItem)[nCount]).length > 0; nCount) {
                                            $(tempTip.children(opts.showMoreListType).children(opts.showMoreListItem)[nCount]).appendTo(oNewMenu);
                                        }

                                    }
                                }
                                if (opts.changeVisibility) { tempTip.css('display', 'none'); }
                                else if ($.data(tempTip, 'top') && $.data(tempTip, 'left')) {
                                    tempTip.css('top', $.data(tempTip, 'top'));
                                    tempTip.css('left', $.data(tempTip, 'left'));
                                }
                                $.data(this, 'tipsy.open', false);
                            });
                        }
                        var self = this;
                        $.data(self, 'tipsy.open', true);
                        var tip = $.data(self, 'tipsy.menu');
                        if (!tip) {
                            if (opts.customMenu) {
                                tip = $('#' + $(this).attr('data-menu'));
                                if (tip) {
                                    if (!opts.changeVisibility) {
                                        $.data(tip, 'left', tip.css('left'));
                                        $.data(tip, 'top', tip.css('top'));
                                    }

                                    $('a', tip).click(function () {
                                        //AAG NFEP Custom code for navigation warnings
                                        if (bConfirmNavAway != null)
                                            bConfirmNavAway = 1;
                                    });
                                    tip.mouseleave(function (event) {
                                        $.data(self, 'tipsy.mouseout', true);
                                        $.data(self, 'tipsy.open', false);
                                    });
                                    tip.mouseenter(function (event) {
                                        $.data(self, 'tipsy.mouseout', true);
                                        $.data(self, 'tipsy.open', true);
                                    });

                                }
                            } else {
                                tip = $('<div></div>').addClass("tipsy").append($('<div></div>').addClass("tipsy-inner"));
                                tip.css({ display: 'none', fontSize: opts.fontSize }).appendTo(document.body);
                                if (!opts.textWrap) { tip.css({ 'white-space': 'nowrap' }); }
                                else { tip.css({ 'width': opts.width }); }
                            }
                            $.data(self, 'tipsy.menu', tip);
                        }
                        if (tip) {
                            if (opts.changeVisibility) { tip.css({ 'display': 'none', 'position': 'absolute', 'z-index': opts.zIndex, 'top': 0, 'left': 0, 'right': '', 'bottom': '' }); }
                            if (!opts.customMenu) {
                                var title;
                                if (typeof opts.title == 'string') { title = $(this).attr(opts.title == 'title' ? 'data-original-title' : opts.title); }
                                else if (typeof opts.title == 'function') { title = opts.title.call(this); }
                                tip.find('.tipsy-inner')[opts.html ? 'html' : 'text'](title || opts.fallback);
                            }
                            var pos = $.extend({}, $(this).offset(), { width: this.offsetWidth, height: this.offsetHeight });
                            var actualWidth = parseInt(tip.css('width')), actualHeight = parseInt(tip.css('height'));
                            var direction = (typeof opts.direction == 'function') ? opts.direction.call(this) : opts.direction;
                            var slideDirection = 'down';
                            switch (direction) {
                                case 'down':
                                    tip.css({ 'top': pos.top + pos.height, 'left': pos.left /* + (pos.width / 2) - (actualWidth / 2) */});
                                    if (!opts.customMenu) { tip.addClass('tipsy-north'); }
                                    slideDirection = 'up';
                                    break;
                                case 'up':
                                    tip.css({ 'top': pos.top - actualHeight, 'left': pos.left /* + (pos.width / 2) - (actualWidth / 2) */});
                                    if (!opts.customMenu) { tip.addClass('tipsy-south'); }
                                    slideDirection = 'down';
                                    break;
                                case 'left':
                                    tip.css({ 'top': pos.top /* + (pos.height / 2) - (actualHeight / 2)*/, 'left': pos.left - actualWidth });
                                    if (!opts.customMenu) { tip.addClass('tipsy-east'); }
                                    slideDirection = 'right';
                                    break;
                                case 'right':
                                    tip.css({ 'top': pos.top /*+ (pos.height / 2) - (actualHeight / 2)*/, 'left': pos.left + pos.width });
                                    if (!opts.customMenu) { tip.addClass('tipsy-west'); }
                                    slideDirection = 'left';
                                    break;
                            }
                            // add horizontal and vertical margins
                            tip.css({
                                'top': parseInt(tip.css('top')) + parseInt(opts.verticalMargin),
                                'left': parseInt(tip.css('left')) + parseInt(opts.horizontalMargin)
                            });
                            if (opts.ensureInScreen) {
                                // makes sure the popup does not extend beyond the screen's size
                                tip.css({
                                    // AAG NS:11640 'top': (parseInt(tip.css('top')) + parseInt(tip.css('height')) > ($(window).height() + 15) ? ($(window).height() + 15) - parseInt(tip.css('height')) : parseInt(tip.css('top'))),
                                    'top': (parseInt(pos.top) + parseInt(tip.css('height')) > ($(window).height() + 15) ? (pos.right + 15) - parseInt(tip.css('height')) : parseInt(tip.css('top'))),
                                    // LP Changed positioning because popup is in the wrong place when the form grew vertically.
                                    //NS:11640
                                    //'left': (parseInt(tip.css('left')) + parseInt(tip.css('width')) > ($(window).width() - 10) ? ($(window).width() - 10) - parseInt(tip.css('width')) : parseInt(tip.css('left')))
                                    // 2012/06/26, Aeron, NS#7326: the popup was incorrectly positioned when the page grew horizontally.
                                    'left': (parseInt(pos.left) + parseInt(tip.css('width')) > ($(window).width() - 10) ? (pos.left + 12) - parseInt(tip.css('width')) : parseInt(tip.css('left')))
                                });
                                // make sure the popup does not move to less than the zero coordinates
                                tip.css({
                                    'top': (parseInt(tip.css('top')) > 0 ? parseInt(tip.css('top')) : 0),
                                    'left': (parseInt(tip.css('left')) > 0 ? parseInt(tip.css('left')) : 0)
                                });
                            }
                            if (opts.effect == 'fade') { tip.css({ 'opacity': 0, 'display': 'block' }).animate({ 'opacity': 1, 'duration': opts.speed }); }
                            else {
                                tip.css({ 'display': 'block' });
                                if (opts.effect == 'bounce') { tip.effect('bounce'); }
                                else if (opts.effect == 'slide') { tip.effect('slide', { 'direction': slideDirection }, opts.speed); }
                            }
                        }

                        $(document).click(function (event) {
                            if ($.data(self, 'tipsy.mouseout')) {
                                setTimeout(function () {
                                    if ($.data(self, 'tipsy.open')) { return; }
                                    if (opts.changeVisibility) {
                                        if (opts.effect == 'fade') { tip.stop().fadeOut(opts.speed, function () { tip.css({ 'display': 'none' }); }); }
                                        else { tip.css({ 'display': 'none' }); }
                                    } else {
                                        tip.css('top', $.data(tip, 'top'));
                                        tip.css('left', $.data(tip, 'left'));
                                    }
                                }, opts.speed);
                            }
                        });
                    }
                    else {
                        $(this).removeClass('TipsyMenuActive');
                        var self = this;
                        $.data(self, 'tipsy.open', false);
                        setTimeout(function () {
                            if ($.data(self, 'tipsy.open')) { return; }
                            var tip = $.data(self, 'tipsy.menu');
                            if (tip) {
                                if (opts.changeVisibility) {
                                    if (opts.effect == 'fade') { tip.stop().fadeOut(opts.speed, function () { tip.css({ 'display': 'none' }); }); }
                                    else { tip.css({ 'display': 'none' }); }
                                } else {
                                    tip.css('top', $.data(tip, 'top'));
                                    tip.css('left', $.data(tip, 'left'));
                                }
                            }
                        }, opts.speed);

                    }
                });
            }
        });
    };

    // Overwrite this method to provide options on a per-element basis.
    // For example, you could store the gravity in a 'tipsy-gravity' attribute:
    // return $.extend({}, options, { direction: $(ele).attr('tipsy-gravity') || 'n' });
    // (remember - do not modify 'options' in place!)
    $.fn.tipsy.elementOptions = function (ele, options) {
        return $.metadata ? $.extend({}, options, $(ele).metadata()) : options;
    };

    $.fn.tipsy.defaults = {
        customMenu: false,
        changeVisibility: true,
        ensureInScreen: true,
        direction: 'down',
        html: false,
        title: 'title',
        fallback: '',
        fontSize: '8pt',
        textWrap: false,
        width: '250px',
        effect: null, // Supported effects: 'fade', 'slide', 'bounce'
        horizontalMargin: 0,
        verticalMargin: 0,
        showEvent: 'hover',
        showMoreHieght: 0,
        showMoreListType: 'ul',
        showMoreListItem: 'li',
        zIndex: '1000',
        adjustListWidth: false,
        adjustWidthSize: 100,
        speed: 100,
        hoverDelayTime: 250

    };

    $.fn.tipsy.autoNS = function () {
        return $(this).offset().top > ($(document).scrollTop() + $(window).height() / 2) ? 'up' : 'down';
    };

    $.fn.tipsy.autoWE = function () {
        return $(this).offset().left > ($(document).scrollLeft() + $(window).width() / 2) ? 'left' : 'right';
    };

})(jQuery);
