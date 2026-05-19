package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Mecanico;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.TipoTrabajo;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.ReceptorEventos;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IControlador extends ReceptorEventos {
    void comenzar();

    void terminar();

    void insertar(Cliente cliente) throws OperationNotSupportedException;

    void insertar(Vehiculo vehiculo) throws OperationNotSupportedException;

    void insertar(Revision revision) throws OperationNotSupportedException;

    void insertar(Mecanico mecanico) throws OperationNotSupportedException;

    Cliente buscar(Cliente cliente);

    Vehiculo buscar(Vehiculo vehiculo);

    Revision buscar(Revision revision);

    Mecanico buscar(Mecanico mecanico);

    Cliente modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException;

    void borrar(Cliente cliente) throws OperationNotSupportedException;

    void borrar(Vehiculo vehiculo) throws OperationNotSupportedException;

    Revision borrar(Revision revision) throws OperationNotSupportedException;

    Mecanico borrar(Mecanico mecanico) throws OperationNotSupportedException;

    Trabajo borrar(Trabajo trabajo) throws OperationNotSupportedException;

    List<Cliente> getClientes();

    List<Vehiculo> getVehiculos();

    List<Trabajo> getTrabajos();

    List<Trabajo> getTrabajos(Cliente cliente);

    List<Trabajo> getTrabajos(Vehiculo vehiculo);

    List<Revision> getRevisiones();

    List<Revision> getRevisiones(Cliente cliente);

    List<Revision> getRevisiones(Vehiculo vehiculo);

    Revision anadirHoras(Revision revision, int horas) throws OperationNotSupportedException;

    Mecanico anadirHoras(Mecanico mecanico, int horas) throws OperationNotSupportedException;

    Trabajo anadirHoras(Vehiculo vehiculo, int horas) throws OperationNotSupportedException;

    Revision anadirPrecioMaterial(Revision revision, float precioMaterial);

    Mecanico anadirPrecioMaterial(Mecanico mecanico, float precioMaterial) throws OperationNotSupportedException;

    Trabajo anadirPrecioMaterial(Vehiculo vehiculo, float precioMaterial) throws OperationNotSupportedException;

    Revision cerrar(Revision revision, LocalDate fechaCierre) throws OperationNotSupportedException;

    Mecanico cerrar(Mecanico mecanico, LocalDate fechaCierre) throws OperationNotSupportedException;

    Trabajo cerrar(Vehiculo vehiculo, LocalDate fechaCierre) throws OperationNotSupportedException;

    Map<TipoTrabajo, Integer> getEstadisticasMensuales(LocalDate mes);
}
