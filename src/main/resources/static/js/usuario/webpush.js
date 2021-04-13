/* global self */
self.addEventListener('push', function (event) {
	if (self.Notification.permission === 'granted') {
		var data = event.data ? event.data.json() : {};
		console.log('[LOG] data:', data);
		event.waitUntil(self.registration.showNotification(data.titulo, {
	        body: data.mensaje,
	        tag: data.nombreUsuario
	    }));
	} else {
		console.error('El usuario retiró el permiso de enviar notificaciones.');
	}
});

self.addEventListener('notificationclick', function (event) {
  event.notification.close();
});
