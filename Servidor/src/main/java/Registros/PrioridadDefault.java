package Registros;

import java.util.ArrayList;

import servidor.Cliente;

public class PrioridadDefault implements Prioridad{

	private ArrayList<String> dnis=new ArrayList<String>();

	@Override
	public ArrayList<String> aplicaPrioridad(ArrayList<Cliente> clientes) {
		for(int i=0;i<clientes.size();i++) {
			dnis.add(clientes.get(i).getDni());
		}
		return dnis;
	}
}
