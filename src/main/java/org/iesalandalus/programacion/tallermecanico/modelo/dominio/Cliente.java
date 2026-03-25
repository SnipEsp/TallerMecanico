package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.util.Objects;

/**
 * Representa un cliente del taller mecánico con sus datos personales.
 * Contiene nombre, DNI y teléfono, con validaciones para cada campo.
 */
public class Cliente {
    private final String ER_NOMBRE = "^[A-ZÁÉÍÓÚÑ][a-záéíóúñü]+";
    private final String ER_DNI = "\\d{8}[A-HJ-NP-TV-Z]";
    private final String ER_TELEFONO = "\\d{9}";
    private String nombre;
    private String dni;
    private String telefono;

    /**
     * Constructor que crea un nuevo cliente con los datos especificados.
     * Valida cada parámetro antes de asignarlo.
     * 
     * @param nombre Nombre del cliente (formato: primera letra mayúscula, resto minúsculas)
     * @param dni DNI del cliente (8 dígitos + letra válida)
     * @param telefono Teléfono del cliente (9 dígitos)
     * @throws NullPointerException Si algún parámetro es nulo
     * @throws IllegalArgumentException Si algún parámetro no cumple el formato
     */
    public Cliente(String nombre, String dni, String telefono) {
        setNombre(nombre);
        setDni(dni);
        setTelefono(telefono);
    }

    /**
     * Constructor copia que crea un nuevo cliente a partir de uno existente.
     * Realiza una copia profunda de los datos del cliente original.
     * 
     * @param cliente Cliente a copiar
     * @throws NullPointerException Si el cliente a copiar es nulo
     */
    public Cliente(Cliente cliente) {
        if (cliente == null) {
            throw new NullPointerException("No se puede copiar un cliente nulo.");
        }
        nombre = cliente.nombre;
        dni = cliente.dni;
        telefono = cliente.telefono;
    }


    /**
     * Obtiene el nombre del cliente.
     * 
     * @return Nombre del cliente
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente con validación de formato.
     * El nombre debe empezar con mayúscula y el resto en minúsculas.
     * 
     * @param nombre Nuevo nombre del cliente
     * @throws NullPointerException Si el nombre es nulo
     * @throws IllegalArgumentException Si el nombre no cumple el formato
     */
    public void setNombre(String nombre) {
        if (nombre == null) {
            throw new NullPointerException("El nombre no puede ser nulo.");
        } else if (!nombre.matches(ER_NOMBRE)) {
            throw new IllegalArgumentException("El nombre no tiene un formato válido.");
        }
        this.nombre = nombre;
    }

    /**
     * Obtiene el DNI del cliente.
     * 
     * @return DNI del cliente
     */
    public String getDni() {
        return dni;
    }

    /**
     * Establece el DNI del cliente con validación de formato.
     * El DNI debe tener 8 dígitos seguidos de una letra válida.
     * 
     * @param dni Nuevo DNI del cliente
     * @throws NullPointerException Si el DNI es nulo
     * @throws IllegalArgumentException Si el DNI no cumple el formato
     */
    private void setDni(String dni) {
        if (dni == null) {
            throw new NullPointerException("El DNI no puede ser nulo.");
        } else if (!dni.matches(ER_DNI)) {
            throw new IllegalArgumentException("El DNI no tiene un formato válido.");
        }
        comprobarLetraDni(dni);
        this.dni = dni;
    }

    /**
     * Comprueba que la letra del DNI sea correcta según el algoritmo oficial.
     * Calcula la letra esperada y la compara con la proporcionada.
     * 
     * @param dni DNI completo a validar
     * @throws IllegalArgumentException Si la letra del DNI no es correcta
     */
    private void comprobarLetraDni(String dni) {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int numero = Integer.parseInt(dni.substring(0, 8));
        char letraEsperada = letras.charAt(numero % 23);

        if (dni.charAt(8) != letraEsperada) {
            throw new IllegalArgumentException("La letra del DNI no es correcta.");
        }
    }

    /**
     * Obtiene el teléfono del cliente.
     * 
     * @return Teléfono del cliente
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del cliente con validación de formato.
     * El teléfono debe tener exactamente 9 dígitos.
     * 
     * @param telefono Nuevo teléfono del cliente
     * @throws NullPointerException Si el teléfono es nulo
     * @throws IllegalArgumentException Si el teléfono no cumple el formato
     */
    public void setTelefono(String telefono) {
        if (telefono == null) {
            throw new NullPointerException("El teléfono no puede ser nulo.");
        } else if (!telefono.matches(ER_TELEFONO)) {
            throw new IllegalArgumentException("El teléfono no tiene un formato válido.");
        }
        this.telefono = telefono;
    }

    /**
     * Método de fábrica que crea un cliente con datos por defecto a partir de un DNI.
     * Utilizado para búsquedas donde solo se necesita el DNI como criterio.
     * 
     * @param dni DNI del cliente a crear
     * @return Cliente con datos por defecto y el DNI especificado
     */
    public static Cliente get(String dni) {
        return new Cliente("Nombrevalido", dni, "623567876");
    }

    /**
     * Compara dos clientes para determinar si son iguales.
     * Dos clientes son iguales si tienen el mismo DNI.
     * 
     * @param o Objeto a comparar
     * @return true si los clientes son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(dni, cliente.dni);
    }

    /**
     * Calcula el código hash del cliente basado en el DNI.
     * Utilizado para optimizar búsquedas en colecciones hash.
     * 
     * @return Código hash del cliente
     */
    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    /**
     * Devuelve una representación en cadena del cliente.
     * Formato: "nombre - dni (telefono)"
     * 
     * @return Representación en cadena del cliente
     */
    @Override
    public String toString() {
        return String.format(("%s - %s (%s)"), nombre, dni, telefono);
    }
}
