/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Serializable;
import java.util.ArrayList;

import controlador.ControladorTelevisor;

/**
 *
 * @author ignacio
 */
public class Televisor implements Serializable{

   /**
	 * 
	 */
	private static final long serialVersionUID = -8432622592732844803L;
	private ArrayList<String> dnis=new ArrayList<String>();
	private ArrayList<String> boxes=new ArrayList<String>();
   private transient BufferedReader entrada;
   private transient BufferedWriter salida;
   private transient SocketTelevisor nuevo;
   private final int MAX_REINTENTOS = 4;
   
   public Televisor() {
	super();
   }

   public Televisor(SocketTelevisor nuevo){
        this.nuevo = nuevo;
    
    }

    public void iniciar() {
        ControladorTelevisor controladorTelevisor=new ControladorTelevisor(this);
        this.nuevo = new SocketTelevisor(controladorTelevisor);
        controladorTelevisor.ejecutar();
    }
    
    public void creaTelevisor(){
    	try {
			nuevo.envio(this, "Televisor");
		} catch (Exception e) {
			// maneja la excepción
			System.err.println("No se pudo conectar. Reintentando…");    // registrar la excepción
		}
    }
    
	public ArrayList<String> getDnis() {
		return dnis;
	}

	public void setDnis(ArrayList<String> dnis) {
		this.dnis = dnis;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SocketTelevisor getNuevo() {
		return nuevo;
	}

	public void setNuevo(SocketTelevisor nuevo) {
		this.nuevo = nuevo;
	}

	public ArrayList<String> getBoxes() {
		return boxes;
	}

	public void setBoxes(ArrayList<String> boxes) {
		this.boxes = boxes;
	}

	public int getMAX_REINTENTOS() {
		return MAX_REINTENTOS;
	}
	
}
