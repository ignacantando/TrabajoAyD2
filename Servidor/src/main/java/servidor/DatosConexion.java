/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;

/**
 *
 * @author ignacio
 */
public class DatosConexion implements Serializable {
 
    private static final long serialVersionUID = 1L;
	public  BufferedReader in;
    public  PrintWriter out;
    public  ObjectInputStream ois;
    public  Socket socket;
    public  DataInputStream dis;
    
    public DatosConexion() {
	}

	public DatosConexion(Socket s){
        this.socket=s;
        try{
        	this.ois = new ObjectInputStream(socket.getInputStream());
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.dis = new DataInputStream(socket.getInputStream());
            this.out = new PrintWriter(socket.getOutputStream(),true);
        }catch(IOException e){

        }
        
    }
    
    public Socket getSocket() {
    	return this.socket;
    }

	public BufferedReader getIn() {
		return in;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}

	public PrintWriter getOut() {
		return out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}

	public ObjectInputStream getOis() {
		return ois;
	}

	public void setOis(ObjectInputStream ois) {
		this.ois = ois;
	}

	public DataInputStream getDis() {
		return dis;
	}

	public void setDis(DataInputStream dis) {
		this.dis = dis;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
    
}
