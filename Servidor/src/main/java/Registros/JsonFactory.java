package Registros;

import java.util.ArrayList;

import servidor.Cliente;

public interface JsonFactory {

	void leerDesdeJson(String archivo);
    void guardarEnJson(String archivo);
}
