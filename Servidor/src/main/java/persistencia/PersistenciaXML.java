package persistencia;



import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class PersistenciaXML implements IPersistencia
{
    private XMLEncoder xe;
    private XMLDecoder xd;
    private FileOutputStream fileoutput;
    private FileInputStream fileinput;


    @Override
    public void abrirInput(String nombre) throws IOException
    {
        fileinput = new FileInputStream(nombre);
        xd = new XMLDecoder(fileinput);

    }

    @Override
    public void abrirOutput(String nombre) throws IOException
    {
        fileoutput = new FileOutputStream(nombre);
        xe = new XMLEncoder(fileoutput);


    }

    @Override
    public void cerrarOutput() throws IOException
    {
        this.xe.close();
    }

    @Override
    public void cerrarInput() throws IOException
    {
        this.xd.close();
    }

    @Override
    public void escribir(Object p) throws IOException
    {
        xe.writeObject(p);

    }

    @Override
    public Serializable leer() throws IOException, ClassNotFoundException
    {
        Serializable p = null;
        if (xd != null)
            p = (Serializable) xd.readObject();

        return p;
    }
}
