package dominio.presupuestos;

public class CantidadPresupuestosIncorrectaException extends RuntimeException {
	public CantidadPresupuestosIncorrectaException(String unMensaje) {
		super(unMensaje);
	}
}
