package abstractfactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import servidor.ClienteLog;

public class XmlArchivoLogs implements IArchivoLogs {
    private ArrayList<ClienteLog> clientesLog;
    private final String archivo = "clientes_log.xml";

    @Override
    public void guardarLogs(ArrayList<ClienteLog> logs) {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        try {
            xmlMapper.writeValue(new File(archivo), logs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<ClienteLog> leerLogs() {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            clientesLog = xmlMapper.readValue(new File(archivo), ArrayList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientesLog;
    }
}
