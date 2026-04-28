package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.controlador.IControlador;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Mecanico;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.vista.IVista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.List;

/**
 * Clase que gestiona la interfaz de usuario del taller mecánico por consola.
 * Implementa el patrón MVC como la Vista, mostrando menús y gestionando
 * la interacción con el usuario a través de la consola.
 */
public class VistaTexto implements IVista {
    /**
     * Referencia al controlador para poder delegar las operaciones del usuario.
     * Se establece mediante inyección de dependencias.
     */
    private IControlador controlador;

    /**
     * Establece la referencia al controlador.
     * Permite a la vista comunicarse con el controlador para procesar las operaciones.
     * 
     * @param controlador Controlador a establecer
     */
    @Override
    public void setControlador(IControlador controlador) {
        if (controlador != null) {
            this.controlador = controlador;
        }
    }

    /**
     * Inicia la ejecución de la vista.
     * Comienza el bucle principal del menú interactivo.
     */
    @Override
    public void comenzar() {
        ejecutar();
    }

    /**
     * Finaliza la ejecución de la vista.
     * Muestra un mensaje de despedida al usuario.
     */
    @Override
    public void terminar() {
        System.out.println("¡Hasta pronto!");
    }

    /**
     * Bucle principal de ejecución de la aplicación.
     * Muestra el menú, permite elegir opciones y ejecuta la correspondiente.
     * El bucle continúa hasta que el usuario selecciona la opción de salir.
     */
    private void ejecutar() {
        Evento evento;
        do {
            Consola.mostrarMenu();
            evento = Consola.elegirOpcion();
            ejecutarOpcion(evento);
        } while (evento != Evento.SALIR);
    }

