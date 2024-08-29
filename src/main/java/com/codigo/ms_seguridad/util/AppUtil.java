package com.codigo.ms_seguridad.util;

public class AppUtil {

    public static boolean isNotNullOrEmpty(String value){
        return value != null && !value.trim().isEmpty();
    }
}
