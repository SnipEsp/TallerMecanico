package org.iesalandalus.programacion.tallermecanico.modelo.cascada;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IFuenteDatos;

import java.time.LocalDate;
import java.util.*;

/**
 * Implementación del modelo del taller mecánico que gestiona las operaciones
 * de clientes, vehículos y trabajos con borrado en cascada.
 * <p>
 * El borrado en cascada significa que al borrar un cliente o vehículo,
 * automáticamente se borran todos los trabajos asociados a ellos.
 * <p>
 * Esta clase utiliza el patrón Factory para crear las colecciones de datos
 * a través de la interfaz IFuenteDatos, permitiendo cambiar la implementación
 * (memoria, ficheros, base de datos) sin modificar esta clase.
 */
public class ModeloCascada implements Modelo {

    /**
     * Factoría que crea las colecciones de datos (clientes, vehículos, trabajos)
     */
    private final IFuenteDatos fuenteDatos;

    /**
     * Colección de clientes del taller
     */
    IClientes clientes;

    /**
     * Colección de vehículos del taller
     */
    IVehiculos vehiculos;

    /**
     * Colección de trabajos (revisiones y mecánicos) del taller
     */
    ITrabajos trabajos;

    /**
     * Constructor que recibe la factoría de datos.
     *
     * @param fuenteDatos Factoría que creará las colecciones cuando se llame a comenzar()
     */
    public ModeloCascada(IFuenteDatos fuenteDatos) {
        this.fuenteDatos = fuenteDatos;
    }

    /**
     * Constructor que recibe las colecciones directamente (para testing).
     *
     * @param clientes  Colección de clientes
     * @param vehiculos Colección de vehículos
     * @param trabajos  Colección de trabajos
     */
    public ModeloCascada(IClientes clientes, IVehiculos vehiculos, ITrabajos trabajos) {
        this.clientes = clientes;
        this.vehiculos = vehiculos;
        this.trabajos = trabajos;
        this.fuenteDatos = null;
    }

    /**
     * Inicializa las colecciones de datos del taller usando la factoría.
     * <p>
     * Este método debe llamarse antes de cualquier otra operación.
     * Crea las instancias de las colecciones (clientes, vehículos, trabajos)
     * a través de la factoría IFuenteDatos, lo que permite cambiar la
     * implementación (memoria, ficheros, BD) sin modificar este código.
     */
    @Override
    public void comenzar() {
        if (fuenteDatos != null) {
            clientes = fuenteDatos.crearClientes();
            vehiculos = fuenteDatos.crearVehiculos();
            trabajos = fuenteDatos.crearTrabajos();
        }
    }

    /**
     * Finaliza la ejecución del modelo.
     * <p>
     * En una implementación con persistencia (ficheros, base de datos),
     * aquí se guardarían los datos antes de cerrar.
     */
    @Override
    public void terminar() {
        System.out.println("El modelo del taller mecánico ha finalizado correctamente.");
    }

