/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ignacio
 */
public class ConexionTotem implements Serializable{
  
    private static final long serialVersionUID = 4209360273818925922L;
    public Socket socket;
    public ObjectOutputStream oos;
    public BufferedReader in;
    public PrintWriter out;
    //ObjectInputStream ois;
    
    
    public void envioCliente(Object objeto,String mensaje){
        try{
            envioDatosAservidor(objeto,mensaje);
        }catch(Exception e){
            
        }finally{
        	cerrarConexion();
        }
    }
    
    public void envioDatosAservidor(Object objeto,String mensaje){
        try{
            System.out.println(objeto);
            System.out.println("modelo.NuevoTotem.envioDatosAservidor()" + Constantes.IP + Constantes.PUERTO);
            abrirConexion(Constantes.IP,Constantes.PUERTO);
            System.out.println("Conexion establecida");
            System.out.println("Enviando datos");
            enviarDatos(objeto,mensaje);
            System.out.println("esperando respuesta");
            out.println(mensaje);
            System.out.println("Respuesta recibida");
            //String msg=in.readLine();
            //System.out.println("Respuesta recibida:"+msg);
        }catch(IOException e){
            
        }
    }
    
    private void abrirConexion(String ip,int puerto) throws IOException{
        this.socket=new Socket(ip,puerto);
        this.oos=new ObjectOutputStream(socket.getOutputStream());
        this.in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out=new PrintWriter(socket.getOutputStream(),true);
        //this.ois=new ObjectInputStream(socket.getInputStream());
    }
    
    private void enviarDatos(Object objeto,String mensaje) throws IOException{
        //System.out.println("llega1");
        //out.println(mensaje);
        //out.flush();
        oos.writeObject(objeto);
        oos.flush();
        System.out.println("llega2");
        System.out.println("datos enviados: objeto:"+objeto+"Mensaje:"+mensaje);
    }
    
    
    private void cerrarConexion(){
        try {
            socket.close();
            oos.close();
            in.close();
            out.close();
            //ois.close();
        } catch (IOException ex) {
            Logger.getLogger(ConexionTotem.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}


