package abstractfactory;


public class XmlArchivoFactory implements IArchivoFactory {
    @Override
    public IArchivoClientes crearArchivoClientes() {
        return new XmlArchivoClientes();
    }

    @Override
    public IArchivoLogs crearArchivoLogs() {
        return new XmlArchivoLogs();
    }
}