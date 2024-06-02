package Registros;

import com.fasterxml.jackson.databind.ObjectMapper;

import servidor.Cliente;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ClientesJsonFactory implements JsonFactory {
	private ArrayList<Cliente> clientes;

    public ClientesJsonFactory() {
    	clientes=new ArrayList<Cliente>();
    }

    @Override
    public void leerDesdeJson(String archivo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ClientesWrapper clientesWrapper = objectMapper.readValue(new File(archivo), ClientesWrapper.class);
            clientes = clientesWrapper.getClientes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void guardarEnJson(String archivo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ClientesWrapper clientesWrapper = new ClientesWrapper();
            clientesWrapper.setClientes(clientes);
            objectMapper.writeValue(new File(archivo), clientesWrapper);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public ArrayList<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(ArrayList<Cliente> clientes) {
		this.clientes = clientes;
	}
    
    

}

