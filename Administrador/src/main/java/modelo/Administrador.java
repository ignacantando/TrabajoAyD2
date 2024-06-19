/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.swing.JOptionPane;

import controlador.Controlador;

/**
 *
 * @author ignacio
 */
public class Administrador implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 56135097522855850L;
	private final int MAX_REINTENTOS = 4;
    private transient BufferedReader entrada;
    private transient BufferedWriter salida;
    private transient SocketAdministrador nuevo;
    private String tiempo;
    private String tiempoPromedio;
    private int personasAtendidas;
   
   public Administrador() {
	super();
   }

    public void iniciar() {
        this.nuevo = new SocketAdministrador();
        Controlador controlador=new Controlador(this);
        controlador.ejecutar();
    }
    
    public void nuevoAdministrador(){
    	try{
            Administrador adm;
    		nuevo.envio(this, "administrador");
            adm = nuevo.recepcion(this, "administrador");
            this.setTiempo(adm.getTiempo());
            this.setTiempoPromedio(adm.getTiempoPromedio());
            this.setPersonasAtendidas(adm.getPersonasAtendidas());
        }catch(Exception e){
			System.err.println("No se pudo conectar. Reintentando…");
        }
    
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

	public SocketAdministrador getNuevo() {
		return nuevo;
	}

	public void setNuevo(SocketAdministrador nuevo) {
		this.nuevo = nuevo;
	}

	public String getTiempo() {
		return tiempo;
	}

	public void setTiempo(String tiempoActual) {
		this.tiempo = tiempoActual;
	}

	public String getTiempoPromedio() {
		return tiempoPromedio;
	}

	public void setTiempoPromedio(String tiempoPromedio) {
		this.tiempoPromedio = tiempoPromedio;
	}

	public int getPersonasAtendidas() {
		return personasAtendidas;
	}

	public void setPersonasAtendidas(int personasAtendidas) {
		this.personasAtendidas = personasAtendidas;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Administrador [tiempo=" + tiempo + ", tiempoPromedio=" + tiempoPromedio + ", personasAtendidas="
				+ personasAtendidas + "]";
	}
    
}
