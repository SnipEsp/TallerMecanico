package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.cascada.ModeloCascada;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;

import javax.naming.OperationNotSupportedException;
import java.util.List;

/**
 * Clase que actúa como intermediario entre el Modelo y la Vista.
 * Implementa el patrón MVC (Modelo-Vista-Controlador) gestionando las
 * operaciones del taller mecánico y coordinando la interacción entre componentes.
 */
public class Controlador implements IControlador {

    private ModeloCascada modeloCascada;
    private Vista vista;

    /**
     * Constructor que inicializa el controlador con el modelo y la vista.
     * Establece la comunicación bidireccional entre los componentes.
     * 
     * @param modeloCascada Modelo que gestiona los datos del taller
     * @param vista Vista que gestiona la interfaz de usuario
     * @throws IllegalArgumentException Si modelo o vista son nulos
     */
    public Controlador(ModeloCascada modeloCascada, Vista vista) {
        if (modeloCascada == null) {
            throw new IllegalArgumentException("El modelo no puede ser nulo.");
        }
        if (vista == null) {
            throw new IllegalArgumentException("La vista no puede ser nula.");
        }
        this.modeloCascada = modeloCascada;
        this.vista = vista;
        this.vista.setControlador(this);
    }

    /**
     * Inicia la aplicación inicializando el modelo y la vista.
     * Prepara el sistema para comenzar las operaciones.
     */
    @Override
    public void comenzar() {
        modeloCascada.comenzar();
        vista.comenzar();
    }

    /**
     * Finaliza la aplicación cerrando el modelo y la vista.
     * Libera recursos y muestra mensajes de finalización.
     */
    @Override
    public void terminar() {
        modeloCascada.terminar();
        vista.terminar();
    }

    /**
     * Inserta un nuevo cliente en el sistema.
     * Delega la operación al modelo para su procesamiento.
     * 
     * @param cliente Cliente a insertar
     * @throws OperationNotSupportedException Si el cliente ya existe o es inválido
     */
    @Override
    public void insertar(Cliente cliente) throws OperationNotSupportedException {
        modeloCascada.insertar(cliente);
    }

