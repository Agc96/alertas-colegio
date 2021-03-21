/* global jQuery, Mustache */
jQuery(function ($) {

    var template = $('#incidenciaTemplate').html();
    var query = null;
    var $card = null;

    /* Formulario de filtros de búsqueda */

    $('#formBusqueda').submit(function (event) {
        event.preventDefault();
        query = {
            idAula: $('#busquedaIdAula').val(),
            termino: $('#inputBusqueda').val(),
            indicePagina: 0,
            numResultados: 6
        };
        buscarIncidencias();
    });

    function buscarIncidencias() {
        sendSearchRequest('/docente/incidencias/buscar', query, template);
    }

    $('#paginaAnterior').click(function () {
        if (query.indicePagina > 0) {
            query.indicePagina--;
            buscarIncidencias();
        }
    });

    $('#paginaSiguiente').click(function () {
        if (query.indicePagina < getIndiceMax()) {
            query.indicePagina++;
            buscarIncidencias();
        }
    });

    /* Crear o eliminar un incidencia */

    $('#registroModal').on('show.bs.modal', function (event) {
        $card = $(event.relatedTarget).parents('.card');
        if ($card.length) {
            // Colocar los datos del registro para editar
            $('#registroFecha').val($card.find('.busquedaFecha').text());
            $('#registroAlumno').val($card.find('.busquedaAlumno').data('id'));
            $('#registroDescripcion').val($card.find('.busquedaDescripcion').data('texto'));
        } else {
            // Limpiar los inputs para registrar
            $('#registroModal').find('input, select, textarea').val(null);
        }
    });

    $('#registroForm').submit(function (event) {
        event.preventDefault();
        sendRequest('/docente/incidencias/guardar', {
            idIncidencia: $card.data('id') || null,
            idAula: $('#busquedaIdAula').val(),
            fecha: $('#registroFecha').val(),
            idAlumno: $('#registroAlumno').val(),
            descripcion: $('#registroDescripcion').val()
        }, function () {
            // Cerrar el modal y mostrar mensaje de éxito
            $('#registroModal').modal('hide');
            showSuccessMessage('Incidencia guardada correctamente.');
            // Volver a cargar los resultados de la búsqueda
            buscarIncidencias();
        });
    });

    /* Eliminar un incidencia */

    $('#listaBusqueda').on('click', '.eliminarIncidencia', function (event) {
        if (confirm('¿Está seguro(a) de eliminar esta incidencia?')) {
            // Enviar la petición AJAX
            sendRequest('/docente/incidencias/eliminar', {
                idIncidencia: $(event.target).parents('.card').data('id')
            }, function () {
                // Mostrar mensaje de éxito
                showSuccessMessage('Incidencia eliminada correctamente.');
                // Volver a cargar los resultados de la búsqueda
                buscarIncidencias();
            }, true);
        }
    });

    /* Enviar el filtro de búsqueda por defecto al cargar la página */
    $('#formBusqueda').submit();

});
