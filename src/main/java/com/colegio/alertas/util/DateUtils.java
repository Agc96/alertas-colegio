package com.colegio.alertas.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sistema de Alertas
 */
public final class DateUtils {

    private static final Logger LOG = Logger.getLogger(DateUtils.class.getName());

    public static final String DDMMYYYY = "dd/MM/yyyy";
    public static final String YYYYMMDD = "yyyy-MM-dd";

    private DateUtils() {
        // Nadie debe ser capaz de instanciar esta clase.
    }

    public static String format(Date date) {
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(DDMMYYYY);
            formatter.setLenient(false);
            return formatter.format(date);
        }
        return null;
    }

    public static Date parse(String string) {
        if (string != null) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat(DDMMYYYY);
                formatter.setLenient(false);
                return formatter.parse(string);
            } catch (ParseException ex) {
                LOG.log(Level.WARNING, "No se pudo convertir la fecha", ex);
            }
        }
        return null;
    }

}
