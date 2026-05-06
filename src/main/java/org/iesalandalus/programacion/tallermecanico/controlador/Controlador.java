package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.FabricaModelo;
import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.cascada.ModeloCascada;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Mecanico;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.FabricaFuenteDatos;
import org.iesalandalus.programacion.tallermecanico.vista.FabricaVista;
import org.iesalandalus.programacion.tallermecanico.vista.IVista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.List;

/**
 * Clase que actúa como intermediario entre el Modelo y la Vista.
 * Implementa el patrón MVC (Modelo-Vista-Controlador) gestionando las
 * operaciones del taller mecánico y coordinando la interacción entre componentes.
 */
public class Controlador implements IControlador {

    private Modelo modelo;
    private IVista vista;

    /**
     * Constructor que inicializa el controlador con las fábricas.
     * Crea el modelo y la vista utilizando las fábricas proporcionadas.
     * 
     * @param fabricaModelo Fábrica para crear el modelo
     * @param fabricaFuenteDatos Fábrica para crear la fuente de datos
     * @param fabricaVista Fábrica para crear la vista
     * @throws IllegalArgumentException Si alguna fábrica es nula
     */
    public Controlador(FabricaModelo fabricaModelo, FabricaFuenteDatos fabricaFuenteDatos, FabricaVista fabricaVista) {
        if (fabricaModelo == null) {
            throw new IllegalArgumentException("La fábrica de modelo no puede ser nula.");
        }
        if (fabricaFuenteDatos == null) {
            throw new IllegalArgumentException("La fábrica de fuente de datos no puede ser nula.");
        }
        if (fabricaVista == null) {
            throw new IllegalArgumentException("La fábrica de vista no puede ser nula.");
        }
        
        // Crear el modelo y la vista usando las fábricas
        this.modelo = fabricaModelo.crear(fabricaFuenteDatos);
        this.vista = fabricaVista.crear();
        this.vista.setControlador(this);
    }

    /**
     * Inicia la aplicación inicializando el modelo y la vista.
     * Prepara el sistema para comenzar las operaciones.
     */
    @Override
    public void comenzar() {
        modelo.comenzar();
        vista.comenzar();
    }

    /**
     * Finaliza la aplicación cerrando el modelo y la vista.
     * Libera recursos y muestra mensajes de finalización.
     */
    @Override
    public void terminar() {
        modelo.terminar();
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
        modelo.insertar(cliente);
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
        modelo.insertar(vehiculo);
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
        Cliente clienteReal = modelo.buscar(revision.getCliente());
        if (clienteReal == null) {
            throw new OperationNotSupportedException("No existe un cliente con el DNI proporcionado.");
        }
        
        // Buscar el vehículo real usando la matrícula del vehículo de búsqueda
        Vehiculo vehiculoReal = modelo.buscar(revision.getVehiculo());
        if (vehiculoReal == null) {
            throw new OperationNotSupportedException("No existe un vehículo con la matrícula proporcionada.");
        }
        
        // Crear la revisión con el cliente y vehículo reales
        Revision revisionReal = new Revision(clienteReal, vehiculoReal, revision.getFechaInicio());
        modelo.insertar(revisionReal);
    }

    /**
     * Inserta un nuevo trabajo mecánico en el sistema.
     * Busca el cliente y vehículo reales antes de crear el trabajo mecánico.
     * 
     * @param mecanico Trabajo mecánico a insertar (con cliente de búsqueda)
     * @throws OperationNotSupportedException Si el trabajo mecánico ya existe o es inválido
     */
    @Override
    public void insertar(Mecanico mecanico) throws OperationNotSupportedException {
        // Buscar el cliente real usando el DNI del cliente de búsqueda
        Cliente clienteReal = modelo.buscar(mecanico.getCliente());
        if (clienteReal == null) {
            throw new OperationNotSupportedException("No existe un cliente con el DNI proporcionado.");
        }
        
        // Buscar el vehículo real usando la matrícula del vehículo de búsqueda
        Vehiculo vehiculoReal = modelo.buscar(mecanico.getVehiculo());
        if (vehiculoReal == null) {
            throw new OperationNotSupportedException("No existe un vehículo con la matrícula proporcionada.");
        }
        
        // Crear el trabajo mecánico con el cliente y vehículo reales
        Mecanico mecanicoReal = new Mecanico(clienteReal, vehiculoReal, mecanico.getFechaInicio());
        modelo.insertar(mecanicoReal);
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
        return modelo.buscar(cliente);
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
        return modelo.buscar(vehiculo);
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
        return modelo.buscar(revision);
    }

