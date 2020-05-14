package dominio.usuario;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Estandar extends Usuario {

	public Estandar(String usuario, String contrasenia) throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
		super(usuario, contrasenia);
	}
}
