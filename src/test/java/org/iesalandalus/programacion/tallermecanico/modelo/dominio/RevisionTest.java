package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RevisionTest {

    private static Cliente cliente;
    private static Vehiculo vehiculo;
    private static LocalDate hoy;
    private static LocalDate ayer;
    private static LocalDate manana;
    private static LocalDate semanaPasada;

    private Revision revision;

    @BeforeAll
    static void setup() {
        hoy = LocalDate.now();
        ayer = hoy.minusDays(1);
        manana = hoy.plusDays(1);
        semanaPasada = hoy.minusDays(7);
    }

    @BeforeEach
    void init() {
        creaComportamientoCliente();
        creaComportamientoVehiculo();
        revision = new Revision(cliente, vehiculo, ayer);
    }

    private void creaComportamientoVehiculo() {
        vehiculo = mock();
        when(vehiculo.marca()).thenReturn("Seat");
        when(vehiculo.modelo()).thenReturn("León");
        when(vehiculo.matricula()).thenReturn("1234BCD");
    }

    private void creaComportamientoCliente() {
        cliente = mock();
        when(cliente.getNombre()).thenReturn("Bob Esponja");
        when(cliente.getDni()).thenReturn("11223344B");
        when(cliente.getTelefono()).thenReturn("950112233");
    }

    @Test
    void constructorClienteValidoVehiculoValidoFechaInicioValidaCreaRevisionCorrectamente() {
        assertEquals(cliente, revision.getCliente());
        assertSame(cliente, revision.getCliente());
        assertEquals(vehiculo, revision.getVehiculo());
        assertSame(vehiculo, revision.getVehiculo());
        assertEquals(ayer, revision.getFechaInicio());
        assertNull(revision.getFechaFin());
        assertEquals(0, revision.getHoras());
        assertEquals(0, revision.getPrecio());
    }

    @Test
        NullPointerException npe = assertThrows(NullPointerException.class, () -> new Revision(null, vehiculo, hoy));
        assertEquals("El cliente no puede ser nulo.", npe.getMessage());
    }

    @Test
        NullPointerException npe = assertThrows(NullPointerException.class, () -> new Revision(cliente, null, hoy));
        assertEquals("El vehículo no puede ser nulo.", npe.getMessage());
    }

    @Test
        NullPointerException npe = assertThrows(NullPointerException.class, () -> new Revision(cliente, vehiculo, null));
        assertEquals("La fecha de inicio no puede ser nula.", npe.getMessage());
    }

    @Test
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> new Revision(cliente, vehiculo, manana));
        assertEquals("La fecha de inicio no puede ser futura.", iae.getMessage());
    }

    @Test
    void constructorRevisionValidaCopiaRevisionCorrectamente() {
        assertDoesNotThrow(() -> revision.anadirHoras(5));
        assertDoesNotThrow(() -> revision.cerrar(hoy));
        Revision revisionCopia = new Revision(revision);
        assertNotSame(cliente, revisionCopia.getCliente());
        assertSame(vehiculo, revisionCopia.getVehiculo());
        assertEquals(ayer, revisionCopia.getFechaInicio());
        assertEquals(hoy, revisionCopia.getFechaFin());
        assertEquals(5, revisionCopia.getHoras());
    }

    @Test
    void constructorRevisionNulaLanzaExcepcion() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> new Revision(null));
    }

        LocalDate fechaFin = semanaPasada.plusDays(dias);
    }

    @Test
    void toStringDevuelveLaCadenaEsperada() {
        String cadenaCliente = "Bob Esponja - 11223344B (950112233)";
        String caenaVehiculo = "Seat León - 1234BCD";
        when(cliente.toString()).thenReturn(cadenaCliente);
        when(vehiculo.toString()).thenReturn(caenaVehiculo);
        assertEquals(cadena, revision.toString());
        assertDoesNotThrow(() -> revision.cerrar(hoy));
        assertEquals(cadena, revision.toString());
    }

}