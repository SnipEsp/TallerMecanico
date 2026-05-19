package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;


import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;

public class Trabajos implements ITrabajos {
    private static final String FICHERO_TRABAJOS = String.format("%s%s%s", "datos", File.separator, "trabajos.xml");
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String RAIZ = "trabajos";
    private static final String TRABAJO = "trabajo";
    private static final String CLIENTE = "cliente";
    private static final String VEHICULO = "vehiculo";
    private static final String FECHA_INICIO = "fechaInicio";
    private static final String FECHA_FIN = "fechaFin";
    private static final String HORAS = "horas";
    private static final String PRECIO_MATERIAL = "precioMaterial";
    private static final String TIPO = "tipo";
    private static final String MECANICO = "mecanico";
    private static final String REVISION = "revision";

    private static Trabajos instancia;
    private final List<Trabajo> coleccionTrabajos;

    public Trabajos() {
        coleccionTrabajos = new ArrayList<>();
    }

    public static Trabajos getInstancia() {
        if (instancia == null) {
            instancia = new Trabajos();
        }
        return instancia;
    }

    @Override
    public List<Trabajo> get() {
        List<Trabajo> trabajosOrdenados = new ArrayList<>(coleccionTrabajos);
        trabajosOrdenados.sort(Comparator.comparing(Trabajo::getFechaInicio)
                .thenComparing(trabajo -> trabajo.getCliente().getNombre())
                .thenComparing(trabajo -> trabajo.getCliente().getDni()));
        return trabajosOrdenados;
    }

