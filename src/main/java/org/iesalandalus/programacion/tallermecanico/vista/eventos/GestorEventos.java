package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gestor de eventos que implementa el patrón Observer.
 * Mantiene un mapa de eventos y sus subscriptores (receptores de eventos).
 * Permite suscribir y desuscribir receptores a eventos específicos,
 * y notificar a todos los subscriptores cuando ocurre un evento.
 */
public class GestorEventos {
    
    /**
     * Mapa que asocia cada evento con su lista de subscriptores.
     */
    private final Map<Evento, List<ReceptorEventos>> subscriptores;
    
    /**
     * Constructor que inicializa el mapa de subscriptores.
     */
    public GestorEventos() {
        this.subscriptores = new HashMap<>();
        // Inicializar listas para todos los eventos
        for (Evento evento : Evento.values()) {
            subscriptores.put(evento, new ArrayList<>());
        }
    }
    
    /**
     * Suscribe un receptor de eventos a un evento específico.
     * 
     * @param evento Evento al que suscribirse
     * @param receptor Receptor de eventos a suscribir
     */
    public void suscribir(Evento evento, ReceptorEventos receptor) {
        if (evento == null) {
            throw new NullPointerException("El evento no puede ser nulo.");
        }
        if (receptor == null) {
            throw new NullPointerException("El receptor no puede ser nulo.");
        }
        List<ReceptorEventos> lista = subscriptores.get(evento);
        if (!lista.contains(receptor)) {
            lista.add(receptor);
        }
    }
    
    /**
     * Desuscribe un receptor de eventos de un evento específico.
     * 
     * @param evento Evento del que desuscribirse
     * @param receptor Receptor de eventos a desuscribir
     */
    public void desuscribir(Evento evento, ReceptorEventos receptor) {
        if (evento == null) {
            throw new NullPointerException("El evento no puede ser nulo.");
        }
        if (receptor == null) {
            throw new NullPointerException("El receptor no puede ser nulo.");
        }
        List<ReceptorEventos> lista = subscriptores.get(evento);
        lista.remove(receptor);
    }
    
    /**
     * Notifica a todos los subscriptores de un evento específico.
     * 
     * @param evento Evento a notificar
     */
    public void notificar(Evento evento) {
        if (evento == null) {
            throw new NullPointerException("El evento no puede ser nulo.");
        }
        List<ReceptorEventos> lista = subscriptores.get(evento);
        for (ReceptorEventos receptor : lista) {
            receptor.actualizar(evento);
        }
    }
}
