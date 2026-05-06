package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;


import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
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

    public List<Trabajo> get() {
        return new ArrayList<>(coleccionTrabajos);
    }

    public List<Trabajo> get(Cliente cliente) {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        List<Trabajo> trabajosCliente = new ArrayList<>();
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getCliente().equals(cliente)) {
                trabajosCliente.add(trabajo);
            }
        }
        return trabajosCliente;
    }

    public List<Trabajo> get(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, VEHICULO_NULO);
        List<Trabajo> trabajosVehiculo = new ArrayList<>();
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getVehiculo().equals(vehiculo)) {
                trabajosVehiculo.add(trabajo);
            }
        }
        return trabajosVehiculo;
    }

    private void comprobarTrabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        Objects.requireNonNull(vehiculo, VEHICULO_NULO);
        Objects.requireNonNull(fechaInicio, "La fecha de inicio no puede ser nula.");
        for (Trabajo trabajo : coleccionTrabajos) {
            if (!trabajo.estaCerrado() && trabajo.getCliente().equals(cliente)) {
                throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo en curso.");
            }
            if (!trabajo.estaCerrado() && trabajo.getVehiculo().equals(vehiculo)) {
                throw new TallerMecanicoExcepcion("El vehículo está actualmente en el taller.");
            }
            if (trabajo.estaCerrado() && !trabajo.getFechaFin().isBefore(fechaInicio) && trabajo.getCliente().equals(cliente)) {
                throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo posterior.");
            }
            if (trabajo.estaCerrado() && !trabajo.getFechaFin().isBefore(fechaInicio) && trabajo.getVehiculo().equals(vehiculo)) {
                throw new TallerMecanicoExcepcion("El vehículo tiene otro trabajo posterior.");
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
        if (trabajo == null) {
            throw new NullPointerException("No se puede insertar un trabajo nulo.");
        }
        comprobarTrabajo(trabajo.getCliente(), trabajo.getVehiculo(), trabajo.getFechaInicio());
        coleccionTrabajos.add(trabajo);
    }

    public Trabajo anadirHoras(Trabajo trabajo, int horas) {
        if (trabajo == null) {
            throw new NullPointerException("No puedo añadir horas a un trabajo nulo.");
        }
        Trabajo trabajoAbierto = getTrabajoAbierto(trabajo.getVehiculo());
        if (trabajoAbierto == null) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }
        trabajoAbierto.anadirHoras(horas);
        return trabajoAbierto;
    }

    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        if (trabajo == null) {
            throw new NullPointerException("No puedo añadir precio del material a un trabajo nulo.");
        }
        Trabajo trabajoAbierto = getTrabajoAbierto(trabajo.getVehiculo());
        if (trabajoAbierto == null) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }
        if (trabajoAbierto instanceof Revision) {
            throw new TallerMecanicoExcepcion("No se puede añadir precio al material para este tipo de trabajos.");
        }
        trabajoAbierto.anadirPrecioMaterial(precioMaterial);
        return trabajoAbierto;
    }

    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) {
        if (trabajo == null) {
            throw new NullPointerException("No puedo cerrar un trabajo nulo.");
        }
        Trabajo trabajoAbierto = getTrabajoAbierto(trabajo.getVehiculo());
        if (trabajoAbierto == null) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }
        trabajoAbierto.cerrar(fechaFin);
        return trabajoAbierto;
    }

    public Trabajo buscar(Trabajo trabajo) {
        if (trabajo == null) {
            throw new NullPointerException("No se puede buscar un trabajo nulo.");
        }
        int indice = coleccionTrabajos.indexOf(trabajo);
        return (indice == -1) ? null : coleccionTrabajos.get(indice);
    }

    public Trabajo borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        if (trabajo == null) {
            throw new NullPointerException("No se puede borrar un trabajo nulo.");
        }
        Trabajo trabajoEncontrado = buscar(trabajo);
        if (trabajoEncontrado != null) {
            coleccionTrabajos.remove(trabajoEncontrado);
            return trabajoEncontrado;
        }
        throw new TallerMecanicoExcepcion("No existe ningún trabajo igual.");
    }

}
