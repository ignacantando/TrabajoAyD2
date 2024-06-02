package servidor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClienteLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private GregorianCalendar tiempoInicio=new GregorianCalendar();
    private GregorianCalendar tiempoFin;
    @JsonProperty("numero_dni")
    private String dni;

    public ClienteLog(String dni) {
    	this.dni=dni;
    	this.setTiempoInicio();
    }

    public ClienteLog() {}

    public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}



	public GregorianCalendar getTiempoInicio() {
		return tiempoInicio;
	}



	public void setTiempoInicio() {
		this.tiempoInicio.setTimeInMillis(System.currentTimeMillis());
	}



	public GregorianCalendar getTiempoFin() {
		return tiempoFin;
	}



	public void setTiempoFin() {
		this.tiempoFin=new GregorianCalendar();
		this.tiempoFin.setTimeInMillis(System.currentTimeMillis());
	}
	
	@Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String inicio = (tiempoInicio != null) ? sdf.format(tiempoInicio.getTime()) : "null";
        String fin = (tiempoFin != null) ? sdf.format(tiempoFin.getTime()) : "null";
        
        return "ClienteLog{" +
                "dni='" + dni + '\'' +
                ", tiempoInicio=" + inicio +
                ", tiempoFin=" + fin +
                '}';
    }

}

