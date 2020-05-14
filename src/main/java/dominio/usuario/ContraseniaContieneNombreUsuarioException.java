package dominio.usuario;

public class ContraseniaContieneNombreUsuarioException extends RuntimeException {
	public ContraseniaContieneNombreUsuarioException(String mensaje) {
		super(mensaje);
	}
}
