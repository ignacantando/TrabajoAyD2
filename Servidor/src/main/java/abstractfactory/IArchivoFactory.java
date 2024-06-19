package abstractfactory;

import java.io.Serializable;

public interface IArchivoFactory extends Serializable{
    IArchivoClientes crearArchivoClientes();
    IArchivoLogs crearArchivoLogs();
}
