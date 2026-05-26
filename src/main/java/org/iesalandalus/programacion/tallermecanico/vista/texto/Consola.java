package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Consola {
    private static final String CADENA_FORMATO_FECHA = "dd/MM/yyyy";

    private Consola() {
    }

    static void mostrarCabecera(String mensaje) {
        System.out.printf("%n%s%n", mensaje);
        System.out.printf("-".repeat(mensaje.length()).concat("%n%n"));
    }

    static void mostrarMenu() {
        mostrarCabecera("Menú de opciones");
        for (Evento opcion : Evento.values()) {
            System.out.println(opcion);
        }
    }

    static Evento elegirOpcion() {
        Evento opcion = null;
        do {
            try {
                System.out.print("Escoja una de las siguientes acciones a realizar (Escribe el número de comando): ");
                opcion = Evento.get(Entrada.entero());
            } catch (IllegalArgumentException iae) {
                System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion == null);
        return opcion;
    }

    static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        return Entrada.entero();
    }

    static float leerReal(String mensaje) {
        System.out.print(mensaje);
        return Entrada.real();
    }

    static String leerCadena(String mensaje) {
        System.out.print(mensaje);
        return Entrada.cadena();
    }

    static LocalDate leerFecha(String mensaje) {
        LocalDate fechaInicial = null;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern(CADENA_FORMATO_FECHA);
        do {
            System.out.print(mensaje);
            String fecha = Entrada.cadena();
            try {
                fechaInicial = LocalDate.parse(fecha, formato);
            } catch (DateTimeException e) {
                System.out.print("Fecha incorrecta. Vuelve a intentarlo.");
            }
        } while (fechaInicial == null);
        return fechaInicial;
    }

}