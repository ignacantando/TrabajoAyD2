package Main;

import java.io.IOException;
import java.net.Socket;

import modelo.Administrador;

public class Main {

	public static void main(String[] args) throws IOException {
        Administrador administrador = new Administrador();
        administrador.iniciar();
	}

}
