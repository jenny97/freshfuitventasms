package com.trabajodegrado.freshfruitventas.configuracion;

public class ContextoSesion {
    
    private static ThreadLocal<Object> actualUsuario = new ThreadLocal<>();

    public static void setUsuarioSesion(Object usuario) {
    	actualUsuario.set(usuario);
    }

    public static Object getUsuarioSesion() {
        return actualUsuario.get();
    }
    
}