    @Override
    public List<Trabajo> get(Cliente cliente) {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        List<Trabajo> trabajosCliente = new ArrayList<>();
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getCliente().equals(cliente)) {
                trabajosCliente.add(trabajo);
            }
        }
        return trabajosCliente;
    }

    @Override
    public List<Trabajo> get(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "No se puede buscar un trabajo de un vehículo nulo.");
        List<Trabajo> trabajosVehiculo = new ArrayList<>();
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getVehiculo().equals(vehiculo)) {
                trabajosVehiculo.add(trabajo);
            }
        }
        return trabajosVehiculo;
    }

    @Override
    public Map<TipoTrabajo, Integer> getEstadisticasMensuales(LocalDate mes) {
        Objects.requireNonNull(mes, "El mes no puede ser nulo.");
        Map<TipoTrabajo, Integer> estadisticas = new HashMap<>();

        for (Trabajo trabajo : coleccionTrabajos) {
            LocalDate fecha = trabajo.getFechaInicio();
            if (fecha.getMonthValue() == mes.getMonthValue() && fecha.getYear() == mes.getYear()) {
                TipoTrabajo tipoTrabajo = TipoTrabajo.get(trabajo);
                estadisticas.put(tipoTrabajo, estadisticas.get(tipoTrabajo) + 1);
            }
        }

        return estadisticas;
    }


    private void comprobarTrabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        Objects.requireNonNull(vehiculo, "No se puede buscar un trabajo de un vehículo nulo.");
        Objects.requireNonNull(fechaInicio, "La fecha de inicio no puede ser nula.");
        for (Trabajo trabajo : coleccionTrabajos) {
            if (!trabajo.estaCerrado() && trabajo.getCliente().equals(cliente)) {
                throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo en curso.");
            }
            if (!trabajo.estaCerrado() && trabajo.getVehiculo().equals(vehiculo)) {
                throw new TallerMecanicoExcepcion("El vehículo está actualmente en el taller.");
            }
            if (trabajo.estaCerrado() && !trabajo.getFechaFin().isBefore(fechaInicio) && trabajo.getCliente().equals(cliente)) {
                throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo posterior.");
            }
            if (trabajo.estaCerrado() && !trabajo.getFechaFin().isBefore(fechaInicio) && trabajo.getVehiculo().equals(vehiculo)) {
                throw new TallerMecanicoExcepcion("El vehículo tiene otro trabajo posterior.");
            }
        }
    }

    public Trabajo getTrabajoAbierto(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "No se puede buscar un trabajo de un vehículo nulo.");
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getVehiculo().equals(vehiculo) && !trabajo.estaCerrado()) {
                return trabajo;
            }
        }
        return null;
    }

    @Override
    public void insertar(Trabajo trabajo) {
        if (trabajo == null) {
            throw new NullPointerException("No se puede insertar un trabajo nulo.");
        }
        comprobarTrabajo(trabajo.getCliente(), trabajo.getVehiculo(), trabajo.getFechaInicio());
        coleccionTrabajos.add(trabajo);
    }

    @Override
    public Trabajo anadirHoras(Trabajo trabajo, int horas) {
        if (trabajo == null) {
            throw new NullPointerException("No puedo añadir horas a un trabajo nulo.");
        }
        Trabajo trabajoAbierto = getTrabajoAbierto(trabajo.getVehiculo());
        if (trabajoAbierto == null) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }
        if (!trabajoAbierto.equals(trabajo)) {
            throw new TallerMecanicoExcepcion("El trabajo indicado no es el trabajo abierto del vehículo.");
        }
        trabajoAbierto.anadirHoras(horas);
        return trabajoAbierto;
    }

    @Override
    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        Trabajo trabajoAbierto = getTrabajoAbierto(trabajo.getVehiculo());

        if (trabajoAbierto == null) {
            throw new TallerMecanicoExcepcion("No puedo añadir precio del material a un trabajo nulo.");
        }

        if (trabajoAbierto instanceof Mecanico mecanico) {
            mecanico.anadirPrecioMaterial(precioMaterial);
        } else {
            throw new TallerMecanicoExcepcion("No se puede añadir precio al material para este tipo de trabajos.");
        }

        return trabajoAbierto;
    }

    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) {
        if (trabajo == null) {
            throw new NullPointerException("No puedo cerrar un trabajo nulo.");
        }
        Trabajo trabajoAbierto = getTrabajoAbierto(trabajo.getVehiculo());
        if (trabajoAbierto == null) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }

        if (!trabajoAbierto.equals(trabajo)) {
            throw new TallerMecanicoExcepcion("El trabajo indicado no es el trabajo abierto del vehículo.");
        }
        trabajoAbierto.cerrar(fechaFin);
        return trabajoAbierto;
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        if (trabajo == null) {
            throw new NullPointerException("No se puede buscar un trabajo nulo.");
        }
        int indice = coleccionTrabajos.indexOf(trabajo);
        return (indice == -1) ? null : coleccionTrabajos.get(indice);
    }

    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede borrar un trabajo nulo.");
        if (!coleccionTrabajos.contains(trabajo)) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo igual.");
        }
        coleccionTrabajos.remove(trabajo);
    }

    public void comenzar() throws ParserConfigurationException {
        Document documentoXML = UtilidadesXml.leerDocumentoXml(FICHERO_TRABAJOS);
        if (documentoXML != null) {
            procesarDocumentoXML(documentoXML);
            System.out.printf("Fichero %s leido correctamente.%n", FICHERO_TRABAJOS);
        }
    }

    private void procesarDocumentoXML(Document documentoXML) throws ParserConfigurationException {
        NodeList lista = documentoXML.getElementsByTagName(TRABAJO);

        for (int i = 0; i < lista.getLength(); i++) {
            Node nodo = lista.item(i);

            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                insertar(getTrabajo((Element) nodo));
            }
        }
    }

    private Trabajo getTrabajo(Element elemento) {
        Cliente cliente = Cliente.get(elemento.getAttribute(CLIENTE));
        cliente = Clientes.getInstancia().buscar(cliente);
        Vehiculo vehiculo = Vehiculo.get(elemento.getAttribute(VEHICULO));
        vehiculo = Vehiculos.getInstancia().buscar(vehiculo);
        LocalDate fechaInicio = LocalDate.parse(elemento.getAttribute(FECHA_INICIO));
        String tipo = elemento.getAttribute(TIPO);
        Trabajo trabajo = null;
        if (tipo.equals(MECANICO)) {
            trabajo = new Mecanico(cliente, vehiculo, fechaInicio);
            if (elemento.hasAttribute(PRECIO_MATERIAL)) {
                ((Mecanico) trabajo).anadirPrecioMaterial(Float.parseFloat(elemento.getAttribute(PRECIO_MATERIAL)));
            }

        } else if (tipo.equals(REVISION)) {
            trabajo = new Revision(cliente, vehiculo, fechaInicio);
        }

        if (elemento.hasAttribute(HORAS) && trabajo != null) {
            int horas = Integer.parseInt(elemento.getAttribute(HORAS));
            trabajo.anadirHoras(horas);
        }
        if (elemento.hasAttribute(FECHA_FIN) && trabajo != null) {
            LocalDate fechaFin = LocalDate.parse(elemento.getAttribute(FECHA_FIN), FORMATO_FECHA);
            trabajo.cerrar(fechaFin);
        }

        return trabajo;

    }

    public void terminar() throws ParserConfigurationException {
        Document documentoXML = crearDocumentoXML();
        UtilidadesXml.escribirDocumentoXml(documentoXML, FICHERO_TRABAJOS);

    }

    private Document crearDocumentoXML() throws ParserConfigurationException {
        DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
        Document documentoXML = null;
        if (constructor != null) {
            documentoXML = constructor.newDocument();
            documentoXML.appendChild(documentoXML.createElement(RAIZ));
            for (Trabajo trabajo : coleccionTrabajos) {
                Element elemento = getElemento(documentoXML, trabajo);
                documentoXML.getDocumentElement().appendChild(elemento);
            }
        }

        return documentoXML;
    }

    private Element getElemento(Document documentoXML, Trabajo trabajo) {
        Element elementoTrabajo = documentoXML.createElement(TRABAJO);
        elementoTrabajo.setAttribute(CLIENTE, trabajo.getCliente().getDni());
        elementoTrabajo.setAttribute(VEHICULO, trabajo.getVehiculo().getMatricula());
        elementoTrabajo.setAttribute(FECHA_INICIO, trabajo.getFechaInicio().format(FORMATO_FECHA));
        if (trabajo.getFechaFin() != null) {
            elementoTrabajo.setAttribute(FECHA_FIN, trabajo.getFechaFin().format(FORMATO_FECHA));
        }
        if (trabajo.getHoras() != 0) {
            elementoTrabajo.setAttribute(HORAS, String.format("%d", trabajo.getHoras()));

            if (trabajo instanceof Revision) {
                elementoTrabajo.setAttribute(TIPO, REVISION);
            } else if (trabajo instanceof Mecanico mecanico) {
                elementoTrabajo.setAttribute(TIPO, MECANICO);
                if (mecanico.getPrecioMaterial() != 0) {
                    elementoTrabajo.setAttribute(PRECIO_MATERIAL, String.format(Locale.US, "%f", mecanico.getPrecioMaterial()));
                }

            }
        }
        return elementoTrabajo;
    }

}
