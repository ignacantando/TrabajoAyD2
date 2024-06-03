package modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketMonitor implements Serializable{

	private static final long serialVersionUID = -4386935053983629234L;
	public Socket socket;
	public ObjectOutputStream oos;
	public BufferedReader in;
	public PrintWriter out;
	public ObjectInputStream ois;
	
	public void recibo_mando() {
		new Thread() {
	        public void run() {
	        	try (Socket socket = new Socket("localhost", 5555)) {
	        		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        		PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

	        		// Leer mensaje inicial del servidor
	        		String serverMessage = reader.readLine();
	        		System.out.println("Mensaje recibido del servidor: " + serverMessage);

	        		// Enviar mensajes al servidor y recibir respuestas
	        		BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));
	        		String userInput;
	        		while (true) {
	        			System.out.print("Mensaje para enviar al servidor: ");
	        			userInput = userInputReader.readLine();

	        			// Enviar mensaje al servidor
	                writer.println(userInput);

	                // Leer respuesta del servidor
	                String serverResponse = reader.readLine();
	                System.out.println("Respuesta del servidor: " + serverResponse);
	        		}
	        	} catch (IOException e) {
	        		System.err.println("Error en el cliente: " + e.getMessage());
	        	}
	        }
		}.start();
	}
	
	public void recibir_mensajes() {
		new Thread() {
	        public void run() {
	            try {
	            	//ServerSocket s = new ServerSocket(7777);
	            	Socket soc = new Socket("localhost",1234);
	                while (!soc.isClosed()) {
	                    //Socket soc = s.accept();
	                    PrintWriter out = new PrintWriter(soc.getOutputStream(), true);
	                    BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
	                    out.println("mensaje desde socket");
	                    String msg = in.readLine();
	                    System.out.println("msg recibido: " + msg);
	                    //s.close();
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	      }
	    }.start();
	}
	
	public void llama(Object objeto,String mensaje){
		   try{
			   System.out.println("objeto: " + objeto);
			   oos.flush();
			   oos.writeObject(null);
			   System.out.println("se mando el objeto");
			   oos.flush();
			   out.println(mensaje);
			   oos.flush();
			   this.cerrarConexion();
		   }catch(Exception e){
			   e.printStackTrace();
	       }
	   }

	    public void abrirConexion(String ip,int puerto) throws IOException{
	        this.socket = new Socket(ip,puerto);
	        this.oos = new ObjectOutputStream(socket.getOutputStream());
	        this.out = new PrintWriter(socket.getOutputStream(),true);
	        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    }
	    
	    private void cerrarConexion(){
	        try {
	            socket.close();
	            oos.close();
	            out.close();
	            in.close();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    }
}
