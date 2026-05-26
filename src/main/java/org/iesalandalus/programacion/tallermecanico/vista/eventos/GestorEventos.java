package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class GestorEventos {
    private Map<Evento, List<ReceptorEventos>> receptores = new EnumMap<>(Evento.class);

    public GestorEventos(Evento... eventos) {
        for (Evento evento : eventos) {
            receptores.put(evento, new ArrayList<>());
        }
    }

    public void suscribir(ReceptorEventos receptorEventos, Evento... evento) {
        for (Evento eventos : evento) {
            List<ReceptorEventos> receptoresEvento = receptores.get(eventos);
            if (receptoresEvento != null) {
                receptoresEvento.add(receptorEventos);
            }
        }
    }

    public void desuscribir(ReceptorEventos receptorEventos, Evento... evento) {
        for (Evento eventos : evento) {
            List<ReceptorEventos> receptoresEvento = receptores.get(eventos);
            if (receptoresEvento != null) {
                receptoresEvento.remove(receptorEventos);
            }
        }
    }

    public void notificar(Evento evento) {
        List<ReceptorEventos> receptoresEvento = receptores.get(evento);
        if (receptoresEvento != null) {
            for (ReceptorEventos receptor : receptoresEvento) {
                receptor.actualizar(evento);
            }
        }
    }

}
