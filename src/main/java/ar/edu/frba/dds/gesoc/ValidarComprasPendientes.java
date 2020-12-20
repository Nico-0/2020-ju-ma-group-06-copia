package ar.edu.frba.dds.gesoc;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import java.util.List;
import java.lang.System;
import dominio.presupuestos.CompraPendiente;

public class ValidarComprasPendientes {
	
	//se crea el ejecutable en la carpeta target luego de "mvn clean install"
	//ejecutar con "java -jar PruebaConcepto-0.0.1-SNAPSHOT-jar-with-dependencies.jar"
	public static void main (String[] args) {
		
		System.out.println("hola soy el programa ejecutable");

	    final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		List<CompraPendiente> comprasPendientes = entityManager.createQuery("from CompraPendiente").getResultList();    	
		transaction.begin();
		comprasPendientes.stream().forEach(compraPendiente -> compraPendiente.validarCompra());
		transaction.commit();
		
		System.out.println("chau soy el programa ejecutable");
		System.exit(0);

	}

}