    /**
     * Inserta un nuevo vehículo en el sistema.
     * Delega la operación al modelo para su procesamiento.
     * 
     * @param vehiculo Vehículo a insertar
     * @throws OperationNotSupportedException Si el vehículo ya existe o es inválido
     */
    @Override
    public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
        modeloCascada.insertar(vehiculo);
    }

    /**
     * Inserta una nueva revisión en el sistema.
     * Busca el cliente y vehículo reales antes de crear la revisión.
     * 
     * @param revision Revisión a insertar (con cliente de búsqueda)
     * @throws OperationNotSupportedException Si la revisión ya existe o es inválida
     */
    @Override
    public void insertar(Revision revision) throws OperationNotSupportedException {
        // Buscar el cliente real usando el DNI del cliente de búsqueda
        Cliente clienteReal = modeloCascada.buscar(revision.getCliente());
        if (clienteReal == null) {
            throw new OperationNotSupportedException("No existe un cliente con el DNI proporcionado.");
        }
        
        // Buscar el vehículo real usando la matrícula del vehículo de búsqueda
        Vehiculo vehiculoReal = modeloCascada.buscar(revision.getVehiculo());
        if (vehiculoReal == null) {
            throw new OperationNotSupportedException("No existe un vehículo con la matrícula proporcionada.");
        }
        
        // Crear la revisión con el cliente y vehículo reales
        Revision revisionReal = new Revision(clienteReal, vehiculoReal, revision.getFechaInicio());
        modeloCascada.insertar(revisionReal);
    }

    /**
     * Busca un cliente en el sistema.
     * Delega la búsqueda al modelo y devuelve el resultado.
     * 
     * @param cliente Cliente a buscar
     * @return Cliente encontrado o null si no existe
     */
    @Override
    public Cliente buscar(Cliente cliente) {
        return modeloCascada.buscar(cliente);
    }

    /**
     * Busca un vehículo en el sistema.
     * Delega la búsqueda al modelo y devuelve el resultado.
     * 
     * @param vehiculo Vehículo a buscar
     * @return Vehículo encontrado o null si no existe
     */
    @Override
    public Vehiculo buscar(Vehiculo vehiculo) {
        return modeloCascada.buscar(vehiculo);
    }

    /**
     * Busca una revisión en el sistema.
     * Delega la búsqueda al modelo y devuelve el resultado.
     * 
     * @param revision Revisión a buscar
     * @return Revisión encontrada o null si no existe
     */
    @Override
    public Revision buscar(Revision revision) {
        return modeloCascada.buscar(revision);
    }

    /**
     * Modifica los datos de un cliente existente.
     * Delega la operación al modelo para su procesamiento.
     * 
     * @param cliente Cliente a modificar
     * @param nombre Nuevo nombre del cliente
     * @param telefono Nuevo teléfono del cliente
     * @return Cliente modificado
     * @throws OperationNotSupportedException Si el cliente no existe o los datos son inválidos
     */
    @Override
    public Cliente modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
        return modeloCascada.modificar(cliente, nombre, telefono);
    }

    /**
     * Borra un cliente y todas sus revisiones asociadas.
     * Delega la operación al modelo que gestiona el borrado en cascada.
     * 
     * @param cliente Cliente a borrar
     * @throws OperationNotSupportedException Si el cliente no existe
     */
    @Override
    public void borrar(Cliente cliente) throws OperationNotSupportedException {
        modeloCascada.borrar(cliente);
    }

    /**
     * Borra un vehículo y todas sus revisiones asociadas.
     * Delega la operación al modelo que gestiona el borrado en cascada.
     * 
     * @param vehiculo Vehículo a borrar
     * @throws OperationNotSupportedException Si el vehículo no existe
     */
    @Override
    public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
        modeloCascada.borrar(vehiculo);
    }

    /**
     * Borra una revisión específica del sistema.
     * Delega la operación al modelo para su procesamiento.
     * 
     * @param revision Revisión a borrar
     * @return Revisión borrada
     * @throws OperationNotSupportedException Si la revisión no existe
     */
    @Override
    public Revision borrar(Revision revision) throws OperationNotSupportedException {
        Trabajo borrado = modeloCascada.borrar((Trabajo) revision);
        return (Revision) borrado;
    }

    /**
     * Obtiene la lista de todos los clientes del sistema.
     * Delega la operación al modelo que devuelve copias protectoras.
     * 
     * @return Lista de clientes
     */
    @Override
    public List<Cliente> getClientes() {
        return modeloCascada.getClientes();
    }

    /**
     * Obtiene la lista de todos los vehículos del sistema.
     * Delega la operación al modelo que devuelve las referencias directamente.
     * 
     * @return Lista de vehículos
     */
    @Override
    public List<Vehiculo> getVehiculos() {
        return modeloCascada.getVehiculos();
    }

    /**
     * Obtiene la lista de todas las revisiones del sistema.
     * Delega la operación al modelo que devuelve copias protectoras.
     * 
     * @return Lista de revisiones
     */
    @Override
    public List<Revision> getRevisiones() {
        List<Trabajo> trabajos = modeloCascada.getTrabajos();
        List<Revision> revisiones = new java.util.ArrayList<>();
        for (Trabajo trabajo : trabajos) {
            if (trabajo instanceof Revision) {
                revisiones.add((Revision) trabajo);
            }
        }
        return revisiones;
    }

    /**
     * Obtiene la lista de revisiones de un cliente específico.
     * Delega la operación al modelo que filtra por cliente.
     * 
     * @param cliente Cliente del que obtener las revisiones
     * @return Lista de revisiones del cliente
     */
    @Override
    public List<Revision> getRevisiones(Cliente cliente) {
        List<Trabajo> trabajos = modeloCascada.getTrabajos(cliente);
        List<Revision> revisiones = new java.util.ArrayList<>();
        for (Trabajo trabajo : trabajos) {
            if (trabajo instanceof Revision) {
                revisiones.add((Revision) trabajo);
            }
        }
        return revisiones;
    }

    /**
     * Obtiene la lista de revisiones de un vehículo específico.
     * Delega la operación al modelo que filtra por vehículo.
     * 
     * @param vehiculo Vehículo del que obtener las revisiones
     * @return Lista de revisiones del vehículo
     */
    @Override
    public List<Revision> getRevisiones(Vehiculo vehiculo) {
        List<Trabajo> trabajos = modeloCascada.getTrabajos(vehiculo);
        List<Revision> revisiones = new java.util.ArrayList<>();
        for (Trabajo trabajo : trabajos) {
            if (trabajo instanceof Revision) {
                revisiones.add((Revision) trabajo);
            }
        }
        return revisiones;
    }

    /**
     * Añade horas a una revisión existente.
     * Delega la operación al modelo que valida y actualiza la revisión.
     * 
     * @param revision Revisión a la que añadir horas
     * @param horas Número de horas a añadir
     * @return Revisión actualizada
     * @throws OperationNotSupportedException Si la revisión no existe o está cerrada
     */
    @Override
    public Revision anadirHoras(Revision revision, int horas) throws OperationNotSupportedException {
        return modeloCascada.anadirHoras(revision, horas);
    }

    /**
     * Añade precio de material a una revisión existente.
     * Delega la operación al modelo que valida y actualiza la revisión.
     * 
     * @param revision Revisión a la que añadir precio
     * @param precioMaterial Precio del material a añadir
     * @return Revisión actualizada
     * @throws OperationNotSupportedException Si la revisión no existe o está cerrada
     */
    @Override
    public Revision anadirPrecioMaterial(Revision revision, float precioMaterial) {
        return modeloCascada.anadirPrecioMaterial(revision, precioMaterial);
    }

    /**
     * Cierra una revisión estableciendo su fecha de fin.
     * Delega la operación al modelo que valida la fecha y cierra la revisión.
     * 
     * @param revision Revisión a cerrar
     * @param fechaCierre Fecha de cierre de la revisión
     * @return Revisión cerrada
     * @throws OperationNotSupportedException Si la revisión no existe o la fecha es inválida
     */
    @Override
    public Revision cerrar(Revision revision, java.time.LocalDate fechaCierre) throws OperationNotSupportedException {
        Trabajo cerrado = modeloCascada.cerrar((Trabajo) revision, fechaCierre);
        return (Revision) cerrado;
    }

    @Override
    public void actualizar(Evento evento) {
        // Event handling implementation
    }
}
