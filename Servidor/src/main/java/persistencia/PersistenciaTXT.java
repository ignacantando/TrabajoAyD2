package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.PrintWriter;
import java.io.Serializable;

public class PersistenciaTXT implements IPersistencia {
	private FileReader fileReader;
	private FileWriter fileWriter;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	//private PrintWriter printWriter;
	
	@Override
	public void abrirInput(String nombre) throws IOException {
		this.fileReader = new FileReader(nombre);
	    this.bufferedReader = new BufferedReader(this.fileReader);
	}

	@Override
	public void abrirOutput(String nombre) throws IOException {
		// TODO Auto-generated method stub
		this.fileWriter = new FileWriter(nombre);
		this.bufferedWriter = new BufferedWriter(this.fileWriter);
		//this.printWriter = new PrintWriter(this.fileWriter);
	}
	
	@Override
	public void cerrarInput() throws IOException {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void cerrarOutput() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void escribir(Object p) throws IOException {
		// TODO Auto-generated method stub
		if (this.bufferedWriter != null)
            this.bufferedWriter.write(p.toString());
	}

	@Override
	public Serializable leer() throws IOException, ClassNotFoundException {
		// Lectura del fichero
        String linea;
        while((linea=this.bufferedReader.readLine())!=null)
           System.out.println(linea);
		return linea;
	}

}
