package dominio;

public class Usuario {
	ValidadorDeContrasenias validador = ValidadorDeContrasenias.getInstance(); 
	private String usuario;
	private String contrasenia;
	
	public Usuario(String usuario, String contrasenia){
		if(!validador.esValida(contrasenia)){
			//tirar una excepcion
		}
		this.usuario = usuario;
		this.contrasenia = contrasenia;
	}
}
