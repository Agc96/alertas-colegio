/* global jQuery, Mustache */
jQuery(function ($) {

    var template = $('#asistenciaTemplate').html();
    var query = null;

    $('#formBusqueda').submit(function (event) {
        event.preventDefault();
        query = {
            idAula: $('#busquedaIdAula').val(),
            termino: $('#inputBusqueda').val(),
            indicePagina: 0,
            numResultados: 6
        };
        buscarAsistencia();
    });

    function buscarAsistencia() {
        sendSearchRequest('/docente/asistencia/buscar', query, template);
    }

    $('#paginaAnterior').click(function () {
        if (query.indicePagina > 0) {
            query.indicePagina--;
            buscarAsistencia();
        }
    });

    $('#paginaSiguiente').click(function () {
        if (query.indicePagina < getIndiceMax()) {
            query.indicePagina++;
            buscarAsistencia();
        }
    });

    /* Eliminar una asistencia */

    $('#listaBusqueda').on('click', '.eliminarAsistencia', function (event) {
        if (confirm('¿Está seguro(a) de eliminar este registro?')) {
            // Enviar la petición AJAX
            sendRequest('/docente/asistencia/eliminar', {
                idAsistencia: $(event.target).parents('.card').data('id')
            }, function () {
                // Mostrar mensaje de éxito
                showSuccessMessage('Registro eliminado correctamente.');
                // Volver a cargar los resultados de la búsqueda
                buscarAsistencia();
            }, true);
        }
    });

    /* Enviar el filtro de búsqueda por defecto al cargar la página */
    $('#formBusqueda').submit();

});
