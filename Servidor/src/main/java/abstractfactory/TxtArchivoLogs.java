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
        clientesLog = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Suponiendo que el formato de cada línea sea el mismo que el de ClienteLog.toString()
                // Puedes ajustar esto dependiendo de cómo se guarden los datos en el archivo JSON
                String[] datos = linea.split(",");
                GregorianCalendar tiempoInicio = parsearFecha(datos[0]);
                GregorianCalendar tiempoFin = parsearFecha(datos[1]);
                ClienteLog log = new ClienteLog(datos[2],tiempoInicio,tiempoFin);
                clientesLog.add(log);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientesLog;
    }
    
    private GregorianCalendar parsearFecha(String fecha) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            dateFormat.setLenient(false);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(dateFormat.parse(fecha));
            return calendar;
        } catch (ParseException e) {
            return null;
        }
    }
}
