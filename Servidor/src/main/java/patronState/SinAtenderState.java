package patronState;

import servidor.Cliente;

public class SinAtenderState implements IState{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2954678991323697328L;
	private Cliente cliente;
	
	public SinAtenderState(Cliente cliente) {
		super();
		this.cliente = cliente;
	}

	@Override
	public void ponerAtendido() {
		this.cliente.setEstado(new AtendidoState(this.cliente));
	}


	@Override
	public void ponerSinAtender() {
		System.out.println("No corresponde el cambio de estado");
	}

}
