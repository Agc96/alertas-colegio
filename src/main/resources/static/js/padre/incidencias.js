/* global jQuery, Mustache */
jQuery(function ($) {

    var template = $('#incidenciaTemplate').html();
    var query = null;

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
        buscarIncidencias();
    });

    function buscarIncidencias() {
        sendSearchRequest('/padre/incidencias/buscar', query, template);
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

    /* Enviar el filtro de búsqueda por defecto al cargar la página */
    $('#formBusqueda').submit();

});
