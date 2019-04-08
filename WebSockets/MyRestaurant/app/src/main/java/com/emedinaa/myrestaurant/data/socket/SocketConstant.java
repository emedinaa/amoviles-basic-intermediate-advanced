package com.emedinaa.myrestaurant.data.socket;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author : Eduardo Medina
 * @see : https://guides.codepath.com/android/Replacing-Enums-with-Enumerated-Annotations
 * @since : 8/17/18
 */
public class SocketConstant {

    // ... type definitions
    // Describes when the annotation will be discarded
    @Retention(RetentionPolicy.SOURCE)
    // Enumerate valid values for this interface
    @StringDef({EMIT_LOGIN, EMIT_ORDER})
    // Create an interface for validating String types
    public @interface SocketEventDef {}

    // Declare the constants
    public static final String EMIT_LOGIN = "loginUsuario";
    public static final String EMIT_ORDER = "registrarOrdenDeCompra";

    private String event;
    // Mark the argument as restricted to these enumerated types
    public SocketConstant(@SocketEventDef String event) {
        this.event = event;
    }
}
