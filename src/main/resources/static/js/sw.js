self.addEventListener('push', function (event) {
    console.log('event', event, 'data', event.data);
    event.waitUntil(self.registration.showNotification('Sistema de Alertas', {
        body: event.data ? event.data.text() : ''
    }));
});
