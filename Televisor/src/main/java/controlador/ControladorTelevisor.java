/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

import modelo.Televisor;
import vista.Ivista_turnos;
import vista.Ventana_Turnos;

/**
 *
 * @author ignacio
 */
public class ControladorTelevisor implements ActionListener, Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1684508436196287600L;
	private Ivista_turnos vista;
    Televisor televisor;
    
    
    public ControladorTelevisor(Televisor televisor){
        this.televisor=televisor;
        this.vista=new Ventana_Turnos();
       
        this.vista.setActionListener(this);
        this.vista.setKeyListener();
    }
    
    public void ejecutar(){
        this.vista.ejecutar();
        televisor.creaTelevisor();
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e){
        
    }
    
    public void Actualiza(String dni,String box){
    	vista.actualiza(dni, box);
    }
    
    public void eliminaprimero() {
    	vista.remove();
    }
}
