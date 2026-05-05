package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.vista.texto.VistaTexto;

/**
 * Enumeración que implementa el patrón Factory Method para la creación de vistas.
 * Permite crear diferentes tipos de vistas (texto, gráfica, etc.) de forma centralizada.
 */
public enum FabricaVista {
    /**
     * Instancia que crea una vista de texto por consola.
     */
    TEXTO;

    /**
     * Crea una instancia de la vista correspondiente al tipo de fábrica.
     * 
     * @return Instancia de la vista creada
     */
    public IVista crear() {
        return new VistaTexto();
    }
}
