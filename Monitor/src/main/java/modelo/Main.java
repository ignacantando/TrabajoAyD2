package modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) throws ClassNotFoundException {
		Ping ping = new Ping();
        ping.iniciar();
    }
	
	public static void main2(String[] args) throws ClassNotFoundException {
		try (Socket socket = new Socket("localhost", 5555)) {
            //inicializo
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            
            while (true) {
                System.out.print("Mensaje para enviar al servidor: ");
                oos.flush();
    			oos.writeObject(null);
    			System.out.println("se mando el objeto");
    			oos.flush();
    			out.println("hola");
    			oos.flush();
                
                // Leer respuesta del servidor
    			String respuesta_servidor;
                while ((respuesta_servidor = reader.readLine()) != null) {
                	ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                    ArrayList<String> receivedList = (ArrayList<String>) inputStream.readObject();
                    System.out.println("ArrayList<String> recibido del cliente:");
                    for (String str : receivedList) {
                        System.out.println("- " + str);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}
