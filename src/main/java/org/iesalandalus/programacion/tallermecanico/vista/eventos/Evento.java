package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.HashMap;
import java.util.Map;

public enum Evento {
    INSERTAR_CLIENTE(0, "Insertar cliente"),
    BUSCAR_CLIENTE(1, "Buscar cliente"),
    BORRAR_CLIENTE(2, "Borrar cliente"),
    LISTAR_CLIENTES(3, "Listar clientes"),
    MODIFICAR_CLIENTE(4, "Modificar cliente"),
    INSERTAR_VEHICULO(5, "Insertar vehículo"),
    BUSCAR_VEHICULO(6, "Buscar vehículo"),
    BORRAR_VEHICULO(7, "Borrar vehículo"),
    LISTAR_VEHICULOS(8, "Listar vehículos"),
    MODIFICAR_VEHICULO(9, "Modificar vehículo"),
    INSERTAR_REVISION(10, "Insertar revisión"),
    INSERTAR_MECANICO(11, "Insertar mecánico"),
    BUSCAR_TRABAJO(12, "Buscar trabajo"),
    BORRAR_TRABAJO(13, "Borrar trabajo"),
    LISTAR_TRABAJOS(14, "Listar trabajos"),
    LISTAR_TRABAJOS_CLIENTE(15, "Listar trabajos cliente"),
    LISTAR_TRABAJOS_VEHICULO(16, "Listar trabajos vehículo"),
    AÑADIR_HORAS_TRABAJO(17, "Añadir horas trabajo"),
    AÑADIR_PRECIO_MATERIAL_TRABAJO(18, "Añadir precio material trabajo"),
    CERRAR_TRABAJO(19, "Cerrar trabajo"),
    SALIR(20, "Salir");

    private static final Map<Integer, Evento> eventos = new HashMap<>();
    private final int codigo;
    private final String texto;

    static {
        for (Evento evento : values()) {
            eventos.put(evento.codigo, evento);
        }
    }

    private Evento(int codigo, String texto) {
        this.codigo = codigo;
        this.texto = texto;
    }

    public static boolean esValido(int codigo) {
        return eventos.containsKey(codigo);
    }

    public static Evento get(int codigo) {
        return eventos.get(codigo);
    }

    @Override
    public String toString() {
        return texto;
    }
}
