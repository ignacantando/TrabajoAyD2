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
        ArrayList<Cliente> clientes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Eliminamos los caracteres innecesarios y dividimos la línea en partes clave
                linea = linea.replace("Cliente{", "").replace("}", "").replace("'", "");
                String[] partes = linea.split(", ");

                if (partes.length == 3) {
                    // Extraemos los datos de cada parte asegurándonos de que tienen el formato esperado
                    String fechaNacimiento = getValue(partes[0], "fecha_de_nacimiento");
                    String numeroDni = getValue(partes[1], "numero_dni");
                    String prioridad = getValue(partes[2], "prioridad");

                    if (fechaNacimiento != null && numeroDni != null && prioridad != null) {
                        // Creamos un objeto Cliente y lo añadimos a la lista
                        Cliente cliente = new Cliente(numeroDni,prioridad,fechaNacimiento);
                        clientes.add(cliente);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    private String getValue(String part, String key) {
        String[] keyValue = part.split("=");
        if (keyValue.length == 2 && keyValue[0].trim().equals(key)) {
            return keyValue[1].trim();
        }
        return null;
    }


}
