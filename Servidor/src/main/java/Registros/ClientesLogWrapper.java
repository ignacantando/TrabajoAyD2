package Registros;

import java.io.Serializable;
import java.util.ArrayList;

import servidor.ClienteLog;

public class ClientesLogWrapper implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<ClienteLog> clientesLog;

    public ArrayList<ClienteLog> getClientesLog() {
        return clientesLog;
    }

    public void setClientesLog(ArrayList<ClienteLog> clientesLog) {
        this.clientesLog = clientesLog;
    }
}

