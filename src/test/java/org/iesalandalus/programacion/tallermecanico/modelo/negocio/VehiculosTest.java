package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros.Vehiculos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VehiculosTest_final {

    private Vehiculo vehiculo1;
    private Vehiculo vehiculo2;
    private IVehiculos iVehiculos;

    @BeforeEach
    void init() {
        Vehiculos.reset();
        iVehiculos = Vehiculos.getInstancia();
        
        // Create real Vehiculo objects for each test
        vehiculo1 = new Vehiculo("Seat", "León", "1234BCD");
        vehiculo2 = new Vehiculo("Renault", "Clio", "1111BBB");
    }

    @Test
    void constructorCreaVehiculosCorrectamente() {
        assertNotNull(iVehiculos);
        assertEquals(0, iVehiculos.get().size());
    }

    @Test
    void getDevuelveVehiculosCorrectamente() {
        assertDoesNotThrow(() -> iVehiculos.insertar(vehiculo1));
        assertDoesNotThrow(() -> iVehiculos.insertar(vehiculo2));
        List<Vehiculo> copiaVehiculos = iVehiculos.get();
        assertEquals(2, copiaVehiculos.size());
        // Check that both vehicles are present (order may vary due to sorting)
        assertTrue(copiaVehiculos.stream().anyMatch(v -> "1234BCD".equals(v.getMatricula())));
        assertTrue(copiaVehiculos.stream().anyMatch(v -> "1111BBB".equals(v.getMatricula())));
    }

    @Test
    void insertarVehiculoValidoInsertaCorrectamente() {
        assertDoesNotThrow(() -> iVehiculos.insertar(vehiculo1));
        Vehiculo encontrado = iVehiculos.buscar(vehiculo1);
        assertNotNull(encontrado);
        assertEquals("1234BCD", encontrado.getMatricula());
    }

    @Test
    void insertarVehiculoNuloLanzaExcepcion() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> iVehiculos.insertar(null));
        assertEquals("No se puede insertar un vehículo nulo.", npe.getMessage());
    }

    @Test
    void insertarVehiculoRepetidoLanzaExcepcion() {
        assertDoesNotThrow(() -> iVehiculos.insertar(vehiculo1));
        TallerMecanicoExcepcion tme = assertThrows(TallerMecanicoExcepcion.class, () -> iVehiculos.insertar(vehiculo1));
        assertEquals("Ya existe un vehículo con esa matrícula.", tme.getMessage());
    }

    @Test
    void borrarVehiculoExistenteBorraVehiculoCorrectamente() {
        assertDoesNotThrow(() -> iVehiculos.insertar(vehiculo1));
        assertDoesNotThrow(() -> iVehiculos.borrar(vehiculo1));
        assertNull(iVehiculos.buscar(vehiculo1));
    }

    @Test
    void borrarVehiculoNoExistenteLanzaExcepcion() {
        assertDoesNotThrow(() -> iVehiculos.insertar(vehiculo1));
        TallerMecanicoExcepcion tme = assertThrows(TallerMecanicoExcepcion.class, () -> iVehiculos.borrar(vehiculo2));
        assertEquals("No existe ningún vehículo con esa matrícula.", tme.getMessage());
    }

    @Test
    void borrarVehiculoNuloLanzaExcepcion() {
        assertDoesNotThrow(() -> iVehiculos.insertar(vehiculo1));
        NullPointerException npe = assertThrows(NullPointerException.class, () -> iVehiculos.borrar(null));
        assertEquals("No se puede borrar un vehículo nulo.", npe.getMessage());
    }

    @Test
    void busarVehiculoExistenteDevuelveVehiculoCorrectamente() {
        assertDoesNotThrow(() -> iVehiculos.insertar(vehiculo1));
        Vehiculo encontrado = iVehiculos.buscar(vehiculo1);
        assertNotNull(encontrado);
        assertEquals("1234BCD", encontrado.getMatricula());
    }

    @Test
    void busarVehiculoNoExistenteDevuelveVehiculoNulo() {
        assertNull(iVehiculos.buscar(vehiculo1));
    }

    @Test
    void buscarVehiculoNuloLanzaExcepcion() {
        assertDoesNotThrow(() -> iVehiculos.insertar(vehiculo1));
        NullPointerException npe = assertThrows(NullPointerException.class, () -> iVehiculos.buscar(null));
        assertEquals("No se puede buscar un vehículo nulo.", npe.getMessage());
    }
}
