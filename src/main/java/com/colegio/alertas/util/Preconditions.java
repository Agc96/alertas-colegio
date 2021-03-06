package com.colegio.alertas.util;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Anthony Gutiérrez
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

    public static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

}
