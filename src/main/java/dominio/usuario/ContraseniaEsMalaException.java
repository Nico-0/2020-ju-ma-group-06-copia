package dominio.usuario;

public class ContraseniaEsMalaException extends RuntimeException {
	public ContraseniaEsMalaException(String mensaje) {
		super(mensaje);
	}
}
