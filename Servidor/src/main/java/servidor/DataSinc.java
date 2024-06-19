package servidor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class DataSinc {

	private static final long serialVersionUID = 1131654034383541732L;
	Socket socket;
	private ObjectOutputStream oos;

	
	public DataSinc() {
	}

	
	private void abrirConexion(int puerto) throws IOException{
	    this.socket=new Socket("localhost",puerto);
	    this.oos=new ObjectOutputStream(socket.getOutputStream());
	}
	
	public void envio(Object objeto,String mensaje,int puerto) throws IOException{ 
	        	System.out.println(objeto);
	        	this.abrirConexion(puerto);
	            enviarDatos(objeto,mensaje);
	    }
	
    	private void enviarDatos(Object objeto,String mensaje){
    		try {
				oos.writeObject(objeto);
				oos.flush();
			} catch (IOException e) {
				System.out.println("nuevo");
				e.printStackTrace();
			}
    	}
		
	    public void cerrarConexion(){
	        try {
	            socket.close();
	            oos.close();
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

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		
		public void seteaParametros(ColasManager colas,ColasManager colas2) {
			colas.setBoxes(colas2.getBoxes());
			colas.setArchivoClientes(colas2.getArchivoClientes());
			colas.setArchivoLogs(colas2.getArchivoLogs());
			colas.setAtendidos(colas2.getAtendidos());
			colas.setClientes(colas2.getClientes());
			colas.setClientesLog(colas2.getClientesLog());
			colas.setDnis(colas2.getDnis());
			colas.setFactory(colas2.getFactory());
			colas.setPrio(colas2.getPrio());
			colas.setPrioridad(colas2.getPrioridad());
			colas.setTiempo(colas2.getTiempo());
			colas.setTiempoFin(colas2.getTiempoFin());
			colas.setTiempoInicio(colas2.getTiempoInicio());
			colas.setTipoArchivo(colas2.getTipoArchivo());
			colas.setIndexBox(colas2.getIndexBox());
			colas.setIndexDnis(colas2.getIndexDnis());
		}

}
