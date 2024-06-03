package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import controlador.ControladorTelevisor;

public class SocketTelevisor implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1131654034383541732L;
	//ArrayList<String> dnis = new ArrayList<String>();
	Socket socket;
	BufferedReader entrada;
	BufferedWriter salida;
	private ObjectOutputStream oos;
	//private ObjectInputStream ois;
	private PrintWriter out;
	private ControladorTelevisor controlador;

	
    public SocketTelevisor(ControladorTelevisor cont) {
		this.controlador=cont;
	}
	
	private void abrirConexion() throws IOException{
	    this.socket=new Socket("localhost",5555);
	    this.salida=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	    this.entrada=new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    this.oos=new ObjectOutputStream(socket.getOutputStream());
	    //this.ois=new ObjectInputStream(socket.getInputStream());
	    this.out=new PrintWriter(socket.getOutputStream(),true);
	}

	    
	    public void envio(Object objeto,String mensaje){
	        try{
	        	System.out.println(objeto);
	        	this.abrirConexion();
	        	System.out.println("Conexion establecida");
	            System.out.println("Enviando datos");
	            enviarDatos(objeto,mensaje);
	            System.out.println("esperando respuesta");
	            out.println(mensaje);
	            System.out.println("Respuesta recibida");
	        }catch(Exception e){
	            
	        }finally{
	        	cerrarConexion();
	        }
	        
	    }
	    private void enviarDatos(Object objeto, String mensaje) throws IOException, ClassNotFoundException {
	    	this.oos.writeObject(objeto);
            this.oos.flush();
            
            while(!this.socket.isClosed()) {
            	System.out.println("ssss");
            	String msgbox=this.entrada.readLine();
            	String msgdni=this.entrada.readLine();
            	System.out.println("msgbox " + msgbox);
            	System.out.println("msgdani " + msgdni);
            	Timer timer = new Timer();         
            	timer.schedule(new ActualizaTask(this.controlador,msgdni,msgbox),2000); 
            	timer.schedule(new RemoveTask(this.controlador),15000);  
            }
        }
		
	    public class ActualizaTask extends TimerTask {

	        private ControladorTelevisor controlador;
	        private String dni;
	        private String box;

	        public ActualizaTask(ControladorTelevisor contro,String dni,String box) {
	        	this.controlador=contro;
	        	this.box=box;
	        	this.dni=dni;
	        }

	        @Override
	        public void run() {
	        		controlador.Actualiza(dni,box);
	        }

	   }
	    
	    public class RemoveTask extends TimerTask {

	        private ControladorTelevisor controlador;

	        public RemoveTask(ControladorTelevisor contro) {
	        	this.controlador=contro;
	        }

	        @Override
	        public void run() {
	        	controlador.eliminaprimero();
	        }

	   }
	    
	    private void cerrarConexion(){
	        try {
	            socket.close();
	            oos.close();
	            out.close();
	            //ois.close();
	        } catch (IOException ex) {
	            Logger.getLogger(Televisor.class.getName()).log(Level.SEVERE, null, ex);
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

		/*public ObjectInputStream getOis() {
			return ois;
		}

		public void setOis(ObjectInputStream ois) {
			this.ois = ois;
		}*/

		public PrintWriter getOut() {
			return out;
		}

		public void setOut(PrintWriter out) {
			this.out = out;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		public ControladorTelevisor getControlador() {
			return controlador;
		}

		public void setControlador(ControladorTelevisor controlador) {
			this.controlador = controlador;
		}

}
