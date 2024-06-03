package modelo;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;

public class Monitor implements Callable<String>{
	
	public Monitor() {
	}
	
	@Override
	public String call() throws UnknownHostException, IOException {
			Socket socket = new Socket("localhost", 5555);
			//inicializo
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            if(socket.isConnected()) {
            	oos.flush();
                oos.writeObject(null);
                System.out.println("se mando el objeto");
                oos.flush();
                out.println("hola");
                oos.flush();
                // Leer respuesta del servidor
                String respuesta_servidor;
                while ((respuesta_servidor = reader.readLine()) != null) {
                	socket.close();
                	return respuesta_servidor;
                }
                socket.close();
                return null;
            }
            socket.close();
            return null;
	}
	
	public void instanciar_nuevo_servidor() {
		try {
			System.out.println("pre socket");
			Socket socket = new Socket("localhost", 7777);
			//inicializo
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
	        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
	        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
	        
	        dos.flush();
			dos.writeUTF("nueva instancia");
			dos.flush();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    @Override
	public String toString() {
		return "Monitor";
	}
}
