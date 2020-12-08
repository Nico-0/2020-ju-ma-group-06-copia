package dominio.usuario;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dominio.validacion.Validacion;

public class ValidadorDeContrasenias {
	
	private static ValidadorDeContrasenias INSTANCE = null;
	private List<String> peoresContrasenias = new ArrayList<String>();
	private List<Validacion> validaciones = new ArrayList<Validacion>();

	public ValidadorDeContrasenias() throws IOException {
		inicializarPeoresContrasenias();
	}

	private void inicializarPeoresContrasenias() throws IOException {
		/*File file = new File("peoresContrasenas.txt");
		Scanner scan = new Scanner(file);
		while (scan.hasNextLine()) {
			peoresContrasenias.add(scan.nextLine());
		}
		scan.close();*/
		
		URL url = new URL("https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10k-most-common.txt");	
		BufferedReader reader = new BufferedReader(
		new InputStreamReader(url.openStream()));
		String line;
	    while ((line = reader.readLine()) != null)
	    	peoresContrasenias.add(line);
	    reader.close();
	}

	static public ValidadorDeContrasenias getInstance() throws IOException {
		if (INSTANCE == null) {
			INSTANCE = new ValidadorDeContrasenias();
		}
		return INSTANCE;
	}
	
	public ValidadorDeContrasenias agregarValidacion(Validacion unaValidacion) {
		validaciones.add(unaValidacion);
		return this;
	}

	public void validarContrasenia(String unaContrasenia, String usuario) {
		validaciones.stream().forEach(validacion -> validacion.validar(unaContrasenia, usuario, peoresContrasenias));
	}
	
}
