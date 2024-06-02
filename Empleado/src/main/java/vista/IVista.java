/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

public interface IVista {

    void setActionListener(ActionListener controlador);

    void setWindowListener(WindowListener controlador);

    void ejecutar();

    void cerrarVentana();

    void lanzarVentanaEmergente(String mensaje);
    
    void setKeyListener();
}
