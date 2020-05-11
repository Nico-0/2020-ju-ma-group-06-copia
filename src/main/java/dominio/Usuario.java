package dominio;

public class Usuario {
	ValidadorDeContrasenias validador = ValidadorDeContrasenias.getInstance(); 
	private String usuario;
	private String contrasenia;
	public Usuario(String usuario, String contrasenia){
		this.usuario = usuario;
		if(validador.esValida(contrasenia)){
			this.contrasenia = contrasenia;
		}
		else {
			//tirar una excepcion
		}
		
	}
}
