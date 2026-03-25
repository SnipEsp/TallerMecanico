package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.List;

/**
 * Clase que gestiona la interfaz de usuario del taller mecánico.
 * Implementa el patrón MVC como la Vista, mostrando menús y gestionando
 * la interacción con el usuario a través de la consola.
 */
public class Vista {
    /**
     * Referencia al controlador para poder delegar las operaciones del usuario.
     * Se establece mediante inyección de dependencias.
     */
    private Controlador controlador;

    /**
     * Establece la referencia al controlador.
     * Permite a la vista comunicarse con el controlador para procesar las operaciones.
     * 
     * @param controlador Controlador a establecer
     */
    public void setControlador(Controlador controlador) {
        if (controlador != null) {
            this.controlador = controlador;
        }
    }

    /**
     * Inicia la ejecución de la vista.
     * Comienza el bucle principal del menú interactivo.
     */
    public void comenzar() {
        ejecutar();
    }

    /**
     * Finaliza la ejecución de la vista.
     * Muestra un mensaje de despedida al usuario.
     */
    public void terminar() {
        System.out.println("¡Hasta pronto!");
    }

    /**
     * Bucle principal de ejecución de la aplicación.
     * Muestra el menú, permite elegir opciones y ejecuta la correspondiente.
     * El bucle continúa hasta que el usuario selecciona la opción de salir.
     */
    public void ejecutar() {
        Opcion opcion;
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            ejecutarOpcion(opcion);
        } while (opcion != Opcion.SALIR);
    }

    /**
     * Ejecuta la operación correspondiente a la opción seleccionada por el usuario.
     * Utiliza un switch para delegar a los métodos específicos de cada operación.
     * Captura excepciones y muestra mensajes de error al usuario.
     * 
     * @param opcion Opción seleccionada por el usuario
     */
    private void ejecutarOpcion(Opcion opcion) {
        try {
            switch (opcion) {
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
                case BUSCAR_REVISION -> buscarRevision();
                case BORRAR_REVISION -> borrarRevision();
                case LISTAR_REVISIONES -> listarRevisiones();
                case LISTAR_REVISIONES_CLIENTE -> listarRevisionesCliente();
                case LISTAR_REVISIONES_VEHICULO -> listarRevisionesVehiculo();
                case ANADIR_HORAS_REVISION -> anadirHoras();
                case ANADIR_PRECIO_MATERIAL_REVISION -> anadirPrecioMaterial();
                case CERRAR_REVISION -> cerrarRevision();
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
     * Gestiona la búsqueda de una revisión.
     * Muestra cabecera, lee datos de la revisión y muestra el resultado.
     */
    private void buscarRevision() {
        Consola.mostrarCabecera("Buscar revisión");
        Revision revision = Consola.leerRevision();
        Revision encontrada = controlador.buscar(revision);
        if (encontrada != null) {
            System.out.println(encontrada);
        } else {
            System.out.println("Revisión no encontrada.");
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
     * Gestiona la adición de horas a una revisión.
     * Muestra cabecera, lee revisión y horas, y delega al controlador.
     * 
     * @throws OperationNotSupportedException Si la revisión no existe o está cerrada
     */
    private void anadirHoras() throws OperationNotSupportedException {
        Consola.mostrarCabecera("Añadir horas a revisión");
        Revision revision = Consola.leerRevision();
        int horas = Consola.leerHoras();
        controlador.anadirHoras(revision, horas);
        System.out.println("Horas añadidas correctamente.");
    }

    /**
     * Gestiona la adición de precio de material a una revisión.
     * Muestra cabecera, lee revisión y precio, y delega al controlador.
     * 
     * @throws OperationNotSupportedException Si la revisión no existe o está cerrada
     */
    private void anadirPrecioMaterial() throws OperationNotSupportedException {
        Consola.mostrarCabecera("Añadir precio material a revisión");
        Revision revision = Consola.leerRevision();
        float precioMaterial = Consola.leerPrecioMaterial();
        controlador.anadirPrecioMaterial(revision, precioMaterial);
        System.out.println("Precio material añadido correctamente.");
    }

    /**
     * Gestiona el cierre de una revisión.
     * Muestra cabecera, lee revisión y fecha de cierre, y delega al controlador.
     * 
     * @throws OperationNotSupportedException Si la revisión no existe o la fecha es inválida
     */
    private void cerrarRevision() throws OperationNotSupportedException {
        Consola.mostrarCabecera("Cerrar revisión");
        Revision revision = Consola.leerRevision();
        LocalDate fechaCierre = Consola.leerFechaCierre();
        controlador.cerrar(revision, fechaCierre);
        System.out.println("Revisión cerrada correctamente.");
    }

    /**
     * Gestiona el borrado de un cliente y sus revisiones asociadas.
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
     * Gestiona el borrado de un vehículo y sus revisiones asociadas.
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
     * Gestiona el borrado de una revisión específica.
     * Muestra cabecera, lee revisión y delega al controlador.
     * 
     * @throws OperationNotSupportedException Si la revisión no existe
     */
    private void borrarRevision() throws OperationNotSupportedException {
        Consola.mostrarCabecera("Borrar revisión");
        Revision revision = Consola.leerRevision();
        controlador.borrar(revision);
        System.out.println("Revisión borrada correctamente.");
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
     * Gestiona el listado de todas las revisiones del sistema.
     * Muestra cabecera, obtiene lista del controlador y la muestra por pantalla.
     */
    private void listarRevisiones() {
        Consola.mostrarCabecera("Listar revisiones");
        List<Revision> revisiones = controlador.getRevisiones();
        if (revisiones.isEmpty()) {
            System.out.println("No hay revisiones registradas.");
        } else {
            for (Revision revision : revisiones) {
                System.out.println(revision);
            }
        }
    }

    /**
     * Gestiona el listado de revisiones de un cliente específico.
     * Muestra cabecera, lee cliente, obtiene sus revisiones y las muestra.
     */
    private void listarRevisionesCliente() {
        Consola.mostrarCabecera("Listar revisiones de cliente");
        Cliente cliente = Consola.leerClienteDni();
        List<Revision> revisiones = controlador.getRevisiones(cliente);
        if (revisiones.isEmpty()) {
            System.out.println("No hay revisiones para este cliente.");
        } else {
            for (Revision revision : revisiones) {
                System.out.println(revision);
            }
        }
    }

    /**
     * Gestiona el listado de revisiones de un vehículo específico.
     * Muestra cabecera, lee vehículo, obtiene sus revisiones y las muestra.
     */
    private void listarRevisionesVehiculo() {
        Consola.mostrarCabecera("Listar revisiones de vehículo");
        Vehiculo vehiculo = Consola.leerVehiculoMatricula();
        List<Revision> revisiones = controlador.getRevisiones(vehiculo);
        if (revisiones.isEmpty()) {
            System.out.println("No hay revisiones para este vehículo.");
        } else {
            for (Revision revision : revisiones) {
                System.out.println(revision);
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
