package dominio.presupuestos;

public class PresupuestoNoCoincideException extends RuntimeException {
	public PresupuestoNoCoincideException (String mensaje) {
		super(mensaje);
	}
}
