package dominio.usuario;

import java.io.FileNotFoundException;

public class Estandar extends Usuario {
	private String usuario;
	private String contrasenia;
	public Estandar(String usuario, String contrasenia) throws FileNotFoundException, ContraseniaEsMalaException{
		super(usuario,contrasenia);
	}
}
