package vista;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Dimension;

public class VentanaPing extends JFrame implements IVista {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JPanel panel_1;
	private JButton pingButton;
	private JLabel pingLabel;
	private JLabel resultadoLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPing frame = new VentanaPing();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaPing() {
		setPreferredSize(new Dimension(640, 480));
		setTitle("Ping");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(this.contentPane);
		this.contentPane.setLayout(new BorderLayout(0, 0));
		
		this.panel = new JPanel();
		this.panel.setBackground(new Color(204, 204, 255));
		this.contentPane.add(this.panel, BorderLayout.CENTER);
		this.panel.setLayout(new GridLayout(2, 0, 0, 0));
		
		this.pingLabel = new JLabel("");
		this.pingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.pingLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
		this.panel.add(this.pingLabel);
		
		this.resultadoLabel = new JLabel("");
		this.resultadoLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
		this.resultadoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.panel.add(this.resultadoLabel);
		
		this.panel_1 = new JPanel();
		this.panel_1.setBackground(new Color(204, 204, 255));
		this.contentPane.add(this.panel_1, BorderLayout.SOUTH);
		
		this.pingButton = new JButton("Realizar Ping");
		this.pingButton.setToolTipText("Realizar Ping");
		this.pingButton.setBackground(new java.awt.Color(204, 153, 255));
		this.pingButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
		this.pingButton.setActionCommand("REALIZAR PING");
		this.panel_1.add(this.pingButton);
	}

	@Override
	public void setActionListener(ActionListener controlador) {
		this.pingButton.addActionListener(controlador);
	}

	@Override
	public void setWindowListener(WindowListener controlador) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ejecutar() {
		setTitle("Ping");
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
	}

	@Override
	public void cerrarVentana() {
        setVisible(false); //Oculto la ventana
	    dispose(); //Cierro la ventana
	}

	public JLabel getPingLabel() {
		return pingLabel;
	}

	public void setPingLabel(JLabel pingLabel) {
		this.pingLabel = pingLabel;
	}

	public JLabel getResultadoLabel() {
		return resultadoLabel;
	}

	public void setResultadoLabel(JLabel resultadoLabel) {
		this.resultadoLabel = resultadoLabel;
	}

}
