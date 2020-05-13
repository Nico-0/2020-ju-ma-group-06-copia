package dominio;

import java.io.FileNotFoundException;

public class Usuario{
	private ValidadorDeContrasenias validador = ValidadorDeContrasenias.getInstance(); 
	private String usuario;
	private String contrasenia;
	
	public Usuario(String usuario, String contrasenia) throws FileNotFoundException{
		if(!validador.esValida(contrasenia)){
			//tirar una excepcion
		}
		this.usuario = usuario;
		this.contrasenia = contrasenia;
	}
}
