package modelo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import controlador.ControladorPing;

public class Ping{
	private String result;
	
	public void iniciar() {
		ControladorPing controladorPing = new ControladorPing(this);
		controladorPing.ejecutar();
		this.inicia();
	}
	
	public void inicia() {
		
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Crear una instancia de la tarea
        Monitor task = new Monitor();

        try {
            // Ejecutar la tarea y obtener un Future que representa el resultado futuro
            Future<String> future = executor.submit(task);

            // Esperar maximo 3 segundos para el resultado de la tarea
            this.result = future.get(3, TimeUnit.SECONDS);
            System.out.println("Resultado de la tarea: " + this.result);
        } catch (TimeoutException e) {
            // La tarea excedio el tiempo limite
            System.err.println("La tarea excedió el tiempo límite.");
            task.instanciar_nuevo_servidor();
            System.err.println("Se instancio una nuevo servidor.");
        } catch (InterruptedException | ExecutionException e) {
        	//System.err.println("Error al ejecutar la tarea: " + e.getMessage());
            System.out.println("error al ejecutar la tarea");
            task.instanciar_nuevo_servidor();
            System.err.println("Se instancio una nuevo servidor.");
        }

        // Apagar el ExecutorService
        executor.shutdown();
    }
	//cambiar el puerto

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
