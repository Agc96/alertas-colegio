# Configuración de HTTPS

## Generación de la llave SSH

```Bash
keytool -genkeypair -alias alertas_colegio -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore alertas_colegio.p12 -validity 3650
```

## Configuración de application.properties

```Bash
server.ssl.key-store-type=PKCS12
server.ssl.key-store=classpath:keystore/alertas_colegio.p12
server.ssl.key-store-password=alertas_colegio
server.ssl.key-alias=alertas_colegio
```

## Links de interés

- https://developers.google.com/web/fundamentals/push-notifications
- https://www.baeldung.com/spring-boot-https-self-signed-certificate
- https://github.com/web-push-libs/webpush-java
- https://gauntface.github.io/simple-push-demo/
- https://serviceworke.rs/push-payload.html
- https://golb.hplar.ch/2019/08/webpush-java.html
