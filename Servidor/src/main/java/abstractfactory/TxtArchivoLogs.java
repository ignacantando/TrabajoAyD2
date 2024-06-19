package abstractfactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import servidor.ClienteLog;

public class TxtArchivoLogs implements IArchivoLogs {
    private ArrayList<ClienteLog> clientesLog;
    private final String archivo = "clientes_log.txt";

    @Override
    public void guardarLogs(ArrayList<ClienteLog> logs) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (ClienteLog log : logs) {
                writer.write(log.toString() + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<ClienteLog> leerLogs() {
        ArrayList<ClienteLog> clientesLog = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Eliminamos los caracteres innecesarios y dividimos la línea en partes clave
                linea = linea.replace("ClienteLog{", "").replace("}", "").replace("'", "");
                String[] partes = linea.split(", ");

                if (partes.length == 3) {
                    // Extraemos los datos de cada parte asegurándonos de que tienen el formato esperado
                    String dni = getValue(partes[0], "dni");
                    String tiempoInicioStr = getValue(partes[1], "tiempoInicio");
                    String tiempoFinStr = getValue(partes[2], "tiempoFin");

                    if (dni != null && tiempoInicioStr != null) {
                        // Parseamos las fechas
                        GregorianCalendar tiempoInicio = parsearFecha(tiempoInicioStr);
                        GregorianCalendar tiempoFin = "null".equals(tiempoFinStr) ? null : parsearFecha(tiempoFinStr);

                        // Creamos un objeto ClienteLog y lo añadimos a la lista
                        ClienteLog log = new ClienteLog(dni, tiempoInicio, tiempoFin);
                        clientesLog.add(log);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientesLog;
    }

    private String getValue(String part, String key) {
        String[] keyValue = part.split("=");
        if (keyValue.length == 2 && keyValue[0].trim().equals(key)) {
            return keyValue[1].trim();
        }
        return null;
    }

    private GregorianCalendar parsearFecha(String fecha) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setLenient(false);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(dateFormat.parse(fecha));
            return calendar;
        } catch (ParseException e) {
            return null;
        }
    }

}
