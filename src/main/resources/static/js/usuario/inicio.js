/* global vapidKey, Notification, Promise */
jQuery(function ($) {

    /* Verificar que se pueda ejecutar el servicio de notificaciones push */

    if (navigator.serviceWorker && window.PushManager) {
        navigator.serviceWorker.register('/webpush.js').then(function (registration) {
            // Verificar si tenemos el permiso del usuario para mostrar notificaciones
            if (Notification.permission === 'denied') {
                mostrarErrorWebPush('Usted ha denegado el permiso para mostrar notificaciones.');
            } else {
                // Verificar si tenemos una suscripción activa
                registration.pushManager.getSubscription().then(function (subscription) {
                    console.log('[LOG] subscription:', subscription);
                    actualizarBotonWebPush(subscription);
                });
            }
            return registration;
        }).catch (function (err) {
            mostrarErrorWebPush('No se pudo registrar el servicio de notificaciones push.', err);
        });
    } else {
        mostrarErrorWebPush('Su navegador Web no permite el uso de notificaciones push.');
    }

    function mostrarErrorWebPush(mensaje, error) {
        if (error) console.error(mensaje, error);
        $('#webPushDisponible, #webPushButton').addClass('d-none');
        $('#webPushError').removeClass('d-none').text(mensaje);
    }

    function actualizarBotonWebPush(subscription) {
        if (subscription) {
            $('#webPushButton').off('click').click(desuscribir).text('Desactivar');
        } else {
            $('#webPushButton').off('click').click(suscribir).text('Activar');
        }
    }

    /* Suscribir al usuario al servicio de notificaciones push */

    function suscribir() {
        return new Promise(function (resolve, reject) {
            var permissionResult = Notification.requestPermission(function (result) {
                resolve(result);
            });
            if (permissionResult) {
                permissionResult.then(resolve, reject);
            }
        }).then(function (permissionResult) {
            if (permissionResult === 'granted') {
                suscribirConPermiso();
            } else if (permissionResult === 'denied') {
                showErrorMessage('Usted ha denegado el permiso para mostrar notificaciones.');
            } else {
                showErrorMessage('Deberá aceptar el permiso si desea que se muestren las notificaciones.');
            }
        });
    }

    function suscribirConPermiso() {
        navigator.serviceWorker.ready.then(function (registration) {
            return registration.pushManager.subscribe({
                userVisibleOnly: true,
                applicationServerKey: urlBase64ToUint8Array(vapidKey)
            });
        }).then(function (subscription) {
            // Enviar la petición AJAX
            var query = convertirSuscripcion(subscription);
            sendRequest('/webpush/suscribir', query, function () {
                showSuccessMessage('Notificaciones push activadas.');
            });
            // Modificar botón de activar o desactivar notificaciones
            actualizarBotonWebPush(true);
        });
    }

    function convertirSuscripcion(subscription) {
        var json = subscription.toJSON();
        return {
            endpoint: json.endpoint,
            key: json.keys.p256dh,
            auth: json.keys.auth
        };
    }

    /* Botón para desuscribirse del servicio de notificaciones push */

    function desuscribir() {
        navigator.serviceWorker.ready.then(function (registration) {
            return registration.pushManager.getSubscription();
        }).then(function (subscription) {
            return subscription.unsubscribe().then(function () {
                console.log('[LOG] subscription:', subscription);
                // Enviar la petición AJAX
                var query = convertirSuscripcion(subscription);
                sendRequest('/webpush/desuscribir', query, function () {
                    showSuccessMessage('Notificaciones push desactivadas correctamente.');
                });
                // Modificar botón de activar o desactivar notificaciones
                actualizarBotonWebPush(false);
            });
        });
    }

    /* Función auxiliar para convertir la llave pública VAPID a un arreglo de bytes */

    function urlBase64ToUint8Array(base64String) {
        var padding = '='.repeat((4 - base64String.length % 4) % 4);
        var base64 = (base64String + padding).replace(/\-/g, '+').replace(/_/g, '/');
        var rawData = window.atob(base64);
        var outputArray = new Uint8Array(rawData.length);
        for (var i = 0; i < rawData.length; ++i) {
            outputArray[i] = rawData.charCodeAt(i);
        }
        return outputArray;
    }

});
