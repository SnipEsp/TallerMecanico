package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Mecanico;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.iesalandalus.programacion.utilidades.Entrada;

/**
 * Clase utilitaria que gestiona la entrada y salida de datos por consola.
 * Proporciona métodos para leer datos del usuario con validación y mostrar información.
 */
public class Consola {
    /**
     * Formato estándar para las fechas introducidas por el usuario.
     */
    private static final String CADENA_FORMATO_FECHA = "dd/MM/yyyy";

    /**
     * Constructor privado para evitar instanciación.
     * Todos los métodos son estáticos, no se necesitan instancias.
     */
    public Consola() {
    }

    /**
     * Muestra una cabecera con el mensaje especificado y una línea separadora.
     * Utilizado para dar formato y estructura a la salida por consola.
     * 
     * @param mensaje Mensaje a mostrar como cabecera
     */
    public static void mostrarCabecera(String mensaje) {
        System.out.println(mensaje);
        System.out.println("-".repeat(mensaje.length()));
    }

    /**
     * Muestra el menú principal de opciones del taller mecánico.
     * Recorre todas las opciones del enum y las muestra numeradas.
     */
    public static void mostrarMenu() {
        mostrarCabecera("Gestión de un taller mecánico");
        for (Evento evento : Evento.values()) {
            System.out.println(evento);
        }
    }

    /**
     * Permite al usuario elegir una opción del menú.
     * Valida que la opción introducida sea válida antes de devolverla.
     * 
     * @return Opción seleccionada por el usuario
     */
    public static Evento elegirOpcion() {
        int numeroOpcion = 0;
        do {
            numeroOpcion = leerEntero("Elige una opción: ");
        } while (!Evento.esValido(numeroOpcion));
        return Evento.get(numeroOpcion);
    }

