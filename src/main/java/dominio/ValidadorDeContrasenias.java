package dominio;

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

	public boolean esValida(String unaContrasenia){
		return (this.esMuyComun(unaContrasenia) && this.recomendacionUno(unaContrasenia) && this.recomendacionDos(unaContrasenia) && this.recomendacionTres(unaContrasenia));

	}
	
	private boolean esMuyComun(String unaContrasenia) {
		return peoresContrasenias.contains(unaContrasenia);  //si pertenece a las 10.000 mas comunes
	}
	
	private boolean recomendacionUno(String unaContrasenia) {
		return true;
	}
	
	private boolean recomendacionDos(String unaContrasenia) {
		return true;
	}
	
	private boolean recomendacionTres(String unaContrasenia) {
		return true;
	}
	
}
