package dominio.usuario;

import java.io.FileNotFoundException;

public class Administrador extends Usuario {
	private String usuario;
	private String contrasenia;
	public Administrador(String usuario, String contrasenia) throws FileNotFoundException, ContraseniaEsMalaException{
		super(usuario,contrasenia);
	}
}
