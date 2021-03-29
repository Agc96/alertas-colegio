package com.colegio.alertas.util;

import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Sistema de Alertas
 */
public final class Preconditions {

    private Preconditions() {
        // Nadie debe ser capaz de instanciar esta clase.
    }

    public static boolean isEmpty(String text) {
        return text == null || text.isEmpty();
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(Collection<?> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isValidDni(String dni) {
        return !isEmpty(dni) && dni.matches("\\d+") && dni.length() == 8;
    }

    public static <T> T checkNotNull(T object, String message) throws AppException {
        if (object == null) {
            throw new AppException(message);
        }
        return object;
    }

    public static String checkNotEmpty(String text, String message) throws AppException {
        if (isEmpty(text)) {
            throw new AppException(message);
        }
        return text;
    }

    public static String checkDni(String dni, String msgEmpty, String msgInvalid) throws AppException {
        checkNotEmpty(dni, msgEmpty);
        if (!isValidDni(dni)) {
            throw new AppException(msgInvalid);
        }
        return dni;
    }

}
