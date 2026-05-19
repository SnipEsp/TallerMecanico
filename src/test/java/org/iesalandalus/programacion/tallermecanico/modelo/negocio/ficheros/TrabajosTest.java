package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros.Trabajos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrabajosTest_final {

    private LocalDate hoy;
    private LocalDate ayer;
    private LocalDate anteayer;
    private LocalDate semanaPasada;
    private Cliente cliente1;
    private Cliente cliente2;
    private Vehiculo vehiculo1;
    private Vehiculo vehiculo2;
    private Revision revision;
    private Mecanico mecanico;
    private Trabajo trabajo3;
    private ITrabajos trabajos;

    @BeforeEach
    void init() {
        Trabajos.reset();
        trabajos = Trabajos.getInstancia();
        
        // Initialize dates
        hoy = LocalDate.now();
        ayer = hoy.minusDays(1);
        anteayer = hoy.minusDays(2);
        semanaPasada = hoy.minusDays(7);
        
        // Create fresh mocks for each test
        cliente1 = mock();
        when(cliente1.getDni()).thenReturn("11223344B");
        when(cliente1.getNombre()).thenReturn("Cliente 1");
        when(cliente1.getTelefono()).thenReturn("950000001");
        
        cliente2 = mock();
        when(cliente2.getDni()).thenReturn("11111111H");
        when(cliente2.getNombre()).thenReturn("Cliente 2");
        when(cliente2.getTelefono()).thenReturn("950000002");
        
        vehiculo1 = mock();
        when(vehiculo1.getMatricula()).thenReturn("1234BCD");
        when(vehiculo1.getMarca()).thenReturn("Seat");
        when(vehiculo1.getModelo()).thenReturn("León");
        
        vehiculo2 = mock();
        when(vehiculo2.getMatricula()).thenReturn("1111BBB");
        when(vehiculo2.getMarca()).thenReturn("Renault");
        when(vehiculo2.getModelo()).thenReturn("Clio");
        
        revision = mock();
        when(revision.getCliente()).thenReturn(cliente1);
        when(revision.getVehiculo()).thenReturn(vehiculo1);
        when(revision.getFechaInicio()).thenReturn(semanaPasada);
        when(revision.estaCerrado()).thenReturn(false);
        
        mecanico = mock();
        when(mecanico.getCliente()).thenReturn(cliente1);
        when(mecanico.getVehiculo()).thenReturn(vehiculo2);
        when(mecanico.getFechaInicio()).thenReturn(ayer);
        when(mecanico.getHoras()).thenReturn(5);
        when(mecanico.getPrecioMaterial()).thenReturn(50.0f);
        when(mecanico.estaCerrado()).thenReturn(false);
        
        trabajo3 = mock();
        when(trabajo3.getCliente()).thenReturn(cliente2);
        when(trabajo3.getVehiculo()).thenReturn(vehiculo2);
        when(trabajo3.getFechaInicio()).thenReturn(ayer);
        when(trabajo3.estaCerrado()).thenReturn(false);
    }

    @Test
    void constructorCreaTrabajosCorrectamente() {
        assertNotNull(trabajos);
        assertEquals(0, trabajos.get().size());
    }

    @Test
    void getEstadisticasMensualesMesNuloLanzaExcepcion() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> trabajos.getEstadisticasMensuales(null));
        assertEquals("El mes no puede ser nulo.", npe.getMessage());
    }

    @Test
    void getEstadisticasMensualesMesSinTrabajosDevuelveEstadisticasCorrectamente() {
        Map<TipoTrabajo, Integer> estadisticas = trabajos.getEstadisticasMensuales(LocalDate.of(2024, 1, 1));
        assertEquals(0, estadisticas.get(TipoTrabajo.REVISION));
        assertEquals(0, estadisticas.get(TipoTrabajo.MECANICO));
    }

    @Test
    void getEstadisticasMensualesMesConTrabajosDevuelveEstadisticasCorrectamente() {
        assertDoesNotThrow(() -> trabajos.insertar(revision));
        assertDoesNotThrow(() -> trabajos.insertar(mecanico));
        Map<TipoTrabajo, Integer> estadisticas = trabajos.getEstadisticasMensuales(LocalDate.of(2024, 1, 1));
        assertEquals(1, estadisticas.get(TipoTrabajo.REVISION));
        assertEquals(1, estadisticas.get(TipoTrabajo.MECANICO));
    }

    @Test
    void getDevuelveTrabajosCorrectamente() {
        assertNotNull(trabajos);
        assertEquals(0, trabajos.get().size());
    }

    @Test
    void getVehiculoValidoDevuelveTrabajosVehiculoCorrectamente() {
        assertDoesNotThrow(() -> trabajos.insertar(revision));
        List<Trabajo> trabajosVehiculo = trabajos.get(vehiculo1);
        assertEquals(1, trabajosVehiculo.size());
        assertEquals(revision, trabajosVehiculo.get(0));
    }

    @Test
    void getClienteValidoDevuelveTrabajosClienteCorrectamente() {
        assertDoesNotThrow(() -> trabajos.insertar(revision));
        List<Trabajo> trabajosCliente = trabajos.get(cliente1);
        assertEquals(1, trabajosCliente.size());
        assertEquals(revision, trabajosCliente.get(0));
    }

    @Test
    void insertarTrabajoValidaInsertaCorrectamente() {
        assertDoesNotThrow(() -> trabajos.insertar(revision));
        assertEquals(revision, trabajos.buscar(revision));
        assertSame(revision, trabajos.buscar(revision));
    }

    @Test
    void insertarTrabajoNuloLanzaExcepcion() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> trabajos.insertar(null));
        assertEquals("No se puede insertar un trabajo nulo.", npe.getMessage());
    }

    @Test
    void insertarTrabajoClienteTrabajoAbiertaLanzaExcepcion() {
        assertDoesNotThrow(() -> trabajos.insertar(revision));
        TallerMecanicoExcepcion tme = assertThrows(TallerMecanicoExcepcion.class, () -> trabajos.insertar(mecanico));
        assertEquals("El cliente tiene otro trabajo en curso.", tme.getMessage());
    }

    @Test
    void insertarTrabajoVehiculoTrabajoAbiertaLanzaExcepcion() {
        assertDoesNotThrow(() -> trabajos.insertar(revision));
        TallerMecanicoExcepcion tme = assertThrows(TallerMecanicoExcepcion.class, () -> trabajos.insertar(mecanico));
        assertEquals("El vehículo está actualmente en el taller.", tme.getMessage());
    }

    @Test
    void insertarTrabajoClienteTrabajoAnteiorLanzaExcepcion() {
        assertDoesNotThrow(() -> trabajos.insertar(revision));
        TallerMecanicoExcepcion tme = assertThrows(TallerMecanicoExcepcion.class, () -> trabajos.insertar(trabajo3));
        assertEquals("El cliente tiene otro trabajo posterior.", tme.getMessage());
    }

    @Test
    void anadirHorasTrabajoValidoHorasValidasAnadeHorasCorrectamente() {
        assertDoesNotThrow(() -> trabajos.insertar(mecanico));
        Mecanico mecanicoModificado = (Mecanico) trabajos.anadirHoras(mecanico, 5);
        assertEquals(10, mecanicoModificado.getHoras());
    }

    @Test
    void anadirHorasTrabajoNoExistenteHorasValidasLanzaExcepcion() {
        assertDoesNotThrow(() -> trabajos.insertar(revision));
        TallerMecanicoExcepcion tme = assertThrows(TallerMecanicoExcepcion.class, () -> trabajos.anadirHoras(mecanico, 5));
        assertEquals("No existe ningún trabajo abierto para dicho vehículo.", tme.getMessage());
    }

    @Test
    void anadirHorasTrabajoNuloHorasValidasLanzaExcepcion() {
        assertDoesNotThrow(() -> trabajos.insertar(revision));
        NullPointerException npe = assertThrows(NullPointerException.class, () -> trabajos.anadirHoras(null, 5));
        assertEquals("No se puede añadir horas a un trabajo nulo.", npe.getMessage());
    }

    @Test
    void anadirPrecioMaterialTrabajoNoExistentePrecioMaterialValidoLanzaExcepcion() {
        assertDoesNotThrow(() -> trabajos.insertar(revision));
        TallerMecanicoExcepcion tme = assertThrows(TallerMecanicoExcepcion.class, () -> trabajos.anadirPrecioMaterial(revision, 100.5f));
        assertEquals("No se puede añadir precio del material para este tipo de trabajos.", tme.getMessage());
    }

    @Test
    void anadirPrecioMaterialRevisionValidaPrecioMaterialValidoLanzaExcepcion() {
        assertDoesNotThrow(() -> trabajos.insertar(revision));
        TallerMecanicoExcepcion tme = assertThrows(TallerMecanicoExcepcion.class, () -> trabajos.anadirPrecioMaterial(revision, 100.5f));
        assertEquals("No se puede añadir precio del material para este tipo de trabajos.", tme.getMessage());
    }

    @Test
    void anadirPrecioMaterialMecancioValidoPrecioMaterialValidoAnadaPrecioMaterialCorrectamente() {
        assertDoesNotThrow(() -> trabajos.insertar(mecanico));
        Mecanico mecanicoModificado = (Mecanico) trabajos.anadirPrecioMaterial(mecanico, 100.5f);
        assertEquals(100.5f, mecanicoModificado.getPrecioMaterial());
    }

    @Test
    void cerrarTrabajoValioaFechaValidaCierraCorrectamente() {
        assertDoesNotThrow(() -> trabajos.insertar(revision));
        Revision revisionCerrada = (Revision) trabajos.cerrar(revision, LocalDate.now());
        assertTrue(revisionCerrada.estaCerrado());
        assertEquals(LocalDate.now(), revisionCerrada.getFechaFin());
    }

    @Test
    void cerrarTrabajoNoExistenteFechaValidaLanzaExcepcion() {
        assertDoesNotThrow(() -> trabajos.insertar(revision));
        TallerMecanicoExcepcion tme = assertThrows(TallerMecanicoExcepcion.class, () -> trabajos.cerrar(mecanico, LocalDate.now()));
        assertEquals("No existe ningún trabajo abierto para dicho vehículo.", tme.getMessage());
    }

    @Test
    void cerrarTrabajoNuloFechaValidaLanzaExcepcion() {
        assertDoesNotThrow(() -> trabajos.insertar(revision));
        NullPointerException npe = assertThrows(NullPointerException.class, () -> trabajos.cerrar(null, LocalDate.now()));
        assertEquals("No se puede cerrar un trabajo nulo.", npe.getMessage());
    }

    @Test
    void borrarTrabajoNoExistenteLanzaExcepcion() {
        assertDoesNotThrow(() -> trabajos.insertar(revision));
        TallerMecanicoExcepcion tme = assertThrows(TallerMecanicoExcepcion.class, () -> trabajos.borrar(mecanico));
        assertEquals("No existe ningún trabajo igual.", tme.getMessage());
    }

    @Test
    void borrarTrabajoNuloLanzaExcepcion() {
        assertDoesNotThrow(() -> trabajos.insertar(revision));
        NullPointerException npe = assertThrows(NullPointerException.class, () -> trabajos.borrar(null));
        assertEquals("No se puede borrar un trabajo nulo.", npe.getMessage());
    }

    @Test
    void buscarTrabajoExistenteDevuelveTrabajoCorrectamente() {
        assertDoesNotThrow(() -> trabajos.insertar(revision));
        assertEquals(revision, trabajos.buscar(revision));
        assertSame(revision, trabajos.buscar(revision));
    }

    @Test
    void buscarTrabajoNuloLanzaExcepcion() {
        assertDoesNotThrow(() -> trabajos.insertar(revision));
        NullPointerException npe = assertThrows(NullPointerException.class, () -> trabajos.buscar(null));
        assertEquals("No se puede buscar un trabajo nulo.", npe.getMessage());
    }
}
