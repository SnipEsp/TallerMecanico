package org.iesalandalus.programacion.tallermecanico;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.FabricaModelo;
import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.FabricaFuenteDatos;
import org.iesalandalus.programacion.tallermecanico.vista.FabricaVista;
import org.iesalandalus.programacion.tallermecanico.vista.IVista;

public class Main {
    public static void main(String[] args) {
        // Crear el modelo usando la fábrica de modelos
        Modelo modelo = FabricaModelo.CASCADA.crear(FabricaFuenteDatos.MEMORIA);
        
        // Crear la vista usando la fábrica de vistas
        IVista vista = FabricaVista.TEXTO.crear();
        
        // Crear el controlador con el modelo y la vista
        Controlador controlador = new Controlador(modelo, vista);
        
        // Iniciar la aplicación
        controlador.comenzar();
        
        // Terminar la aplicación
        controlador.terminar();
    }
}
