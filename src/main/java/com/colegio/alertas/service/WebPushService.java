package com.colegio.alertas.service;

import com.colegio.alertas.dto.MensajeDto;
import com.colegio.alertas.dto.SuscripcionDto;
import com.colegio.alertas.entity.Alumno;
import com.colegio.alertas.entity.Aula;
import com.colegio.alertas.entity.Suscripcion;
import com.colegio.alertas.entity.Usuario;
import com.colegio.alertas.repository.AlumnoRepository;
import com.colegio.alertas.repository.SuscripcionRepository;
import com.colegio.alertas.repository.UsuarioRepository;
import com.colegio.alertas.util.AppException;
import com.colegio.alertas.util.DtoUtils;
import com.colegio.alertas.util.Preconditions;
import com.colegio.alertas.util.SecurityUtils;
import com.colegio.alertas.util.enums.TipoWebPush;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushAsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sistema de Alertas
 */
@Service
public class WebPushService {

    private static final Logger LOG = Logger.getLogger(WebPushService.class.getName());

    @Value("${webpush.public}")
    private String publicKey;

    @Value("${webpush.private}")
    private String privateKey;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    private PushAsyncService pushService;

    private PushAsyncService getPushService() throws AppException {
        if (pushService == null) {
            try {
                pushService = new PushAsyncService(publicKey, privateKey);
            } catch (GeneralSecurityException ex) {
                String msg = "Hubo un error al iniciar el servicio de notificaciones push.";
                LOG.log(Level.SEVERE, msg + " " + ex.getMessage(), ex);
                throw new AppException(msg, ex);
            }
        }
        return pushService;
    }

    public void suscribir(SuscripcionDto dto) throws AppException {
        // Colocar los datos de la suscripción
        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setEndpoint(Preconditions.checkNotEmpty(dto.getEndpoint(),
                "Falta colocar el endpoint de la suscripción al servicio."));
        suscripcion.setKey(Preconditions.checkNotEmpty(dto.getKey(),
                "Falta colocar la llave pública de la suscripción al servicio."));
        suscripcion.setAuth(Preconditions.checkNotEmpty(dto.getAuth(),
                "Falta colocar el auth de la suscripción al servicio."));
        // Buscar el usuario
        Usuario usuario = usuarioRepository.findByNombreUsuario(SecurityUtils.getUsername());
        if (usuario == null) {
            throw new AppException("No se encontró al usuario con el nombre especificado.");
        }
        suscripcion.setUsuario(usuario);
        // Guardar la suscripción en la base de datos
        suscripcionRepository.save(suscripcion);
    }

    public void desuscribir(String endpoint) throws AppException {
        Suscripcion suscripcion = suscripcionRepository.findByEndpoint(endpoint);
        if (suscripcion == null) {
            throw new AppException("No se encontró la suscripción con el endpoint especificado.");
        }
        suscripcionRepository.delete(suscripcion);
    }

    public void notificar(Usuario destino, TipoWebPush tipo, Object item) {
        List<Suscripcion> suscripciones = suscripcionRepository.buscar(destino.getNombreUsuario());
        if (!Preconditions.isEmpty(suscripciones)) {
            for (Suscripcion suscripcion : suscripciones) {
                try {
                    MensajeDto mensaje = new MensajeDto();
                    mensaje.setTag(tipo.getTag());
                    mensaje.setTitulo(tipo.getTitulo());
                    mensaje.setMensaje(formatearMensaje(destino, tipo, item));
                    // Enviar la notificación usando el servicio de notificaciones push
                    Notification notification = new Notification(suscripcion.getEndpoint(),
                            suscripcion.getKey(), suscripcion.getAuth(),
                            objectMapper.writeValueAsBytes(mensaje));
                    getPushService().send(notification);
                } catch (Exception ex) {
                    LOG.log(Level.WARNING, "Error al enviar la notificación push.", ex);
                }
            }
        }
    }

    private String formatearMensaje(Usuario destino, TipoWebPush tipo, Object item) {
        Usuario origen = usuarioRepository.findByNombreUsuario(SecurityUtils.getUsername());
        switch (tipo) {
            case COMUNICADO:
                Aula aula = (Aula)item;
                return String.format(tipo.getMensaje(),
                        DtoUtils.obtenerNombreCompleto(destino),
                        DtoUtils.obtenerNombreCompleto(origen),
                        aula.getGrado().getNombre(),
                        aula.getAnio().getIdAnio());
            case INCIDENCIA:
            case ENTREVISTA:
                return String.format(tipo.getMensaje(), 
                        DtoUtils.obtenerNombreCompleto(destino),
                        DtoUtils.obtenerNombreCompleto(origen),
                        DtoUtils.obtenerNombreCompleto((Alumno)item));
        }
        return null;
    }

}
