package org.iesalandalus.programacion.tallermecanico.modelo;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Clientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Revisiones;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Vehiculos;

import java.time.LocalDate;
import java.util.*;

public class Modelo {

    Clientes clientes;
    Vehiculos vehiculos;
    Revisiones revisiones;

    public void comenzar() {
        clientes = new Clientes();
        vehiculos = new Vehiculos();
        revisiones = new Revisiones();
    }

    public void terminar() {
        System.out.println("El modelo del taller mecánico ha finalizado correctamente.");
    }

    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        clientes.insertar(new Cliente(cliente));

    }

    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo, "No se puede insertar un vehículo nulo.");
        // No hace falta 'new Vehiculo(vehiculo)' porque es inmutable (ES UN REGISTRO).
        vehiculos.insertar(vehiculo);
    }

    public void insertar(Revision revision) throws TallerMecanicoExcepcion {
        revisiones.insertar(new Revision(revision));

    }

    public Cliente buscar(Cliente cliente) {
        Objects.requireNonNull(cliente, "No se puede buscar un cliente nulo.");
        Cliente encontrado = clientes.buscar(cliente);
        // Si existe, devolvemos una copia para proteger la instancia original
        return (encontrado == null) ? null : new Cliente(encontrado);
    }


    // Busca un vehículo y lo devuelve (no hace falta copia porque es record).

    public Vehiculo buscar(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "No se puede buscar un vehículo nulo.");
        // Al ser record, devolvemos la referencia directamente
        return vehiculos.buscar(vehiculo);
    }


    // Busca una revisión y devuelve una nueva instancia (copia) si existe.

    public Revision buscar(Revision revision) {
        Objects.requireNonNull(revision, "No se puede buscar una revisión nula.");
        Revision encontrada = revisiones.buscar(revision);
        // Si existe, usamos el constructor copia de Revision
        return (encontrada == null) ? null : new Revision(encontrada);
    }


    /**
     * Aquí uso el metodo modificar de la propia clase clientes, el "homólogo".
     */
    public Cliente modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion {
        return clientes.modificar(cliente, nombre, telefono);
    }

    /**
     * En estos 2 metodos he usado para cada uno el método correspondiente proveniente de las otras clases
     **/

    public Revision anadirHoras(Revision revision, int horas) throws TallerMecanicoExcepcion {

        return revisiones.anadirHoras(revision, horas);
    }

    public Revision anadirPrecioMaterial(Revision revision, float precioMaterial) {

        return revisiones.anadirPrecioMaterial(revision, precioMaterial);
    }

    public Revision cerrar(Revision revision, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        // Cerramos la revisión en el negocio (donde se valida la fecha)
        Revision cerrada = revisiones.cerrar(revision, fechaFin);
        // Devolvemos una nueva instancia (constructor copia) para evitar Aliasing
        return new Revision(cerrada);
    }

    /**
     * Esta parte borra un cliente y todas sus revisiones asociadas.
     */
    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        // 1. Obtenemos todas las revisiones de ese cliente
        List<Revision> revisionesCliente = revisiones.get(cliente);

        // 2. Borramos cada una de esas revisiones de la colección global
        for (Revision r : revisionesCliente) {
            revisiones.borrar(r);
        }

        // 3. Finalmente, borramos al cliente de su propia colección
        clientes.borrar(cliente);
    }

    /**
     * Esta parte borra un vehículo y todas sus revisiones asociadas.
     */
    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        // 1. Obtenemos todas las revisiones de ese vehículo
        List<Revision> revisionesVehiculo = revisiones.get(vehiculo);

        // 2. Las borramos de la colección global de revisiones
        for (Revision r : revisionesVehiculo) {
            revisiones.borrar(r);
        }

        // 3. Finalmente, borramos el vehículo
        vehiculos.borrar(vehiculo);
    }

    /**
     * Esta parte borra revisión específica (aquí no hay cascada, solo se borra ella).
     */
    public void borrar(Revision revision) throws TallerMecanicoExcepcion {
        revisiones.borrar(revision);
    }


    public List<Cliente> getClientes() {
        List<Cliente> copias = new ArrayList<>();
        for (Cliente cliente : clientes.get()) {
            copias.add(new Cliente(cliente));
        }
        return copias;
    }

    /**
     * Devuelve una nueva lista con los vehículos.
     * Al ser un record, no hace falta crear nuevas instancias de cada vehículo.
     */
    public List<Vehiculo> getVehiculos() {
        List<Vehiculo> copias = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculos.get()) {
            copias.add(vehiculo);
        }
        return copias;
    }


    public List<Revision> getRevisiones() {
        List<Revision> copiasRevisiones = new ArrayList<>();

        for (Revision revision : revisiones.get()) {
            copiasRevisiones.add(new Revision(revision));
        }
        return copiasRevisiones;
    }

    public List<Revision> getRevisiones(Cliente cliente) {
        List<Revision> copias = new ArrayList<>();
        for (Revision revision : revisiones.get(cliente)) {
            copias.add(new Revision(revision));
        }
        return copias;
    }

    public List<Revision> getRevisiones(Vehiculo vehiculo) {
        List<Revision> copias = new ArrayList<>();
        for (Revision revision : revisiones.get(vehiculo)) {
            copias.add(new Revision(revision));
        }
        return copias;
    }
}


