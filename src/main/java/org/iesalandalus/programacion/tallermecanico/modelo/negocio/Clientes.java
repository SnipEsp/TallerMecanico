package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;

import java.util.ArrayList;

public class Clientes {
    public ArrayList<Cliente> listaClientes = new ArrayList<>();

    public ArrayList get() {
        return new ArrayList<>(this.listaClientes);
    }

    public void insertar(Cliente cliente) {
        if (cliente != null && !this.listaClientes.contains(cliente)) {
            this.listaClientes.add(cliente);
        }
    }

    public Cliente modificar(Cliente cliente, String nuevoNombre, String nuevoTelefono) throws Exception {
        int indice = this.listaClientes.indexOf(cliente);
        if (indice == -1) {
            throw new TallerMecanicoExcepcion("Error: Cliente no encontrado.");
        }

        Cliente encontrado = this.listaClientes.get(indice);

        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            encontrado.setNombre(nuevoNombre);
        }
        if (nuevoTelefono != null && !nuevoTelefono.trim().isEmpty()) {
            encontrado.setTelefono(nuevoTelefono);
        }

        return encontrado;
    }

    public Cliente buscar(Cliente cliente) {
        int indice = this.listaClientes.indexOf(cliente);
        if (cliente == null) {
            throw new NullPointerException("No se puede buscar un cliente nulo.");
        }
        if (indice >= 0) {
            return this.listaClientes.get(indice);
        }
        return null;
    }

    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        boolean eliminado = this.listaClientes.remove(cliente);
        if (cliente == null) {
            throw new NullPointerException("No se puede eliminar un cliente nulo.");
        }
        if (!eliminado) {
            throw new TallerMecanicoExcepcion("Error: No se puede borrar un cliente que no existe en la lista.");
        }
    }


}




