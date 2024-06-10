/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import servidor.Servidor;

/**
 *
 * @author ignacio
 */
public class Main {
    
    public static void main(String[] args) throws Exception {
    	Servidor servidor = new Servidor();
    	servidor.startServer();
    }
    
    private static void handleClient(Socket clientSocket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

            // Comunicación bidireccional
            writer.println("¡Conexión establecida con el servidor!");

            String clientMessage;
            while ((clientMessage = reader.readLine()) != null) {
                System.out.println("Mensaje recibido del cliente: " + clientMessage);

                // Procesar la solicitud del cliente (aquí puedes implementar tu lógica)
                String response = "Respuesta del servidor";
                //private ArrayList<String> boxes=new ArrayList<String>();
                ArrayList<String> stringList = new ArrayList<>();
                stringList.add("Hola");
                stringList.add("Cómo");
                stringList.add("Estás");
                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream()); 
                outputStream.writeObject(stringList);

                // Enviar la respuesta al cliente
                //writer.println(response);
                System.out.println("le mando la lista");
            }

            // Cerrar la conexión con el cliente
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error al manejar la conexión con el cliente: " + e.getMessage());
        }
    }
    
}