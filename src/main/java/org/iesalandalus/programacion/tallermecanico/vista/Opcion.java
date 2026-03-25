package org.iesalandalus.programacion.tallermecanico.vista;

import java.util.Map;
import java.util.HashMap;

/**
 * Enumeración que representa todas las opciones disponibles en el menú del taller mecánico.
 * Cada opción tiene un número identificador y un mensaje descriptivo.
 * Proporciona métodos para validar y obtener opciones por su número.
 */
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
    
    /**
     * Número identificador único de cada opción.
     */
    private final int numeroOpcion;
    
    /**
     * Mensaje descriptivo que se muestra en el menú para cada opción.
     */
    private final String mensaje;
    
    /**
     * Mapa estático que almacena todas las opciones indexadas por su número.
     * Permite una búsqueda eficiente de opciones por número.
     */
    private static final Map<Integer, Opcion> opciones = new HashMap<>();
    
    /**
     * Bloque estático que inicializa el mapa de opciones.
     * Recorre todas las constantes del enum y las registra en el mapa.
     */
    static {
        for (Opcion opcion : Opcion.values()) {
            opciones.put(opcion.numeroOpcion, opcion);
        }
    }
    
    /**
     * Constructor privado para las constantes del enum.
     * Inicializa el número y el mensaje descriptivo de cada opción.
     * 
     * @param numeroOpcion Número identificador de la opción
     * @param mensaje Mensaje descriptivo de la opción
     */
    Opcion(int numeroOpcion, String mensaje) {
        this.numeroOpcion = numeroOpcion;
        this.mensaje = mensaje;
    }
    
    /**
     * Verifica si un número de opción es válido.
     * Comprueba si existe una opción con ese número en el mapa.
     * 
     * @param numeroOpcion Número de opción a validar
     * @return true si la opción existe, false en caso contrario
     */
    public static boolean esValida(int numeroOpcion) {
        return opciones.containsKey(numeroOpcion);
    }
    
    /**
     * Obtiene una opción a partir de su número identificador.
     * Valida que el número sea correcto antes de devolver la opción.
     * 
     * @param numeroOpcion Número de la opción a obtener
     * @return Opción correspondiente al número
     * @throws IllegalArgumentException Si el número de opción no es válido
     */
    public static Opcion get(int numeroOpcion) {
        if (!esValida(numeroOpcion)) {
            throw new IllegalArgumentException("Opción no válida");
        }
        return opciones.get(numeroOpcion);
    }
    
    /**
     * Devuelve la representación en cadena de la opción.
     * Formato: "numero. mensaje" para mostrar en el menú.
     * 
     * @return Representación formateada de la opción
     */
    @Override
    public String toString() {
        return String.format("%d. %s", numeroOpcion, mensaje);
    }
}
