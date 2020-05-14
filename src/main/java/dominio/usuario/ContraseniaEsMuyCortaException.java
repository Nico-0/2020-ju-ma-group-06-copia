package dominio.usuario;

public class ContraseniaEsMuyCortaException extends RuntimeException {
	public ContraseniaEsMuyCortaException(String mensaje) {
		super(mensaje);
	}
}
