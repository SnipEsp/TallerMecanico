package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.List;

public class Vista {
    private Controlador controlador;

    public void setControlador(Controlador controlador) {
        if (controlador != null) {
            this.controlador = controlador;
        }
    }

    public void comenzar() {
        ejecutar();
    }

    public void terminar() {
        System.out.println("¡Hasta pronto!");
    }

    public void ejecutar() {
        Opcion opcion;
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            ejecutarOpcion(opcion);
        } while (opcion != Opcion.SALIR);
    }

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

    private void insertarCliente() throws OperationNotSupportedException {
        Consola.mostrarCabecera("Insertar cliente");
        Cliente cliente = Consola.leerCliente();
        controlador.insertar(cliente);
        System.out.println("Cliente insertado correctamente.");
    }

    private void insertarVehiculo() throws OperationNotSupportedException {
        Consola.mostrarCabecera("Insertar vehículo");
        Vehiculo vehiculo = Consola.leerVehiculo();
        controlador.insertar(vehiculo);
        System.out.println("Vehículo insertado correctamente.");
    }

    private void insertarRevision() throws OperationNotSupportedException {
        Consola.mostrarCabecera("Insertar revisión");
        Revision revision = Consola.leerRevision();
        controlador.insertar(revision);
        System.out.println("Revisión insertada correctamente.");
    }

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

    private void modificarCliente() throws OperationNotSupportedException {
        Consola.mostrarCabecera("Modificar cliente");
        Cliente cliente = Consola.leerClienteDni();
        String nuevoNombre = Consola.leerNuevoNombre();
        String nuevoTelefono = Consola.leerNuevoTelefono();
        controlador.modificar(cliente, nuevoNombre, nuevoTelefono);
        System.out.println("Cliente modificado correctamente.");
    }

    private void anadirHoras() throws OperationNotSupportedException {
        Consola.mostrarCabecera("Añadir horas a revisión");
        Revision revision = Consola.leerRevision();
        int horas = Consola.leerHoras();
        controlador.anadirHoras(revision, horas);
        System.out.println("Horas añadidas correctamente.");
    }

    private void anadirPrecioMaterial() throws OperationNotSupportedException {
        Consola.mostrarCabecera("Añadir precio material a revisión");
        Revision revision = Consola.leerRevision();
        float precioMaterial = Consola.leerPrecioMaterial();
        controlador.anadirPrecioMaterial(revision, precioMaterial);
        System.out.println("Precio material añadido correctamente.");
    }

    private void cerrarRevision() throws OperationNotSupportedException {
        Consola.mostrarCabecera("Cerrar revisión");
        Revision revision = Consola.leerRevision();
        LocalDate fechaCierre = Consola.leerFechaCierre();
        controlador.cerrar(revision, fechaCierre);
        System.out.println("Revisión cerrada correctamente.");
    }

    private void borrarCliente() throws OperationNotSupportedException {
        Consola.mostrarCabecera("Borrar cliente");
        Cliente cliente = Consola.leerClienteDni();
        controlador.borrar(cliente);
        System.out.println("Cliente borrado correctamente.");
    }

    private void borrarVehiculo() throws OperationNotSupportedException {
        Consola.mostrarCabecera("Borrar vehículo");
        Vehiculo vehiculo = Consola.leerVehiculoMatricula();
        controlador.borrar(vehiculo);
        System.out.println("Vehículo borrado correctamente.");
    }

    private void borrarRevision() throws OperationNotSupportedException {
        Consola.mostrarCabecera("Borrar revisión");
        Revision revision = Consola.leerRevision();
        controlador.borrar(revision);
        System.out.println("Revisión borrada correctamente.");
    }

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

    private void salir() {
        System.out.println("Saliendo del programa...");
    }
}
