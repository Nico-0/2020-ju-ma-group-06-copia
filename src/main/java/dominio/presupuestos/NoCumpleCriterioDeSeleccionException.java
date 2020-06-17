package dominio.presupuestos;

public class NoCumpleCriterioDeSeleccionException extends RuntimeException {
	public NoCumpleCriterioDeSeleccionException (String mensaje) {
		super(mensaje);
	}
}
