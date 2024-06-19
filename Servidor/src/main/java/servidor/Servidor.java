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

import constantes.Constantes;
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
			this.serverSocket = new ServerSocket(Constantes.PUERTO);
			esPrincipal=true;
		} catch (IOException e) {
			esPrincipal=false;
			this.serverSocket = new ServerSocket(Constantes.PUERTOSECUNDARIO);
		}
    }
    
    public void startServer() {
    	try {
			while(true) {
				Socket clientSocket = this.serverSocket.accept();
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
        private DataSinc data;

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
                    Totem totem=(Totem)obj;
                    manager.newCliente(totem.getDni());
                    System.out.println("documentos: " + manager.getDnis().toString());
                }else if(obj instanceof Televisor){
                    manager.creaTele(datos);
                }else if(obj instanceof Empleado){
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
                     }
                }
                else if(obj instanceof ColasManager) {
                	ColasManager cola=(ColasManager)obj;
                	data=new DataSinc();
                	data.seteaParametros(manager, cola);
                } 
                if(servidor.esPrincipal==true) {
                	data=new DataSinc();
                    data.envio(manager,"Actualiza",Constantes.PUERTOSECUNDARIO);
                }
            } catch (IOException e) {
                e.getStackTrace();
            } catch (ClassNotFoundException e) {
            	
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
            this.serverSocket = new ServerSocket(Constantes.PUERTO);
            this.esPrincipal = true;
            System.out.println("Servidor secundario ahora es el principal, escuchando en el puerto " + Constantes.PUERTO);
            startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public boolean isEsPrincipal() {
		return esPrincipal;
	}

	public void setEsPrincipal(boolean esPrincipal) {
		this.esPrincipal = esPrincipal;
	}

	public ColasManager getManager() {
		return manager;
	}

	public void setManager(ColasManager manager) {
		this.manager = manager;
	}
    
	
}