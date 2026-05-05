package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.util.Objects;

public class Vehiculo {
    private static final String ER_MARCA = "[A-Z][a-z]+(?:[- ]?[A-Z][a-z]+)?|[A-Z]+";
    private static final String ER_MATRICULA = "\\d{4}[^\\W_AEIOUa-z]{3}";
    private String marca;
    private String modelo;
    private String matricula;

    public Vehiculo(String marca, String modelo, String matricula) {
        validarMarca(marca);
        validarModelo(modelo);
        validarMatricula(matricula);
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
    }

    private void validarMarca(String marca) {
        if (marca == null) {
            throw new NullPointerException("La marca no puede ser nula.");
        } else if (!marca.matches(ER_MARCA)) {
            throw new IllegalArgumentException("La marca no tiene un formato válido.");
        }
    }

    private void validarModelo(String modelo) {
        if (modelo == null) {
            throw new NullPointerException("El modelo no puede ser nulo.");
        }
        if (modelo.isBlank()) {
            throw new IllegalArgumentException("El modelo no puede estar en blanco.");
        }
    }

    private void validarMatricula(String matricula) {
        if (matricula == null) {
            throw new NullPointerException("La matrícula no puede ser nula.");
        } else if (!matricula.matches(ER_MATRICULA)) {
            throw new IllegalArgumentException("La matrícula no tiene un formato válido.");
        }
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getMatricula() {
        return matricula;
    }

    public static Vehiculo get(String matricula) {
        return new Vehiculo("Seat", "Toledo", matricula);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vehiculo vehiculo = (Vehiculo) o;
        return Objects.equals(matricula, vehiculo.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula);
    }

    @Override
    public String toString() {
        return String.format(("%s %s - %s"), marca, modelo, matricula);
    }
}
