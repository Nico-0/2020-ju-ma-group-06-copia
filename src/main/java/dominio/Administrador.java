package dominio;

public class Administrador extends Usuario {
	private String usuario;
	private String contrasenia;
	public Administrador(String usuario, String contrasenia){
		super(usuario,contrasenia);
	}
		
}
