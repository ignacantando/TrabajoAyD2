package abstractfactory;

import java.io.Serializable;
import java.util.ArrayList;

import servidor.Cliente;

public interface IArchivoClientes extends Serializable{
    void guardarClientes(ArrayList<Cliente> clientes);
    ArrayList<Cliente> leerClientes();
}
