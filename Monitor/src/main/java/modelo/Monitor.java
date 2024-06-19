package modelo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Monitor implements Serializable {
	private static final long serialVersionUID = 1131654034383541732L;
	Socket socket;
	private ObjectOutputStream oos;
	private PrintWriter out;
	
	public Monitor() {
	}

	
	private void abrirConexion(int puerto) throws IOException{
	    this.socket=new Socket("localhost",puerto);
	    this.oos=new ObjectOutputStream(socket.getOutputStream());
	    this.out=new PrintWriter(socket.getOutputStream(),true);
	}
	
	public void envio(Object objeto,String mensaje,int puerto) throws IOException{ 
	        	System.out.println(objeto);
	        	this.abrirConexion(puerto);
	            enviarDatos(objeto,mensaje);
	            out.println(mensaje);
	    }
	
    	private void enviarDatos(Object objeto,String mensaje) throws IOException{
    		oos.writeObject(objeto);
    		oos.flush();
    		
    		out.println(mensaje);
    		oos.flush();
    	}
		
	    public void cerrarConexion(){
	        try {
	            socket.close();
	            oos.close();
	            out.close();
	        } catch (IOException ex) {
	        }

	    }
	    
		public Socket getSocket() {
			return socket;
		}

		public void setSocket(Socket socket) {
			this.socket = socket;
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
