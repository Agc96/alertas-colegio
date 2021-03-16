/* global Mustache */
jQuery(function($) {

    var template = $('#resultadoTemplate').html();
    var query = null;
    var maxPages = 0;
    var $card = null;

    /* Formulario de filtros de búsqueda */

    $('#formBusqueda').submit(function (event) {
        event.preventDefault();
        query = {
            termino: $('#inputBusqueda').val(),
            numPagina: 0,
            numResultados: 6
        };
        enviarFiltros();
    });

    function enviarFiltros() {
        // Limpiar los resultados de la búsqueda
        $('#listaBusqueda').children().remove();
        $('#resumenBusqueda').text('Espere...');
        $('.paginationWrapper').addClass('d-none');
        // Enviar la petición AJAX
        sendRequest('/admin/alumnos/buscar', query, function (response) {
            if (response.total > 0) {
                // Actualizar resultados de la búsqueda
                $('#resumenBusqueda').html('Se encontraron <strong>' + response.total + '</strong> resultados.');
                $.each(response.lista, function (_, item) {
                    $('#listaBusqueda').append(Mustache.render(template, item));
                });
                // Actualizar paginado
                $('.paginationWrapper').removeClass('d-none');
                maxPages = Math.ceil(response.total / query.numResultados);
                $('.numPaginado').text(query.numPagina + 1);
                $('.totalPaginado').text(maxPages);
            } else {
                // Actualizar resultados de la búsqueda
                $('#resumenBusqueda').text('No se encontraron resultados.');
                // Actualizar paginado
                maxPages = 0;
            }
        });
    }

    $('.paginaAnterior').click(function () {
        if (query.numPagina > 0) {
            query.numPagina--;
            enviarFiltros();
        }
    });

    $('.paginaSiguiente').click(function () {
        if (query.numPagina < maxPages) {
            query.numPagina++;
            enviarFiltros();
        }
    });

    /* Crear o eliminar un alumno */

    $('#registroModal').on('show.bs.modal', function (event) {
        $card = $(event.relatedTarget).parents('.card');
        if ($card.length) {
            // Colocar los datos del registro para editar
            $('#registroDni').val($card.find('.busquedaDni').text());
            $('#registroNombres').val($card.find('.busquedaNombres').text());
            $('#registroApellidos').val($card.find('.busquedaApellidos').text());
            $('#registroFechaNac').val($card.find('.busquedaFechaNac').text());
            $('#registroPadre').val($card.find('.busquedaPadre').data('id'));
        } else {
            // Limpiar los inputs para registrar
            $('#registroModal').find('input, select').val(null);
        }
    });

    $('#registroForm').submit(function (event) {
        event.preventDefault();
        sendRequest('/admin/alumnos/registrar', {
            idAlumno: $card.data('id') || null,
            dni: $('#registroDni').val(),
            nombres: $('#registroNombres').val(),
            apellidos: $('#registroApellidos').val(),
            fechaNacimiento: $('#registroFechaNac').val(),
            idPadre: $('#registroPadre').val() //data('id') || null
        }, function () {
            // Cerrar el modal y mostrar mensaje de éxito
            $('#registroModal').modal('hide');
            showSuccessMessage('Alumno guardado correctamente.');
            // Volver a cargar los resultados de la búsqueda
            enviarFiltros();
        });
    });

    /* Eliminar un alumno */

    $('#listaBusqueda').on('click', '.eliminarRegistro', function (event) {
        if (confirm('¿Está seguro(a) de eliminar este usuario?')) {
            sendRequest('/admin/alumnos/eliminar', {
                idAlumno: $(event.target).parents('.card').data('id')
            }, function () {
                // Mostrar mensaje de éxito
                showSuccessMessage('Usuario eliminado correctamente.');
                // Volver a cargar los resultados de la búsqueda
                enviarFiltros();
            });
        }
    });

    /* Enviar el filtro de búsqueda por defecto al cargar la página */
    $('#formBusqueda').submit();

});
