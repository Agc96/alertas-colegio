/* global jQuery, Mustache */
jQuery(function ($) {

    var template = $('#alumnoTemplate').html();
    var query = null;
    var alumnos = new Set();

    /* Formulario de búsqueda de alumnos */

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
        sendSearchRequest('/admin/alumnos/buscar', query, template, function (alumno) {
            // Buscar si el alumno está registrado en la lista
            if (alumnos.has(alumno.idAlumno)) {
                alumno.checked = true;
            }
        });
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

    /* Checkboxes de la lista de alumnos */

    $('#listaBusqueda').on('change', '.custom-control-input', function () {
        var idAlumno = $(this).parents('.card').data('id');
        // Buscar si el alumno está en la lista de alumnos
        if (this.checked) {
            alumnos.add(idAlumno);
        } else {
            alumnos.delete(idAlumno);
        }
    });

    /* Botón de guardado */

    $('#btnGuardar').click(function () {
        sendRequest('/admin/aulas/guardar', {
            idAula: $('#idAula').val(),
            idAnio: $('#registroAnio').val(),
            idGrado: $('#registroGrado').val(),
            nombreUsuarioDocente: $('#registroDocente').val(),
            alumnos: Array.from(alumnos)
        }, function () {
            showSuccessMessage('Aula guardada correctamente.');
            // Ir a los resultados de la búsqueda
            setTimeout(function () {
                window.location.href = '/admin/aulas';
            }, 1000);
        });
    });

    /* Carga inicial de datos del aula */

    var idAula = $('#idAula').val();
    if (idAula) {
        sendRequest('/admin/aulas/detalle', {
            idAula: idAula
        }, function (response) {
            $('#registroAnio').val(response.idAnio);
            $('#registroGrado').val(response.idGrado);
            $('#registroDocente').val(response.nombreUsuarioDocente);
            alumnos = new Set(response.alumnos);
            $('#formBusqueda').submit();
        }, true);
    } else {
        $('#formBusqueda').submit();
    }

});
