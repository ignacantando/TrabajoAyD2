package persistencia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import org.json.JSONObject;

public class PersistenciaJSON implements IPersistencia{
	private FileOutputStream fileoutput;
    private FileInputStream fileinput;
    private JSONObject jo;
    
	@Override
	public void abrirInput(String nombre) throws IOException {
		this.fileinput = new FileInputStream(nombre);
		this.jo = new JSONObject(fileinput);
	}

	@Override
	public void abrirOutput(String nombre) throws IOException {
		this.fileinput = new FileInputStream(nombre);
		this.jo = new JSONObject(fileinput);
	}

	@Override
	public void cerrarOutput() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void cerrarInput() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void escribir(Object p) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public Serializable leer() throws IOException, ClassNotFoundException {
		Serializable p = null;
        /*if (objectinput != null)
            p = (Serializable) objectinput.readObject();*/
        return p;
	}

}
