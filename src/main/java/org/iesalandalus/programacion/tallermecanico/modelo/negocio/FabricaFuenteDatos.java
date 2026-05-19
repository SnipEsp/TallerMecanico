package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros.FICHEROS;

public enum FabricaFuenteDatos {
    FICHEROS;

    public IFuenteDatos crear() {
        return new FICHEROS();
    }

}
