$(document).ready(function () {
    $('.table .editButton').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        console.log(href);
        $.get(href, function (car, status) {
            console.log(car);
            $('#idUpdate').val(car.id);
            $('#markUpdate').val(car.mark);
            $('#modelUpdate').val(car.model);
            $('#colorUpdate').val(car.color);
        });
        $('#editModal').modal();
    });

    $('.table .deleteButton').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#deleteModal #idDelete').attr('href', href);
        $('#deleteModal').modal();
    });

});
