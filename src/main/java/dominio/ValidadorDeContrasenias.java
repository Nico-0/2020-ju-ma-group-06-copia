package dominio;

public class ValidadorDeContrasenias {
	private static ValidadorDeContrasenias INSTANCE = null;

	private ValidadorDeContrasenias(){}
	
	static public ValidadorDeContrasenias getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ValidadorDeContrasenias();
        }
        return INSTANCE;
    }

	public boolean esValida(String unaContrasenia){
		return (this.esMuyComun(unaContrasenia) && this.recomendacionUno(unaContrasenia) && this.recomendacionDos(unaContrasenia) && this.recomendacionTres(unaContrasenia));

	}
	
	private boolean esMuyComun(String unaContrasenia) {
		return true;  //si pertenece a las 10.000 mas comunes
	}
	
	private boolean recomendacionUno(String unaContrasenia) {
		return true;
	}
	
	private boolean recomendacionDos(String unaContrasenia) {
		return true;
	}
	
	private boolean recomendacionTres(String unaContrasenia) {
		return true;
	}
	
}
