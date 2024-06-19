package abstractfactory;

import java.io.Serializable;
import java.util.ArrayList;

import servidor.ClienteLog;

public interface IArchivoLogs extends Serializable{
    void guardarLogs(ArrayList<ClienteLog> logs);
    ArrayList<ClienteLog> leerLogs();
}
