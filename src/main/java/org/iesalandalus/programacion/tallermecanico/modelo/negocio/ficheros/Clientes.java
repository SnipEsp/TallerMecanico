package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Clientes implements IClientes {
    // Instancia única y privada
    private static Clientes instancia;

    // Nombre del fichero XML
    private static final String FICHERO_CLIENTES = "datos/clientes.xml";

    // Colección de clientes
    private List<Cliente> listaClientes;

    // Constructor privado para evitar instanciación externa
    private Clientes() {
        this.listaClientes = new ArrayList<>();
    }

    // Método público estático para obtener la instancia única
    public static Clientes getInstancia() {
        if (instancia == null) {
            instancia = new Clientes();
        }
        return instancia;
    }

    public static void reset() {
        instancia = null;
    }

    @Override
    public List<Cliente> get() {
        List<Cliente> clientesOrdenados = new ArrayList<>(this.listaClientes);
        clientesOrdenados.sort(Comparator.comparing(Cliente::getNombre).thenComparing(Cliente::getDni));
        return clientesOrdenados;
    }

    @Override
    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        if (cliente == null) {
            throw new NullPointerException("No se puede insertar un cliente nulo.");
        }
        if (this.listaClientes.contains(cliente)) {
            throw new TallerMecanicoExcepcion("Ya existe un cliente con ese DNI.");
        }
        this.listaClientes.add(cliente);
    }

    @Override
    public Cliente modificar(Cliente cliente, String nuevoNombre, String nuevoTelefono) throws TallerMecanicoExcepcion {
        if (cliente == null) {
            throw new NullPointerException("No se puede modificar un cliente nulo.");
        }
        int indice = this.listaClientes.indexOf(cliente);
        if (indice == -1) {
            throw new TallerMecanicoExcepcion("No existe ningún cliente con ese DNI.");
        }

        Cliente encontrado = this.listaClientes.get(indice);

        if (nuevoNombre != null) {
            encontrado.setNombre(nuevoNombre);
        }
        if (nuevoTelefono != null) {
            encontrado.setTelefono(nuevoTelefono);
        }

        return encontrado;
    }

    @Override
    public Cliente buscar(Cliente cliente) {
        if (cliente == null) {
            throw new NullPointerException("No se puede buscar un cliente nulo.");
        }
        int indice = this.listaClientes.indexOf(cliente);
        if (indice >= 0) {
            return this.listaClientes.get(indice);
        }
        return null;
    }

    @Override
    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        if (cliente == null) {
            throw new NullPointerException("No se puede borrar un cliente nulo.");
        }
        boolean eliminado = this.listaClientes.remove(cliente);
        if (!eliminado) {
            throw new TallerMecanicoExcepcion("No existe ningún cliente con ese DNI.");
        }
    }

    private Cliente getCliente(Element elemento) {
        String dni = elemento.getAttribute("dni");
        String nombre = elemento.getAttribute("nombre");
        String telefono = elemento.getAttribute("telefono");
        return new Cliente(nombre, dni, telefono);
    }

    public void comenzar() {
        Document documentoXML = UtilidadesXml.leerDocumentoXml(FICHERO_CLIENTES);
        if (documentoXML != null) {
            procesarDocumentoXML(documentoXML);
            System.out.printf("Fichero %s leido correctamente.%n", FICHERO_CLIENTES);
        }
    }

    procesarDocumentoXML(Document documentoXML) {
        NodeList lista = documentoXML.getElementsByTagName(TRABAJO);

        for (int i = 0; i < lista.getLength(); i++) {
            insertar(getTrabajo((Element) nodo));
        }
    }

    public void terminar() throws ParserConfigurationException {
        Document documentoXML = crearDocumentoXML();
        UtilidadesXml.escribirDocumentoXml(documentoXML, FICHERO_CLIENTES);

    }

}




