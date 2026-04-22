package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public abstract class Trabajo {
    protected static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final float FACTOR_DIA = 10f;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int horas;
    private Cliente cliente;
    private Vehiculo vehiculo;

    public Trabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        setCliente(cliente);
        setVehiculo(vehiculo);
        setFechaInicio(fechaInicio);
        fechaFin = null;
        horas = 0;
    }

    public Trabajo(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "No se puede copiar un trabajo nulo.");
        cliente = trabajo.getCliente();
        vehiculo = trabajo.getVehiculo();
        fechaInicio = trabajo.getFechaInicio();
        fechaFin = trabajo.getFechaFin();
        horas = trabajo.getHoras();
    }

    public static Trabajo copiar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "El trabajo no puede ser nulo.");
        Trabajo trabajoCopiado = null;
        if (trabajo instanceof Revision revision) {
            trabajoCopiado = new Revision(revision);
        } else if (trabajo instanceof Mecanico mecanico) {
            trabajoCopiado = new Mecanico(mecanico);
        }
        return trabajoCopiado;
    }

    public static Trabajo get(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        return null;
    }

    public Cliente getCliente() {
        return cliente;
    }

    private void setCliente(Cliente cliente) {
        this.cliente = Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    private void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    private void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = Objects.requireNonNull(fechaInicio, "La fecha de inicio no puede ser nula.");
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getHoras() {
        return horas;
    }

    public boolean estaCerrado() {
        return fechaFin != null;
    }

    public void anadirHoras(int horas) throws TallerMecanicoExcepcion {
        if (horas <= 0) {
            throw new IllegalArgumentException("Las horas a añadir deben ser mayores que cero.");
        }
        if (estaCerrado()) {
            throw new TallerMecanicoExcepcion("No se puede añadir horas, ya que el trabajo está cerrado.");
        }
        this.horas += horas;
    }

    public void cerrar(LocalDate fechaFin) throws TallerMecanicoExcepcion {
        if (estaCerrado()) {
            throw new TallerMecanicoExcepcion("El trabajo ya está cerrado.");
        }
        if (fechaFin == null) {
            throw new NullPointerException("La fecha de fin no puede ser nula.");
        }
        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
        this.fechaFin = fechaFin;
    }

    public abstract float getPrecio();

    public abstract float getPrecioEspecifico();

    public abstract void anadirPrecioMaterial(float precioMaterial) throws TallerMecanicoExcepcion;

    private float getPrecioFijo() {
        return FACTOR_DIA * getDias();
    }

    private float getDias() {
        if (fechaFin == null) {
            return 0;
        }
        return fechaInicio.until(fechaFin).getDays();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trabajo trabajo = (Trabajo) o;
        return Objects.equals(getCliente(), trabajo.getCliente()) &&
                Objects.equals(getVehiculo(), trabajo.getVehiculo()) &&
                Objects.equals(getFechaInicio(), trabajo.getFechaInicio());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCliente(), getVehiculo(), getFechaInicio());
    }
}
