package dominio.usuario;

public class ContraseniaEsMalaException extends Exception {
	public ContraseniaEsMalaException(String mensaje) {
		super(mensaje);
	}
}
