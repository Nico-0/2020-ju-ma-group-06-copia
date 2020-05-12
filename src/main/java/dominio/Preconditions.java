package dominio;

public class Preconditions {
	public static void validateNotNull(Object valor, String mensaje) throws PreconditionFailed{
		if(valor == null) {
			throw new PreconditionFailed(mensaje);
		}
	}
}
