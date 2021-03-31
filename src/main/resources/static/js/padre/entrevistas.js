/* global jQuery, Mustache */
jQuery(function ($) {

    var template = $('#entrevistaTemplate').html();
    var query = null;
    var $card = null;

    /* Formulario de filtros de búsqueda */

    $('#formBusqueda').submit(function (event) {
        event.preventDefault();
        query = {
            idAula: $('#busquedaIdAula').val(),
            idAlumno: $('#busquedaIdAlumno').val(),
            termino: $('#inputBusqueda').val(),
            indicePagina: 0,
            numResultados: 6
        };
        buscarEntrevistas();
    });

    function buscarEntrevistas() {
        sendSearchRequest('/padre/entrevistas/buscar', query, template);
    }

    $('#paginaAnterior').click(function () {
        if (query.indicePagina > 0) {
            query.indicePagina--;
            buscarEntrevistas();
        }
    });

    $('#paginaSiguiente').click(function () {
        if (query.indicePagina < getIndiceMax()) {
            query.indicePagina++;
            buscarEntrevistas();
        }
    });

    /* Crear o eliminar un entrevista */

    $('#registroModal').on('show.bs.modal', function (event) {
        $card = $(event.relatedTarget).parents('.card');
        if ($card.length) {
            $('#registroFecha').val($card.find('.busquedaFecha').text());
            $('#registroMotivo').val($card.find('.busquedaMotivo').data('texto'));
        } else {
            $('#registroModal').find('input, select, textarea').val(null);
        }
    });

    $('#registroForm').submit(function (event) {
        event.preventDefault();
        sendRequest('/padre/entrevistas/guardar', {
            idEntrevista: $card.data('id') || null,
            idAula: $('#busquedaIdAula').val(),
            idAlumno: $('#busquedaIdAlumno').val(),
            fecha: $('#registroFecha').val(),
            motivo: $('#registroMotivo').val()
        }, function () {
            $('#registroModal').modal('hide');
            showSuccessMessage('Entrevista guardada correctamente.');
            buscarEntrevistas();
        });
    });

    /* Eliminar un entrevista */

    $('#listaBusqueda').on('click', '.eliminarRegistro', function (event) {
        if (confirm('¿Está seguro(a) de eliminar esta entrevista?')) {
            sendRequest('/padre/entrevistas/eliminar', {
                idEntrevista: $(event.target).parents('.card').data('id')
            }, function () {
                showSuccessMessage('Entrevista eliminada correctamente.');
                buscarEntrevistas();
            }, true);
        }
    });

    /* Enviar el filtro de búsqueda por defecto al cargar la página */
    $('#formBusqueda').submit();

});
