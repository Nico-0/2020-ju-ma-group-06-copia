package dominio.usuario;

public class ContraseniaRepiteCaracteresException extends RuntimeException {
	public ContraseniaRepiteCaracteresException(String mensaje) {
		super(mensaje);
	}
}
