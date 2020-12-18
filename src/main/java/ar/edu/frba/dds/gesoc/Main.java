package ar.edu.frba.dds.gesoc;

import java.util.TimerTask;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import java.util.List;
import java.util.Timer;
import java.lang.System;
import dominio.TareaProgramada;
import dominio.presupuestos.CompraPendiente;

public class Main {
	
	//se crea el ejecutable en la carpeta target luego de "mvn clean install"
	//ejecutar con "java -jar PruebaConcepto-0.0.1-SNAPSHOT-jar-with-dependencies.jar"
public static void main (String[] args) {
	
	System.out.println("hola soy el programa ejecutable");
	//TareaProgramada tareaProgramada = new TareaProgramada(2000);
	
	
    final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
	final EntityTransaction transaction = entityManager.getTransaction();
	List<CompraPendiente> comprasPendientes = entityManager.createQuery("from CompraPendiente").getResultList();    	
	transaction.begin();
	comprasPendientes.stream().forEach(compraPendiente -> compraPendiente.validarCompra());
	transaction.commit();

	}

}