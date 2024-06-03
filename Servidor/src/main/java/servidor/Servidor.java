package servidor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import modelo.Administrador;
import modelo.Empleado;
import modelo.Televisor;
import modelo.Totem;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class Servidor extends Thread implements Serializable {
    private static final long serialVersionUID = 4209360273818925922L;
    private ColasManager manager;
    private PrintWriter out;
    private ServerSocket serverSocket;
    

    public Servidor() {
        this.manager = ColasManager.getInstancia();
        try {
            this.serverSocket = new ServerSocket(5555);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
        try {
            while (true) {
                Socket clientSocket = this.serverSocket.accept();
                Thread thread = new Thread(new ClientHandler(clientSocket, manager));
                System.out.println(thread);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private ColasManager manager;

        public ClientHandler(Socket clientSocket, ColasManager manager) {
            this.clientSocket = clientSocket;
            this.manager = manager;
        }

        @Override
        public void run() {
            try {
                DatosConexion datos = new DatosConexion(this.clientSocket);
                Object obj = datos.ois.readObject();
                if (obj instanceof Totem) {
                    System.out.println("Entra totem");
                    Totem totem = (Totem) obj;
                    System.out.println("DNI que entra: " + totem.getDni());
                    manager.newCliente(totem.getDni());
                    System.out.println("Documentos: " + manager.getDnis().toString());
                    mandar_int("agregar index dnis", manager.obtener_index_dnis());
                    mandar_objeto("agregar dnis", manager.obtener_dnis().getLast());
                } else if (obj instanceof Televisor) {
                    System.out.println("Entra televisor");
                    System.out.println("Datos: " + datos);
                    manager.creaTele(datos);
                    mandar_objeto("televisor", manager.obtener_teles());
                } else if (obj instanceof Empleado) {
                    System.out.println("Entra empleado");
                    Empleado empleado = (Empleado) obj;
                    String msg = datos.in.readLine();
                    if (msg.equalsIgnoreCase("nuevo")) {
                        manager.newBox(datos);
                        mandar_int("agregar index box", manager.obtener_index_box());
                        mandar_objeto("nuevo boxes", manager.obtener_boxes());
                    } else {
                        manager.llamaCliente(String.valueOf(empleado.getPuesto()));
                        mandar_objeto("agregar dnis", manager.obtener_dnis());
                        mandar_objeto("agregar atendidos", manager.obtener_atendidos());
                    }
                } else if (obj instanceof Administrador) {
                    System.out.println("Entra administrador");
                    this.manager.nuevoAdministrador(datos);
                } else if (obj == null) {
                    System.out.println("Entra ping al servidor");
                    MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
                    MemoryUsage cantMemoriaUsada = mbean.getHeapMemoryUsage();
                    datos.out.flush();
                    datos.out.println(cantMemoriaUsada.toString());
                    datos.out.flush();
                }
            } catch (IOException e) {
                System.err.println("Error al manejar la conexión con el cliente: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void mandar_int(String mensaje, int numero_enviar) {
        try {
            Socket socket = new Socket("localhost", 5555);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.flush();
            dos.writeUTF(mensaje);
            dos.flush();
            System.out.println("Se mandó el mensaje");
            dos.flush();
            dos.writeInt(numero_enviar);
            dos.flush();
            System.out.println("Se mandó el integer");
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mandar_objeto(String mensaje, Object objeto) {
        try {
            Socket socket = new Socket("localhost", 5555);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dos.flush();
            dos.writeUTF(mensaje);
            dos.flush();
            System.out.println("Se mandó el mensaje");
            oos.flush();
            System.out.println(objeto);
            oos.writeObject(objeto);
            oos.flush();
            System.out.println("Se mandó el objeto");
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cerrarSocket() {
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
