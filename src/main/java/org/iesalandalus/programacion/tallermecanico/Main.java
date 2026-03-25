package org.iesalandalus.programacion.tallermecanico;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;

/**
 * Clase principal que inicia la aplicación del taller mecánico.
 * Crea las instancias de Modelo, Vista y Controlador, y ejecuta la aplicación.
 */
public class Main {
    /**
     * Método principal que inicia la aplicación.
     * Crea las instancias necesarias y delega el control al Controlador.
     *
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        Vista vista = new Vista();
        Modelo modelo = new Modelo();

        Controlador controlador = new Controlador(modelo, vista);
        controlador.comenzar();
    }
}
