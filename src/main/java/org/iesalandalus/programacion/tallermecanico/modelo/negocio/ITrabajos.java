package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.TipoTrabajo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ITrabajos {
    List<Trabajo> get();

    List<Trabajo> get(Cliente cliente);

    List<Trabajo> get(Vehiculo vehiculo);

    void insertar(Trabajo trabajo);

    Trabajo anadirHoras(Trabajo trabajo, int horas);

    Trabajo getTrabajoAbierto(Vehiculo vehiculo);

    Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial);

    Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin);

    Trabajo buscar(Trabajo trabajo);

    Trabajo borrar(Trabajo trabajo) throws TallerMecanicoExcepcion;

    Map<TipoTrabajo, Integer> getEstadisticasMensuales();
}
