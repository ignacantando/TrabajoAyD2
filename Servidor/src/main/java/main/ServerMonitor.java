package main;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import servidor.Servidor;

public class ServerMonitor extends Thread {
	private static ServerMonitor instance = null;
    private final String host;
    private final int port;
    private final int timeout;
    private final int failureThreshold;
    private int failureCount = 0;
    private Servidor serverSecundario;
    private Servidor serverPrimario;
    
    private static final String LOCK_FILE = "server.lock";
    
    private ServerMonitor(String host, int port, int timeout, int failureThreshold) {
        this.host = host;
        this.port = port;
        this.timeout = timeout;
        this.failureThreshold = failureThreshold;
    }

    public static synchronized ServerMonitor getInstance(String host, int port, int timeout, int failureThreshold) {
        if (instance == null) {
            instance = new ServerMonitor(host, port, timeout, failureThreshold);
        }
        return instance;
    }

    public void nuevoServer(Servidor server) {
    	if(serverPrimario!=null) {
    		serverSecundario=server;
    	}
    	else {
    		serverPrimario=server;
    	}
    }
    @Override
    public void run() {
        while (true) {
            if (!isServerAvailable()) {
                failureCount++;
                if (failureCount >= failureThreshold) {
                    if (serverSecundario != null) {
                        System.out.println("Servidor principal no disponible. Levantando servidor secundario...");
                        serverSecundario.start();
                        serverPrimario=serverSecundario;
                        serverSecundario=null;
                        
                    }
                }
            } else {
                failureCount = 0;
            }
            File lockFile = new File(LOCK_FILE);
            if (lockFile.exists()) {
                lockFile.delete();
                System.out.println("Archivo de bloqueo eliminado.");
            }
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isServerAvailable() {
        try (Socket socket = new Socket()) {
            SocketAddress socketAddress = new InetSocketAddress(host, port);
            socket.connect(socketAddress, timeout);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

	public Servidor getServerSecundario() {
		return serverSecundario;
	}

	public void setServerSecundario(Servidor serverSecundario) {
		this.serverSecundario = serverSecundario;
	}
    
}
