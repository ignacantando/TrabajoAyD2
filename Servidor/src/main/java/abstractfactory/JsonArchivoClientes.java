package abstractfactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import servidor.Cliente;
public class JsonArchivoClientes implements IArchivoClientes {
	private ArrayList<Cliente> clientes;
	private final String archivo = "clientes.json";
	
	@Override
    public void guardarClientes(ArrayList<Cliente> clientes) {
		 ObjectMapper objectMapper = new ObjectMapper();
	        try {
	            ClientesWrapper clientesWrapper = new ClientesWrapper();
	            clientesWrapper.setClientes(clientes);
	            objectMapper.writeValue(new File(archivo), clientesWrapper);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
    }

	@Override
	public ArrayList<Cliente> leerClientes() {
		  ObjectMapper objectMapper = new ObjectMapper();
	        try {
	            ClientesWrapper clientesWrapper = objectMapper.readValue(new File(archivo), ClientesWrapper.class);
	            clientes = clientesWrapper.getClientes();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return clientes;
	}  
}
