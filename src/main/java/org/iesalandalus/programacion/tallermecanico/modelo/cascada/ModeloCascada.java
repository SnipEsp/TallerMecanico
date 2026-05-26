package org.iesalandalus.programacion.tallermecanico.modelo.cascada;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModeloCascada implements Modelo {
    private IClientes clientes;
    private ITrabajos trabajos;
    private IVehiculos vehiculos;

    public ModeloCascada(FabricaFuenteDatos fabricaFuenteDatos) {
        Objects.requireNonNull(fabricaFuenteDatos, "");
        IFuenteDatos fuenteDatos = fabricaFuenteDatos.crear();
        clientes = fuenteDatos.crearClientes();
        trabajos = fuenteDatos.crearTrabajos();
        vehiculos = fuenteDatos.crearVehiculos();
        comenzar();
    }

    @Override
    public void comenzar() {
        System.out.println("Modelo comenzado.");
    }

    @Override
    public void terminar() {
        System.out.println("Ya está, xoxo");
    }

    @Override
    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "No se puede insertar un cliente nulo.");
        clientes.insertar(new Cliente(cliente));
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo, "No se puede insertar un vehículo nulo.");
        vehiculos.insertar(vehiculo);
    }

    @Override
    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede insertar una revisión nula.");
        Cliente cliente = clientes.buscar(trabajo.getCliente());
        Vehiculo vehiculo = vehiculos.buscar(trabajo.getVehiculo());
        if (trabajo instanceof Revision) {
            trabajo = new Revision(cliente, vehiculo, trabajo.getFechaInicio());
        } else if (trabajo instanceof Mecanico) {
            trabajo = new Mecanico(cliente, vehiculo, trabajo.getFechaInicio());
        }
        trabajos.insertar(trabajo);
    }

    @Override
    public Cliente buscar(Cliente cliente) {
        cliente = Objects.requireNonNull(clientes.buscar(cliente), "No se puede buscar un cliente nulo.");
        return new Cliente(cliente);     }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo) {
        vehiculo = Objects.requireNonNull(vehiculos.buscar(vehiculo), "No se puede buscar un vehículo nulo.");
        return vehiculo;
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        Objects.requireNonNull(trabajos.buscar(trabajo), "No se puede buscar una revisión nula.");
        return Trabajo.copiar(trabajo);
    }

    @Override
    public Cliente modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion {
        return new Cliente(clientes.modificar(cliente, nombre, telefono));
    }

    @Override
    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede añadir horas a una revisión nula.");
        return trabajos.anadirHoras(trabajo, horas);
    }

    @Override
    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede añadir material a una revisión nula.");
        return trabajos.anadirPrecioMaterial(trabajo, precioMaterial);
    }

    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        return trabajos.cerrar(trabajo, fechaFin);
    }

    @Override
    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        List<Trabajo> trabajosClientes = trabajos.get(cliente);
        for (Trabajo trabajo : trabajosClientes) {
            trabajos.borrar(trabajo);
        }
        clientes.borrar(cliente);
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        List<Trabajo> trabajosVehiculos = trabajos.get(vehiculo);
        for (Trabajo trabajo : trabajosVehiculos) {
            trabajos.borrar(trabajo);
        }
        vehiculos.borrar(vehiculo);

    }

    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        trabajos.borrar(trabajo);
    }

    @Override
    public List<Cliente> getClientes() {
        List<Cliente> copiaClientes = new ArrayList<>();
        for (Cliente cliente : clientes.get()) {
            copiaClientes.add(new Cliente(cliente));
        }
        return copiaClientes;
    }

    @Override
    public List<Vehiculo> getVehiculos() {
        return vehiculos.get();
    }

    @Override
    public List<Trabajo> getTrabajos() {
        List<Trabajo> copiaTrabajos = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get()) {
            copiaTrabajos.add(Trabajo.copiar(trabajo));
        }
        return copiaTrabajos;
    }

    @Override
    public List<Trabajo> getTrabajos(Cliente cliente) {
        List<Trabajo> revisionesCliente = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(cliente)) {
            revisionesCliente.add(Trabajo.copiar(trabajo));
        }
        return revisionesCliente;
    }

    @Override
    public List<Trabajo> getTrabajos(Vehiculo vehiculo) {
        List<Trabajo> trabajosVehiculo = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(vehiculo)) {
            trabajosVehiculo.add(Trabajo.copiar(Trabajo.get(vehiculo)));
        }
        return trabajosVehiculo;
    }
}
