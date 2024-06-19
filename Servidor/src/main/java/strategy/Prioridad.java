package strategy;

import java.io.Serializable;
import java.util.ArrayList;

import servidor.Cliente;

public interface Prioridad extends Serializable{

	ArrayList<String> aplicaPrioridad(ArrayList<Cliente> clientes);
}
