package org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;

import java.util.ArrayList;
import java.util.List;

public class Clientes implements org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes {
    private List<Cliente> listaClientes = new ArrayList<>();

    @Override
    public List<Cliente> get() {
        return new ArrayList<>(this.listaClientes);
    }

    @Override
    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        if (cliente == null) {
            throw new NullPointerException("No se puede insertar un cliente nulo.");
        }
        if (this.listaClientes.contains(cliente)) {
            throw new TallerMecanicoExcepcion("Error: Ya existe un cliente con ese DNI.");
        }
        this.listaClientes.add(cliente);
    }

    @Override
    public Cliente modificar(Cliente cliente, String nuevoNombre, String nuevoTelefono) throws TallerMecanicoExcepcion {
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
            throw new NullPointerException("No se puede eliminar un cliente nulo.");
        }
        boolean eliminado = this.listaClientes.remove(cliente);
        if (!eliminado) {
            throw new TallerMecanicoExcepcion("Error: No se puede borrar un cliente que no existe en la lista.");
        }
    }


}




