package dominio.usuario;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Usuario {
	
	@Id
	@GeneratedValue
	private Long id;
	@Transient	//TODO singleton?
	private ValidadorDeContrasenias validador = ValidadorDeContrasenias.getInstance();
	private String usuario;
	private byte[] hashedPassword;
	private byte[] salt;
	@OneToOne
	public BandejaDeMensajes bandejaDeEntrada = new BandejaDeMensajes();

	public Usuario(String usuario, String contrasenia) throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
		validador.validarContrasenia(contrasenia, usuario);
		inicializarSalt();
		this.usuario = usuario;
		this.hashedPassword = hashearContrasenia(contrasenia);
	}
	
	private void inicializarSalt() {
		SecureRandom random = new SecureRandom();
		salt = new byte[16];
		random.nextBytes(salt);
	}
	
	private byte[] hashearContrasenia(String contrasenia) throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeySpec spec = new PBEKeySpec(contrasenia.toCharArray(), salt, 65536, 128);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		return factory.generateSecret(spec).getEncoded();
	}

	public boolean laContraseniaEs(String unaContrasenia) throws NoSuchAlgorithmException, InvalidKeySpecException {
		return Arrays.equals(hashedPassword,hashearContrasenia(unaContrasenia));
	}
	
	public void recibirMensaje(Mensaje unMensaje) {
		bandejaDeEntrada.agregarMensaje(unMensaje);
	}
}
