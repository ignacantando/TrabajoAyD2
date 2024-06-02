package Registros;

import java.util.ArrayList;

import servidor.Cliente;

public class PrioridadAfinidad implements Prioridad{

	private ArrayList<String> dnis=new ArrayList<String>();
	@Override
	public ArrayList<String> aplicaPrioridad(ArrayList<Cliente> clientes) {
		for(int i=0;i<clientes.size();i++) {
			if(clientes.get(i).getPrioridad().compareToIgnoreCase("Platinum")==0) {
				dnis.add(clientes.get(i).getDni());
			}
		}
		for(int i=0;i<clientes.size();i++) {
			if(clientes.get(i).getPrioridad().compareToIgnoreCase("Gold")==0) 
				dnis.add(clientes.get(i).getDni());
		}
		for(int i=0;i<clientes.size();i++) {
			if(clientes.get(i).getPrioridad().compareToIgnoreCase("Default")==0) 
				dnis.add(clientes.get(i).getDni());
		}
		return dnis;
	}   
}
  
