package dominio.usuario;

import java.io.FileNotFoundException;

public class Usuario{
	private ValidadorDeContrasenias validador = ValidadorDeContrasenias.getInstance(); 
	private String usuario;
	private String contrasenia;
	
	public Usuario(String usuario, String contrasenia) throws FileNotFoundException, ContraseniaEsMalaException{
		validador.validarContrasenia(contrasenia);
		this.usuario = usuario;
		this.contrasenia = contrasenia;
	}
}
