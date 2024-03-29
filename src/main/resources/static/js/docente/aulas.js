/* global jQuery, Mustache */
jQuery(function ($) {

    var template = $('#aulaTemplate').html();
    var query = null;

    $('#formBusqueda').submit(function (event) {
        event.preventDefault();
        query = {
            termino: $('#inputBusqueda').val(),
            indicePagina: 0,
            numResultados: 4
        };
        buscarAulas();
    });

    function buscarAulas() {
        sendSearchRequest('/aulas/buscar', query, template);
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

    /* Enviar el filtro de búsqueda por defecto al cargar la página */
    $('#formBusqueda').submit();

});
