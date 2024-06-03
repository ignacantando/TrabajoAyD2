package main;

import java.io.File;
import java.io.IOException;

import servidor.Servidor;

public class Main {
    private static final String LOCK_FILE = "server.lock";

    public static void main(String[] args) {
        if (isPrimaryInstance()) {
            Servidor servidorPrincipal = new Servidor();
            servidorPrincipal.start();

            // Iniciar el monitor del servidor principal
            ServerMonitor monitor = ServerMonitor.getInstance("localhost", 5555, 5000, 3);
            monitor.start();
        } else {
        	ServerMonitor monitor = ServerMonitor.getInstance("localhost", 5555, 5000, 3);
        	Servidor servidor= new Servidor();
        	servidor.start();
        	monitor.setServerSecundario(servidor);
            System.out.println("Esta instancia solo funcionará como servidor secundario si el primario falla.");
        }
    }

    private static boolean isPrimaryInstance() {
        File lockFile = new File(LOCK_FILE);
        if (lockFile.exists()) {
            return false;
        } else {
            try {
                if (lockFile.createNewFile()) {
                    lockFile.deleteOnExit(); // Asegura que el archivo se borre cuando la JVM se cierre
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}
