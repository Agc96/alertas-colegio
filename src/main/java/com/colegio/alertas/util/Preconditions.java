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

}
