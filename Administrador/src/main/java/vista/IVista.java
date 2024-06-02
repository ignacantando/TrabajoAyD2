/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.JLabel;

public interface IVista {

    void setActionListener(ActionListener controlador);

    void setWindowListener(WindowListener controlador);

    void ejecutar();

    void cerrarVentana();

    void lanzarVentanaEmergente(String mensaje);
    
    void setKeyListener();
    
    public JLabel getPersonasAtendidasLabel();
    
    public void setPersonasAtendidasLabel(JLabel personasAtendidasLabel);
    
    public JLabel getCantPersonasAtendidasLabel();
    
    public void setCantPersonasAtendidasLabel(JLabel cantPersonasAtendidasLabel);
    
    public JLabel getTiempoPromedioLabel();
    
    public void setTiempoPromedioLabel(JLabel tiempoPromedioLabel);
    
    public JLabel getCantTPLabel();
    
    public void setCantTPLabel(JLabel cantTPLabel) ;
    
    public JLabel getTiempoLabel();
    
    public void setTiempoLabel(JLabel tiempoLabel);
    
    public JLabel getCantTiempoLabel();
    
    public void setCantTiempoLabel(JLabel cantTiempoLabel);
}
