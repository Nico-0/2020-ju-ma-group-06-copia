package dominio.presupuestos;

public class CantidadPresupuestosIncorrectaException extends RuntimeException {
	public CantidadPresupuestosIncorrectaException(int cantidadCargada, int cantidadNecesaria) {
		super("La cantidad de presupuestos cargada es incorrecta. Se necesitan "
				+ cantidadNecesaria + "y se han cargado " + cantidadCargada);
	}
}
