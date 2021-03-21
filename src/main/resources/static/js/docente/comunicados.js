/* global jQuery, Mustache */
jQuery(function ($) {

    var template = $('#comunicadoTemplate').html();
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
        buscarComunicados();
    });

    function buscarComunicados() {
        sendSearchRequest('/docente/comunicados/buscar', query, template);
    }

    $('#paginaAnterior').click(function () {
        if (query.indicePagina > 0) {
            query.indicePagina--;
            buscarComunicados();
        }
    });

    $('#paginaSiguiente').click(function () {
        if (query.indicePagina < getIndiceMax()) {
            query.indicePagina++;
            buscarComunicados();
        }
    });

    /* Crear o eliminar un comunicado */

    $('#registroModal').on('show.bs.modal', function (event) {
        $card = $(event.relatedTarget).parents('.card');
        if ($card.length) {
            // Colocar los datos del registro para editar
            $('#registroTitulo').val($card.find('.busquedaTitulo').text());
            $('#registroFecha').val($card.find('.busquedaFecha').text());
            $('#registroDescripcion').val($card.find('.busquedaDescripcion').data('texto'));
        } else {
            // Limpiar los inputs para registrar
            $('#registroModal').find('input, select, textarea').val(null);
        }
    });

    $('#registroForm').submit(function (event) {
        event.preventDefault();
        sendRequest('/docente/comunicados/guardar', {
            idComunicado: $card.data('id') || null,
            idAula: $('#busquedaIdAula').val(),
            titulo: $('#registroTitulo').val(),
            fecha: $('#registroFecha').val(),
            descripcion: $('#registroDescripcion').val()
        }, function () {
            // Cerrar el modal y mostrar mensaje de éxito
            $('#registroModal').modal('hide');
            showSuccessMessage('Comunicado guardado correctamente.');
            // Volver a cargar los resultados de la búsqueda
            buscarComunicados();
        });
    });

    /* Eliminar un comunicado */

    $('#listaBusqueda').on('click', '.eliminarComunicado', function (event) {
        if (confirm('¿Está seguro(a) de eliminar este comunicado?')) {
            // Enviar la petición AJAX
            sendRequest('/docente/comunicados/eliminar', {
                idComunicado: $(event.target).parents('.card').data('id')
            }, function () {
                // Mostrar mensaje de éxito
                showSuccessMessage('Comunicado eliminado correctamente.');
                // Volver a cargar los resultados de la búsqueda
                buscarComunicados();
            }, true);
        }
    });

    /* Enviar el filtro de búsqueda por defecto al cargar la página */
    $('#formBusqueda').submit();

});
