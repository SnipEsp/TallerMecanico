package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public abstract class Trabajo {
    static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final float FACTOR_DIA = 10f;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int horas;
    private Cliente cliente;
    private Vehiculo vehiculo;

    protected Trabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        setCliente(cliente);
        setVehiculo(vehiculo);
        setFechaInicio(fechaInicio);
    }

    protected Trabajo(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "El trabajo no puede ser nulo.");
        cliente = new Cliente(trabajo.cliente);
        vehiculo = trabajo.vehiculo;
        fechaInicio = trabajo.fechaInicio;
        fechaFin = trabajo.fechaFin;
        horas = trabajo.horas;
    }

    public static Trabajo copiar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "El trabajo no puede ser nulo.");
        if (trabajo instanceof Mecanico mecanico) {
            return new Mecanico(mecanico);
        } else if (trabajo instanceof Revision revision) {
            return new Revision(revision);
        } else {
            throw new IllegalArgumentException("El tipo de trabajo no es válido.");
        }
    }

    public static Trabajo get(Vehiculo vehiculo) {
        Cliente clienteExistente = new Cliente("Paquito El Chocolatero", "76659888Z", "697655853");
        return new Mecanico(clienteExistente, vehiculo, LocalDate.now());
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
        if (fechaInicio.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser futura.");
        }
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    private void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = Objects.requireNonNull(fechaFin, "La fecha de fin no puede ser nula.");
        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
        if (fechaFin.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser futura.");
        }
    }

    public int getHoras() {
        return horas;
    }

    public void anadirHoras(int horas) throws TallerMecanicoExcepcion {
        if (horas <= 0) {
            throw new IllegalArgumentException("Las horas a añadir deben ser mayores que cero.");
        } else if (estaCerrado()) {
            throw new TallerMecanicoExcepcion("No se puede añadir horas, ya que el trabajo está cerrado.");
        }
        this.horas += horas;
    }

    public boolean estaCerrado() {
        return fechaFin != null;
    }

    public void cerrar(LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(fechaFin, "La fecha de fin no puede ser nula.");
        if (estaCerrado()) {
            throw new TallerMecanicoExcepcion("El trabajo ya está cerrado.");
        }
        setFechaFin(fechaFin);
    }

    public float getPrecio() {
        return getPrecioEspecifico() + getPrecioFijo();
    }

    private float getPrecioFijo() {
        return FACTOR_DIA * getDias();
    }

    private float getDias() {
        if (fechaFin == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(fechaInicio, fechaFin);
    }

    public abstract float getPrecioEspecifico();

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Trabajo trabajo)) return false;
        return Objects.equals(fechaInicio, trabajo.fechaInicio) && Objects.equals(cliente, trabajo.cliente) && Objects.equals(vehiculo, trabajo.vehiculo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fechaInicio, cliente, vehiculo);
    }
}

