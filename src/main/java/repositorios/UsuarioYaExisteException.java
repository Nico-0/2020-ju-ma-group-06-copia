package repositorios;

public class UsuarioYaExisteException extends RuntimeException {
	public UsuarioYaExisteException(String mensaje) {
		super(mensaje);
	}
}
