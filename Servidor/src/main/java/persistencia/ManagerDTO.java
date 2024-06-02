package persistencia;

import java.io.Serializable;
import java.util.ArrayList;

public class ManagerDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2730080190814934056L;
	private ArrayList<String> boxes=new ArrayList<String>();
    private ArrayList<String> dnis=new ArrayList<String>();
	public ManagerDTO() {
		super();
	}
	public ArrayList<String> getBoxes() {
		return boxes;
	}
	public void setBoxes(ArrayList<String> boxes) {
		this.boxes = boxes;
	}
	public ArrayList<String> getDnis() {
		return dnis;
	}
	public void setDnis(ArrayList<String> dnis) {
		this.dnis = dnis;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}