    /**
     * Busca un trabajo mecánico en el sistema.
     * Delega la búsqueda al modelo y devuelve el resultado.
     * 
     * @param mecanico Trabajo mecánico a buscar
     * @return Trabajo mecánico encontrado o null si no existe
     */
    @Override
    public Mecanico buscar(Mecanico mecanico) {
        Trabajo trabajo = modelo.buscar((Trabajo) mecanico);
        return (trabajo instanceof Mecanico) ? (Mecanico) trabajo : null;
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
        return modelo.modificar(cliente, nombre, telefono);
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
        modelo.borrar(cliente);
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
        modelo.borrar(vehiculo);
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
        Trabajo borrado = modelo.borrar((Trabajo) revision);
        return (Revision) borrado;
    }

    /**
     * Borra un trabajo mecánico específico del sistema.
     * Delega la operación al modelo para su procesamiento.
     * 
     * @param mecanico Trabajo mecánico a borrar
     * @return Trabajo mecánico borrado
     * @throws OperationNotSupportedException Si el trabajo mecánico no existe
     */
    @Override
    public Mecanico borrar(Mecanico mecanico) throws OperationNotSupportedException {
        Trabajo borrado = modelo.borrar((Trabajo) mecanico);
        return (Mecanico) borrado;
    }

    /**
     * Borra un trabajo específico del sistema.
     * Delega la operación al modelo para su procesamiento.
     * 
     * @param trabajo Trabajo a borrar
     * @return Trabajo borrado
     * @throws OperationNotSupportedException Si el trabajo no existe
     */
    @Override
    public Trabajo borrar(Trabajo trabajo) throws OperationNotSupportedException {
        return modelo.borrar(trabajo);
    }

    /**
     * Obtiene la lista de todos los clientes del sistema.
     * Delega la operación al modelo que devuelve copias protectoras.
     * 
     * @return Lista de clientes
     */
    @Override
    public List<Cliente> getClientes() {
        return modelo.getClientes();
    }

    /**
     * Obtiene la lista de todos los vehículos del sistema.
     * Delega la operación al modelo que devuelve las referencias directamente.
     * 
     * @return Lista de vehículos
     */
    @Override
    public List<Vehiculo> getVehiculos() {
        return modelo.getVehiculos();
    }

    /**
     * Obtiene la lista de todos los trabajos del sistema.
     * Delega la operación al modelo que devuelve copias protectoras.
     * 
     * @return Lista de trabajos
     */
    @Override
    public List<Trabajo> getTrabajos() {
        return modelo.getTrabajos();
    }

    /**
     * Obtiene la lista de trabajos de un cliente específico.
     * Delega la operación al modelo que filtra por cliente.
     * 
     * @param cliente Cliente del que obtener los trabajos
     * @return Lista de trabajos del cliente
     */
    @Override
    public List<Trabajo> getTrabajos(Cliente cliente) {
        return modelo.getTrabajos(cliente);
    }

    /**
     * Obtiene la lista de trabajos de un vehículo específico.
     * Delega la operación al modelo que filtra por vehículo.
     * 
     * @param vehiculo Vehículo del que obtener los trabajos
     * @return Lista de trabajos del vehículo
     */
    @Override
    public List<Trabajo> getTrabajos(Vehiculo vehiculo) {
        return modelo.getTrabajos(vehiculo);
    }

    /**
     * Obtiene la lista de todas las revisiones del sistema.
     * Delega la operación al modelo que devuelve copias protectoras.
     * 
     * @return Lista de revisiones
     */
    @Override
    public List<Revision> getRevisiones() {
        List<Trabajo> trabajos = modelo.getTrabajos();
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
        List<Trabajo> trabajos = modelo.getTrabajos(cliente);
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
        List<Trabajo> trabajos = modelo.getTrabajos(vehiculo);
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
        return modelo.anadirHoras(revision, horas);
    }

