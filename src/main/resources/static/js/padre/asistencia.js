/* global jQuery, Mustache */
jQuery(function ($) {

    var template = $('#asistenciaTemplate').html();
    var query = null;

    $('#formBusqueda').submit(function (event) {
        event.preventDefault();
        query = {
            idAula: $('#busquedaIdAula').val(),
            idAlumno: $('#busquedaIdAlumno').val(),
            termino: $('#inputBusqueda').val(),
            indicePagina: 0,
            numResultados: 6
        };
        buscarAsistencia();
    });

    function buscarAsistencia() {
        sendSearchRequest('/padre/asistencia/buscar', query, template);
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

    /* Enviar el filtro de búsqueda por defecto al cargar la página */
    $('#formBusqueda').submit();

});
