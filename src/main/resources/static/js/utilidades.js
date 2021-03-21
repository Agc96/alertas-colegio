/* global iziToast, Mustache */
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

    window.sendRequest = function (url, data, callback, onlyOneParam) {
        // Formular la petición AJAX
        var query = {
            data: data,
            type: 'POST',
            dataType: 'json',
            url: url
        };
        if (!onlyOneParam) {
            query.contentType = 'application/json';
            query.data = JSON.stringify(data);
        }
        // Enviar la petición AJAX
        $.ajax(query).done(function (response) {
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

    window.sendSearchRequest = function (url, data, template, callbackBefore, callbackAfter) {
        // Limpiar los resultados de la búsqueda
        $('#listaBusqueda').children().remove();
        $('#resumenBusqueda').text('Espere...');
        $('#paginationWrapper').addClass('d-none');
        // Enviar la petición AJAX
        sendRequest(url, data, function (response) {
            if (response.total > 0) {
                // Actualizar resultados de la búsqueda
                $('#resumenBusqueda').html('Se encontraron <strong>' + response.total + '</strong> resultados.');
                $.each(response.lista, function (_, item) {
                    if (callbackBefore) callbackBefore(item);
                    $('#listaBusqueda').append(Mustache.render(template, item));
                    if (callbackAfter) callbackAfter(item);
                });
                // Actualizar paginado
                $('#paginationWrapper').removeClass('d-none');
                $('#numPaginado').text(data.indicePagina + 1);
                $('#totalPaginado').text(response.numPaginas);
            } else {
                // Actualizar resultados de la búsqueda
                $('#resumenBusqueda').text('No se encontraron resultados.');
                // Actualizar paginado
                $('#numPaginado, #totalPaginado').text(0);
            }
        });
    };

    window.getIndiceMax = function () {
        return parseInt($('#totalPaginado').text()) - 1;
    };

});
