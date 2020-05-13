package dominio;

import java.io.FileNotFoundException;

public class Estandar extends Usuario {
	private String usuario;
	private String contrasenia;
	public Estandar(String usuario, String contrasenia) throws FileNotFoundException{
		super(usuario,contrasenia);
	}
}
