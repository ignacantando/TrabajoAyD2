package servidor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import abstractfactory.*;
import modelo.Llamado;
import modelo.Monitoreo;
import modelo.Notificacion;
import modelo.Registro;
import strategy.Prioridad;
import strategy.PrioridadAfinidad;
import strategy.PrioridadDefault;
import strategy.PrioridadEdad;


public class ColasManager implements Registro,Llamado,Notificacion,Monitoreo{

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
    
    
    private String prioridad;
    private String tipoArchivo;
    private Prioridad prio;
    private final String archivoJson = "clientes.json";
    private final String archivoLogJson = "clientes_log.json";

    
    
    
    private ColasManager() {
    	ConfigLoader configLoader = new ConfigLoader("config.properties");

        int port = configLoader.getIntProperty("server.port");
        prioridad= configLoader.getStringProperty("server.prioridad");
        tipoArchivo = configLoader.getStringProperty("server.tipoarchivo");

        
        IArchivoFactory factory;

        if(tipoArchivo.compareToIgnoreCase("json")==0) {
        	factory = new JsonArchivoFactory();
        }
        else if(tipoArchivo.compareToIgnoreCase("xml")==0) {
        	  factory = new XmlArchivoFactory();
        }
        else {
        	  factory = new TxtArchivoFactory();
        }
        
        IArchivoClientes archivoClientes = factory.crearArchivoClientes();
    	IArchivoLogs archivoLogs = factory.crearArchivoLogs();
    	clientesLog=archivoLogs.leerLogs();
    	clientes=archivoClientes.leerClientes();
    	System.out.println(clientes);
    	System.out.println(clientesLog);
    	
    	
        if(prioridad.compareToIgnoreCase("afinidad")==0) {
        	prio=new PrioridadAfinidad();
        }
        else if(prioridad.compareToIgnoreCase("edad")==0) {
        	prio=new PrioridadEdad();
        }
        else {
        	prio=new PrioridadDefault();
        }   
        
        dnis=prio.aplicaPrioridad(clientes);
        System.out.println(dnis);
        for (int i = 0; i < dnis.size(); i++) {
			this.tiempoInicio.add((int) (System.currentTimeMillis()/1000));
		}
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
    		if(dni.compareTo(clientes.get(i).getDni())==0){
    			esta=1;
    			this.clientes.get(i).ponerSinAtender();
    			System.out.println(this.clientes.get(i).getEstado());
    		}
    	}
    	if(esta==0) {
    		Cliente nuevo=new Cliente(dni,"Default","2024-05-31");
    		ClienteLog nuevolog=new ClienteLog(dni);
    		
    		clientesLog.add(nuevolog);
    		clientes.add(nuevo);
    		this.dnis.add(dni);
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
    				this.clientes.get(i).ponerAtendido();
    				System.out.println(this.clientes.get(i).getEstado());
    				clientesLog.get(i).setTiempoFin();
    			}	
    		}
    		indexDnis++;
    		dnis.remove(0);
    	}
    	else {
    		System.out.println("No hay clientes");
    	}  	
    }
    
    public String calculaTiempo() {
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
	
    public String calculaTiempoPromedio(int personas) {
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