    /**
     * Inserta un nuevo cliente en el sistema.
     * <p>
     * Crea una copia del cliente antes de insertarlo para proteger la instancia
     * original que pasó el cliente (evita que modificaciones externas afecten
     * al dato almacenado).
     *
     * @param cliente Cliente a insertar
     * @throws TallerMecanicoExcepcion Si el cliente ya existe o es nulo
     */
    @Override
    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        clientes.insertar(new Cliente(cliente));
    }

    /**
     * Inserta un nuevo vehículo en el sistema.
     * <p>
     * No crea copia porque Vehiculo es un record (inmutable por diseño en Java),
     * por lo que no puede ser modificado después de su creación.
     *
     * @param vehiculo Vehículo a insertar
     * @throws TallerMecanicoExcepcion Si el vehículo ya existe o es nulo
     */
    @Override
    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo, "No se puede insertar un vehículo nulo.");
        vehiculos.insertar(vehiculo);
    }

    /**
     * Inserta un nuevo trabajo (revisión o mecánico) en el sistema.
     * <p>
     * Usa el método estático Trabajo.copiar() que crea la copia adecuada
     * según el tipo concreto (Revision o Mecanico). Esto protege la instancia
     * original y evita problemas de polimorfismo.
     *
     * @param trabajo Trabajo a insertar (puede ser Revision o Mecanico)
     * @throws TallerMecanicoExcepcion Si el trabajo ya existe o es nulo
     */
    @Override
    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        clientes.buscar(trabajo.getCliente());
        vehiculos.buscar(trabajo.getVehiculo());
        trabajos.insertar(Trabajo.copiar(trabajo));
    }

    /**
     * Busca un cliente en el sistema usando su DNI.
     * <p>
     * Si el cliente existe, devuelve una copia para proteger la instancia
     * original almacenada en la colección (evita que el cliente modifique
     * directamente el dato almacenado).
     *
     * @param cliente Cliente a buscar (solo se usa el DNI para la búsqueda)
     * @return Copia del cliente encontrado o null si no existe
     */
    @Override
    public Cliente buscar(Cliente cliente) {
        Objects.requireNonNull(cliente, "No se puede buscar un cliente nulo.");
        Cliente encontrado = clientes.buscar(cliente);
        return (encontrado == null) ? null : new Cliente(encontrado);
    }


    /**
     * Busca un vehículo en el sistema usando su matrícula.
     * <p>
     * Devuelve la referencia directamente porque Vehiculo es un record
     * (inmutable), por lo que no hay riesgo de que el cliente modifique
     * el dato almacenado.
     *
     * @param vehiculo Vehículo a buscar (solo se usa la matrícula para la búsqueda)
     * @return Vehículo encontrado o null si no existe
     */
    @Override
    public Vehiculo buscar(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "No se puede buscar un vehículo nulo.");
        return vehiculos.buscar(vehiculo);
    }


    /**
     * Busca una revisión en el sistema.
     * <p>
     * La búsqueda se realiza en la colección de trabajos usando el cliente,
     * vehículo y fecha de inicio. Si se encuentra, devuelve una copia
     * para proteger la instancia original.
     *
     * @param revision Revisión a buscar
     * @return Copia de la revisión encontrada o null si no existe
     */
    @Override
    public Revision buscar(Revision revision) {
        Objects.requireNonNull(revision, "No se puede buscar una revisión nula.");
        Trabajo encontrado = trabajos.buscar(revision);
        return (encontrado == null) ? null : (Revision) Trabajo.copiar(encontrado);
    }

    /**
     * Busca un trabajo en el sistema.
     * <p>
     * La búsqueda se realiza en la colección de trabajos. Si se encuentra, devuelve una copia
     * para proteger la instancia original.
     *
     * @param trabajo Trabajo a buscar
     * @return Copia del trabajo encontrado o null si no existe
     */
    @Override
    public Trabajo buscar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "No se puede buscar un trabajo nulo.");
        Trabajo encontrado = trabajos.buscar(trabajo);
        return (encontrado == null) ? null : (Trabajo) Trabajo.copiar(encontrado);
    }


    /**
     * Modifica el nombre y teléfono de un cliente existente.
     * <p>
     * Delega la operación a la colección de clientes, que valida que
     * el cliente exista y actualiza sus datos.
     *
     * @param cliente  Cliente a modificar (se usa el DNI para identificarlo)
     * @param nombre   Nuevo nombre del cliente
     * @param telefono Nuevo teléfono del cliente
     * @return Cliente modificado
     * @throws TallerMecanicoExcepcion Si el cliente no existe o los datos son inválidos
     */
    @Override
    public Cliente modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion {
        return clientes.modificar(cliente, nombre, telefono);
    }

    /**
     * Añade horas a una revisión existente.
     * <p>
     * Delega la operación a la colección de trabajos, que valida que
     * la revisión exista, no esté cerrada y que las horas sean positivas.
     *
     * @param revision Revisión a la que añadir horas
     * @param horas    Número de horas a añadir (debe ser mayor que 0)
     * @return Revisión actualizada con las nuevas horas
     * @throws TallerMecanicoExcepcion Si la revisión no existe, está cerrada o las horas son inválidas
     */
    @Override
    public Revision anadirHoras(Revision revision, int horas) throws TallerMecanicoExcepcion {
        return (Revision) trabajos.anadirHoras(revision, horas);
    }

    /**
     * Añade horas a un trabajo existente.
     * <p>
     * Delega la operación a la colección de trabajos, que valida que
     * el trabajo exista, no esté cerrado y que las horas sean positivas.
     *
     * @param trabajo Trabajo al que añadir horas
     * @param horas   Número de horas a añadir (debe ser mayor que 0)
     * @return Trabajo actualizado con las nuevas horas
     * @throws TallerMecanicoExcepcion Si el trabajo no existe, está cerrado o las horas son inválidas
     */
    @Override
    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        Trabajo trabajoActualizado = trabajos.anadirHoras(trabajo, horas);
        return (Trabajo) Trabajo.copiar(trabajoActualizado);
    }

    /**
     * Añade horas al trabajo abierto de un vehículo.
     * <p>
     * Busca el trabajo abierto del vehículo y le añade las horas especificadas.
     *
     * @param vehiculo Vehículo cuyo trabajo abierto se actualizará
     * @param horas    Número de horas a añadir (debe ser mayor que 0)
     * @return Trabajo actualizado con las nuevas horas
     * @throws TallerMecanicoExcepcion Si no hay trabajo abierto o las horas son inválidas
     */
    @Override
    public Trabajo anadirHoras(Vehiculo vehiculo, int horas) throws TallerMecanicoExcepcion {
        Trabajo trabajoAbierto = trabajos.getTrabajoAbierto(vehiculo);
        if (trabajoAbierto == null) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }
        Trabajo trabajoActualizado = trabajos.anadirHoras(trabajoAbierto, horas);
        return (Trabajo) Trabajo.copiar(trabajoActualizado);
    }

    /**
     * Añade el precio del material utilizado en una revisión.
     * <p>
     * Solo aplicable a revisiones (no a trabajos mecánicos).
     * Delega la operación a la colección de trabajos.
     *
     * @param revision       Revisión a la que añadir precio
     * @param precioMaterial Precio del material a añadir
     * @return Revisión actualizada con el precio del material
     * @throws TallerMecanicoExcepcion Si la revisión no existe o está cerrada
     */
    @Override
    public Revision anadirPrecioMaterial(Revision revision, float precioMaterial) {
        return (Revision) trabajos.anadirPrecioMaterial(revision, precioMaterial);
    }

    /**
     * Añade el precio del material utilizado en un trabajo.
     * <p>
     * Solo aplicable a trabajos mecánicos.
     * Delega la operación a la colección de trabajos.
     *
     * @param trabajo        Trabajo al que añadir precio
     * @param precioMaterial Precio del material a añadir
     * @return Trabajo actualizado con el precio del material
     * @throws TallerMecanicoExcepcion Si el trabajo no existe o está cerrado
     */
    @Override
    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        Trabajo trabajoActualizado = trabajos.anadirPrecioMaterial(trabajo, precioMaterial);
        return (Trabajo) Trabajo.copiar(trabajoActualizado);
    }

    /**
     * Añade el precio del material al trabajo abierto de un vehículo.
     * <p>
     * Busca el trabajo abierto del vehículo y le añade el precio del material especificado.
     *
     * @param vehiculo       Vehículo cuyo trabajo abierto se actualizará
     * @param precioMaterial Precio del material a añadir
     * @return Trabajo actualizado con el precio del material
     * @throws TallerMecanicoExcepcion Si no hay trabajo abierto o el precio es inválido
     */
    @Override
    public Trabajo anadirPrecioMaterial(Vehiculo vehiculo, float precioMaterial) throws TallerMecanicoExcepcion {
        Trabajo trabajoAbierto = trabajos.getTrabajoAbierto(vehiculo);
        if (trabajoAbierto == null) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }
        Trabajo trabajoActualizado = trabajos.anadirPrecioMaterial(trabajoAbierto, precioMaterial);
        return (Trabajo) Trabajo.copiar(trabajoActualizado);
    }

    /**
     * Cierra un trabajo estableciendo su fecha de fin.
     * <p>
     * Una vez cerrado, no se pueden añadir más horas ni material.
     * Delega la validación de la fecha a la colección de trabajos.
     * Devuelve una copia para evitar aliasing (que el cliente modifique
     * el trabajo almacenado a través de la referencia devuelta).
     *
     * @param trabajo  Trabajo a cerrar
     * @param fechaFin Fecha de cierre del trabajo (no puede ser anterior a la fecha de inicio)
     * @return Nueva instancia del trabajo cerrado
     * @throws TallerMecanicoExcepcion Si el trabajo no existe, ya está cerrado o la fecha es inválida
     */
    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Trabajo cerrado = trabajos.cerrar(trabajo, fechaFin);
        return (Trabajo) Trabajo.copiar(cerrado);
    }

    /**
     * Cierra el trabajo abierto de un vehículo estableciendo su fecha de fin.
     * <p>
     * Busca el trabajo abierto del vehículo y lo cierra con la fecha especificada.
     *
     * @param vehiculo Vehículo cuyo trabajo abierto se cerrará
     * @param fechaFin Fecha de cierre del trabajo (no puede ser anterior a la fecha de inicio)
     * @return Trabajo cerrado
     * @throws TallerMecanicoExcepcion Si no hay trabajo abierto o la fecha es inválida
     */
    @Override
    public Trabajo cerrar(Vehiculo vehiculo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Trabajo trabajoAbierto = trabajos.getTrabajoAbierto(vehiculo);
        if (trabajoAbierto == null) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }
        Trabajo cerrado = trabajos.cerrar(trabajoAbierto, fechaFin);
        return (Trabajo) Trabajo.copiar(cerrado);
    }

    /**
     * Borra un cliente y todos sus trabajos asociados (operación en cascada).
     * <p>
     * El borrado en cascada es necesario para mantener la integridad de los datos:
     * no pueden existir trabajos sin un cliente asociado.
     * <p>
     * Orden de operaciones:
     * 1. Obtiene todos los trabajos del cliente
     * 2. Borra cada trabajo de la colección global
     * 3. Borra el cliente de su colección
     *
     * @param cliente Cliente a borrar
     * @throws TallerMecanicoExcepcion Si el cliente no existe
     */
    @Override
    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        List<Trabajo> trabajosCliente = trabajos.get(cliente);
        for (Trabajo t : trabajosCliente) {
            trabajos.borrar(t);
        }
        clientes.borrar(cliente);
    }

    /**
     * Borra un vehículo y todos sus trabajos asociados (operación en cascada).
     * <p>
     * El borrado en cascada es necesario para mantener la integridad de los datos:
     * no pueden existir trabajos sin un vehículo asociado.
     * <p>
     * Orden de operaciones:
     * 1. Obtiene todos los trabajos del vehículo
     * 2. Borra cada trabajo de la colección global
     * 3. Borra el vehículo de su colección
     *
     * @param vehiculo Vehículo a borrar
     * @throws TallerMecanicoExcepcion Si el vehículo no existe
     */
    @Override
    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        List<Trabajo> trabajosVehiculo = trabajos.get(vehiculo);
        for (Trabajo t : trabajosVehiculo) {
            trabajos.borrar(t);
        }
        vehiculos.borrar(vehiculo);
    }

    /**
     * Borra un trabajo específico del sistema.
     * <p>
     * A diferencia de borrar(Cliente) y borrar(Vehiculo), este método
     * NO realiza borrado en cascada. Solo borra el trabajo indicado,
     * dejando el cliente y vehículo asociados intactos.
     * Devuelve una copia del trabajo borrado.
     *
     * @param trabajo Trabajo a borrar
     * @return Copia del trabajo borrado
     * @throws TallerMecanicoExcepcion Si el trabajo no existe
     */
    @Override
    public Trabajo borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Trabajo borrado = trabajos.borrar(trabajo);
        return (Trabajo) Trabajo.copiar(borrado);
    }


    /**
     * Devuelve una lista con todos los clientes del sistema.
     * <p>
     * Crea copias de cada cliente para proteger las instancias originales
     * almacenadas en la colección. Esto evita que el cliente modifique
     * accidentalmente los datos almacenados.
     *
     * @return Lista de copias de todos los clientes
     */
    @Override
    public List<Cliente> getClientes() {
        List<Cliente> copias = new ArrayList<>();
        for (Cliente cliente : clientes.get()) {
            copias.add(new Cliente(cliente));
        }
        return copias;
    }

    /**
     * Devuelve una lista con todos los vehículos del sistema.
     * <p>
     * No crea copias porque Vehiculo es un record (inmutable), por lo que
     * no hay riesgo de que el cliente modifique los datos almacenados.
     *
     * @return Lista de todos los vehículos
     */
    @Override
    public List<Vehiculo> getVehiculos() {
        List<Vehiculo> copias = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculos.get()) {
            copias.add(vehiculo);
        }
        return copias;
    }


    /**
     * Devuelve una lista con todos los trabajos del sistema.
     * <p>
     * Incluye tanto revisiones como trabajos mecánicos.
     * Crea copias de cada trabajo usando el método estático Trabajo.copiar()
     * que maneja correctamente el polimorfismo (crea Revision o Mecanico según corresponda).
     *
     * @return Lista de copias de todos los trabajos
     */
    public List<Trabajo> getTrabajos() {
        List<Trabajo> copiasTrabajos = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get()) {
            copiasTrabajos.add((Trabajo) Trabajo.copiar(trabajo));
        }
        return copiasTrabajos;
    }

    /**
     * Devuelve una lista con todos los trabajos de un cliente específico.
     * <p>
     * Filtra los trabajos por el cliente especificado y crea copias
     * para proteger las instancias originales.
     *
     * @param cliente Cliente del que obtener los trabajos
     * @return Lista de copias de los trabajos del cliente
     */
    public List<Trabajo> getTrabajos(Cliente cliente) {
        List<Trabajo> copias = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(cliente)) {
            copias.add((Trabajo) Trabajo.copiar(trabajo));
        }
        return copias;
    }

    /**
     * Devuelve una lista con todos los trabajos de un vehículo específico.
     * <p>
     * Filtra los trabajos por el vehículo especificado y crea copias
     * para proteger las instancias originales.
     *
     * @param vehiculo Vehículo del que obtener los trabajos
     * @return Lista de copias de los trabajos del vehículo
     */
    public List<Trabajo> getTrabajos(Vehiculo vehiculo) {
        List<Trabajo> copias = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(vehiculo)) {
            copias.add((Trabajo) Trabajo.copiar(trabajo));
        }
        return copias;
    }
}


