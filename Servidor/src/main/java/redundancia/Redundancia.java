package redundancia;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import servidor.ColasManager;
import servidor.DatosConexion;
import servidor.Servidor;

public class Redundancia extends Thread{
    private PrintWriter out;
    private ServerSocket serverSocket;

    
    public Redundancia() throws IOException{
    	this.serverSocket = new ServerSocket(7777);
    	System.out.println("Servidor Redundancia iniciado. Esperando conexiones...");
    }
    
    public void startServer() {
    	try {
			while(true) {
				Socket clientSocket = this.serverSocket.accept();
				Thread thread = new Thread(new ClientHandler(clientSocket));
				thread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private ColasManager manager;
        
        public ClientHandler(Socket clientSocket) {
        	this.manager = ColasManager.getInstancia();
        	this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
            	DatosConexion datos = new DatosConexion(this.clientSocket);
            	//int numero_recibido = datos.dis.readInt();
            	//System.out.println("numero recibido: " + numero_recibido);
                String mensaje_recibido = datos.dis.readUTF();
                System.out.println("mensaje recibido: " + mensaje_recibido);
                if(mensaje_recibido.equals("agregar index dnis")) {
                	int numero_recibido = datos.dis.readInt();
                	System.out.println("numero recibido DENTRO DEL IF: " + numero_recibido);
                	manager.agregarIndexBox(numero_recibido);
                }else if (mensaje_recibido.equals("agregar dnis")) {
                	System.out.println("ENTRO EN EL IF DNIS");
                	ArrayList<String> dnis_recibido = (ArrayList<String>) datos.ois.readObject();
                	manager.agregarDnis(dnis_recibido);
                	System.out.println("recibo dnis");
                }else if(mensaje_recibido.equals("agregar index box")) {
                	int numero_recibido = datos.dis.readInt();
                	System.out.println("numero recibido DENTRO DEL IF: " + numero_recibido);
                	manager.agregarIndexBox(numero_recibido);
                }else if(mensaje_recibido.equals("agregar boxes")) {
                	System.out.println("ENTRO EN EL IF BOXES");
                	ArrayList<String> boxes_recibido = (ArrayList<String>) datos.ois.readObject();
                	manager.agregarBoxes(boxes_recibido);
                }else if(mensaje_recibido.equals("agregar atendidos")) {
                	System.out.println("entra atentidos en el if");
                	ArrayList<String> atendidos_recibido = (ArrayList<String>) datos.ois.readObject();
                	manager.agregarAtendidos(atendidos_recibido);
                }else if (mensaje_recibido.equals("televisor")) {
                	System.out.println("entra televisor en el if");
                	/*ArrayList<DatosConexion> teles = (ArrayList<DatosConexion>) datos.ois.readObject();
                	manager.agregarTeles(teles);
                	DataInputStream recibo = (DataInputStream) datos.ois.readObject();
                	System.out.println("post agregar teles");*/
                }else if(mensaje_recibido.equals("nueva instancia")) {
                	try {
                		System.out.println("se instancia nuevo servidor");
						Servidor servidor = new Servidor(this.manager);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            } catch (IOException e) {
                System.err.println("Error al manejar la conexión con el cliente: " + e.getMessage());
            } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}