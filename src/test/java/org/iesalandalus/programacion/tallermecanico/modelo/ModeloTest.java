package org.iesalandalus.programacion.tallermecanico.modelo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ModeloTest {

    @Mock
    @Mock
    @Mock
    @InjectMocks

    private static Cliente cliente;
    private static Vehiculo vehiculo;
    private static Revision revision;

    private AutoCloseable procesadorAnotaciones;
    private MockedConstruction<Cliente> controladorCreacionMockCliente;
    private MockedConstruction<Clientes> controladorCreacionMockClientes;
    private MockedConstruction<Vehiculos> controladorCreacionMockVehiculos;
    private MockedConstruction<Revision> controladorCreacionMockRevision;


    @BeforeAll
        cliente = mock();
        when(cliente.getNombre()).thenReturn("Bob Esponja");
        when(cliente.getDni()).thenReturn("11223344B");
        when(cliente.getTelefono()).thenReturn("950112233");
        vehiculo = mock();
        when(vehiculo.marca()).thenReturn("Seat");
        when(vehiculo.modelo()).thenReturn("León");
        when(vehiculo.matricula()).thenReturn("1234BCD");
        revision = mock();
        when(revision.getCliente()).thenReturn(cliente);
        when(revision.getVehiculo()).thenReturn(vehiculo);
        when(revision.getFechaInicio()).thenReturn(LocalDate.now().minusDays(1));
    }

    @BeforeEach
    void init() {
        controladorCreacionMockCliente = mockConstruction(Cliente.class);
        controladorCreacionMockClientes = mockConstruction(Clientes.class);
        controladorCreacionMockVehiculos = mockConstruction(Vehiculos.class);
        controladorCreacionMockRevision = mockConstruction(Revision.class);
        procesadorAnotaciones = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void close() throws Exception {
        procesadorAnotaciones.close();
        controladorCreacionMockCliente.close();
        controladorCreacionMockClientes.close();
        controladorCreacionMockVehiculos.close();
        controladorCreacionMockRevision.close();
    }

    @Test
    void terminarNoHaceNada() {
        assertDoesNotThrow(() -> modelo.terminar());
    }

    @Test
    void insertarClienteLlamaClientesInsertar() {
        assertDoesNotThrow(() -> modelo.insertar(cliente));
        assertDoesNotThrow(() -> verify(clientes).insertar(any(Cliente.class)));
        assertDoesNotThrow(() -> verify(clientes, times(0)).insertar(cliente));
    }

    @Test
    void insertarVehiculoLlamaVehiculosInsertar() {
        assertDoesNotThrow(() -> modelo.insertar(vehiculo));
        assertDoesNotThrow(() -> verify(vehiculos).insertar(vehiculo));
    }

    @Test
        when(clientes.buscar(cliente)).thenReturn(cliente);
        when(vehiculos.buscar(vehiculo)).thenReturn(vehiculo);
        assertDoesNotThrow(() -> modelo.insertar(revision));
        orden.verify(clientes).buscar(cliente);
        orden.verify(vehiculos).buscar(vehiculo);
    }

    @Test
    void buscarClienteLlamaClientesBuscar() {
        assertDoesNotThrow(() -> modelo.insertar(cliente));
        when(clientes.buscar(cliente)).thenReturn(cliente);
        Cliente clienteEncontrado = modelo.buscar(cliente);
        verify(clientes).buscar(cliente);
        assertNotSame(cliente, clienteEncontrado);
    }

    @Test
    void buscarVehiculoLlamaVehiculosBuscar() {
        assertDoesNotThrow(() -> modelo.insertar(vehiculo));
        when(vehiculos.buscar(vehiculo)).thenReturn(vehiculo);
        modelo.buscar(vehiculo);
        verify(vehiculos).buscar(vehiculo);
    }

    @Test
        assertDoesNotThrow(() -> modelo.insertar(revision));
    }

    @Test
    void modificarClienteLlamaClientesModificar() {
        assertDoesNotThrow(() -> modelo.modificar(cliente, "Patricio Estrella", "950123456"));
        assertDoesNotThrow(() -> verify(clientes).modificar(cliente, "Patricio Estrella", "950123456"));
    }

    @Test
        assertDoesNotThrow(() -> modelo.anadirHoras(revision, 10));
    }

    @Test
        assertDoesNotThrow(() -> modelo.anadirPrecioMaterial(revision, 100f));
    }

    @Test
        assertDoesNotThrow(() -> modelo.cerrar(revision, LocalDate.now()));
    }

    @Test
        assertDoesNotThrow(() -> modelo.borrar(cliente));
        }
        assertDoesNotThrow(() -> orden.verify(clientes).borrar(cliente));
    }

    }

    @Test
        assertDoesNotThrow(() -> modelo.borrar(vehiculo));
        }
        assertDoesNotThrow(() -> orden.verify(vehiculos).borrar(vehiculo));
    }

    }

    @Test
        assertDoesNotThrow(() -> modelo.borrar(revision));
    }

    @Test
    void getClientesLlamaClientesGet() {
        when(clientes.get()).thenReturn(new ArrayList<>(List.of(cliente)));
        List<Cliente> clientesExistentes = modelo.getClientes();
        verify(clientes).get();
        assertNotSame(cliente, clientesExistentes.get(0));
    }

    @Test
    void getVehiculosLlamaVehiculosGet() {
        when(vehiculos.get()).thenReturn(new ArrayList<>(List.of(vehiculo)));
        List<Vehiculo> vehiculosExistentes = modelo.getVehiculos();
        verify(vehiculos).get();
        assertSame(vehiculo, vehiculosExistentes.get(0));
    }

    @Test
    }

    @Test
    }

    @Test
    }

}