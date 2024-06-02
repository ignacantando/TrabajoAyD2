package persistencia;

import java.io.IOException;

public class Escritura
{

    public static void main(String[] args)
    {
	IPersistencia idao = new PersistenciaXML();

	try
    {
		idao.abrirOutput("Turnos.xml");
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
