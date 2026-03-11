package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Revision {
    private static float PRECIO_HORA;
    private static float PRECIO_DIA;
    private static float PRECIO_MATERIAL;
    protected static DateTimeFormatter FORMATO_FECHA;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int horas;
    private float precioMaterial;

    public Revision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {

    }
}
