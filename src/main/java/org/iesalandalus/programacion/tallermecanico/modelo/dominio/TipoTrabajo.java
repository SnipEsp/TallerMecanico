package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

public enum TipoTrabajo {
    MECANICO("Mecánico"),
    REVISION("Revisión");

    private String nombre;

    private TipoTrabajo(String nombre) {
        this.nombre = nombre;
    }

    public static TipoTrabajo get(Trabajo trabajo) {
        if (trabajo instanceof Mecanico) {
            return MECANICO;
        } else if (trabajo instanceof Revision) {
            return REVISION;
        } else {
            throw new IllegalArgumentException("Tipo de trabajo no reconocido.");
        }
    }
}
