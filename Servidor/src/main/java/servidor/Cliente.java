package servidor;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Date;

public class Cliente implements Serializable {

	private static final long serialVersionUID = 4875610979375323141L;

    
    @JsonProperty("numero_dni")
    private String dni;

    @JsonProperty("fecha_de_nacimiento")
    private String fecha_de_nacimiento;

    @JsonProperty("prioridad")
    private String prioridad;
   

	public Cliente(String dni,String prio,String fecha) {
		this.dni=dni;
		this.fecha_de_nacimiento=fecha;
		this.prioridad=prio;
	}


	public Cliente() {}

    @Override
    public String toString() {
        
        return "Cliente{" +
                "fecha_de_nacimiento='" + fecha_de_nacimiento + '\'' +
                ", numero_dni='" + dni + '\'' +
                ", prioridad=" + prioridad +'}';
    }
	

	// Getters y setters
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }


    public String getFecha_de_nacimiento() {
        return fecha_de_nacimiento;
    }

    public void setFecha_de_nacimiento(String fecha_de_nacimiento) {
        this.fecha_de_nacimiento = fecha_de_nacimiento;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
      
}
