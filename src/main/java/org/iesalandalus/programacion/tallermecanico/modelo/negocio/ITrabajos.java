package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import java.time.LocalDate;

public interface ITrabajos {
    Trabajo[] get();
    Trabajo[] get(Cliente cliente);
    Trabajo[] get(Vehiculo vehiculo);
    void insertar(Trabajo trabajo);
    Trabajo anadirHoras(Trabajo trabajo, int horas);
    Trabajo getTrabajoAbierto(Vehiculo vehiculo);
    Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial);
    Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin);
    Trabajo buscar(Trabajo trabajo);
    Trabajo borrar(Trabajo trabajo);
}
