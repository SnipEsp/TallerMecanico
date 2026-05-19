package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros.Clientes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientesTest_final {

    private Cliente cliente1;
    private Cliente cliente2;
    private IClientes clientes;

    @BeforeEach
    void init() {
        Clientes.reset();
        clientes = Clientes.getInstancia();
        
        // Create fresh mocks for each test
        cliente1 = mock();
        when(cliente1.getDni()).thenReturn("11223344B");
        when(cliente1.getNombre()).thenReturn("Cliente 1");
        when(cliente1.getTelefono()).thenReturn("950000001");
        
        cliente2 = mock();
        when(cliente2.getDni()).thenReturn("11111111H");
        when(cliente2.getNombre()).thenReturn("Cliente 2");
        when(cliente2.getTelefono()).thenReturn("950000002");
    }

    @Test
    void constructorCreaClientesCorrectamente() {
        assertNotNull(clientes);
        assertEquals(0, clientes.get().size());
    }

    @Test
    void getDevuelveClientesCorrectamente() {
        assertDoesNotThrow(() -> clientes.insertar(cliente1));
        assertDoesNotThrow(() -> clientes.insertar(cliente2));
        List<Cliente> copiaClientes = clientes.get();
        assertEquals(cliente1, copiaClientes.get(0));
        assertSame(cliente1, copiaClientes.get(0));
        assertEquals(cliente2, copiaClientes.get(1));
        assertSame(cliente2, copiaClientes.get(1));
    }

    @Test
    void insertarClienteValidoInsertaCorrectamente() {
        assertDoesNotThrow(() -> clientes.insertar(cliente1));
        assertEquals(cliente1, clientes.buscar(cliente1));
        assertSame(cliente1, clientes.buscar(cliente1));
    }

    @Test
    void insertarClienteNuloLanzaExcepcion() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> clientes.insertar(null));
        assertEquals("No se puede insertar un cliente nulo.", npe.getMessage());
    }

    @Test
    void insertarClienteRepetidoLanzaExcepcion() {
        assertDoesNotThrow(() -> clientes.insertar(cliente1));
        TallerMecanicoExcepcion tme = assertThrows(TallerMecanicoExcepcion.class, () -> clientes.insertar(cliente1));
        assertEquals("Ya existe un cliente con ese DNI.", tme.getMessage());
    }

    @Test
    void borrarClienteExistenteBorraVehiculoCorrectamente() {
        assertDoesNotThrow(() -> clientes.insertar(cliente1));
        assertDoesNotThrow(() -> clientes.borrar(cliente1));
        assertNull(clientes.buscar(cliente1));
    }

    @Test
    void borrarClienteNoExistenteLanzaExcepcion() {
        assertDoesNotThrow(() -> clientes.insertar(cliente1));
        TallerMecanicoExcepcion tme = assertThrows(TallerMecanicoExcepcion.class, () -> clientes.borrar(cliente2));
        assertEquals("No existe ningún cliente con ese DNI.", tme.getMessage());
    }

    @Test
    void borrarClienteNuloLanzaExcepcion() {
        assertDoesNotThrow(() -> clientes.insertar(cliente1));
        NullPointerException npe = assertThrows(NullPointerException.class, () -> clientes.borrar(null));
        assertEquals("No se puede borrar un cliente nulo.", npe.getMessage());
    }

    @Test
    void busarClienteExistenteDevuelveClienteCorrectamente() {
        assertDoesNotThrow(() -> clientes.insertar(cliente1));
        assertEquals(cliente1, clientes.buscar(cliente1));
        assertSame(cliente1, clientes.buscar(cliente1));
    }

    @Test
    void buscarClienteNuloLanzaExcepcion() {
        assertDoesNotThrow(() -> clientes.insertar(cliente1));
        NullPointerException npe = assertThrows(NullPointerException.class, () -> clientes.buscar(null));
        assertEquals("No se puede buscar un cliente nulo.", npe.getMessage());
    }
}
