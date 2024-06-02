package Registros;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import servidor.Cliente;
import servidor.ClienteLog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ClientesLogJsonFactory implements JsonFactory {
    private ArrayList<ClienteLog> clientesLog;
    
    public ClientesLogJsonFactory() {
        clientesLog = new ArrayList<>();
    }

    @Override
    public void leerDesdeJson(String archivo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ClientesLogWrapper logWrapper = objectMapper.readValue(new File(archivo), ClientesLogWrapper.class);
            clientesLog = logWrapper.getClientesLog();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

 
    @Override
    public void guardarEnJson(String archivo) {
        ObjectMapper objectMapper = new ObjectMapper();
  
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        try {
            ClientesLogWrapper logWrapper = new ClientesLogWrapper();
            logWrapper.setClientesLog(clientesLog);
            objectMapper.writeValue(new File(archivo), logWrapper);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ClienteLog> getClientesLog() {
        return clientesLog;
    }

	public void setClientesLog(ArrayList<ClienteLog> clientesLog) {
		this.clientesLog = clientesLog;
	}   
}
