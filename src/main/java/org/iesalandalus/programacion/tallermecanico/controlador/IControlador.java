package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.ReceptorEventos;

import javax.naming.OperationNotSupportedException;
import java.util.List;

public interface IControlador extends ReceptorEventos {
    void comenzar();

    void terminar();

    void insertar(Cliente cliente) throws OperationNotSupportedException;

    void insertar(Vehiculo vehiculo) throws OperationNotSupportedException;

    void insertar(Revision revision) throws OperationNotSupportedException;

    Cliente buscar(Cliente cliente);

    Vehiculo buscar(Vehiculo vehiculo);

    Revision buscar(Revision revision);

    Cliente modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException;

    void borrar(Cliente cliente) throws OperationNotSupportedException;

    void borrar(Vehiculo vehiculo) throws OperationNotSupportedException;

    Revision borrar(Revision revision) throws OperationNotSupportedException;

    List<Cliente> getClientes();

    List<Vehiculo> getVehiculos();

    List<Revision> getRevisiones();

    List<Revision> getRevisiones(Cliente cliente);

    List<Revision> getRevisiones(Vehiculo vehiculo);

    Revision anadirHoras(Revision revision, int horas) throws OperationNotSupportedException;

    Revision anadirPrecioMaterial(Revision revision, float precioMaterial);

    Revision cerrar(Revision revision, java.time.LocalDate fechaCierre) throws OperationNotSupportedException;
}
