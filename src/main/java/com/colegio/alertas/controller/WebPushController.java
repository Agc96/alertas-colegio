package com.colegio.alertas.controller;

import com.colegio.alertas.dto.BaseDto;
import com.colegio.alertas.dto.SuscripcionDto;
import com.colegio.alertas.service.WebPushService;
import com.colegio.alertas.util.AppException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Sistema de Alertas
 */
@Controller
public class WebPushController {

    private static final Logger LOG = Logger.getLogger(WebPushController.class.getName());

    @Autowired
    private WebPushService webPushService;

    @PostMapping("/webpush/suscribir")
    @ResponseBody
    public BaseDto suscribir(@RequestBody SuscripcionDto suscripcion) {
        BaseDto response = new BaseDto();
        try {
            webPushService.suscribir(suscripcion);
        } catch (AppException ex) {
            String msg = "No se pudo suscribir el usuario al servicio de notificaciones push.";
            LOG.log(Level.SEVERE, msg + " " + ex.getMessage(), ex);
            response.setError(true, msg);
        }
        return response;
    }

    @PostMapping("/webpush/desuscribir")
    @ResponseBody
    public BaseDto desuscribir(@RequestBody SuscripcionDto suscripcion) {
        BaseDto response = new BaseDto();
        try {
            webPushService.desuscribir(suscripcion.getEndpoint());
        } catch (AppException ex) {
            String msg = "No se pudo desuscribir al usuario del servicio de notificaciones push.";
            LOG.log(Level.SEVERE, msg + " " + ex.getMessage(), ex);
            response.setError(true, msg);
        }
        return response;
    }

}
