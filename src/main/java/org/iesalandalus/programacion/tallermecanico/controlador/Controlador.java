package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;



import java.util.Objects;

public class Controlador implements IControlador {
    private final Modelo modelo;
    private final Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        Objects.requireNonNull(modelo, "El modelo no puede ser nulo.");
        Objects.requireNonNull(vista, "La vista no puede ser nula.");
        this.modelo = modelo;
        this.vista = vista;
        vista.getGestorEventos().suscribir(this, Evento.values());
    }
    @Override
    public void comenzar() {
        modelo.comenzar();
        vista.comenzar();
    }
    @Override
    public void terminar() {
        vista.terminar();
        modelo.terminar();
    }

    @Override
    public void actualizar(Evento evento) {
        try {
            switch (evento) {
                case INSERTAR_CLIENTE -> {
                    modelo.insertar(vista.leerCliente());
                    vista.notificarResultado(Evento.INSERTAR_CLIENTE,"Cliente insertado correctamente.", true);
                }
                case BUSCAR_CLIENTE -> {
                    System.out.println(modelo.buscar(vista.leerClienteDni()));
                    vista.notificarResultado(Evento.BUSCAR_CLIENTE,"Cliente encontrado.", true);
                }
                case BORRAR_CLIENTE -> {
                    modelo.borrar(vista.leerClienteDni());
                    vista.notificarResultado(Evento.BORRAR_CLIENTE,"Cliente borrado correctamente.", true);
                }
                case LISTAR_CLIENTES -> {
                    vista.mostrarClientes(modelo.getClientes());
                    vista.notificarResultado(Evento.LISTAR_CLIENTES,"Clientes listados correctamente.", true);
                }
                case MODIFICAR_CLIENTE -> {
                    modelo.modificar(vista.leerClienteDni(), vista.leerNuevoNombre(), vista.leerNuevoTelefono());
                    vista.notificarResultado(Evento.MODIFICAR_CLIENTE,"Cliente modificado correctamente.", true);
                }
                case INSERTAR_VEHICULO -> {
                    modelo.insertar(vista.leerVehiculo());
                    vista.notificarResultado(Evento.INSERTAR_VEHICULO,"Vehículo insertado correctamente.", true);
                }
                case BUSCAR_VEHICULO -> {
                    System.out.println(modelo.buscar(vista.leerVehiculoMatricula()));
                    vista.notificarResultado(Evento.BUSCAR_VEHICULO,"Vehículo encontrado.", true);
                }
                case BORRAR_VEHICULO -> {
                    modelo.borrar(vista.leerVehiculoMatricula());
                    vista.notificarResultado(Evento.BORRAR_VEHICULO,"Vehículo borrado correctamente.", true);
                }
                case LISTAR_VEHICULOS -> {
                    vista.mostrarVehiculos(modelo.getVehiculos());
                    vista.notificarResultado(Evento.LISTAR_VEHICULOS,"Vehículos listados correctamente.", true);
                }
                case INSERTAR_REVISION -> {
                    modelo.insertar(vista.leerRevision());
                    vista.notificarResultado(Evento.INSERTAR_REVISION,"Revisión insertada correctamente.", true);
                }
                case INSERTAR_MECANICO -> {
                    modelo.insertar(vista.leerMecanico());
                    vista.notificarResultado(Evento.INSERTAR_MECANICO,"Mecánico insertado correctamente.", true);
                }
                case BUSCAR_TRABAJO -> {
                    System.out.println(modelo.buscar(vista.leerTrabajoVehiculo()));
                    vista.notificarResultado(Evento.BUSCAR_TRABAJO,"Trabajo encontrado.", true);
                }
                case BORRAR_TRABAJO -> {
                    modelo.borrar(vista.leerRevision());
                    vista.notificarResultado(Evento.BORRAR_TRABAJO,"Trabajo borrado correctamente.", true);
                }
                case LISTAR_TRABAJOS -> {
                    vista.mostrarTrabajos(modelo.getTrabajos());
                    vista.notificarResultado(Evento.LISTAR_TRABAJOS,"Trabajos listados correctamente.", true);
                }
                case LISTAR_TRABAJOS_CLIENTE -> {
                    vista.mostrarTrabajos(modelo.getTrabajos(vista.leerClienteDni()));
                    vista.notificarResultado(Evento.LISTAR_TRABAJOS_CLIENTE,"Trabajos del cliente listados correctamente.", true);
                }
                case LISTAR_TRABAJOS_VEHICULO -> {
                    vista.mostrarTrabajos(modelo.getTrabajos(vista.leerVehiculoMatricula()));
                    vista.notificarResultado(Evento.LISTAR_TRABAJOS_VEHICULO,"Trabajos del vehículo listados correctamente.", true);
                }
                case ANADIR_HORAS_TRABAJO -> {
                    modelo.anadirHoras(vista.leerTrabajoVehiculo(), vista.leerHoras());
                    vista.notificarResultado(Evento.ANADIR_HORAS_TRABAJO,"Horas añadidas al trabajo correctamente.", true);
                }
                case ANADIR_PRECIO_MATERIAL_TRABAJO -> {
                    modelo.anadirPrecioMaterial(vista.leerTrabajoVehiculo(), vista.leerPrecioMaterial());
                    vista.notificarResultado(Evento.ANADIR_PRECIO_MATERIAL_TRABAJO,"Precio del material añadido al trabajo correctamente.", true);
                }
                case CERRAR_TRABAJO -> {
                    modelo.cerrar(vista.leerTrabajoVehiculo(), vista.leerFechaCierre());
                    vista.notificarResultado(Evento.CERRAR_TRABAJO,"Trabajo cerrado correctamente.", true);
                }
                case SALIR -> terminar();
            }
        } catch (Exception e) {
            System.out.printf("Error: %s%n", e.getMessage());
        }
    }
}