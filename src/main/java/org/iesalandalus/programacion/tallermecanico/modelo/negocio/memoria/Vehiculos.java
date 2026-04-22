package org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria;

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
            throw new TallerMecanicoExcepcion("No se puede insertar un vehículo nulo.");
        }

        if (coleccionVehiculos.contains(vehiculo)) {
            throw new TallerMecanicoExcepcion("El vehículo ya existe en el sistema.");
        }

        this.coleccionVehiculos.add(vehiculo);
    }

    /**
     * Busca un vehículo por la matrícula.
     * Devuelve el vehículo encontrado o null.
     */
    @Override
    public Vehiculo buscar(Vehiculo vehiculo) {
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
        if (!coleccionVehiculos.remove(vehiculo)) {
            throw new TallerMecanicoExcepcion("El vehículo a borrar no existe.");
        }
    }
}
