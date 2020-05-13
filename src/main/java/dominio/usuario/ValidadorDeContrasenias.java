package dominio.usuario;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class ValidadorDeContrasenias {
	private static ValidadorDeContrasenias INSTANCE = null;
	private List<String> peoresContrasenias;
	
	private ValidadorDeContrasenias() throws FileNotFoundException {
		inicializarPeoresContrasenias();
	}
	
	private void inicializarPeoresContrasenias() throws FileNotFoundException {
		File file = new File("peoresContraseñas.txt");
    	Scanner scan = new Scanner(file);
    	while(scan.hasNextLine()) {
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
	
	public void validarContrasenia(String unaContrasenia) throws ContraseniaEsMalaException {
		validarContraseniaEsMala(unaContrasenia);
		validarRecomendacionUno(unaContrasenia);
		validarRecomendacionDos(unaContrasenia);
		validarRecomendacionTres(unaContrasenia);
	}
	
	private void validarContraseniaEsMala(String unaContrasenia) throws ContraseniaEsMalaException {
		if(peoresContrasenias.contains(unaContrasenia))
			throw new ContraseniaEsMalaException("La contraseña pertenece al TOP 10000 de las peores contrasenias");
	}
	
	private void validarRecomendacionUno(String unaContrasenia) {
		//Si no cumple, lanza excepcion
	}
	
	private void validarRecomendacionDos(String unaContrasenia) {
		//Si no cumple, lanza excepcion		
	}
	
	private void validarRecomendacionTres(String unaContrasenia) {
		//Si no cumple, lanza excepcion
	}
	
}
