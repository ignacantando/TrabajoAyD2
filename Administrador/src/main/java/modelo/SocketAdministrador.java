package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketAdministrador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 649391259069756428L;

	public SocketAdministrador() {
		
	}
	Socket socket;
	BufferedReader entrada;
	BufferedWriter salida;
	private ObjectOutputStream oos;
	private PrintWriter out;
	
	private void abrirConexion() throws IOException{
	    this.socket=new Socket("localhost",5555);
	    this.salida=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	    this.entrada=new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    this.oos=new ObjectOutputStream(socket.getOutputStream());
	    this.out=new PrintWriter(socket.getOutputStream(),true);
	}

	    public void envio(Object objeto,String mensaje){
	        try{
	        	this.abrirConexion();
	            enviarDatos(objeto,mensaje);
	        }catch(Exception e){   
	        }
	    }
	    private void enviarDatos(Object objeto, String mensaje) throws IOException {
	    	this.oos.writeObject(objeto);
            this.oos.flush();
            this.salida.write("Administrador" + "\n");
            this.salida.flush(); 
            this.out.println(this.salida);
            this.out.flush();
		}
	    
	    public Administrador recepcion(Object objeto,String mensaje){
	        Administrador adm = new Administrador();
	        try{
	            out.println(mensaje);
	            adm.setPersonasAtendidas(Integer.parseInt(this.entrada.readLine()));
	            adm.setTiempo(this.entrada.readLine());
	            adm.setTiempoPromedio(this.entrada.readLine());
	        }catch(Exception e){
	            
	        }finally{
	        	cerrarConexion();
	        }
			return adm;
	    }
	    private void cerrarConexion(){
	        try {
	            socket.close();
	            oos.close();
	            out.close();
	        } catch (IOException ex) {
	            Logger.getLogger(Administrador.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }

		public Socket getSocket() {
			return socket;
		}

		public void setSocket(Socket socket) {
			this.socket = socket;
		}

		public BufferedReader getEntrada() {
			return entrada;
		}

		public void setEntrada(BufferedReader entrada) {
			this.entrada = entrada;
		}

		public BufferedWriter getSalida() {
			return salida;
		}

		public void setSalida(BufferedWriter salida) {
			this.salida = salida;
		}

		public ObjectOutputStream getOos() {
			return oos;
		}

		public void setOos(ObjectOutputStream oos) {
			this.oos = oos;
		}

		public PrintWriter getOut() {
			return out;
		}

		public void setOut(PrintWriter out) {
			this.out = out;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

}
