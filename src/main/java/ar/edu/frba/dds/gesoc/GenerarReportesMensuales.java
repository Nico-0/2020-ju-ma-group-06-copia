package ar.edu.frba.dds.gesoc;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import java.util.List;
import java.lang.System;
import dominio.entidad.Entidad;
import dominio.entidad.Reporte;
import repositorios.RepositorioEntidades;

public class GenerarReportesMensuales {
	
	//se crea el ejecutable en la carpeta target luego de "mvn clean install"
	//ejecutar con "java -jar PruebaConcepto-0.0.1-SNAPSHOT-jar-with-dependencies.jar"
	public static void main (String[] args) {
		
		System.out.println("hola soy el programa ejecutable");
		
		List<Entidad> entidades = RepositorioEntidades.getInstance().getEntidades();
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		entidades.stream().forEach((entidad) -> {
			Reporte reporte = entidad.generarReporte();
			entityManager.persist(reporte);
			entidad.agregarReporte(reporte);
		});
		transaction.commit();
		
		System.out.println("chau soy el programa ejecutable");
		System.exit(0);

	}

}