/* global jQuery, Mustache */
jQuery(function ($) {

    var template = $('#alumnoTemplate').html();
    var query = null;
    var $card = null;

    /* Formulario de filtros de búsqueda */

    $('#formBusqueda').submit(function (event) {
        event.preventDefault();
        query = {
            termino: $('#inputBusqueda').val(),
            indicePagina: 0,
            numResultados: 6
        };
        buscarAlumnos();
    });

    function buscarAlumnos() {
        sendSearchRequest('/admin/alumnos/buscar', query, template);
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
        sendRequest('/admin/alumnos/guardar', {
            idAlumno: $card.data('id') || null,
            dni: $('#registroDni').val(),
            nombres: $('#registroNombres').val(),
            apellidos: $('#registroApellidos').val(),
            fechaNacimiento: $('#registroFechaNac').val(),
            nombreUsuarioPadre: $('#registroPadre').val() //data('id') || null
        }, function () {
            // Cerrar el modal y mostrar mensaje de éxito
            $('#registroModal').modal('hide');
            showSuccessMessage('Alumno guardado correctamente.');
            // Volver a cargar los resultados de la búsqueda
            buscarAlumnos();
        });
    });

    /* Eliminar un alumno */

    $('#listaBusqueda').on('click', '.eliminarRegistro', function (event) {
        if (confirm('¿Está seguro(a) de eliminar este usuario?')) {
            // Enviar la petición AJAX
            sendRequest('/admin/alumnos/eliminar', {
                idAlumno: $(event.target).parents('.card').data('id')
            }, function () {
                // Mostrar mensaje de éxito
                showSuccessMessage('Usuario eliminado correctamente.');
                // Volver a cargar los resultados de la búsqueda
                buscarAlumnos();
            }, true);
        }
    });

    /* Enviar el filtro de búsqueda por defecto al cargar la página */
    $('#formBusqueda').submit();

});
