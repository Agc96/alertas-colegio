/* global jQuery, Mustache */
jQuery(function ($) {

    var template = $('#resultadoTemplate').html();
    var query = null;
    var maxPages = 0;
    var $card = null;

    /* Formulario de búsqueda */

    $('#formBusqueda').submit(function (event) {
        event.preventDefault();
        query = {
            termino: $('#inputBusqueda').val(),
            numPagina: 0,
            numResultados: 6
        };
        enviarFiltros();
    });

    function enviarFiltros() {
        // Limpiar los resultados de la búsqueda
        $('#listaBusqueda').children().remove();
        $('#resumenBusqueda').text('Espere...');
        $('.paginationWrapper').addClass('d-none');
        // Enviar la petición AJAX
        sendRequest('/admin/usuarios/buscar', query, function (response) {
            if (response.total > 0) {
                // Actualizar resultados de la búsqueda
                $('#resumenBusqueda').html('Se encontraron <strong>' + response.total + '</strong> resultados.');
                $.each(response.lista, function (_, usuario) {
                    $('#listaBusqueda').append(Mustache.render(template, usuario));
                });
                // Actualizar paginado
                $('.paginationWrapper').removeClass('d-none');
                maxPages = Math.ceil(response.total / query.numResultados);
                $('.numPaginado').text(query.numPagina + 1);
                $('.totalPaginado').text(maxPages);
            } else {
                // Actualizar resultados de la búsqueda
                $('#resumenBusqueda').text('No se encontraron resultados.');
            }
        });
    }

    $('.paginaAnterior').click(function () {
        if (query.numPagina > 0) {
            query.numPagina--;
            enviarFiltros();
        }
    });

    $('.paginaSiguiente').click(function () {
        if (query.numPagina < maxPages) {
            query.numPagina++;
            enviarFiltros();
        }
    });

    /* Crear o eliminar un usuario */

    $('#registroModal').on('show.bs.modal', function (event) {
        $card = $(event.relatedTarget).parents('.card');
        if ($card.length) {
            // Colocar los datos del registro para editar
            $('#registroNombreUsuario').val($card.find('.busquedaNombreUsuario').text());
            $('#registroDni').val($card.find('.busquedaDni').text());
            $('#registroNombres').val($card.find('.busquedaNombres').text());
            $('#registroApellidos').val($card.find('.busquedaApellidos').text());
            $('#registroTipoUsuario').val($card.find('.busquedaTipoUsuario').data('id'));
            // Desactivar edición de nombre de usuario y contraseña
            $('#registroNombreUsuario, #registroContrasenia').prop('disabled', true);
        } else {
            // Limpiar los inputs para registrar
            $('#registroModal').find('input, select').val(null);
            // Activar edición de nombre de usuario y contraseña
            $('#registroNombreUsuario, #registroContrasenia').prop('disabled', false);
        }
    });

    $('#registroForm').submit(function (event) {
        event.preventDefault();
        sendRequest('/admin/usuarios/registrar', {
            idUsuario: $card.data('id') || null,
            nombreUsuario: $('#registroNombreUsuario').val(),
            contrasenia: $('#registroContrasenia').val(),
            dni: $('#registroDni').val(),
            nombres: $('#registroNombres').val(),
            apellidos: $('#registroApellidos').val(),
            idTipoUsuario: $('#registroTipoUsuario').val()
        }, function () {
            // Cerrar el modal y mostrar mensaje de éxito
            $('#registroModal').modal('hide');
            showSuccessMessage('Usuario guardado correctamente.');
            // Volver a cargar los resultados de la búsqueda
            enviarFiltros();
        });
    });

    /* Eliminar un usuario */

    $('#listaBusqueda').on('click', '.eliminarRegistro', function (event) {
        if (confirm('¿Está seguro(a) de eliminar este usuario?')) {
            sendRequest('/admin/usuarios/eliminar', {
                idUsuario: $(event.target).parents('.card').data('id')
            }, function () {
                // Mostrar mensaje de éxito
                showSuccessMessage('Usuario eliminado correctamente.');
                // Volver a cargar los resultados de la búsqueda
                enviarFiltros();
            });
        }
    });

    /* Enviar el filtro de búsqueda por defecto al cargar la página */
    $('#formBusqueda').submit();

});
