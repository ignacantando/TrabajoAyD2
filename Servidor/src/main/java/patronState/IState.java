package patronState;

import java.io.Serializable;

public interface IState extends Serializable{
	//cuando no corresponda el cambio -> exception 
	void ponerAtendido();
	void ponerSinAtender();
}
