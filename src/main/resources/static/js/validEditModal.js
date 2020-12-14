// Validate Edit Car
let editIdError;
let editMarkError;
let editModelError;

function validateEditSail() {
    // id
    let idEditValue = $('#idUpdate').val();
    if (idEditValue.length === 0) {
        $('#idEditMessage').show();
        editIdError = true;
    } else {
        $('#idEditMessage').hide();
        editIdError = false;
    }
    // mark
    let modelEditValue = $('#markUpdate').val();
    if (modelEditValue.length === 0) {
        $('#markEditMessage').show();
        editMarkError = true;
    } else {
        $('#markEditMessage').hide();
        editMarkError = false;
    }
    // model
    let markEditValue = $('#modelUpdate').val();
    if (markEditValue.length === 0) {
        $('#modelEditMessage').show();
        editModelError = true;
    } else {
        $('#modelEditMessage').hide();
        editModelError = false;
    }
    return !(editIdError ||editMarkError || editModelError );

}

// Submit add Car
$('#submitEdit').click(function () {
    return validateEditSail();
});
