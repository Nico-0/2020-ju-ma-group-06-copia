package dominio.usuario;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ValidadorDeContrasenias {
	private static ValidadorDeContrasenias INSTANCE = null;
	public List<String> peoresContrasenias = new ArrayList<String>();

	public ValidadorDeContrasenias() throws FileNotFoundException {
		inicializarPeoresContrasenias();
	}

	private void inicializarPeoresContrasenias() throws FileNotFoundException {
		File file = new File("peoresContrasenas.txt");
		Scanner scan = new Scanner(file);
		while (scan.hasNextLine()) {
			peoresContrasenias.add(scan.nextLine());
		}
		scan.close();
	}

	static public ValidadorDeContrasenias getInstance() throws FileNotFoundException {
		if (INSTANCE == null) {
			INSTANCE = new ValidadorDeContrasenias();
		}
		return INSTANCE;
	}

	public void validarContrasenia(String unaContrasenia, String usuario) {
		validarContraseniaEsMala(unaContrasenia);
		validarCantidadCaracteres(unaContrasenia);
		contieneCaracteresRepetitivos(unaContrasenia);
		contieneElNombreDeUsuario(unaContrasenia, usuario);
	}

	private void validarContraseniaEsMala(String unaContrasenia) {
		if (peoresContrasenias.contains(unaContrasenia))
			throw new ContraseniaEsMalaException("La contraseña pertenece al TOP 10000 de las peores contrasenias");
	}

	private void validarCantidadCaracteres(String unaContrasenia) {
		if (unaContrasenia.length() < 8) {
			throw new ContraseniaEsMalaException("La contraseña debe tener al menos 8 caracteres");
		}
	}

	private void contieneCaracteresRepetitivos(String unaContrasenia) {
		char[] auxiliar = unaContrasenia.toCharArray();
		for (int i = 0; i < (unaContrasenia.length() - 2); i++) {
			if (auxiliar[i] == auxiliar[i + 1] && auxiliar[i] == auxiliar[i + 2]) {
				throw new ContraseniaEsMalaException(
						"La contraseña no puede contener más de dos caracteres iguales seguidos");
			}
		}
	}

	private void contieneElNombreDeUsuario(String unaContrasenia, String usuario) {
		if (unaContrasenia.indexOf(usuario) > -1) {
			throw new ContraseniaEsMalaException("La contraseña no puede contener el nombre de usuario");
		}
	} // En el futuro puede recibir una lista de palabras clave, no solo el ususario

}
