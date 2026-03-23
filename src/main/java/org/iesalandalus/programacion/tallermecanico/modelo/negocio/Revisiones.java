package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Revisiones {

    private List<Revision> listaRevisiones;

    public Revisiones() {
        listaRevisiones = new ArrayList<>();
    }

    public List<Revision> get() {
        return new ArrayList<>(listaRevisiones);
    }

    public List<Revision> get(Cliente cliente) {
        ArrayList<Revision> revisionesCliente = new ArrayList<>();
        for (Revision revision : listaRevisiones) {
            if (revision.getCliente().equals(cliente)) {
                revisionesCliente.add(revision);
            }
        }
        return revisionesCliente;
    }

    public List<Revision> get(Vehiculo vehiculo) {
        ArrayList<Revision> revisionesVehiculo = new ArrayList<>();
        for (Revision revision : listaRevisiones) {
            if (revision.getVehiculo().equals(vehiculo)) {
                revisionesVehiculo.add(revision);
            }
        }
        return revisionesVehiculo;
    }

    public void insertar(Revision revision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision, "No se puede insertar una revisión nula.");
        comprobarRevision(revision.getCliente(), revision.getVehiculo(), revision.getFechaInicio());
        listaRevisiones.add(revision);
    }

    private void comprobarRevision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) throws TallerMecanicoExcepcion {
        for (Revision revision : listaRevisiones) {
            // Comprobaciones para el cliente
            if (revision.getCliente().equals(cliente)) {
                if (!revision.estaCerrada()) {
                    throw new TallerMecanicoExcepcion("El cliente tiene otra revisión en curso.");
                } else if (revision.getFechaFin().isAfter(fechaInicio) || revision.getFechaFin().isEqual(fechaInicio)) {
                    throw new TallerMecanicoExcepcion("El cliente tiene una revisión posterior.");
                }
            }
            // Comprobaciones para el vehículo
            if (revision.getVehiculo().equals(vehiculo)) {
                if (!revision.estaCerrada()) {
                    throw new TallerMecanicoExcepcion("El vehículo está actualmente en revisión.");
                } else if (revision.getFechaFin().isAfter(fechaInicio) || revision.getFechaFin().isEqual(fechaInicio)) {
                    throw new TallerMecanicoExcepcion("El vehículo tiene una revisión posterior.");
                }
            }
        }
    }

    public Revision anadirHoras(Revision revision, int horas) throws TallerMecanicoExcepcion {
        // 1. Buscamos la revisión real en nuestra lista
        // Usamos el método getRevision auxiliar (el que lanza excepción si no existe)
        Revision encontrada = getRevision(revision);

        // 2. Le pedimos a la instancia 'encontrada' que sume las horas
        // Este método 'anadirHoras' de la clase Revision (singular) es el que es VOID
        encontrada.anadirHoras(horas);

        // 3. Devolvemos la revisión ya modificada
        return encontrada;
    }

    public Revision anadirPrecioMaterial(Revision revision, float precioMaterial) throws TallerMecanicoExcepcion {
        // 1. Buscamos la revisión real en nuestra lista
        // Usamos el método getRevision auxiliar (el que lanza excepción si no existe)
        Revision encontrada = getRevision(revision);

        // 2. Le pedimos a la instancia 'encontrada' que sume las horas
        // Este método 'añadirPrecioMaterial' de la clase Revision (singular) es el que es VOID
        encontrada.anadirPrecioMaterial(precioMaterial);

        // 3. Devolvemos la revisión ya modificada
        return encontrada;
    }

    public Revision cerrar(Revision revision, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        // 1. Buscamos la revisión real en nuestra lista
        // Usamos el método getRevision auxiliar (el que lanza excepción si no existe)
        Revision encontrada = getRevision(revision);

        // 2. Le pedimos a la instancia 'encontrada' que sume las horas
        // Este método 'cerrar' de la clase Revision (singular) es el que es VOID
        encontrada.cerrar(fechaFin);

        // 3. Devolvemos la revisión ya modificada
        return encontrada;
    }

    private Revision getRevision(Revision revision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision, "No puedo operar sobre una revisión nula.");
        int indice = listaRevisiones.indexOf(revision);
        if (indice == -1) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }
        return listaRevisiones.get(indice);
    }

    public Revision buscar(Revision revision) {
        Objects.requireNonNull(revision, "No se puede buscar una revisión nula.");
        int indice = listaRevisiones.indexOf(revision);
        return (indice == -1) ? null : listaRevisiones.get(indice);
    }

    public void borrar(Revision revision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision, "No se puede borrar una revisión nula.");
        if (!listaRevisiones.remove(revision)) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }
    }
}