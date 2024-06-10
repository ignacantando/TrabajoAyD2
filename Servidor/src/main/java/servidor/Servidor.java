/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

import modelo.Administrador;
import modelo.Empleado;
import modelo.Monitor;
import modelo.Ping;
import modelo.Televisor;
import modelo.Totem;

/**
 *
 * @author ignacio
 */
public class Servidor extends Thread implements  Serializable{
    private static final long serialVersionUID = 4209360273818925922L;
    private ColasManager manager;
    private ServerSocket serverSocket;
    private boolean esPrincipal;
    
    public Servidor() throws IOException{
    	this.manager = ColasManager.getInstancia();
    	try {
			this.serverSocket = new ServerSocket(5555);
		} catch (IOException e) {
			esPrincipal=false;
			this.serverSocket = new ServerSocket(7777);
		}
    }
    
    public Servidor(ColasManager manager) throws IOException {
    	this.manager = manager;
    	this.serverSocket = new ServerSocket(5555);
    	System.out.println("Servidor TCP secundario iniciado. Esperando conexiones...");
    }

    
    public void startServer() {
    	try {
			while(true) {
				Socket clientSocket = this.serverSocket.accept();
				System.out.println(serverSocket.getLocalPort());
				Thread thread = new Thread(new ClientHandler(clientSocket,manager,this));
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private ColasManager manager;
        private Servidor servidor;

        public ClientHandler(Socket clientSocket, ColasManager manager, Servidor servidor) {
            this.clientSocket = clientSocket;
            this.manager = manager;
            this.servidor = servidor;
        }

        @Override
        public void run() {
            try { 
                DatosConexion datos = new DatosConexion(this.clientSocket);
                
                Object obj = datos.ois.readObject();
                if(obj instanceof Totem){
                	System.out.println("entra totem");
                    Totem totem=(Totem)obj;
                    System.out.println("dni que entra: " + totem.getDni());
                    manager.newCliente(totem.getDni());
                    System.out.println("documentos: " + manager.getDnis().toString());
                }else if(obj instanceof Televisor){
                	System.out.println("entra televisor");
                	System.out.println("datos: " + datos);
                    manager.creaTele(datos);
                }else if(obj instanceof Empleado){
                	System.out.println("entra empleado");
                    Empleado empleado = (Empleado) obj;
                    String msg=datos.in.readLine();
                    if(msg.equalsIgnoreCase("nuevo")) {
                    	manager.newBox(datos);
                    }else {
                    	manager.llamaCliente(String.valueOf(empleado.getPuesto()));
                    }
                }else if(obj instanceof Administrador){
                	this.manager.nuevoAdministrador(datos);
                }
                else if(obj instanceof Ping){
                	String msg=datos.in.readLine();
                	 if(msg.equalsIgnoreCase("nuevo")) {
                		servidor.cambiarAPuertoPrincipal();
                     }else {
                     	
                     }
                }
            } catch (IOException e) {
                System.err.println("Error al manejar la conexión con el cliente: " + e.getMessage());
            } catch (ClassNotFoundException e) {
            	
			}catch(NullPointerException e) {
			}
        }
    }
    

 // Método para cerrar el ServerSocket
    public void cerrarSocket() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("Server socket cerrado.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para cambiar al puerto principal
    public void cambiarAPuertoPrincipal() {
        try {
            cerrarSocket();
            this.serverSocket = new ServerSocket(5555);
            this.esPrincipal = true;
            System.out.println("Servidor secundario ahora es el principal, escuchando en el puerto " + 5555);
            startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}