// 유효성 검사 함수
function validateField(fieldName, fieldValue, url, displayFieldError, formId, buttonId) {
  $.ajax({
        type: 'POST',
        url: url,
        contentType: 'application/json',
        data: JSON.stringify({ fieldName: fieldName, fieldValue: fieldValue }),
        success: function(errors) {
            displayFieldError(formId, fieldName, errors);
            validateForm(formId, buttonId);
        },
        error: function(xhr) {
            console.error('Validation Error: ' + xhr.statusText);
        }
    });
}

// 전체 폼의 유효성 검사
function validateForm(formId, buttonId) {
    var formError = $('#' + formId + ' .error').text().trim();
    if (formError === '') {
        $('#' + buttonId).prop('disabled', false);
    } else {
        $('#' + buttonId).prop('disabled', true);
    }
}

// 개별 필드 에러 메시지 표시 함수
function displayFieldError(formId, fieldName, errors) {
   	if (errors[fieldName]) {
        $('#' + formId + ' #' + fieldName + 'Error').html('<div>' + errors[fieldName][0] + '</div>').css('color', 'red');
    } else {
        $('#' + formId + ' #' + fieldName + 'Error').empty();
    }
}

// 전체 에러 메시지 표시 함수
function displayErrors(formId, errors) {
    var fields = Object.keys(errors);
    fields.forEach(function(fieldName) {
        if (errors[fieldName]) {
            $('#' + formId + ' #' + fieldName + 'Error').html('<div>' + errors[fieldName][0] + '</div>').css('color', 'red');
        } else {
            $('#' + formId + ' #' + fieldName + 'Error').empty();
        }
    });
}
