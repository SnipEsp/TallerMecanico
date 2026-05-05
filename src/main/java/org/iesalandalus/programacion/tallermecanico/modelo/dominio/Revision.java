package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Revision extends Trabajo {
    private static final float FACTOR_HORA = 35f;

    public Revision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        super(cliente, vehiculo, fechaInicio);
    }

    public Revision(Revision revision) {
        super(revision);
    }

    public Revision(Trabajo trabajo) {
        super(trabajo);
        Objects.requireNonNull(trabajo, "El trabajo no puede ser nulo.");
        if (!(trabajo instanceof Revision)) {
            throw new IllegalArgumentException("El trabajo debe ser de tipo Revision.");
        }
    }

    @Override
    public float getPrecio() {
        if (!estaCerrado()) {
            return 0;
        }
        return (getHoras() * FACTOR_HORA);
    }

    @Override
    public float getPrecioEspecifico() {
        return (estaCerrado()) ? FACTOR_HORA * getHoras() : 0;
    }

    @Override
    public void anadirPrecioMaterial(float precioMaterial) throws TallerMecanicoExcepcion {
        throw new TallerMecanicoExcepcion("No se puede añadir precio al material para este tipo de trabajos.");
    }

    @Override
    public String toString() {
        String cadena;
        if (!estaCerrado()) {
            cadena = String.format("Revision -> %s - %s (%s - ): %d horas", getCliente(), getVehiculo(), getFechaInicio().format(FORMATO_FECHA));
        } else {
            cadena = String.format("Revision -> %s - %s (%s - %s): %d horas, %.2f € total", getCliente(), getVehiculo(), getFechaInicio().format(FORMATO_FECHA), getFechaFin().format(FORMATO_FECHA), getHoras(), getPrecio());
        }
        return cadena;
    }
}
