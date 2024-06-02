package servidor;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import Registros.ClientesJsonFactory;
import Registros.ClientesLogJsonFactory;
import Registros.Prioridad;
import Registros.PrioridadAfinidad;
import Registros.PrioridadDefault;
import Registros.PrioridadEdad;
import modelo.EmpleadoTrigger;
import modelo.Estadisticas;
import modelo.Notificador;
import modelo.Registro;

public class ColasManager implements Registro,EmpleadoTrigger,Notificador,Estadisticas{

    private ArrayList<String> boxes=new ArrayList<String>();
    private ArrayList<String> dnis=new ArrayList<String>();
    private ArrayList<String> atendidos=new ArrayList<String>();
    private ArrayList<Integer> tiempoInicio=new ArrayList<Integer>();
    private ArrayList<Integer> tiempoFin=new ArrayList<Integer>();
    
    
    private ArrayList<Cliente> clientes=new ArrayList<Cliente>();
    private ArrayList<ClienteLog> clientesLog=new ArrayList<ClienteLog>();
    
    private static int indexBox=0;
    private static int indexDnis=0;
    private ArrayList<DatosConexion> teles=new ArrayList<DatosConexion>();
    private long tiempo = System.currentTimeMillis();
    private static ColasManager instancia;
    
    
    private static String prioridad="Default";
    private Prioridad prio;
    private final String archivoJson = "clientes.json";
    private final String archivoLogJson = "clientes_log.json";
    private ClientesJsonFactory clientesFactory;
    private ClientesLogJsonFactory logFactory;
    
    
    
    private ColasManager() {
    	clientesFactory = new ClientesJsonFactory();
        logFactory = new ClientesLogJsonFactory();
     
   
        clientesFactory.leerDesdeJson(archivoJson);
        clientes=clientesFactory.getClientes();
        
        System.out.println(clientes);
        logFactory.leerDesdeJson(archivoLogJson);
        clientesLog=logFactory.getClientesLog();
        System.out.println(clientesLog);
        
        if(this.prioridad.compareToIgnoreCase("afinidad")==0) {
        	prio=new PrioridadAfinidad();
        }
        else if(this.prioridad.compareToIgnoreCase("edad")==0) {
        	prio=new PrioridadEdad();
        }
        else {
        	prio=new PrioridadDefault();
        }   
        
        dnis=prio.aplicaPrioridad(clientes);
        System.out.println(dnis);
    }
    
    public static ColasManager getInstancia() {
        if (instancia == null) {
            instancia = new ColasManager();
        }
        return instancia;
    }
    
    public ArrayList<String> obtener_boxes() {
    	return this.boxes;
    }
    
