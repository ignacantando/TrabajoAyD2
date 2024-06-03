package patronState;

import servidor.Cliente;

public class AtendidoState implements IState{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8006364235598048877L;
	private Cliente cliente;
	
	public AtendidoState(Cliente cliente) {
		super();
		this.cliente = cliente;
	}

	@Override
	public void ponerAtendido() {
		System.out.println("No corresponde el cambio de estado");
	}


	@Override
	public void ponerSinAtender() {
		this.cliente.setEstado(new SinAtenderState(this.cliente));
	}


}
