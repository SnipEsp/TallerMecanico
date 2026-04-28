package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.controlador.IControlador;

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
}
