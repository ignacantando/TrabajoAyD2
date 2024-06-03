package vista;

import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.JLabel;

public interface IVista {
	void setActionListener(ActionListener controlador);
    void setWindowListener(WindowListener controlador);
    void ejecutar();
    void cerrarVentana();
    public JLabel getPingLabel();
	public void setPingLabel(JLabel pingLabel);
	public JLabel getResultadoLabel();
	public void setResultadoLabel(JLabel resultadoLabel);
}
