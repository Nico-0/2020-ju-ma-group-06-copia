package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.TareaProgramada;
import dominio.compra.Proveedor;
import dominio.presupuestos.CompraPendiente;
import dominio.usuario.Mensaje;
import dominio.usuario.TipoUsuario;
import dominio.usuario.Usuario;
import dominio.usuario.ValidadorDeContrasenias;
import dominio.validacion.EsMala;
import repositorios.RepositorioUsuarios;

public class Bootstrap extends AbstractPersistenceTest implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps{

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		new Bootstrap().init();
	}
	
	public static void init() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException{
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		/*
		CompraPendiente compra = entityManager.createQuery("from CompraPendiente", CompraPendiente.class).getResultList().get(0);
		if(compra == null) {
			compra = new CompraPendiente();
			compra.setFecha(LocalDate.now());
			compra.getDetalle().setMoneda("ninguna");
		}*/
		Usuario usuario = RepositorioUsuarios.getInstance().getUsuario("pepe");
		if(usuario == null)
			usuario = RepositorioUsuarios.getInstance().crearUsuario("pepe", "1234", TipoUsuario.ESTANDAR);
		ValidadorDeContrasenias.getInstance().agregarValidacion(new EsMala());
		//transaction.begin();
		//entityManager.persist(compra);
		//transaction.commit();
		//usuario.recibirMensaje(new Mensaje(compra, "Mensaje numero 1"));
		//usuario.recibirMensaje(new Mensaje(compra, "Mensaje numero 2"));
		//TareaProgramada tareaProgramada = new TareaProgramada(600000); // Cada 10 minutos
	}
}
