package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.controlador.IControlador;

import java.time.Month;

/**
 * Interfaz que define el contrato para las vistas del taller mecánico.
 * Implementa el patrón MVC como la Vista, mostrando menús y gestionando
 * la interacción con el usuario.
 */
public interface IVista {
    /**
     * Establece la referencia al controlador.
     * Permite a la vista comunicarse con el controlador para procesar las operaciones.
     * 
     * @param controlador Controlador a establecer
     */
    void setControlador(IControlador controlador);

    /**
     * Inicia la ejecución de la vista.
     * Comienza el bucle principal del menú interactivo.
     */
    void comenzar();

    /**
     * Finaliza la ejecución de la vista.
     * Muestra un mensaje de despedida al usuario.
     */
    void terminar();

    /**
     * Lee un mes del año desde la entrada del usuario.
     * 
     * @return Mes del año introducido por el usuario
     */
    Month leerMes();

    /**
     * Muestra las estadísticas mensuales de trabajos.
     * 
     * @param mes Mes para el que mostrar las estadísticas
     */
    void mostrarEstadisticasMensuales(Month mes);
}
