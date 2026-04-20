package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VehiculosTest {

    private static Vehiculo vehiculo1;
    private static Vehiculo vehiculo2;

    @BeforeAll
        vehiculo1 = mock();
        when(vehiculo1.matricula()).thenReturn("1234BCD");
        vehiculo2 = mock();
        when(vehiculo2.matricula()).thenReturn("1111BBB");
    }

    @BeforeEach
    void init() {
    }

    @Test
    void constructorCreaVehiculosCorrectamente() {
    }

    @Test
    void getDevuelveVehiculosCorrectamente() {
        assertEquals(vehiculo1, copiaVehiculos.get(0));
        assertSame(vehiculo1, copiaVehiculos.get(0));
        assertEquals(vehiculo2, copiaVehiculos.get(1));
        assertSame(vehiculo2, copiaVehiculos.get(1));
    }

    @Test
    void insertarVehiculoValidoInsertaCorrectamente() {
    }

    @Test
    void insertarVehiculoNuloLanzaExcepcion() {
        assertEquals("No se puede insertar un vehículo nulo.", npe.getMessage());
    }

    @Test
    void insertarVehiculoRepetidoLanzaExcepcion() {
        assertEquals("Ya existe un vehículo con esa matrícula.", tme.getMessage());
    }

    @Test
    void borrarVehiculoExistenteBorraVehiculoCorrectamente() {
    }

    @Test
    void borrarVehiculoNoExistenteLanzaExcepcion() {
        assertEquals("No existe ningún vehículo con esa matrícula.", tme.getMessage());
    }

    @Test
    void borrarVehiculoNuloLanzaExcepcion() {
        assertEquals("No se puede borrar un vehículo nulo.", npe.getMessage());
    }

    @Test
    void busarVehiculoExistenteDevuelveVehiculoCorrectamente() {
    }

    @Test
    void busarVehiculoNoExistenteDevuelveVehiculoNulo() {
    }

    @Test
    void buscarVehiculoNuloLanzaExcepcion() {
        assertEquals("No se puede buscar un vehículo nulo.", npe.getMessage());
    }
}