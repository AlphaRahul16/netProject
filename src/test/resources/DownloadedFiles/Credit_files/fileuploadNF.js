///File upload script
///D. Smith
///09/2013

function pageLoad() {
    AddFileUpload();
}

function AddFileUpload() {
    $('.av-fileupload-async').fileupload({
        dataType: 'json',
        done: function (e, data) {
            var jsonObj = $.parseJSON(data.result);
            $.each(jsonObj, function (index, file) {
                $("#uploadedfiles").append($('<li/>').text(file.name));
            });
            $(".progress").hide();

            var relatedControlName = $(this).attr('id').replace('__async', '');

            if (relatedControlName) {
                if ($('#' + relatedControlName).val() != "") {
                    var currentValue = $('#' + relatedControlName).val();
                    var currentValueObj = $.parseJSON(currentValue);
                    currentValueObj = currentValueObj.concat(jsonObj);
                    $('#' + relatedControlName).val(JSON.stringify(currentValueObj));
                } else {
                    $('#' + relatedControlName).val(data.result);
                }
            }
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('.progress .bar').css('width', progress + '%');
        },
        start: function () {
            $(".progress").show();
        },
        fail: function (e, data) {
            $(".progress").hide();
            $("#uploadedfiles").append($('<li/>').text('Failed to upload file'));
        }
    }).prop('disabled', !$.support.fileInput)
        .parent().addClass($.support.fileInput ? undefined : 'disabled');
}