package abstractfactory;

public class TxtArchivoFactory implements IArchivoFactory {
    @Override
    public IArchivoClientes crearArchivoClientes() {
        return new TxtArchivoClientes();
    }

    @Override
    public IArchivoLogs crearArchivoLogs() {
        return new TxtArchivoLogs();
    }
}
