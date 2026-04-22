package org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria;


import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trabajos implements ITrabajos {
    private final List<Trabajo> coleccionTrabajos;
    public static final String VEHICULO_NULO = "El vehículo no puede ser nulo.";
    public static final String TRABAJO_NULO = "El trabajo no puede ser nulo.";

    public Trabajos() {
        coleccionTrabajos = new ArrayList<>();
    }

    public Trabajo[] get() {
        return coleccionTrabajos.toArray(new Trabajo[0]);
    }

    public Trabajo[] get(Cliente cliente) {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        List<Trabajo> trabajosCliente = new ArrayList<>();
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getCliente().equals(cliente)) {
                trabajosCliente.add(trabajo);
            }
        }
        return trabajosCliente.toArray(new Trabajo[0]);
    }

    public Trabajo[] get(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, VEHICULO_NULO);
        List<Trabajo> trabajosVehiculo = new ArrayList<>();
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getVehiculo().equals(vehiculo)) {
                trabajosVehiculo.add(trabajo);
            }
        }
        return trabajosVehiculo.toArray(new Trabajo[0]);
    }

    private void comprobarTrabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        Objects.requireNonNull(vehiculo, VEHICULO_NULO);
        Objects.requireNonNull(fechaInicio, "La fecha de inicio no puede ser nula.");
        for (Trabajo trabajo : coleccionTrabajos) {
            if (!trabajo.estaCerrado() && trabajo.getCliente().equals(cliente) && trabajo.getVehiculo().equals(vehiculo)) {
                throw new IllegalArgumentException("Ya existe un trabajo abierto para ese cliente y vehículo.");
            }
        }
    }

    public Trabajo getTrabajoAbierto(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, VEHICULO_NULO);
        for (Trabajo trabajo : coleccionTrabajos) {
            if (!trabajo.estaCerrado() && trabajo.getVehiculo().equals(vehiculo)) {
                return trabajo;
            }
        }
        return null;
    }

    public void insertar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, TRABAJO_NULO);
        comprobarTrabajo(trabajo.getCliente(), trabajo.getVehiculo(), trabajo.getFechaInicio());
        coleccionTrabajos.add(trabajo);
    }

    public Trabajo anadirHoras(Trabajo trabajo, int horas) {
        Objects.requireNonNull(trabajo, TRABAJO_NULO);
        Trabajo trabajoAbierto = getTrabajoAbierto(trabajo.getVehiculo());
        if (trabajoAbierto == null) {
            throw new IllegalArgumentException("No existe un trabajo abierto para el vehículo indicado.");
        }
        trabajoAbierto.anadirHoras(horas);
        return trabajoAbierto;
    }

    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) {
        Objects.requireNonNull(trabajo, TRABAJO_NULO);
        Trabajo trabajoAbierto = getTrabajoAbierto(trabajo.getVehiculo());
        if (trabajoAbierto == null) {
            throw new IllegalArgumentException("No existe un trabajo abierto para el vehículo indicado.");
        }
        trabajoAbierto.anadirPrecioMaterial(precioMaterial);
        return trabajoAbierto;
    }

    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) {
        Objects.requireNonNull(trabajo, TRABAJO_NULO);
        Trabajo trabajoAbierto = getTrabajoAbierto(trabajo.getVehiculo());
        if (trabajoAbierto == null) {
            throw new IllegalArgumentException("No existe un trabajo abierto para el vehículo indicado.");
        }
        trabajoAbierto.cerrar(fechaFin);
        return trabajoAbierto;
    }

    public Trabajo buscar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, TRABAJO_NULO);
        int indice = coleccionTrabajos.indexOf(trabajo);
        return (indice == -1) ? null : coleccionTrabajos.get(indice);
    }

    public Trabajo borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, TRABAJO_NULO);
        Trabajo trabajoEncontrado = buscar(trabajo);
        if (trabajoEncontrado != null) {
            coleccionTrabajos.remove(trabajoEncontrado);
            return trabajoEncontrado;
        }
        throw new TallerMecanicoExcepcion("No existe el trabajo a borrar.");
    }

}
