package abstractfactory;

public class JsonArchivoFactory implements IArchivoFactory {
    @Override
    public IArchivoClientes crearArchivoClientes() {
        return new JsonArchivoClientes();
    }

    @Override
    public IArchivoLogs crearArchivoLogs() {
        return new JsonArchivoLogs();
    }
}