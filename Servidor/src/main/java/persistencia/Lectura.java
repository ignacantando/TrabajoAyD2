package persistencia;

import java.io.IOException;

import servidor.ColasManager;

public class Lectura {
	
	public static void main (String [] args) {

	IPersistencia idao = new PersistenciaXML();


	try
	{
	    idao.abrirInput("Turnos.xml");
	    
	    ManagerDTO managerDTO = (ManagerDTO) idao.leer();
	    //Util.managerFromManagerDTO(managerDTO); //usa el singleton
	    
	    idao.cerrarInput();
	    System.out.println("Cola recuperada: ");
	    System.out.println(ColasManager.getInstancia());
	} catch (Exception e)
	{
	    // TODO Auto-generated catch block
	    System.err.println("Exception " + e.getMessage());
	}

	    try
	        {
	           	idao.abrirOutput("Orden_Atencion.xml");
	            System.out.println("Crea archivo escritura");
	            //ManagerDTO edto=Util.managerDTOfromManager();
	            
	            //idao.escribir(edto);
	            
	            
	            System.out.println("grabada exitosamente");
	            idao.cerrarOutput();
	            System.out.println("Archivo cerrado");
	        } catch (IOException e)
	        {
	            // TODO Auto-generated catch block
	            System.out.println(e.getLocalizedMessage());
	        }
	}
}
