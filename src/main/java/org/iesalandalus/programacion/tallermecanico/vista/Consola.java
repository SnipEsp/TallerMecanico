package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {
    private static final String CADENA_FORMATO_FECHA = "dd/MM/yyyy";

    public Consola() {
    }

    public static void mostrarCabecera(String mensaje) {
        System.out.println(mensaje);
        System.out.println("-".repeat(mensaje.length()));
    }

    public static void mostrarMenu() {
        mostrarCabecera("Gestión de un taller mecánico");
        for (Opcion opcion : Opcion.values()) {
            System.out.println(opcion);
        }
    }

    public static Opcion elegirOpcion() {
        int numeroOpcion = 0;
        do {
            numeroOpcion = leerEntero("Elige una opción: ");
        } while (!Opcion.esValida(numeroOpcion));
        return Opcion.get(numeroOpcion);
    }

    public static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        try {
            return Entrada.entero();
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe introducir un número entero.");
            return leerEntero(mensaje);
        }
    }

    public static float leerReal(String mensaje) {
        System.out.print(mensaje);
        try {
            return Entrada.real();
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe introducir un número real.");
            return leerReal(mensaje);
        }
    }

    public static String leerCadena(String mensaje) {
        System.out.print(mensaje);
        return Entrada.cadena();
    }

    public static LocalDate leerFecha(String mensaje) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CADENA_FORMATO_FECHA);
        while (true) {
            System.out.print(mensaje);
            try {
                return LocalDate.parse(Entrada.cadena(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Error: Formato de fecha inválido. Use " + CADENA_FORMATO_FECHA);
            }
        }
    }

    public static Cliente leerCliente() {
        String nombre = leerCadena("Introduce el nombre: ");
        String dni = leerCadena("Introduce el DNI: ");
        String telefono = leerCadena("Introduce el teléfono: ");
        return new Cliente(nombre, dni, telefono);
    }

    public static Cliente leerClienteDni() {
        String dni = leerCadena("Introduce el DNI del cliente: ");
        return Cliente.get(dni);
    }

    public static String leerNuevoNombre() {
        return leerCadena("Introduce el nuevo nombre: ");
    }

    public static String leerNuevoTelefono() {
        return leerCadena("Introduce el nuevo teléfono: ");
    }

    public static Vehiculo leerVehiculo() {
        String marca = leerCadena("Introduce la marca: ");
        String modelo = leerCadena("Introduce el modelo: ");
        String matricula = leerCadena("Introduce la matrícula: ");
        return new Vehiculo(marca, modelo, matricula);
    }

    public static Vehiculo leerVehiculoMatricula() {
        String matricula = leerCadena("Introduce la matrícula del vehículo: ");
        return Vehiculo.get(matricula);
    }

    public static Revision leerRevision() {
        Cliente cliente = leerClienteDni();
        Vehiculo vehiculo = leerVehiculoMatricula();
        LocalDate fechaInicio = leerFecha("Introduce la fecha de inicio: ");
        return new Revision(cliente, vehiculo, fechaInicio);
    }

    public static int leerHoras() {
        return leerEntero("Introduce las horas a añadir: ");
    }

    public static float leerPrecioMaterial() {
        return leerReal("Introduce el precio del material: ");
    }

    public static LocalDate leerFechaCierre() {
        return leerFecha("Introduce la fecha de cierre: ");
    }
}
