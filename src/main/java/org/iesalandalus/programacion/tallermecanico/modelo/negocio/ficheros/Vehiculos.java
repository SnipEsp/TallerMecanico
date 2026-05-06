package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import java.util.ArrayList;
import java.util.List;

public class Vehiculos implements org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos {
    private List<Vehiculo> coleccionVehiculos;

    public Vehiculos() {
        this.coleccionVehiculos = new ArrayList<>();
    }

    /**
     * Devuelve una nueva lista con los mismos elementos.
     * Al ser Vehiculo.java un record, no hay riesgo de modificar los objetos originales.
     */
    @Override
    public List<Vehiculo> get() {
        return new ArrayList<>(this.coleccionVehiculos);
    }

    /**
     * Inserta un vehículo si no es nulo y no está repetido.
     */
    @Override
    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        if (vehiculo == null) {
            throw new NullPointerException("No se puede insertar un vehículo nulo.");
        }

        if (coleccionVehiculos.contains(vehiculo)) {
            throw new TallerMecanicoExcepcion("Ya existe un vehículo con esa matrícula.");
        }

        this.coleccionVehiculos.add(vehiculo);
    }

    /**
     * Busca un vehículo por la matrícula.
     * Devuelve el vehículo encontrado o null.
     */
    @Override
    public Vehiculo buscar(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new NullPointerException("No se puede buscar un vehículo nulo.");
        }
        int indice = coleccionVehiculos.indexOf(vehiculo);
        if (indice != -1) {
            return coleccionVehiculos.get(indice);
        }
        return null;
    }

    /**
     * Borra el vehículo si existe, si no lanza excepción.
     */
    @Override
    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        if (vehiculo == null) {
            throw new NullPointerException("No se puede borrar un vehículo nulo.");
        }
        if (!coleccionVehiculos.remove(vehiculo)) {
            throw new TallerMecanicoExcepcion("No existe ningún vehículo con esa matrícula.");
        }
    }
}
