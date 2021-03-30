jQuery(function ($) {

    var template = $('#aulaAlumnoTemplate').html();
    var query = null;

    $('#formBusqueda').submit(function (event) {
        event.preventDefault();
        query = {
            termino: $('#inputBusqueda').val(),
            indicePagina: 0,
            numResultados: 4
        };
        buscarAlumnos();
    });

    function buscarAlumnos() {
        sendSearchRequest('/padre/buscar', query, template);
    }

    $('#paginaAnterior').click(function () {
        if (query.indicePagina > 0) {
            query.indicePagina--;
            buscarAlumnos();
        }
    });

    $('#paginaSiguiente').click(function () {
        if (query.indicePagina < getIndiceMax()) {
            query.indicePagina++;
            buscarAlumnos();
        }
    });

    /* Enviar el filtro de búsqueda por defecto al cargar la página */
    $('#formBusqueda').submit();

});