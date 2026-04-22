package org.iesalandalus.programacion.tallermecanico.modelo;

import org.iesalandalus.programacion.tallermecanico.modelo.cascada.ModeloCascada;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.FabricaFuenteDatos;

public enum FabricaModelo {
    CASCADA;

    public Modelo crear(FabricaFuenteDatos fuenteDatos) {
        return new ModeloCascada(fuenteDatos.crear());
    }
}
