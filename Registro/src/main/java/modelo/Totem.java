/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import controlador.ControladorTotem;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Serializable;

import javax.swing.JOptionPane;

public class Totem implements Serializable,Registro{
    
    private static final long serialVersionUID = 4209360273818925922L;
    
    private transient BufferedReader entrada;
    private transient BufferedWriter salida;
    //private transient String dni;
    private transient ConexionTotem nuevo;
    //private BufferedReader entrada;
    //private BufferedWriter salida;
    private String dni;
    private final int MAX_REINTENTOS = 4;
    //private NuevoTotem nuevo;
    
    public Totem() {
    }

    public Totem(BufferedWriter salida, String dni, ConexionTotem nuevo) {
        this.salida = salida;
        this.dni = dni;
        this.nuevo = nuevo;
    }

    public void iniciar() {
        ControladorTotem controladorTotem = new ControladorTotem(this);
        controladorTotem.ejecutar();
        nuevo = new ConexionTotem();
    }

    public void newCliente(String dni) {
        try {
        	setDni(dni);
            nuevo.envioCliente(this, "cliente");
        } catch (Exception e) {
            // maneja la excepción
        	JOptionPane.showMessageDialog(null, "No se pudo conectar. Reintentando…");    // registrar la excepción
            // dormir el thread durante 5 segundos antes de volver a intentarlo
            
        }
    }

    @Override
	public String toString() {
		return "Totem [dni=" + dni + "]";
	}

	public String getDni() {
        return this.dni;
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

	public ConexionTotem getNuevo() {
		return nuevo;
	}

	public void setNuevo(ConexionTotem nuevo) {
		this.nuevo = nuevo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
