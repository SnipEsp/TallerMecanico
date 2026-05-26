package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;

import java.time.LocalDate;
import java.util.List;

public class VistaTexto implements org.iesalandalus.programacion.tallermecanico.vista.Vista {
    private GestorEventos gestorEventos = new GestorEventos(Evento.values());

    @Override
    public GestorEventos getGestorEventos() {
        return gestorEventos;
    }

    @Override
    public void comenzar() {
        Evento opcion;
        System.out.println("¡¡¡Bienvenido al taller mecánico de Maricarmen!!!");
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            ejecutar(opcion);
        } while (opcion != Evento.SALIR);
    }

   private void ejecutar(Evento opcion) {
       try {
           gestorEventos.notificar(opcion);
       } catch (Exception e) {
           System.out.printf("Error: %s%n", e.getMessage());
       }
   }

    @Override
    public void terminar(){
        System.out.println("¡¡¡Hasta luego Maricarmen!!!");
    }

    @Override
    public Cliente leerCliente() {
        System.out.println("Introduce los datos del cliente:");
        String nombre = Consola.leerCadena("Nombre: ");
        String dni = Consola.leerCadena("DNI: ");
        String telefono = Consola.leerCadena("Teléfono: ");
        return new Cliente(nombre, dni, telefono);
    }

    @Override
    public Cliente leerClienteDni() {
        String dni = Consola.leerCadena("DNI: ");
        return Cliente.get(dni);
    }

    @Override
    public String leerNuevoNombre() {
        return Consola.leerCadena("Introduce el nuevo nombre del cliente:");
    }

    @Override
    public String leerNuevoTelefono() {
        return Consola.leerCadena("Introduce el nuevo teléfono del cliente:");
    }

    @Override
    public Vehiculo leerVehiculo() {
        String marca = Consola.leerCadena("Marca: ");
        String modelo = Consola.leerCadena("Modelo: ");
        String matricula = Consola.leerCadena("Matrícula: ");
        return new Vehiculo(marca, modelo, matricula);
    }

    @Override
    public Vehiculo leerVehiculoMatricula() {
        return Vehiculo.get(Consola.leerCadena("Matrícula: "));
    }

    @Override
    public Revision leerRevision() {
        Cliente cliente = leerClienteDni();
        Vehiculo vehiculo = leerVehiculoMatricula();
        LocalDate fechaRevision = Consola.leerFecha("Introduce la fecha de la revisión: ");
        return new Revision(cliente, vehiculo, fechaRevision);
    }

    @Override
    public Trabajo leerMecanico() {
        Cliente cliente = leerClienteDni();
        Vehiculo vehiculo = leerVehiculoMatricula();
        LocalDate fechaRevision = Consola.leerFecha("Introduce la fecha de la revisión: ");
        return new Mecanico(cliente, vehiculo, fechaRevision);
    }

    @Override
    public Trabajo leerTrabajoVehiculo() {
        Cliente cliente = leerClienteDni();
        Vehiculo vehiculo = leerVehiculoMatricula();
        LocalDate fechaInicio = Consola.leerFecha("Introduce la fecha de inicio: ");
        return new Revision(cliente, vehiculo, fechaInicio);
    }

    @Override
    public int leerHoras() {
        return Consola.leerEntero("Introduce las horas: ");
    }

    @Override
    public float leerPrecioMaterial() {
        return Consola.leerReal("Introduce el precio del material: ");
    }

    @Override
    public LocalDate leerFechaCierre() {
        return Consola.leerFecha("Introduce la fecha de cierre de la revisión: ");
    }

    @Override
    public void notificarResultado(Evento evento, String texto, boolean exito) {
        if (exito) {
            System.out.printf("%s: %s%n", evento, texto);
        } else {
            System.out.printf("Error en %s: %s%n", evento, texto);
        }
    }

    @Override
    public void mostrarCliente(Cliente cliente) {
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
        } else {
            System.out.println("Cliente encontrado:");
            System.out.println(cliente);
        }
    }

    @Override
    public void mostrarVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            System.out.println("Vehículo no encontrado.");
        } else {
            System.out.println("Vehículo encontrado:");
            System.out.println(vehiculo);
        }
    }

        @Override
        public void mostrarTrabajo(Trabajo trabajo) {
            if (trabajo == null) {
                System.out.println("Trabajo no encontrado.");
            } else {
                System.out.println("Trabajo encontrado:");
                System.out.println(trabajo);
            }
        }

    @Override
    public void mostrarClientes(List<Cliente> clientes) {
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes para mostrar.");
        } else {
            System.out.println("Lista de clientes:");
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }
        }
    }

    @Override
    public void mostrarVehiculos(List<Vehiculo> vehiculos) {
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos para mostrar.");
        } else {
            System.out.println("Lista de vehículos:");
            for (Vehiculo vehiculo : vehiculos) {
                System.out.println(vehiculo);
            }
        }
    }

    @Override
    public void mostrarTrabajos(List<Trabajo> trabajos) {
        if (trabajos.isEmpty()) {
            System.out.println("No hay trabajos para mostrar.");
        } else {
            System.out.println("Lista de trabajos:");
            for (Trabajo trabajo : trabajos) {
                System.out.println(trabajo);
            }
        }
    }
}