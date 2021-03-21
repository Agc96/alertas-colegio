/* global jQuery, Mustache */
jQuery(function ($) {

    var template = $('#aulaTemplate').html();
    var query = null;

    $('#formBusqueda').submit(function (event) {
        event.preventDefault();
        query = {
            termino: $('#inputBusqueda').val(),
            indicePagina: 0,
            numResultados: 6
        };
        buscarAulas();
    });

    function buscarAulas() {
        sendSearchRequest('/admin/aulas/buscar', query, template);
    }

    $('#paginaAnterior').click(function () {
        if (query.indicePagina > 0) {
            query.indicePagina--;
            buscarAulas();
        }
    });

    $('#paginaSiguiente').click(function () {
        if (query.indicePagina < getIndiceMax()) {
            query.indicePagina++;
            buscarAulas();
        }
    });

    /* Eliminar un aula */

    $('#listaBusqueda').on('click', '.eliminarRegistro', function (event) {
        if (confirm('¿Está seguro(a) de eliminar esta aula?')) {
            // Enviar la petición AJAX
            sendRequest('/admin/aulas/eliminar', {
                idAula: $(event.target).parents('.card').data('id')
            }, function () {
                // Mostrar mensaje de éxito
                showSuccessMessage('Aula eliminada correctamente.');
                // Volver a cargar los resultados de la búsqueda
                buscarAulas();
            }, true);
        }
    });

    /* Enviar el filtro de búsqueda por defecto al cargar la página */
    $('#formBusqueda').submit();

});
