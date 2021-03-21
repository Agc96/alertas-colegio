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
    public static final String DDMMYYYY_HHMM_24 = "dd/MM/yyyy HH:mm";
    public static final String YYYYMMDD_HHMM_24 = "yyyy-MM-dd HH:mm";
    public static final String HHMM_24 = "HH:mm";

    private DateUtils() {
        // Nadie debe ser capaz de instanciar esta clase.
    }

    public static String format(Date date) {
        return format(date, DDMMYYYY);
    }

    public static String format(Date date, String format) {
        return date == null ? null : getFormatter(format).format(date);
    }

    public static Date parse(String string) {
        return parse(string, DDMMYYYY);
    }

    public static Date parse(String string, String format) {
        try {
            return string == null ? null : getFormatter(format).parse(string);
        } catch (ParseException ex) {
            LOG.log(Level.WARNING, "No se pudo convertir la fecha", ex);
            return null;
        }
    }

    private static SimpleDateFormat getFormatter(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setLenient(false);
        return formatter;
    }

}