    /**
     * Ejecuta la operación correspondiente al evento seleccionado por el usuario.
     * Utiliza un switch para delegar a los métodos específicos de cada operación.
     * Captura excepciones y muestra mensajes de error al usuario.
     * 
     * @param evento Evento seleccionado por el usuario
     */
    private void ejecutarOpcion(Evento evento) {
        try {
            switch (evento) {
                case INSERTAR_CLIENTE -> insertarCliente();
                case BUSCAR_CLIENTE -> buscarCliente();
                case BORRAR_CLIENTE -> borrarCliente();
                case LISTAR_CLIENTES -> listarClientes();
                case MODIFICAR_CLIENTE -> modificarCliente();
                case INSERTAR_VEHICULO -> insertarVehiculo();
                case BUSCAR_VEHICULO -> buscarVehiculo();
                case BORRAR_VEHICULO -> borrarVehiculo();
                case LISTAR_VEHICULOS -> listarVehiculos();
                case INSERTAR_REVISION -> insertarRevision();
                case INSERTAR_MECANICO -> insertarMecanico();
                case BUSCAR_TRABAJO -> buscarTrabajo();
                case BORRAR_TRABAJO -> borrarTrabajo();
                case LISTAR_TRABAJOS -> listarTrabajos();
                case LISTAR_TRABAJOS_CLIENTE -> listarTrabajosCliente();
                case LISTAR_TRABAJOS_VEHICULO -> listarTrabajosVehiculo();
                case AÑADIR_HORAS_TRABAJO -> anadirHoras();
                case AÑADIR_PRECIO_MATERIAL_TRABAJO -> anadirPrecioMaterial();
                case CERRAR_TRABAJO -> cerrarTrabajo();
                case SALIR -> salir();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Gestiona la inserción de un nuevo cliente.
     * Muestra cabecera, lee datos del cliente y delega al controlador.
     * 
     * @throws OperationNotSupportedException Si el cliente ya existe o es inválido
     */
    private void insertarCliente() throws OperationNotSupportedException {
        Consola.mostrarCabecera("Insertar cliente");
        Cliente cliente = Consola.leerCliente();
        controlador.insertar(cliente);
        System.out.println("Cliente insertado correctamente.");
    }

    /**
     * Gestiona la inserción de un nuevo vehículo.
     * Muestra cabecera, lee datos del vehículo y delega al controlador.
     * 
     * @throws OperationNotSupportedException Si el vehículo ya existe o es inválido
     */
    private void insertarVehiculo() throws OperationNotSupportedException {
        Consola.mostrarCabecera("Insertar vehículo");
        Vehiculo vehiculo = Consola.leerVehiculo();
        controlador.insertar(vehiculo);
        System.out.println("Vehículo insertado correctamente.");
    }

    /**
     * Gestiona la inserción de una nueva revisión.
     * Muestra cabecera, lee datos de la revisión y delega al controlador.
     * 
     * @throws OperationNotSupportedException Si la revisión ya existe o es inválida
     */
    private void insertarRevision() throws OperationNotSupportedException {
        Consola.mostrarCabecera("Insertar revisión");
        Revision revision = Consola.leerRevision();
        controlador.insertar(revision);
        System.out.println("Revisión insertada correctamente.");
    }

    /**
     * Gestiona la inserción de un nuevo trabajo mecánico.
     * Muestra cabecera, lee datos del trabajo mecánico y delega al controlador.
     * 
     * @throws OperationNotSupportedException Si el trabajo mecánico ya existe o es inválido
     */
    private void insertarMecanico() throws OperationNotSupportedException {
        Consola.mostrarCabecera("Insertar trabajo mecánico");
        Mecanico mecanico = Consola.leerMecanico();
        controlador.insertar(mecanico);
        System.out.println("Trabajo mecánico insertado correctamente.");
    }

    /**
     * Gestiona la búsqueda de un cliente por DNI.
     * Muestra cabecera, lee DNI y muestra el resultado de la búsqueda.
     */
    private void buscarCliente() {
        Consola.mostrarCabecera("Buscar cliente");
        Cliente cliente = Consola.leerClienteDni();
        Cliente encontrado = controlador.buscar(cliente);
        if (encontrado != null) {
            System.out.println(encontrado);
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    /**
     * Gestiona la búsqueda de un vehículo por matrícula.
     * Muestra cabecera, lee matrícula y muestra el resultado de la búsqueda.
     */
    private void buscarVehiculo() {
        Consola.mostrarCabecera("Buscar vehículo");
        Vehiculo vehiculo = Consola.leerVehiculoMatricula();
        Vehiculo encontrado = controlador.buscar(vehiculo);
        if (encontrado != null) {
            System.out.println(encontrado);
        } else {
            System.out.println("Vehículo no encontrado.");
        }
    }

    /**
     * Gestiona la búsqueda de un trabajo (revisión o mecánico).
     * Muestra cabecera, lee datos del trabajo y muestra el resultado.
     */
    private void buscarTrabajo() {
        Consola.mostrarCabecera("Buscar trabajo");
        // Para buscar un trabajo, necesitamos la matrícula del vehículo
        String matricula = Consola.leerMatricula();
        Vehiculo vehiculo = Vehiculo.get(matricula);
        List<Trabajo> trabajos = controlador.getTrabajos(vehiculo);
        if (trabajos.isEmpty()) {
            System.out.println("No hay trabajos para este vehículo.");
        } else {
            for (Trabajo trabajo : trabajos) {
                System.out.println(trabajo);
            }
        }
    }

    /**
     * Gestiona la modificación de los datos de un cliente.
     * Muestra cabecera, lee cliente y nuevos datos, y delega al controlador.
     * 
     * @throws OperationNotSupportedException Si el cliente no existe o los datos son inválidos
     */
    private void modificarCliente() throws OperationNotSupportedException {
        Consola.mostrarCabecera("Modificar cliente");
        Cliente cliente = Consola.leerClienteDni();
        String nuevoNombre = Consola.leerNuevoNombre();
        String nuevoTelefono = Consola.leerNuevoTelefono();
        controlador.modificar(cliente, nuevoNombre, nuevoTelefono);
        System.out.println("Cliente modificado correctamente.");
    }

    /**
     * Gestiona la adición de horas a un trabajo.
     * Muestra cabecera, lee matrícula y horas, y delega al controlador.
     * 
     * @throws OperationNotSupportedException Si no existe trabajo abierto o está cerrado
     */
    private void anadirHoras() throws OperationNotSupportedException {
        Consola.mostrarCabecera("Añadir horas a trabajo");
        String matricula = Consola.leerMatricula();
        Vehiculo vehiculo = Vehiculo.get(matricula);
        int horas = Consola.leerHoras();
        controlador.anadirHoras(vehiculo, horas);
        System.out.println("Horas añadidas correctamente.");
    }

    /**
     * Gestiona la adición de precio de material a un trabajo mecánico.
     * Muestra cabecera, lee matrícula y precio, y delega al controlador.
     * 
     * @throws OperationNotSupportedException Si no existe trabajo abierto o está cerrado
     */
    private void anadirPrecioMaterial() throws OperationNotSupportedException {
        Consola.mostrarCabecera("Añadir precio material a trabajo");
        String matricula = Consola.leerMatricula();
        Vehiculo vehiculo = Vehiculo.get(matricula);
        float precioMaterial = Consola.leerPrecioMaterial();
        controlador.anadirPrecioMaterial(vehiculo, precioMaterial);
        System.out.println("Precio material añadido correctamente.");
    }

    /**
     * Gestiona el cierre de un trabajo.
     * Muestra cabecera, lee matrícula y fecha de cierre, y delega al controlador.
     * 
     * @throws OperationNotSupportedException Si no existe trabajo abierto o la fecha es inválida
     */
    private void cerrarTrabajo() throws OperationNotSupportedException {
        Consola.mostrarCabecera("Cerrar trabajo");
        String matricula = Consola.leerMatricula();
        Vehiculo vehiculo = Vehiculo.get(matricula);
        LocalDate fechaCierre = Consola.leerFechaCierre();
        controlador.cerrar(vehiculo, fechaCierre);
        System.out.println("Trabajo cerrado correctamente.");
    }

    /**
     * Gestiona el borrado de un cliente y sus trabajos asociados.
     * Muestra cabecera, lee cliente y delega al controlador.
     * 
     * @throws OperationNotSupportedException Si el cliente no existe
     */
    private void borrarCliente() throws OperationNotSupportedException {
        Consola.mostrarCabecera("Borrar cliente");
        Cliente cliente = Consola.leerClienteDni();
        controlador.borrar(cliente);
        System.out.println("Cliente borrado correctamente.");
    }

    /**
     * Gestiona el borrado de un vehículo y sus trabajos asociados.
     * Muestra cabecera, lee vehículo y delega al controlador.
     * 
     * @throws OperationNotSupportedException Si el vehículo no existe
     */
    private void borrarVehiculo() throws OperationNotSupportedException {
        Consola.mostrarCabecera("Borrar vehículo");
        Vehiculo vehiculo = Consola.leerVehiculoMatricula();
        controlador.borrar(vehiculo);
        System.out.println("Vehículo borrado correctamente.");
    }

    /**
     * Gestiona el borrado de un trabajo específico.
     * Muestra cabecera, lee trabajo y delega al controlador.
     * 
     * @throws OperationNotSupportedException Si el trabajo no existe
     */
    private void borrarTrabajo() throws OperationNotSupportedException {
        Consola.mostrarCabecera("Borrar trabajo");
        String matricula = Consola.leerMatricula();
        Vehiculo vehiculo = Vehiculo.get(matricula);
        controlador.borrar(vehiculo);
        System.out.println("Trabajo borrado correctamente.");
    }

    /**
     * Gestiona el listado de todos los clientes del sistema.
     * Muestra cabecera, obtiene lista del controlador y la muestra por pantalla.
     */
    private void listarClientes() {
        Consola.mostrarCabecera("Listar clientes");
        List<Cliente> clientes = controlador.getClientes();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }
        }
    }

    /**
     * Gestiona el listado de todos los vehículos del sistema.
     * Muestra cabecera, obtiene lista del controlador y la muestra por pantalla.
     */
    private void listarVehiculos() {
        Consola.mostrarCabecera("Listar vehículos");
        List<Vehiculo> vehiculos = controlador.getVehiculos();
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
        } else {
            for (Vehiculo vehiculo : vehiculos) {
                System.out.println(vehiculo);
            }
        }
    }

    /**
     * Gestiona el listado de todos los trabajos del sistema.
     * Muestra cabecera, obtiene lista del controlador y la muestra por pantalla.
     */
    private void listarTrabajos() {
        Consola.mostrarCabecera("Listar trabajos");
        List<Trabajo> trabajos = controlador.getTrabajos();
        if (trabajos.isEmpty()) {
            System.out.println("No hay trabajos registrados.");
        } else {
            for (Trabajo trabajo : trabajos) {
                System.out.println(trabajo);
            }
        }
    }

    /**
     * Gestiona el listado de trabajos de un cliente específico.
     * Muestra cabecera, lee cliente, obtiene sus trabajos y los muestra.
     */
    private void listarTrabajosCliente() {
        Consola.mostrarCabecera("Listar trabajos de cliente");
        Cliente cliente = Consola.leerClienteDni();
        List<Trabajo> trabajos = controlador.getTrabajos(cliente);
        if (trabajos.isEmpty()) {
            System.out.println("No hay trabajos para este cliente.");
        } else {
            for (Trabajo trabajo : trabajos) {
                System.out.println(trabajo);
            }
        }
    }

    /**
     * Gestiona el listado de trabajos de un vehículo específico.
     * Muestra cabecera, lee vehículo, obtiene sus trabajos y los muestra.
     */
    private void listarTrabajosVehiculo() {
        Consola.mostrarCabecera("Listar trabajos de vehículo");
        Vehiculo vehiculo = Consola.leerVehiculoMatricula();
        List<Trabajo> trabajos = controlador.getTrabajos(vehiculo);
        if (trabajos.isEmpty()) {
            System.out.println("No hay trabajos para este vehículo.");
        } else {
            for (Trabajo trabajo : trabajos) {
                System.out.println(trabajo);
            }
        }
    }

    /**
     * Gestiona la salida del programa.
     * Muestra un mensaje informativo antes de terminar.
     */
    private void salir() {
        System.out.println("Saliendo del programa...");
    }
}
