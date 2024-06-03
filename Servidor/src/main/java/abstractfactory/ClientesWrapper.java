package abstractfactory;

import java.util.ArrayList;

import servidor.Cliente;

public class ClientesWrapper {

	 private ArrayList<Cliente> clientes;

	    public ArrayList<Cliente> getClientes() {
	        return clientes;
	    }

	    public void setClientes(ArrayList<Cliente> clientes) {
	        this.clientes = clientes;
	    }

	    @Override
	    public String toString() {
	        return "ClientesWrapper{" +
	                "clientes=" + clientes +
	                '}';
	    }
}
