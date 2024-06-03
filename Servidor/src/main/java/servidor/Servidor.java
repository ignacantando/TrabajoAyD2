/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import modelo.Administrador;
import modelo.Empleado;
import modelo.Televisor;
import modelo.Totem;

/**
 *
 * @author ignacio
 */
public class Servidor extends Thread implements Serializable{
    private static final long serialVersionUID = 4209360273818925922L;
    private ColasManager manager;
    private PrintWriter out;
    private ServerSocket serverSocket;
    
    public Servidor() throws Exception{
    	this.manager = ColasManager.getInstancia();
    	this.serverSocket = new ServerSocket(5555);
    	System.out.println("Servidor TCP iniciado. Esperando conexiones...");
    	/*Thread.sleep(5000);
 		throw new Exception("Exception message");*/
    }
    
    public Servidor(ColasManager manager) throws IOException {
    	this.manager = manager;
    	this.serverSocket = new ServerSocket(5555);
    	System.out.println("Servidor TCP secundario iniciado. Esperando conexiones...");
    }
    //para test
    /*public Servidor(String mensaje) {
    	try {
			this.serverSocket = new ServerSocket(5555);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Servidor ALTER TCP iniciado. Esperando conexiones...");
    }*/
    
    public void startServer() {
    	try {
			while(true) {
				Socket clientSocket = this.serverSocket.accept();
				Thread thread = new Thread(new ClientHandler(clientSocket,manager));
				System.out.println(thread);
				thread.start();
				/*try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private ColasManager manager;

        public ClientHandler(Socket clientSocket,ColasManager manager) {
            this.clientSocket = clientSocket;
            this.manager = manager;
        }

        @Override
        public void run() {
            try { 
                DatosConexion datos = new DatosConexion(this.clientSocket);
                
                //ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                Object obj = datos.ois.readObject();
                if(obj instanceof Totem){
                	System.out.println("entra totem");
                    Totem totem=(Totem)obj;
                    System.out.println("dni que entra: " + totem.getDni());
                    manager.newCliente(totem.getDni());
                    System.out.println("documentos: " + manager.getDnis().toString());
                    
                    mandar_int("agregar index dnis",manager.obtener_index_dnis()); //manager.obtener_index_dnis()
                    //mandar_objeto("agregar dnis",manager.obtener_dnis().getLast());
                }else if(obj instanceof Televisor){
                	System.out.println("entra televisor");
                	System.out.println("datos: " + datos);
                    manager.creaTele(datos);
                    //zona de pruebas
                    mandar_objeto("televisor",manager.obtener_teles());
                    //mandar_objeto("televisor",manager.obtener_teles().getLast());
                }else if(obj instanceof Empleado){
                	System.out.println("entra empleado");
                    Empleado empleado = (Empleado) obj;
                    String msg=datos.in.readLine();
                    if(msg.equalsIgnoreCase("nuevo")) {
                    	manager.newBox(datos);
                    	//boxes.add(indexBox,String.valueOf(indexBox));
            	    	//indexBox++;
                    	mandar_int("agregar index box",manager.obtener_index_box());
                    	mandar_objeto("nuevo boxes",manager.obtener_boxes());
                    }else {
                    	manager.llamaCliente(String.valueOf(empleado.getPuesto()));
                    	//atendidos.add(dnis.get(0));
                		//dnis.remove(0);
                    	mandar_objeto("agregar dnis",manager.obtener_dnis());
                    	mandar_objeto("agregar atendidos",manager.obtener_atendidos());
                    }
                }else if(obj instanceof Administrador){
                	System.out.println("entra administrador");
                	this.manager.nuevoAdministrador(datos);
                }
                else if (obj==null){
                	System.out.println("entra ping al servidor");
                	MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
                	MemoryUsage cantMemoriaUsada = mbean.getHeapMemoryUsage();
                	datos.out.flush();
                	datos.out.println(cantMemoriaUsada.toString());
                	datos.out.flush();
                }
            } catch (IOException e) {
                System.err.println("Error al manejar la conexión con el cliente: " + e.getMessage());
            } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
    
    public static void mandar_int(String mensaje,int numero_enviar) {
		try {
			Socket socket = new Socket("localhost", 5555);
			//inicializo
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
	        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
	        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
	        dos.flush();
			dos.writeUTF(mensaje);
			dos.flush();
			System.out.println("se mando el mensaje");
			dos.flush();
	        dos.writeInt(numero_enviar);
	        dos.flush();
	        System.out.println("se mando el integer");
	        socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void mandar_objeto(String mensaje,Object objeto) {
		try {
			Socket socket = new Socket("localhost", 5555);
			//inicializo
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
	        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
	        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			dos.flush();
			dos.writeUTF(mensaje);
			dos.flush();
			System.out.println("se mando el mensaje");
			oos.flush();
			System.out.println(objeto);
	        oos.writeObject(objeto);
	        oos.flush();
	        System.out.println("se mando el objeto");
	        socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void cerrarsocket(){
    
    }
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}