package org.iesalandalus.programacion.tallermecanico.modelo;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.TipoTrabajo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface Modelo {
    void comenzar();

    void terminar();

    void insertar(Cliente cliente) throws TallerMecanicoExcepcion;

    void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion;

    void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion;

    Cliente buscar(Cliente cliente);

    Vehiculo buscar(Vehiculo vehiculo);

    Revision buscar(Revision revision);

    Trabajo buscar(Trabajo trabajo);

    Cliente modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion;

    Revision anadirHoras(Revision revision, int horas) throws TallerMecanicoExcepcion;

    Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion;

    Trabajo anadirHoras(Vehiculo vehiculo, int horas) throws TallerMecanicoExcepcion;

    Revision anadirPrecioMaterial(Revision revision, float precioMaterial);

    Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion;

    Trabajo anadirPrecioMaterial(Vehiculo vehiculo, float precioMaterial) throws TallerMecanicoExcepcion;

    Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion;

    Trabajo cerrar(Vehiculo vehiculo, LocalDate fechaFin) throws TallerMecanicoExcepcion;

    void borrar(Cliente cliente) throws TallerMecanicoExcepcion;

    void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion;

    Trabajo borrar(Trabajo trabajo) throws TallerMecanicoExcepcion;

    List<Cliente> getClientes();

    List<Vehiculo> getVehiculos();

    List<Trabajo> getTrabajos();

    List<Trabajo> getTrabajos(Cliente cliente);

    List<Trabajo> getTrabajos(Vehiculo vehiculo);

    Map<TipoTrabajo, Integer> getEstadisticasMensuales();

}
