package dominio;

public class Preconditions {
	public static void validateNotNull(Object valor, String mensaje) {
		if(valor == null) {
			throw new PreconditionFailed(mensaje);
		}
	}
}
