package modelo;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;



public class Ping extends Thread implements Serializable{
	private transient static final long serialVersionUID = 4209360273818925922L;
	private transient String ip;
	private transient Socket socket;
	private transient int puerto;
	private transient Monitor monitor=new Monitor();
	
	public Ping(String ip,int puerto) {
		this.ip=ip;
		this.puerto=puerto;
	}
	public Ping() {}
	
	public void run() {
		int cont=0;
		while(true) {
			if (Conectado()) {
				System.out.println("conexion exitosa");
				cont=0;
			}else {
				if(cont>4) {
					try {
						System.out.println("Cambiando SV");
						monitor.envio(this,"nuevo",7777);
						cont=0;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				System.out.println(" conexion fallida");
				cont++;
			}
			try {
				this.sleep(5000);
			} catch (InterruptedException e) {
				
			}
		}
	}
	public boolean Conectado() {
		try {
			monitor.envio(this,"normal",5555);
			monitor.cerrarConexion();
			return true;
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public int getPuerto() {
		return puerto;
	}
	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}
	public Monitor getMonitor() {
		return monitor;
	}
	public void setMonitor(Monitor monitor) {
		this.monitor = monitor;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
