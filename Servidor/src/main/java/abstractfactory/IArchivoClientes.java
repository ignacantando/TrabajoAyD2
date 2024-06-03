package abstractfactory;

import java.util.ArrayList;

import servidor.Cliente;

public interface IArchivoClientes {
    void guardarClientes(ArrayList<Cliente> clientes);
    ArrayList<Cliente> leerClientes();
}
