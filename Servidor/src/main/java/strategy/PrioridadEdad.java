package strategy;

import java.util.ArrayList;

import servidor.Cliente;

public class PrioridadEdad implements Prioridad{

	private ArrayList<String> dnis=new ArrayList<String>();
	@Override
	public ArrayList<String> aplicaPrioridad(ArrayList<Cliente> clientes) {
		for(int i=0;i<clientes.size();i++) {
			int year=Integer.parseInt(clientes.get(i).getFecha_de_nacimiento().substring(0, 4));
			if(year<=1960) 
				dnis.add(clientes.get(i).getDni());
		}
		for(int i=0;i<clientes.size();i++) {
			int year=Integer.parseInt(clientes.get(i).getFecha_de_nacimiento().substring(0, 4));
			if(year<=2005) 
				dnis.add(clientes.get(i).getDni());
		}
		for(int i=0;i<clientes.size();i++) {
			int year=Integer.parseInt(clientes.get(i).getFecha_de_nacimiento().substring(0, 4));
			if(year>2005) 
				dnis.add(clientes.get(i).getDni());
		}
		return dnis;
	}   
}
