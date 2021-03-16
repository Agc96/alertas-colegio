/* global iziToast */
jQuery(function ($) {

    /* Configurar los botones con tooltip */

    $('[data-toggle=tooltip]').tooltip();

    /* Configurar los enlaces */

    $('a[href="#"]').click(function (event) {
        event.preventDefault();
    });

    /* Configurar los inputs tipo fecha */

    if ($.fn.datepicker) {
        $('.dateInput').datepicker({
            dateFormat: 'dd/mm/yy',
            monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio',
                'Agosto', 'Setiembre', 'Octubre', 'Noviembre', 'Diciembre'],
            dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
            dayNamesMin: ['Dom', 'Lun', 'Mar', 'Mié', 'Jue', 'Vie', 'Sáb'],
            prevText: 'Anterior',
            nextText: 'Siguiente',
            showOtherMonths: true,
            selectOtherMonths: true
        });
    }

    /* Notificaciones toast */

    window.showMessage = function (type, message, title, icon) {
        iziToast.show({
            class: type || '',
            title: title || '',
            message: message || '',
            animateInside: false,
            position: 'topRight',
            progressBar: false,
            icon: icon || '',
            timeout: 3200,
            transitionIn: 'fadeInLeft',
            transitionOut: 'fadeOut',
            transitionInMobile: 'fadeIn',
            transitionOutMobile: 'fadeOut'
        });
    };

    window.showErrorMessage = function (message) {
        showMessage('bg-danger', message, 'Error', 'fa fa-ban');
    };

    window.showSuccessMessage = function (message) {
        showMessage('bg-success', message, 'Éxito', 'fa fa-check');
    };

    /* Pantalla de cargado */

    window.loadingStart = function () {
        // TODO
    };

    window.loadingStop = function () {
        // TODO
    };

    /* Envío de peticiones AJAX */

    window.sendRequest = function (url, data, callback) {
        $.ajax({
            data: data,
            type: 'POST',
            dataType: 'json',
            url: url
        }).done(function (response) {
            if (response.error) {
                showErrorMessage(response.mensaje);
            } else {
                callback(response);
            }
        }).fail(function (error) {
            console.log(error);
            showErrorMessage('Hubo un error desconocido en el servidor.');
        });
    };

});
