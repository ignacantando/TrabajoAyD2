package abstractfactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import servidor.Cliente;

public class TxtArchivoClientes implements IArchivoClientes {
    private ArrayList<Cliente> clientes;
    private final String archivo = "clientes.txt";

    @Override
    public void guardarClientes(ArrayList<Cliente> clientes) {
        try (FileWriter writer = new FileWriter(archivo)) {
            for (Cliente cliente : clientes) {
                writer.write(cliente.toString() + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Cliente> leerClientes() {
        clientes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Suponiendo que el formato de cada línea sea el mismo que el de Cliente.toString()
                String[] datos = linea.split(",");
                Cliente cliente = new Cliente(datos[0], datos[2], datos[1]); 
                clientes.add(cliente);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientes;
    }
}
