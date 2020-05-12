package dominio;

public class Administrador extends Usuario {
	String usuario;
	String contrasenia;
	Administrador(String usuario, String contrasenia){
		super(usuario,contrasenia);
	}
		
}