    /**
     * Añade horas a un trabajo mecánico existente.
     * Delega la operación al modelo que valida y actualiza el trabajo mecánico.
     * 
     * @param mecanico Trabajo mecánico al que añadir horas
     * @param horas Número de horas a añadir
     * @return Trabajo mecánico actualizado
     * @throws OperationNotSupportedException Si el trabajo mecánico no existe o está cerrado
     */
    @Override
    public Mecanico anadirHoras(Mecanico mecanico, int horas) throws OperationNotSupportedException {
        Trabajo trabajo = modelo.anadirHoras((Trabajo) mecanico, horas);
        return (Mecanico) trabajo;
    }

    /**
     * Añade horas al trabajo abierto de un vehículo.
     * Delega la operación al modelo que valida y actualiza el trabajo.
     * 
     * @param vehiculo Vehículo cuyo trabajo abierto se actualizará
     * @param horas Número de horas a añadir
     * @return Trabajo actualizado
     * @throws OperationNotSupportedException Si no hay trabajo abierto o está cerrado
     */
    @Override
    public Trabajo anadirHoras(Vehiculo vehiculo, int horas) throws OperationNotSupportedException {
        return modelo.anadirHoras(vehiculo, horas);
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
        return modelo.anadirPrecioMaterial(revision, precioMaterial);
    }

    /**
     * Añade precio de material a un trabajo mecánico existente.
     * Delega la operación al modelo que valida y actualiza el trabajo mecánico.
     * 
     * @param mecanico Trabajo mecánico al que añadir precio
     * @param precioMaterial Precio del material a añadir
     * @return Trabajo mecánico actualizado
     * @throws OperationNotSupportedException Si el trabajo mecánico no existe o está cerrado
     */
    @Override
    public Mecanico anadirPrecioMaterial(Mecanico mecanico, float precioMaterial) throws OperationNotSupportedException {
        Trabajo trabajo = modelo.anadirPrecioMaterial((Trabajo) mecanico, precioMaterial);
        return (Mecanico) trabajo;
    }

    /**
     * Añade precio de material al trabajo abierto de un vehículo.
     * Delega la operación al modelo que valida y actualiza el trabajo.
     * 
     * @param vehiculo Vehículo cuyo trabajo abierto se actualizará
     * @param precioMaterial Precio del material a añadir
     * @return Trabajo actualizado
     * @throws OperationNotSupportedException Si no hay trabajo abierto o está cerrado
     */
    @Override
    public Trabajo anadirPrecioMaterial(Vehiculo vehiculo, float precioMaterial) throws OperationNotSupportedException {
        return modelo.anadirPrecioMaterial(vehiculo, precioMaterial);
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
    public Revision cerrar(Revision revision, LocalDate fechaCierre) throws OperationNotSupportedException {
        Trabajo cerrado = modelo.cerrar((Trabajo) revision, fechaCierre);
        return (Revision) cerrado;
    }

    /**
     * Cierra un trabajo mecánico estableciendo su fecha de fin.
     * Delega la operación al modelo que valida la fecha y cierra el trabajo.
     * 
     * @param mecanico Trabajo mecánico a cerrar
     * @param fechaCierre Fecha de cierre del trabajo
     * @return Trabajo mecánico cerrado
     * @throws OperationNotSupportedException Si el trabajo no existe o la fecha es inválida
     */
    @Override
    public Mecanico cerrar(Mecanico mecanico, LocalDate fechaCierre) throws OperationNotSupportedException {
        Trabajo cerrado = modelo.cerrar((Trabajo) mecanico, fechaCierre);
        return (Mecanico) cerrado;
    }

    /**
     * Cierra el trabajo abierto de un vehículo estableciendo su fecha de fin.
     * Delega la operación al modelo que valida la fecha y cierra el trabajo.
     * 
     * @param vehiculo Vehículo cuyo trabajo abierto se cerrará
     * @param fechaCierre Fecha de cierre del trabajo
     * @return Trabajo cerrado
     * @throws OperationNotSupportedException Si no hay trabajo abierto o la fecha es inválida
     */
    @Override
    public Trabajo cerrar(Vehiculo vehiculo, LocalDate fechaCierre) throws OperationNotSupportedException {
        return modelo.cerrar(vehiculo, fechaCierre);
    }

    @Override
    public void actualizar(Evento evento) {
        // Event handling implementation - not needed for text-based view
    }
}
