package dominio;

public class PreconditionFailed extends Exception {
	PreconditionFailed(String mensaje){
		super(mensaje);
	}
}
