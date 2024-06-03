package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.Ping;
import vista.IVista;
import vista.VentanaPing;

public class ControladorPing implements ActionListener {
	
	private IVista vista;
	private Ping ping;
	
	public ControladorPing(Ping ping) {
		super();
		this.vista = new VentanaPing();
		this.ping = ping;
		this.vista.setActionListener(this);
	}
	
	public void ejecutar(){
        this.vista.ejecutar();
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
        case "REALIZAR PING":
            this.ping.inicia();
            this.vista.getPingLabel().setText("Resultado de la tarea:");
            this.vista.getResultadoLabel().setText(this.ping.getResult());
        break;
    }
	}

}
