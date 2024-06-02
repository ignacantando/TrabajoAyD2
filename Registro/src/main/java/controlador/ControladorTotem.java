/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import vista.VentanaPrincipal;
import vista.Ventana_login;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import modelo.Totem;
import vista.IVista;
import vista.I_Login;
/**
 *
 * @author ignacio
 */
public class ControladorTotem implements ActionListener,Serializable{
    
    private I_Login vistalogin;
    private Totem totem;
    private IVista vistaprincipal;
    private static final long serialVersionUID = 4209360273818925922L;
    
    public ControladorTotem(Totem totem){
        this.totem=totem;
        vistalogin=new Ventana_login();
        vistaprincipal=new VentanaPrincipal();
        this.totem=totem;
        vistalogin.setActionListener(this);
        vistalogin.setKeyListener();
        vistaprincipal.setActionListener(this);
        vistaprincipal.setKeyListener();
    }
    
    public void ejecutar(){
        vistaprincipal.ejecutar();
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e){
        switch (e.getActionCommand()) {
            case "INGRESAR":
            	if (vistalogin.getDnioid().isBlank()) {
        			JOptionPane.showMessageDialog(null, "El campo no puede estar vacío");
        		}
            	else if (vistalogin.getDnioid().length()>8) {
        			JOptionPane.showMessageDialog(null, "El DNI no puede tener más de 8 dígitos");
        		}
            	else{
	                totem.newCliente(vistalogin.getDnioid());
	                vistalogin.cerrarVentana();
	                vistaprincipal.ejecutar();
            	}
            break;
            case "ATRAS":
                vistalogin.cerrarVentana();
                vistaprincipal.ejecutar();
            break;
            case "SOLICITAR":
                vistaprincipal.cerrarVentana();
                vistalogin.ejecutar();
            break;
            case "SALIR":
                vistaprincipal.cerrarVentana();
        }
    }

    
}
