package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.util.Objects;

public class Cliente {
    private final String ER_NOMBRE = "^[A-ZÁÉÍÓÚÑ][a-záéíóúñü]+$";
    private final String ER_DNI = "\\d{8}[A-HJ-NP-TV-Z]";
    private final String ER_TELEFONO = "\\d{9}";
    private String nombre;
    private String dni;
    private String telefono;

    public Cliente(String nombre, String dni, String telefono) {
        setNombre(nombre);
        setDni(dni);
        setTelefono(telefono);
    }

    public Cliente(Cliente cliente) {
        if (cliente == null) {
            throw new NullPointerException("No se puede copiar un cliente nulo.");
        }
        nombre = cliente.nombre;
        dni = cliente.dni;
        telefono = cliente.telefono;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null) {
            throw new NullPointerException("El nombre no puede ser nulo.");
        } else if (!nombre.matches(ER_NOMBRE)) {
            throw new IllegalArgumentException("El nombre no tiene un formato válido.");
        }
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    private void setDni(String dni) {
        if (dni == null) {
            throw new NullPointerException("El DNI no puede ser nulo.");
        } else if (!dni.matches(ER_DNI)) {
            throw new IllegalArgumentException("El DNI no tiene un formato válido.");
        }
        this.dni = dni;
    }

    private boolean comprobarLetraDni(String dni) {
        // Validar letra DNI
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int numero = Integer.parseInt(dni.substring(0, 8));
        char letraEsperada = letras.charAt(numero % 23);

        if (dni.charAt(8) != letraEsperada) {
            throw new IllegalArgumentException("La letra del DNI no es correcta.");
        } else {
            return true;
        }
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono == null) {
            throw new NullPointerException("El teléfono no puede ser nulo.");
        } else if (!telefono.matches(ER_TELEFONO)) {
            throw new IllegalArgumentException("El teléfono no tiene un formato válido.");
        }
        this.telefono = telefono;
    }

    public static Cliente get(String dni) {
        return new Cliente("Nombre valido", dni, "623567876");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(ER_NOMBRE, cliente.ER_NOMBRE) && Objects.equals(ER_DNI, cliente.ER_DNI) && Objects.equals(ER_TELEFONO, cliente.ER_TELEFONO) && Objects.equals(nombre, cliente.nombre) && Objects.equals(dni, cliente.dni) && Objects.equals(telefono, cliente.telefono);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ER_NOMBRE, ER_DNI, ER_TELEFONO, nombre, dni, telefono);
    }

    @Override
    public String toString() {
        return String.format(("%s - %s (%s)"), nombre, dni, telefono);
    }
}
