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
let addDateError;
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
    // production date
    let dateAddValue = $('#dateAddCar').val();
    if (dateAddValue.length === 0) {
        $('#dateAddMessage').show();
        addDateError = true;
    } else if (!(isValidDate(dateAddValue))) {
        $('#dateAddMessage').html
        ("**should be date format: yyyy-mm-dd");
        $('#dateAddMessage').css("color", "red");
        addDateError = true;
    } else {
        $('#dateAddMessage').hide();
        addDateError = false;
    }
    return !(addMarkError || addModelError || addDateError);
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

// Submit add Car
$('#submitAdd').click(function () {
    return validateAddCar();
});


// toggleAddModal on click
$('#toggleAddModal').click(function () {
    $('#markAddMessage').hide();
    $('#modelAddMessage').hide();
    $('#dateAddMessage').hide();
});