/* global jQuery, Mustache */
jQuery(function ($) {

    var template = $('#usuarioTemplate').html();
    var query = null;
    var $card = null;

    /* Formulario de búsqueda */

    $('#formBusqueda').submit(function (event) {
        event.preventDefault();
        query = {
            termino: $('#inputBusqueda').val(),
            indicePagina: 0,
            numResultados: 6
        };
        buscarUsuarios();
    });

    function buscarUsuarios() {
        sendSearchRequest('/admin/usuarios/buscar', query, template);
    }

    $('#paginaAnterior').click(function () {
        if (query.indicePagina > 0) {
            query.indicePagina--;
            buscarUsuarios();
        }
    });

    $('#paginaSiguiente').click(function () {
        if (query.indicePagina < getIndiceMax()) {
            query.indicePagina++;
            buscarUsuarios();
        }
    });

    /* Crear o eliminar un usuario */

    $('#registroModal').on('show.bs.modal', function (event) {
        $card = $(event.relatedTarget).parents('.card');
        if ($card.length) {
            // Colocar los datos del registro para editar
            $('#registroNombreUsuario').val($card.find('.busquedaUserName').text());
            $('#registroDni').val($card.find('.busquedaDni').text());
            $('#registroNombres').val($card.find('.busquedaNombres').text());
            $('#registroApellidos').val($card.find('.busquedaApellidos').text());
            $('#registroRol').val($card.find('.busquedaRol').data('id'));
            // Desactivar edición de nombre de usuario, contraseña y tipo de usuario
            $('#registroNombreUsuario, #registroContrasenia, #registroRol').prop('disabled', true);
            $('#registroRol option[value="1"]').prop('disabled', false);
        } else {
            // Limpiar los inputs para registrar
            $card = null;
            $('#registroModal').find('input, select').val(null);
            // Activar edición de nombre de usuario, contraseña y tipo de usuario
            $('#registroNombreUsuario, #registroContrasenia, #registroRol').prop('disabled', false);
            $('#registroRol option[value="1"]').prop('disabled', true);
        }
    });

    $('#registroForm').submit(function (event) {
        event.preventDefault();
        // Obtener la URL a la cual enviar los datos
        var url = $card ? '/admin/usuarios/editar' : '/admin/usuarios/crear';
        // Obtener los datos a enviar
        var data = {
            nombreUsuario: $('#registroNombreUsuario').val(),
            contrasenia: $('#registroContrasenia').val(),
            dni: $('#registroDni').val(),
            nombres: $('#registroNombres').val(),
            apellidos: $('#registroApellidos').val(),
            idRol: $('#registroRol').val()
        };
        // Enviar la petición AJAX
        sendRequest(url, data, function () {
            // Cerrar el modal y mostrar mensaje de éxito
            $('#registroModal').modal('hide').find('input, select').val(null);
            showSuccessMessage('Usuario guardado correctamente.');
            // Volver a cargar los resultados de la búsqueda
            buscarUsuarios();
        });
    });

    /* Eliminar un usuario */

    $('#listaBusqueda').on('click', '.eliminarRegistro', function (event) {
        if (confirm('¿Está seguro(a) de eliminar este usuario?')) {
            // Enviar la petición AJAX
            sendRequest('/admin/usuarios/eliminar', {
                nombreUsuario: $(event.target).parents('.card').find('.busquedaUserName').text()
            }, function () {
                // Mostrar mensaje de éxito
                showSuccessMessage('Usuario eliminado correctamente.');
                // Volver a cargar los resultados de la búsqueda
                buscarUsuarios();
            }, true);
        }
    });

    /* Enviar el filtro de búsqueda por defecto al cargar la página */
    $('#formBusqueda').submit();

});
