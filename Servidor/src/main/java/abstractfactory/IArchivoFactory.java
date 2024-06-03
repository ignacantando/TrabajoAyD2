package abstractfactory;

public interface IArchivoFactory {
    IArchivoClientes crearArchivoClientes();
    IArchivoLogs crearArchivoLogs();
}