    public void newBox(DatosConexion datos) {
    	PrintWriter out;
		try {
			out = new PrintWriter(datos.getSocket().getOutputStream(), true);
			out.println(String.valueOf(indexBox));
	    	this.nuevoEmpleado(String.valueOf(indexBox));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void newCliente(String dni) {
    	int esta=0;
    	//Mirar si esta en la lista de clientes y sino agregarlo con prioridad minima
    	for(int i=0;i<clientes.size();i++) {
    		if(dni.compareTo(clientes.get(i).getDni())==0)
    			esta=1;
    	}
    	if(esta==0) {
    		Cliente nuevo=new Cliente(dni,"Default","2024-05-31");
    		ClienteLog nuevolog=new ClienteLog(dni);
    		
    		clientesLog.add(nuevolog);
    		clientes.add(nuevo);
    		
    		clientesFactory.setClientes(clientes);
    		logFactory.setClientesLog(clientesLog);
    		
    		clientesFactory.guardarEnJson(archivoJson);
    		logFactory.guardarEnJson(archivoLogJson);
    	}
    		
    	this.tiempoInicio.add((int) (System.currentTimeMillis()/1000));
    }
    
    public void llamaCliente(String box) {
    	PrintWriter out;
    	if(dnis.size()>=1) {
    		for(int i=0;i<teles.size();i++) {
    			try {
					out = new PrintWriter(teles.get(i).getSocket().getOutputStream(), true);
					out.println(box);
					out.println(dnis.get(0));
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}		
    		atendidos.add(indexDnis, dnis.get(0));
    		this.tiempoFin.add(indexDnis, (int) (System.currentTimeMillis()/1000));
    		for(int i=0;i<clientes.size();i++) {
    			if(clientesLog.get(i).getDni().compareTo(dnis.get(0))==0) {
    				clientesLog.get(i).setTiempoFin();
    			}	
    		}
    		logFactory.guardarEnJson(archivoLogJson);
    		indexDnis++;
    		dnis.remove(0);
    	}
    	else {
    		System.out.println("No hay clientes");
    	}  	
    }
    
    protected String calculaTiempo() {
    	int segundos = (int) ((System.currentTimeMillis() - this.tiempo)/1000);
    	int minutos = segundos/60;
    	int horas = minutos/60;
    	String tiempoActual;
    	if (segundos > 60) {
			segundos = segundos%60;
		}if (minutos > 60) {
			minutos = minutos%60;
		}
		if (minutos < 10 && segundos < 10) {
    		tiempoActual = horas + ":0" + minutos + ":0" + segundos;
		} else if (minutos < 10) {
			tiempoActual = horas + ":0" + minutos + ":" + segundos;
		} else if (segundos < 10) {
    		tiempoActual = horas + ":" + minutos + ":0" + segundos;
    	}else {
    		tiempoActual = horas + ":" + minutos + ":" + segundos;
		}
    	return tiempoActual;
	}
	
    protected String calculaTiempoPromedio(int personas) {
    	String tiempoActual;
    	if (personas == 0) {
			tiempoActual = "0:00:00";
		}
    	else{
    		ArrayList<Integer> tiempoAux = new ArrayList<Integer>();
	    	long suma = 0;
	    	for (int i = 0; i < personas; i++) {
				tiempoAux.add(i, this.tiempoFin.get(i)-this.tiempoInicio.get(i));
				suma+=tiempoAux.get(i);
			}
	    	long promedio = suma/personas;
	    	int segundos = (int) (promedio);
	    	int minutos = segundos/60;
	    	int horas = minutos/60;
    		if (segundos > 60) {
    			segundos = segundos%60;
    		}if (minutos > 60) {
    			minutos = minutos%60;
    		}
    		if (minutos < 10 && segundos < 10) {
        		tiempoActual = horas + ":0" + minutos + ":0" + segundos;
    		} else if (minutos < 10) {
    			tiempoActual = horas + ":0" + minutos + ":" + segundos;
    		} else if (segundos < 10) {
        		tiempoActual = horas + ":" + minutos + ":0" + segundos;
        	}else {
        		tiempoActual = horas + ":" + minutos + ":" + segundos;
    		}
        	return tiempoActual;
    	}
		return tiempoActual;
	}
    
    public void creaTele(DatosConexion datos) {
    	teles.add(datos);
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

	@Override
	public void nuevoEmpleado(String mensaje) {
		boxes.add(indexBox,mensaje);
    	indexBox++;
	}

	@Override
	public void CreaTelevisor() {

	}

	
	public void nuevoAdministrador(DatosConexion datos) {
		datos.out.println(this.getAtendidos().size());
    	datos.out.println(this.calculaTiempo());
    	datos.out.println(this.calculaTiempoPromedio(this.getAtendidos().size()));
    	datos.out.flush();
	}

	@Override
	public void nuevoAdministrador() {

	}
	
	public int obtener_index_dnis() {
    	return indexBox;
    }
    public int obtener_index_box() {
    	return indexBox;
    }
    public ArrayList<String> obtener_dnis(){
    	return this.dnis;
    }
    public ArrayList<String> obtener_atendidos() {
    	return this.atendidos;
    }
    public ArrayList<DatosConexion> obtener_teles(){
    	return this.teles;
    }
    public void agregarBox(String box) {
    	this.boxes.add(box);
    }
    public void agregarIndexBox(int indexBox) {
    	ColasManager.indexBox = indexBox;
    }
    public void agregarDnis(ArrayList<String> dnis_nuevo) {
    	this.dnis = dnis_nuevo;
    }
    public void agregarBoxes(ArrayList<String> boxes) {
    	this.boxes = boxes;
    }
    public void agregarAtendidos(ArrayList<String> atendidos) {
    	this.atendidos = atendidos;
    }
    public void agregarTeles(ArrayList<DatosConexion> teles2) {
    	this.teles = teles2;
    }
    
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void imprimirClientes() {
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }

}
