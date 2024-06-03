package servidor;

import java.util.ArrayList;

public class Colas {

	 private ArrayList<String> boxes=new ArrayList<String>();
	    private ArrayList<String> dnis=new ArrayList<String>();
	    private ArrayList<String> atendidos=new ArrayList<String>();
	    private ArrayList<Integer> tiempoInicio=new ArrayList<Integer>();
	    private ArrayList<Integer> tiempoFin=new ArrayList<Integer>();
	   
	    
	    private ArrayList<Cliente> clientes=new ArrayList<Cliente>();
	    private ArrayList<ClienteLog> clientesLog=new ArrayList<ClienteLog>();
	    
		public ArrayList<String> getBoxes() {
			return boxes;
		}
		public void setBoxes(ArrayList<String> boxes) {
			this.boxes = boxes;
		}
		public ArrayList<String> getDnis() {
			return dnis;
		}
		public void setDnis(ArrayList<String> dnis) {
			this.dnis = dnis;
		}
		public ArrayList<String> getAtendidos() {
			return atendidos;
		}
		public void setAtendidos(ArrayList<String> atendidos) {
			this.atendidos = atendidos;
		}
		public ArrayList<Integer> getTiempoInicio() {
			return tiempoInicio;
		}
		public void setTiempoInicio(ArrayList<Integer> tiempoInicio) {
			this.tiempoInicio = tiempoInicio;
		}
		public ArrayList<Integer> getTiempoFin() {
			return tiempoFin;
		}
		public void setTiempoFin(ArrayList<Integer> tiempoFin) {
			this.tiempoFin = tiempoFin;
		}
		public ArrayList<Cliente> getClientes() {
			return clientes;
		}
		public void setClientes(ArrayList<Cliente> clientes) {
			this.clientes = clientes;
		}
		public ArrayList<ClienteLog> getClientesLog() {
			return clientesLog;
		}
		public void setClientesLog(ArrayList<ClienteLog> clientesLog) {
			this.clientesLog = clientesLog;
		}
}
