jQuery(function ($) {

    /* Verificar que exista el servicio de push notifications */

    if (!navigator.serviceWorker) {
        $('#msgErrorNotificacion').removeClass('d-none');
        $('#msgNotificacion, #btnNotificacion').remove();
    }

    /* Solicitar al usuario para que active el servicio de push notifications */

    $('#btnNotificacion').click(function () {
        navigator.serviceWorker.register('/js/sw.js');

        navigator.serviceWorker.ready.then(function (registration) {
            // TODO
        });
    });

});