    /**
     * Lee un número entero desde la entrada estándar.
     * Valida que el dato introducido sea un entero válido, sino solicita nuevamente.
     * 
     * @param mensaje Mensaje a mostrar al usuario
     * @return Número entero introducido por el usuario
     */
    public static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        try {
            return Entrada.entero();
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe introducir un número entero.");
            return leerEntero(mensaje);
        }
    }

    /**
     * Lee un número real (float) desde la entrada estándar.
     * Valida que el dato introducido sea un real válido, sino solicita nuevamente.
     * 
     * @param mensaje Mensaje a mostrar al usuario
     * @return Número real introducido por el usuario
     */
    public static float leerReal(String mensaje) {
        System.out.print(mensaje);
        try {
            return Entrada.real();
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe introducir un número real.");
            return leerReal(mensaje);
        }
    }

    /**
     * Lee una cadena de texto desde la entrada estándar.
     * No realiza validaciones específicas, simplemente captura la entrada.
     * 
     * @param mensaje Mensaje a mostrar al usuario
     * @return Cadena de texto introducida por el usuario
     */
    public static String leerCadena(String mensaje) {
        System.out.print(mensaje);
        return Entrada.cadena();
    }

    /**
     * Lee una fecha desde la entrada estándar usando el formato dd/MM/yyyy.
     * Valida que la fecha tenga el formato correcto, sino solicita nuevamente.
     * 
     * @param mensaje Mensaje a mostrar al usuario
     * @return Fecha introducida por el usuario
     */
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

    /**
     * Lee todos los datos necesarios para crear un nuevo cliente.
     * Solicita nombre, DNI y teléfono y crea una instancia de Cliente.
     * 
     * @return Nueva instancia de Cliente con los datos introducidos
     */
    public static Cliente leerCliente() {
        String nombre = leerCadena("Introduce el nombre: ");
        String dni = leerCadena("Introduce el DNI: ");
        String telefono = leerCadena("Introduce el teléfono: ");
        return new Cliente(nombre, dni, telefono);
    }

    /**
     * Lee un DNI y crea un cliente usando el método de fábrica.
     * Utilizado para búsquedas donde solo se necesita el DNI como criterio.
     * 
     * @return Cliente con datos por defecto y el DNI especificado
     */
    public static Cliente leerClienteDni() {
        String dni = leerCadena("Introduce el DNI del cliente: ");
        return Cliente.get(dni);
    }

    /**
     * Lee un nuevo nombre para modificar un cliente existente.
     * 
     * @return Nuevo nombre introducido por el usuario
     */
    public static String leerNuevoNombre() {
        return leerCadena("Introduce el nuevo nombre: ");
    }

    /**
     * Lee un nuevo teléfono para modificar un cliente existente.
     * 
     * @return Nuevo teléfono introducido por el usuario
     */
    public static String leerNuevoTelefono() {
        return leerCadena("Introduce el nuevo teléfono: ");
    }

    /**
     * Lee todos los datos necesarios para crear un nuevo vehículo.
     * Solicita marca, modelo y matrícula y crea una instancia de Vehiculo.
     * 
     * @return Nueva instancia de Vehiculo con los datos introducidos
     */
    public static Vehiculo leerVehiculo() {
        String marca = leerCadena("Introduce la marca: ");
        String modelo = leerCadena("Introduce el modelo: ");
        String matricula = leerCadena("Introduce la matrícula: ");
        return new Vehiculo(marca, modelo, matricula);
    }

    /**
     * Lee una matrícula y crea un vehículo usando el método de fábrica.
     * Utilizado para búsquedas donde solo se necesita la matrícula como criterio.
     * 
     * @return Vehiculo con datos por defecto y la matrícula especificada
     */
    public static Vehiculo leerVehiculoMatricula() {
        String matricula = leerCadena("Introduce la matrícula del vehículo: ");
        return Vehiculo.get(matricula);
    }

    /**
     * Lee todos los datos necesarios para crear una nueva revisión.
     * Solicita cliente, vehículo y fecha de inicio y crea una instancia de Revision.
     * 
     * @return Nueva instancia de Revision con los datos introducidos
     */
    public static Revision leerRevision() {
        Cliente clienteBusqueda = leerClienteDni();
        Vehiculo vehiculo = leerVehiculoMatricula();
        LocalDate fechaInicio = leerFecha("Introduce la fecha de inicio: ");
        return new Revision(clienteBusqueda, vehiculo, fechaInicio);
    }

    /**
     * Lee todos los datos necesarios para crear un nuevo trabajo mecánico.
     * Solicita cliente, vehículo y fecha de inicio y crea una instancia de Mecanico.
     * 
     * @return Nueva instancia de Mecanico con los datos introducidos
     */
    public static Mecanico leerMecanico() {
        Cliente clienteBusqueda = leerClienteDni();
        Vehiculo vehiculo = leerVehiculoMatricula();
        LocalDate fechaInicio = leerFecha("Introduce la fecha de inicio: ");
        return new Mecanico(clienteBusqueda, vehiculo, fechaInicio);
    }

    /**
     * Lee una matrícula para obtener el trabajo abierto de un vehículo.
     * 
     * @return Matrícula del vehículo
     */
    public static String leerMatricula() {
        return leerCadena("Introduce la matrícula del vehículo: ");
    }

    /**
     * Lee el número de horas a añadir a un trabajo.
     * 
     * @return Número de horas introducido por el usuario
     */
    public static int leerHoras() {
        return leerEntero("Introduce las horas a añadir: ");
    }

    /**
     * Lee el precio del material a añadir a un trabajo mecánico.
     * 
     * @return Precio del material introducido por el usuario
     */
    public static float leerPrecioMaterial() {
        return leerReal("Introduce el precio del material: ");
    }

    /**
     * Lee la fecha de cierre para un trabajo.
     * Utiliza el mismo método de lectura de fechas con formato estándar.
     * 
     * @return Fecha de cierre introducida por el usuario
     */
    public static LocalDate leerFechaCierre() {
        return leerFecha("Introduce la fecha de cierre: ");
    }
}
