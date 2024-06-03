/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.Administrador;
import vista.IVista;
import vista.Ventana_Estadisticas;

/**
 *
 * @author ignacio
 */
public class Controlador implements ActionListener{
 
    private IVista vista;
    Administrador admin;
    
    
    public Controlador(Administrador adm){
        this.admin=adm;
        vista=new Ventana_Estadisticas();
       
        vista.setActionListener(this);
        vista.setKeyListener();
    }
    
    public void ejecutar(){
        vista.ejecutar();
        this.actualizar();
    }
    
    public void actualizar(){
        admin.nuevoAdministrador();
        this.vista.getCantTiempoLabel().setText(this.admin.getTiempo());
        this.vista.getCantTPLabel().setText(this.admin.getTiempoPromedio());
        this.vista.getCantPersonasAtendidasLabel().setText(Integer.toString(this.admin.getPersonasAtendidas()));
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        switch (e.getActionCommand()) {
            case "SALIR":
                vista.cerrarVentana();
            break;
            case "ACTUALIZAR":
            	this.actualizar();
            break;
        }
    }
}
