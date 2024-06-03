package abstractfactory;

import java.util.ArrayList;

import servidor.ClienteLog;

public interface IArchivoLogs {
    void guardarLogs(ArrayList<ClienteLog> logs);
    ArrayList<ClienteLog> leerLogs();
}
