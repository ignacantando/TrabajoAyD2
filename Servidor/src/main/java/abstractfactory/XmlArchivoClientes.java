package abstractfactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import servidor.Cliente;

public class XmlArchivoClientes implements IArchivoClientes {
    private ArrayList<Cliente> clientes;
    private final String archivo = "clientes.xml";

    @Override
    public void guardarClientes(ArrayList<Cliente> clientes) {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            ClientesWrapper clientesWrapper = new ClientesWrapper();
            clientesWrapper.setClientes(clientes);
            xmlMapper.writeValue(new File(archivo), clientesWrapper);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Cliente> leerClientes() {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            ClientesWrapper clientesWrapper = xmlMapper.readValue(new File(archivo), ClientesWrapper.class);
            clientes = clientesWrapper.getClientes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientes;
    }
}