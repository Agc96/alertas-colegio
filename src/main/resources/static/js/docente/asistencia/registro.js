/* global jQuery, Mustache */
jQuery(function ($) {

    var template = $('#alumnoTemplate').html();
    var query = null;
    var detalles = [];

    /* Formulario de búsqueda de alumnos */

    $('#formBusqueda').submit(function (event) {
        event.preventDefault();
        query = {
            idAula: $('#registroIdAula').val(),
            termino: $('#inputBusqueda').val(),
            indicePagina: 0,
            numResultados: 6
        };
        buscarAlumnos();
    });

    function buscarAlumnos() {
        sendSearchRequest('/docente/alumnos/buscar', query, template, null, function (alumno) {
            // Buscar el alumno por ID para colocar el estado
            for (var i = 0; i < detalles.length; i++) {
                if (detalles[i].idAlumno === alumno.idAlumno) {
                    var estado = detalles[i].idEstadoAsistencia;
                    $('#registroEstado' + alumno.idAlumno).val(estado);
                    return;
                }
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

    $('#listaBusqueda').on('change', '.registroEstado', function () {
        var idAlumno = $(this).parents('.card').data('id');
        var estado = this.value;
        // Buscar el alumno por ID para actualizar su estado
        for (var i = 0; i < detalles.length; i++) {
            if (detalles[i].idAlumno === idAlumno) {
                detalles[i].idEstadoAsistencia = estado;
                return;
            }
        }
        // Si no se encontró el alumno, agregar al final de la lista
        detalles.push({
            idDetalleAsistencia: null,
            idAlumno: idAlumno,
            idEstadoAsistencia: estado
        });
    });

    /* Botón de guardado */

    $('#btnGuardar').click(function () {
        var idAula = $('#registroIdAula').val();
        // Enviar la petición AJAX
        sendRequest('/docente/asistencia/guardar', {
            idAsistencia: $('#registroIdAsistencia').val(),
            idAula: idAula,
            fecha: $('#registroFecha').val(),
            detalles: detalles
        }, function () {
            showSuccessMessage('Asistencia guardada correctamente.');
            setTimeout(function () {
                window.location.href = '/aulas/' + idAula + '/asistencia';
            }, 1000);
        });
    });

    /* Carga inicial de datos del aula */

    var idAsistencia = $('#registroIdAsistencia').val();
    if (idAsistencia) {
        sendRequest('/docente/asistencia/detalle', {
            idAsistencia: idAsistencia
        }, function (response) {
            console.log(response);
            $('#registroFecha').val(response.fecha);
            detalles = response.detalles;
            $('#formBusqueda').submit();
        }, true);
    } else {
        $('#formBusqueda').submit();
    }

    /* TEST*/

    window.getDetalles = function () {
        return detalles;
    };

});
