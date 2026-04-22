package org.iesalandalus.programacion.tallermecanico.modelo;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.Clientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Revisiones;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.Vehiculos;

import java.time.LocalDate;
import java.util.*;

public class Modelo {

    Clientes clientes;
    Vehiculos vehiculos;
    Revisiones revisiones;

    /**
     * Inicializa las colecciones de clientes, vehículos y revisiones del taller.
     * Crea nuevas instancias de Clientes, Vehiculos y Revisiones.
     */
    public void comenzar() {
        clientes = new Clientes();
        vehiculos = new Vehiculos();
        revisiones = new Revisiones();
    }

    /**
     * Finaliza la ejecución del modelo mostrando un mensaje de confirmación.
     * Libera los recursos y cierra las operaciones del taller mecánico.
     */
    public void terminar() {
        System.out.println("El modelo del taller mecánico ha finalizado correctamente.");
    }

    /**
     * Inserta un nuevo cliente en el sistema.
     * Crea una copia del cliente para proteger la instancia original.
     * 
     * @param cliente Cliente a insertar
     * @throws TallerMecanicoExcepcion Si el cliente ya existe o es nulo
     */
    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        clientes.insertar(new Cliente(cliente));

    }

    /**
     * Inserta un nuevo vehículo en el sistema.
     * No crea copia porque Vehiculo es un record (inmutable).
     * 
     * @param vehiculo Vehículo a insertar
     * @throws TallerMecanicoExcepcion Si el vehículo ya existe o es nulo
     */
    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo, "No se puede insertar un vehículo nulo.");
        // No hace falta 'new Vehiculo(vehiculo)' porque es inmutable (ES UN REGISTRO).
        vehiculos.insertar(vehiculo);
    }

    /**
     * Inserta una nueva revisión en el sistema.
     * Crea una copia de la revisión para proteger la instancia original.
     * 
     * @param revision Revisión a insertar
     * @throws TallerMecanicoExcepcion Si la revisión ya existe o es nula
     */
    public void insertar(Revision revision) throws TallerMecanicoExcepcion {
        revisiones.insertar(new Revision(revision));

    }

    /**
     * Busca un cliente en el sistema.
     * Si existe, devuelve una copia para proteger la instancia original.
     * 
     * @param cliente Cliente a buscar
     * @return Copia del cliente encontrado o null si no existe
     */
    public Cliente buscar(Cliente cliente) {
        Objects.requireNonNull(cliente, "No se puede buscar un cliente nulo.");
        Cliente encontrado = clientes.buscar(cliente);
        // Si existe, devolvemos una copia para proteger la instancia original
        return (encontrado == null) ? null : new Cliente(encontrado);
    }


    /**
     * Busca un vehículo en el sistema.
     * Devuelve la referencia directamente porque es un record (inmutable).
     * 
     * @param vehiculo Vehículo a buscar
     * @return Vehículo encontrado o null si no existe
     */
    public Vehiculo buscar(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "No se puede buscar un vehículo nulo.");
        // Al ser record, devolvemos la referencia directamente
        return vehiculos.buscar(vehiculo);
    }


    /**
     * Busca una revisión en el sistema.
     * Si existe, devuelve una nueva instancia (copia) usando el constructor copia.
     * 
     * @param revision Revisión a buscar
     * @return Copia de la revisión encontrada o null si no existe
     */
    public Revision buscar(Revision revision) {
        Objects.requireNonNull(revision, "No se puede buscar una revisión nula.");
        Revision encontrada = revisiones.buscar(revision);
        // Si existe, usamos el constructor copia de Revision
        return (encontrada == null) ? null : new Revision(encontrada);
    }


    /**
     * Modifica los datos de un cliente existente.
     * Delega la operación a la clase Clientes.
     * 
     * @param cliente Cliente a modificar
     * @param nombre Nuevo nombre del cliente
     * @param telefono Nuevo teléfono del cliente
     * @return Cliente modificado
     * @throws TallerMecanicoExcepcion Si el cliente no existe o los datos son inválidos
     */
    public Cliente modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion {
        return clientes.modificar(cliente, nombre, telefono);
    }

    /**
     * Añade horas a una revisión existente.
     * Delega la operación a la clase Revisiones.
     * 
     * @param revision Revisión a la que añadir horas
     * @param horas Número de horas a añadir
     * @return Revisión actualizada
     * @throws TallerMecanicoExcepcion Si la revisión no existe o está cerrada
     */
    public Revision anadirHoras(Revision revision, int horas) throws TallerMecanicoExcepcion {
        return revisiones.anadirHoras(revision, horas);
    }

    /**
     * Añade precio de material a una revisión existente.
     * Delega la operación a la clase Revisiones.
     * 
     * @param revision Revisión a la que añadir precio
     * @param precioMaterial Precio del material a añadir
     * @return Revisión actualizada
     * @throws TallerMecanicoExcepcion Si la revisión no existe o está cerrada
     */
    public Revision anadirPrecioMaterial(Revision revision, float precioMaterial) {
        return revisiones.anadirPrecioMaterial(revision, precioMaterial);
    }

    /**
     * Cierra una revisión estableciendo su fecha de fin.
     * Cierra la revisión en el negocio y devuelve una copia para evitar aliasing.
     * 
     * @param revision Revisión a cerrar
     * @param fechaFin Fecha de cierre de la revisión
     * @return Nueva instancia de la revisión cerrada
     * @throws TallerMecanicoExcepcion Si la revisión no existe o la fecha es inválida
     */
    public Revision cerrar(Revision revision, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        // Cerramos la revisión en el negocio (donde se valida la fecha)
        Revision cerrada = revisiones.cerrar(revision, fechaFin);
        // Devolvemos una nueva instancia (constructor copia) para evitar Aliasing
        return new Revision(cerrada);
    }

    /**
     * Borra un cliente y todas sus revisiones asociadas (operación en cascada).
     * Primero borra las revisiones del cliente y luego el cliente mismo.
     * 
     * @param cliente Cliente a borrar
     * @throws TallerMecanicoExcepcion Si el cliente no existe
     */
    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        // 1. Obtenemos todas las revisiones de ese cliente
        List<Revision> revisionesCliente = revisiones.get(cliente);

        // 2. Borramos cada una de esas revisiones de la colección global
        for (Revision r : revisionesCliente) {
            revisiones.borrar(r);
        }

        // 3. Finalmente, borramos al cliente de su propia colección
        clientes.borrar(cliente);
    }

    /**
     * Borra un vehículo y todas sus revisiones asociadas (operación en cascada).
     * Primero borra las revisiones del vehículo y luego el vehículo mismo.
     * 
     * @param vehiculo Vehículo a borrar
     * @throws TallerMecanicoExcepcion Si el vehículo no existe
     */
    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        // 1. Obtenemos todas las revisiones de ese vehículo
        List<Revision> revisionesVehiculo = revisiones.get(vehiculo);

        // 2. Las borramos de la colección global de revisiones
        for (Revision r : revisionesVehiculo) {
            revisiones.borrar(r);
        }

        // 3. Finalmente, borramos el vehículo
        vehiculos.borrar(vehiculo);
    }

    /**
     * Borra una revisión específica del sistema.
     * No hay cascada, solo se borra la revisión indicada.
     * 
     * @param revision Revisión a borrar
     * @throws TallerMecanicoExcepcion Si la revisión no existe
     */
    public void borrar(Revision revision) throws TallerMecanicoExcepcion {
        revisiones.borrar(revision);
    }


    /**
     * Devuelve una lista con todos los clientes del sistema.
     * Crea copias de cada cliente para proteger las instancias originales.
     * 
     * @return Lista de copias de todos los clientes
     */
    public List<Cliente> getClientes() {
        List<Cliente> copias = new ArrayList<>();
        for (Cliente cliente : clientes.get()) {
            copias.add(new Cliente(cliente));
        }
        return copias;
    }

    /**
     * Devuelve una lista con todos los vehículos del sistema.
     * Al ser un record, no hace falta crear nuevas instancias de cada vehículo.
     * 
     * @return Lista de todos los vehículos
     */
    public List<Vehiculo> getVehiculos() {
        List<Vehiculo> copias = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculos.get()) {
            copias.add(vehiculo);
        }
        return copias;
    }


    /**
     * Devuelve una lista con todas las revisiones del sistema.
     * Crea copias de cada revisión usando el constructor copia.
     * 
     * @return Lista de copias de todas las revisiones
     */
    public List<Revision> getRevisiones() {
        List<Revision> copiasRevisiones = new ArrayList<>();

        for (Revision revision : revisiones.get()) {
            copiasRevisiones.add(new Revision(revision));
        }
        return copiasRevisiones;
    }

    /**
     * Devuelve una lista con todas las revisiones de un cliente específico.
     * Crea copias de cada revisión para proteger las instancias originales.
     * 
     * @param cliente Cliente del que obtener las revisiones
     * @return Lista de copias de las revisiones del cliente
     */
    public List<Revision> getRevisiones(Cliente cliente) {
        List<Revision> copias = new ArrayList<>();
        for (Revision revision : revisiones.get(cliente)) {
            copias.add(new Revision(revision));
        }
        return copias;
    }

    /**
     * Devuelve una lista con todas las revisiones de un vehículo específico.
     * Crea copias de cada revisión para proteger las instancias originales.
     * 
     * @param vehiculo Vehículo del que obtener las revisiones
     * @return Lista de copias de las revisiones del vehículo
     */
    public List<Revision> getRevisiones(Vehiculo vehiculo) {
        List<Revision> copias = new ArrayList<>();
        for (Revision revision : revisiones.get(vehiculo)) {
            copias.add(new Revision(revision));
        }
        return copias;
    }
}


