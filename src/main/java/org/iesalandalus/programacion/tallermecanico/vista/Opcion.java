package org.iesalandalus.programacion.tallermecanico.vista;

import java.util.Map;
import java.util.HashMap;

public enum Opcion {
    INSERTAR_CLIENTE(1, "Insertar cliente"),
    BUSCAR_CLIENTE(2, "Buscar cliente"),
    BORRAR_CLIENTE(3, "Borrar cliente"),
    LISTAR_CLIENTES(4, "Listar clientes"),
    MODIFICAR_CLIENTE(5, "Modificar cliente"),
    INSERTAR_VEHICULO(6, "Insertar vehículo"),
    BUSCAR_VEHICULO(7, "Buscar vehículo"),
    BORRAR_VEHICULO(8, "Borrar vehículo"),
    LISTAR_VEHICULOS(9, "Listar vehículos"),
    INSERTAR_REVISION(10, "Insertar revisión"),
    BUSCAR_REVISION(11, "Buscar revisión"),
    BORRAR_REVISION(12, "Borrar revisión"),
    LISTAR_REVISIONES(13, "Listar revisiones"),
    LISTAR_REVISIONES_CLIENTE(14, "Listar revisiones cliente"),
    LISTAR_REVISIONES_VEHICULO(15, "Listar revisiones vehículo"),
    ANADIR_HORAS_REVISION(16, "Añadir horas revisión"),
    ANADIR_PRECIO_MATERIAL_REVISION(17, "Añadir precio material revisión"),
    CERRAR_REVISION(18, "Cerrar revisión"),
    SALIR(19, "Salir");
    
    private final int numeroOpcion;
    private final String mensaje;
    private static final Map<Integer, Opcion> opciones = new HashMap<>();
    
    static {
        for (Opcion opcion : Opcion.values()) {
            opciones.put(opcion.numeroOpcion, opcion);
        }
    }
    
    Opcion(int numeroOpcion, String mensaje) {
        this.numeroOpcion = numeroOpcion;
        this.mensaje = mensaje;
    }
    
    public static boolean esValida(int numeroOpcion) {
        return opciones.containsKey(numeroOpcion);
    }
    
    public static Opcion get(int numeroOpcion) {
        if (!esValida(numeroOpcion)) {
            throw new IllegalArgumentException("Opción no válida");
        }
        return opciones.get(numeroOpcion);
    }
    
    @Override
    public String toString() {
        return String.format("%d. %s", numeroOpcion, mensaje);
    }
}
