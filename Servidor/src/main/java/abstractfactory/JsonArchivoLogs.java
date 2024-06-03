package abstractfactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import servidor.ClienteLog;

public class JsonArchivoLogs implements IArchivoLogs {
	private ArrayList<ClienteLog> clientesLog;
	private final String archivo = "clientes_log.json";
	
	@Override
    public void guardarLogs(ArrayList<ClienteLog> logs) {
		 ObjectMapper objectMapper = new ObjectMapper();
		  
	        objectMapper.registerModule(new JavaTimeModule());
	        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	        try {
	            ClientesLogWrapper logWrapper = new ClientesLogWrapper();
	            logWrapper.setClientesLog(logs);
	            objectMapper.writeValue(new File(archivo), logWrapper);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
    }

    
	@Override
	public ArrayList<ClienteLog> leerLogs() {
		ObjectMapper objectMapper = new ObjectMapper();
        try {
            ClientesLogWrapper logWrapper = objectMapper.readValue(new File(archivo), ClientesLogWrapper.class);
            clientesLog = logWrapper.getClientesLog();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientesLog;
	}
    
    
}
