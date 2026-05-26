package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.HashMap;
import java.util.Map;

public enum Evento {
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
    INSERTAR_MECANICO(11, "Insertar mecánico."),
    BUSCAR_TRABAJO(12, "Buscar trabajo"),
    BORRAR_TRABAJO(13, "Borrar trabajo"),
    LISTAR_TRABAJOS(14, "Listar trabajos"),
    LISTAR_TRABAJOS_CLIENTE(15, "Listar trabajos de un cliente"),
    LISTAR_TRABAJOS_VEHICULO(16, "Listar trabajos de un vehículo"),
    ANADIR_HORAS_TRABAJO(17, "Añadir horas a un trabajo"),
    ANADIR_PRECIO_MATERIAL_TRABAJO(18, "Añadir precio material a un trabajo"),
    CERRAR_TRABAJO(19, "Cerrar trabajo"),
    SALIR(20, "Salir");

    private final int numeroOpcion;
    private final String mensaje;

    private static final Map<Integer, Evento> opciones = new HashMap<>();

    static {
        for (Evento opcion : values()) {
            opciones.put(opcion.numeroOpcion, opcion);
        }
    }

    private Evento(int numeroOpcion, String mensaje) {
        this.numeroOpcion = numeroOpcion;
        this.mensaje = mensaje;
    }

    public static boolean esValida(int numeroOpcion) {
        return opciones.containsKey(numeroOpcion);
    }

    public static Evento get(int numeroOpcion) {
        if (!esValida(numeroOpcion)) {
            throw new IllegalArgumentException("Número de opción no válido: " + numeroOpcion);
        }
        return opciones.get(numeroOpcion);
    }

    @Override
    public String toString() {
        return String.format("%d. %s", numeroOpcion, mensaje);
    }
}
