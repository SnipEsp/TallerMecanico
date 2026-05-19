package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Vehiculos implements IVehiculos {
    private static Vehiculos instancia;
    private static final String FICHERO_VEHICULOS = "datos/vehiculos.xml";
    private List<Vehiculo> coleccionVehiculos;


    private Vehiculos() {
        this.coleccionVehiculos = new ArrayList<>();
    }

    public static Vehiculos getInstancia() {
        if (instancia == null) {
            instancia = new Vehiculos();
        }
        return instancia;
    }
    
    public static void reset() {
        instancia = null;
    }

    /**
     * Devuelve una nueva lista con los mismos elementos.
     * Al ser Vehiculo.java un record, no hay riesgo de modificar los objetos originales.
     */
    @Override
    public List<Vehiculo> get() {
        List<Vehiculo> vehiculosOrdenados = new ArrayList<>(this.coleccionVehiculos);
        vehiculosOrdenados.sort(Comparator.comparing(Vehiculo::getMarca).thenComparing(Vehiculo::getModelo).thenComparing(Vehiculo::getMatricula));
        return vehiculosOrdenados;
    }

    /**
     * Inserta un vehículo si no es nulo y no está repetido.
     */
    @Override
    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        if (vehiculo == null) {
            throw new NullPointerException("No se puede insertar un vehículo nulo.");
        }

        if (coleccionVehiculos.contains(vehiculo)) {
            throw new TallerMecanicoExcepcion("Ya existe un vehículo con esa matrícula.");
        }

        this.coleccionVehiculos.add(vehiculo);
    }

    /**
     * Busca un vehículo por la matrícula.
     * Devuelve el vehículo encontrado o null.
     */
    @Override
    public Vehiculo buscar(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new NullPointerException("No se puede buscar un vehículo nulo.");
        }
        int indice = coleccionVehiculos.indexOf(vehiculo);
        if (indice != -1) {
            return coleccionVehiculos.get(indice);
        }
        return null;
    }

    /**
     * Borra el vehículo si existe, si no lanza excepción.
     */
    @Override
    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        if (vehiculo == null) {
            throw new NullPointerException("No se puede borrar un vehículo nulo.");
        }
        if (!coleccionVehiculos.remove(vehiculo)) {
            throw new TallerMecanicoExcepcion("No existe ningún vehículo con esa matrícula.");
        }
    }

    public void comenzar() {
        File fichero = new File(FICHERO_VEHICULOS);
        if (fichero.exists()) {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(fichero);
                document.getDocumentElement().normalize();
                
                NodeList nodosVehiculo = document.getElementsByTagName("vehiculo");
                for (int i = 0; i < nodosVehiculo.getLength(); i++) {
                    Node nodo = nodosVehiculo.item(i);
                    if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                        Element elemento = (Element) nodo;
                        String marca = elemento.getAttribute("marca");
                        String modelo = elemento.getAttribute("modelo");
                        String matricula = elemento.getAttribute("matricula");
                        Vehiculo vehiculo = new Vehiculo(marca, modelo, matricula);
                        coleccionVehiculos.add(vehiculo);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error al leer el fichero de vehículos: " + e.getMessage());
            }
        }
    }

    public void terminar() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            
            Element raiz = document.createElement("vehiculos");
            document.appendChild(raiz);
            
            for (Vehiculo vehiculo : coleccionVehiculos) {
                Element elementoVehiculo = document.createElement("vehiculo");
                elementoVehiculo.setAttribute("marca", vehiculo.getMarca());
                elementoVehiculo.setAttribute("modelo", vehiculo.getModelo());
                elementoVehiculo.setAttribute("matricula", vehiculo.getMatricula());
                raiz.appendChild(elementoVehiculo);
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(FICHERO_VEHICULOS));
            
            transformer.transform(source, result);
        } catch (Exception e) {
            System.out.println("Error al escribir el fichero de vehículos: " + e.getMessage());
        }
    }
}
