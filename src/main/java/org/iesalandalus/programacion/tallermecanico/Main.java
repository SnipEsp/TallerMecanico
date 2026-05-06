package org.iesalandalus.programacion.tallermecanico;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.FabricaModelo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.FabricaFuenteDatos;
import org.iesalandalus.programacion.tallermecanico.vista.FabricaVista;

public class Main {
    public static void main(String[] args) {
        // Crear el controlador pasándole las fábricas
        Controlador controlador = new Controlador(FabricaModelo.CASCADA, FabricaFuenteDatos.MEMORIA, FabricaVista.TEXTO);
        
        // Iniciar la aplicación
        controlador.comenzar();
        
        // Terminar la aplicación
        controlador.terminar();
    }
}
