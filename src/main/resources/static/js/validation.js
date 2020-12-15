// Validate Edit Car
let editIdError;
let editMarkError;
let editModelError;
function validateEditCar() {
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

// validate add Car
let addMarkError;
let addModelError;
function validateAddCar() {
    // mark
    let modelAddValue = $('#markAddCar').val();
    if (modelAddValue.length === 0) {
        $('#markAddMessage').show();
        addMarkError = true;
    } else {
        $('#markAddMessage').hide();
        addMarkError = false;
    }
    // model
    let markAddValue = $('#modelAddCar').val();
    if (markAddValue.length === 0) {
        $('#modelAddMessage').show();
        addModelError = true;
    } else {
        $('#modelAddMessage').hide();
        addModelError = false;
    }
    return !(addMarkError || addModelError );
}

//validate modify Field
let editFieldIDError;
let editFieldValueError;
function validateEditField() {
    // id
    let modifyFieldIDValue = $('#idEditField').val();
    if (modifyFieldIDValue.length === 0) {
        $('#idEditFieldMessage').show();
        editFieldIDError = true;
    } else {
        $('#idEditFieldMessage').hide();
        editFieldIDError = false;
    }
    // value
    let modifyFieldValueValue = $('#valueModifyField').val();
    if (modifyFieldValueValue.length === 0) {
        $('#valueEditFieldMessage').show();
        editFieldValueError = true;
    } else {
        $('#valueEditFieldMessage').hide();
        editFieldValueError = false;
    }
    return !(editFieldIDError || editFieldValueError );
}

// Submit edit Car
$('#submitEdit').click(function () {
    return validateEditCar();
});

// Submit edit Car
$('#submitAdd').click(function () {
    return validateAddCar();
});

// Submit edit field
$('#modifyField').click(function () {
    return validateEditField();
});

// toggleAddModal on click
$('#toggleAddModal').click(function () {
    $('#markAddMessage').hide();
    $('#modelAddMessage').hide();
});