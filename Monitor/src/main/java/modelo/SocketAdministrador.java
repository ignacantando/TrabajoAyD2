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
	private ObjectInputStream ois;
	private PrintWriter out;
	
	private void abrirConexion() throws IOException{
	    this.socket=new Socket("localhost",1234);
	    this.salida=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	    this.entrada=new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    this.oos=new ObjectOutputStream(socket.getOutputStream());
	    this.ois=new ObjectInputStream(socket.getInputStream());
	    this.out=new PrintWriter(socket.getOutputStream(),true);
	}

	    public void envio(Object objeto,String mensaje){
	        try{
	        	System.out.println(objeto);
	        	this.abrirConexion();
	        	System.out.println("Conexion establecida");
	            System.out.println("Enviando datos");
	            enviarDatos(objeto,mensaje);
	        }catch(Exception e){
	            
	        }
	    }
	    private void enviarDatos(Object objeto, String mensaje) throws IOException {
	    	this.oos.writeObject(objeto);
            this.oos.flush();
            this.salida.write("Administrador" + "\n"); // Agrega un salto de línea al final del mensaje
            this.salida.flush(); // Asegúrate de que el mensaje se envíe inmediatamente
            System.out.println(this.salida);
            this.out.println(this.salida);
            this.out.flush();
		}
	    
	    public Monitor recepcion(Object objeto,String mensaje){
	        Monitor adm = null;
	        try{
	            Object objetoARecibir;
	        	System.out.println("esperando respuesta");
	            out.println(mensaje);
	            System.out.println("Respuesta recibida");
	            objetoARecibir = ois.readObject();
	            System.out.println("llega2");
	            System.out.println("datos recibidos");
	            if(objetoARecibir instanceof Monitor){
	                adm = (Monitor) objetoARecibir;
	                System.out.println(adm);
	            }
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
	            ois.close();
	        } catch (IOException ex) {
	            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
	        }

	    }
		/*public ArrayList<String> getDnis() {
			return dnis;
		}

		public void setDnis(ArrayList<String> dnis) {
			this.dnis = dnis;
		}*/

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

		public ObjectInputStream getOis() {
			return ois;
		}

		public void setOis(ObjectInputStream ois) {
			this.ois = ois;
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
