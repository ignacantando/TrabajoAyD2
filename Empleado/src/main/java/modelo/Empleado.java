/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package modelo;

import controlador.ControladorEmpleado;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 *
 * @author ignacio
 */
public class Empleado implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2179221794384146037L;

	public Empleado(){
		System.out.println();
		box++;	
	}
    
    private transient BufferedReader entrada;
    private transient BufferedWriter salida;
    private transient SocketEmpleado nuevo;
    private String dni;
    private int box;
    private final int MAX_REINTENTOS = 4;
    
    public void iniciar() {
        nuevo = new SocketEmpleado();
        ControladorEmpleado controladorEmpleado=new ControladorEmpleado(this);
        controladorEmpleado.ejecutar();
    }
    
    public void ingresa(String mensaje){
    	try{
    		if(mensaje.equals("nuevo"))
    			nuevoEmpleado(mensaje);
    		else
    			llamaCliente(mensaje);
        }catch(Exception e){
        	// maneja la excepción
        	JOptionPane.showMessageDialog(null, "No se pudo conectar. Reintentando…");    // registrar la excepción
        }
    }
    
    public void llamaCliente(String mensaje) {
    	nuevo.llama(this, String.valueOf(box));
    }
    
    public void nuevoEmpleado(String mensaje) {
    	this.setPuesto(Integer.valueOf(nuevo.envio(this, mensaje)));
    }
	public int getPuesto() {
		return box;
	}

	public void setPuesto(int puesto) {
		this.box = puesto;
	}


	public String getDni() {
		return dni;
	}


	public void setDni(String dni) {
		this.dni = dni;
	}

	public BufferedReader getEntrada() {
		return entrada;
	}


	public void setEntrada(BufferedReader entrada) {
		this.entrada = entrada;
	}


	public BufferedWriter getSalida() {
		return salida;
	}


	public void setSalida(BufferedWriter salida) {
		this.salida = salida;
	}


	public SocketEmpleado getNuevo() {
		return nuevo;
	}


	public void setNuevo(SocketEmpleado nuevo) {
		this.nuevo = nuevo;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "Empleado [puesto=" + box + ", dnis=" + "]";
	}


    
}
