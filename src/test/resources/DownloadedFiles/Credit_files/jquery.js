/* AAG NS:5651 2/9/2012
extending jquery ui dialogs to have minimize and maximize button on jquery dialogs
*/
(function ($) {
    var _init = $.ui.dialog.prototype._init;
    var moveToTop = $.ui.dialog.prototype.moveToTop;
    $.extend($.ui.dialog.prototype.options, { minimize: false, disableMoveToTop: false });

    //Custom Dialog Init
    $.ui.dialog.prototype._init = function () {
        var self = this;
        _init.apply(this, arguments);


        if (this.options.modal || this.options.minimize) {
            /* Original styles for minimize
            var szAvDialogStyle = ".ui-dialog .ui-dialog-titlebar-min{ position: absolute; right: 23px; top: 50%; width: 19px; margin: -10px 0 0 0; padding: 1px; height: 18px; } \n " +
            ".ui-dialog .ui-dialog-titlebar-min span { display: block; margin: 1px; } \n " +
            ".ui-dialog .ui-dialog-titlebar-min:hover, .ui-dialog .ui-dialog-titlebar-min:focus { padding: 0; } \n " +
            ".ui-dialog .ui-dialog-titlebar-rest{ position: absolute; right: 23px; top: 50%; width: 19px; margin: -10px 0 0 0; padding: 1px; height: 18px; } \n " +
            ".ui-dialog .ui-dialog-titlebar-rest span { display: block; margin: 1px; } \n " +
            ".ui-dialog .ui-dialog-titlebar-rest:hover, .ui-dialog .ui-dialog-titlebar-rest:focus { padding: 0; } \n ";
            $('<style type="text/css">' + szAvDialogStyle + '</style>').appendTo("Head"); */
            //Reference to the titlebar
            uiDialogTitlebar = this.uiDialogTitlebar;

            //Save the height of the titlebar for the minimize operation
            if (this.options.modal)
                this.titlebarHeight = parseInt(uiDialogTitlebar.css('height').replace("px", "")) + parseInt(uiDialogTitlebar.css('padding-top').replace("px", "")) + parseInt(uiDialogTitlebar.css('padding-bottom').replace("px", "")) + parseInt(uiDialogTitlebar.css('margin-top').replace("px", "")) + parseInt(uiDialogTitlebar.css('margin-bottom').replace("px", "")) + parseInt(this.uiDialog.css('padding-top').replace("px", ""));
            else //don't include margin
                this.titlebarHeight = parseInt(uiDialogTitlebar.css('height').replace("px", "")) + parseInt(uiDialogTitlebar.css('padding-top').replace("px", "")) + parseInt(uiDialogTitlebar.css('padding-bottom').replace("px", "")) + parseInt(this.uiDialog.css('padding-top').replace("px", ""));

            //we need two variables to preserve the original width and height so that can be restored.
            this.options.originalWidth = this.options.width;
            this.options.originalHeight = this.options.height;
            this.originalTop = this.uiDialog.css("top");
            this.originalLeft = this.uiDialog.css("left");
            this.originalPosition = this.uiDialog.css("position");

            //save a reference to the resizable handle so we can hide it when necessary.
            this.resizeableHandle = this.uiDialog.resizable().find('.ui-resizable-se');

            uiDialogTitlebar.append('<a href="#" id="dialog-minimize" class="dialog-minimize ui-dialog-titlebar-min"><span class="ui-icon ui-icon-minusthick"></span></a>');
            uiDialogTitlebar.append('<a href="#"class="dialog-restore ui-dialog-titlebar-rest"><span class="ui-icon ui-icon-newwin"></span></a>');

            //Minimize Button
            this.uiDialogTitlebarMin = $('.dialog-minimize', uiDialogTitlebar).hover(function () {
                $(this).addClass('ui-state-hover');
            }, function () {
                $(this).removeClass('ui-state-hover');
            }).click(function () {
                self.minimize();
                return false;
            });

            //Restore Button
            this.uiDialogTitlebarRest = $('.dialog-restore', uiDialogTitlebar).hover(function () {
                $(this).addClass('ui-state-hover');
            }, function () {
                $(this).removeClass('ui-state-hover');
            }).click(function () {
                self.restore();
                self.moveToTop(true);
                return false;
            }).hide();
        }

    };

    $.ui.dialog.prototype.moveToTop = function () {
        if (this.options.disableMoveToTop)
            return false;
        else {
            moveToTop.apply(this,arguments);
        }
    };

    $.extend($.ui.dialog.prototype, {
        restore: function () {

            //restore resizable functionality
            this.uiDialog.resizable("option", "disabled", false);
            //show the resizeable handle
            this.resizeableHandle.show();

            var nCurrentLeft = parseInt(this.uiDialog.css("left").replace("px"));
            //restore the orignal dimensions
            this.uiDialog.removeClass('dialog-minimize');
            this.uiDialog.css({ width: this.options.originalWidth, height: this.options.originalHeight, top: this.originalTop, left: this.originalLeft, position: this.originalPosition, bottom: '' })
            //show the dialog content
            this.element.show();

            //swap the buttons
            this.uiDialogTitlebarRest.hide();
            this.uiDialogTitlebarMin.show();
            for(var nI =0; nI < $('.dialog-restore').length; nI++ ) {

                if ($($('.dialog-restore')[nI]).css("display").toLowerCase() != "none") {
                    var nDialogLeft = parseInt($($('.dialog-restore')[nI]).parents("div.ui-dialog:first").css("left").replace("px", ""));
                    if(nDialogLeft > nCurrentLeft)
                        $($('.dialog-restore')[nI]).parents("div.ui-dialog:first").css("left", (nDialogLeft - 175));
                }
            }
        },
        minimize: function () {
            //disable resizable
            this.uiDialog.resizable("option", "disabled", true);
            this.resizeableHandle.hide();

            //Store the original height/width
            this.options.originalWidth = this.options.width;
            if (this.options.originalHeight != 'auto')
                this.options.originalHeight = this.options.height;
            this.originalTop = this.uiDialog.css("top");
            this.originalLeft = this.uiDialog.css("left");
            this.originalPosition = this.uiDialog.css("position");

            var szNewTop = window.innerHeight - this.titlebarHeight;
            var szOriginalOverflow = $(document.body).css("overflow");
            $(document.body).css("overflow","auto");
            if (window.innerWidth < document.body.scrollWidth)//AAG acount for bottom scrollbar
                szNewTop = szNewTop - 20;
            $(document.body).css("overflow", szOriginalOverflow);
            for(var nI =0; nI < $('.dialog-restore').length; nI++ ) {
                
                if ($($('.dialog-restore')[nI]).css("display").toLowerCase() != "none") {
                    $($('.dialog-restore')[nI]).parents("div.ui-dialog:first").css("left", (parseInt($($('.dialog-restore')[nI]).parents("div.ui-dialog:first").css("left").replace("px","")) +175));
                }
            }

            //collapse dialog
            this.uiDialog.addClass('avdialog-minimize');
            this.uiDialog.animate({ height: this.titlebarHeight, width: '175px',top: szNewTop, left: 0, bottom: 0 }, 200).css("position", "fixed");
            
            setTimeout('$(\'.avdialog-minimize\').css(\'top\',\'auto\');', 201);
            
            //hide the content
            //this.element.hide();

            //swap buttons to show restore
            this.uiDialogTitlebarMin.hide();
            this.uiDialogTitlebarRest.show();
            this.uiDialog.removeClass("ui-state-disabled");
        }
    });
})(jQuery);