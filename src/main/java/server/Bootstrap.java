package server;

import java.io.FileNotFoundException;
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

import dominio.presupuestos.CompraPendiente;
import dominio.usuario.Mensaje;
import dominio.usuario.TipoUsuario;
import dominio.usuario.Usuario;
import repositorios.RepositorioUsuarios;

public class Bootstrap extends AbstractPersistenceTest implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps{

	public static void main(String[] args) throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
		new Bootstrap().init();
	}
	
	public static void init() throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException{
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		CompraPendiente compra = new CompraPendiente();
		compra.setFecha(LocalDate.now());
		compra.getDetalle().setMoneda("ninguna");
		Usuario usuario = RepositorioUsuarios.getInstance().getUsuario("pepe");
		//Usuario usuario = RepositorioUsuarios.crearUsuario("pepe", "1234", TipoUsuario.ESTANDAR);
		transaction.begin();
		entityManager.persist(compra);
		transaction.commit();
		/*usuario.recibirMensaje(new Mensaje(compra, "Mensaje numero 1"));
		usuario.recibirMensaje(new Mensaje(compra, "Mensaje numero 2"));
		usuario.recibirMensaje(new Mensaje(compra, "Mensaje numero 3"));
		usuario.recibirMensaje(new Mensaje(compra, "Mensaje numero 4"));*/ //no deja borrar las compras pendientes porque la tabla mensajes se las guarda de FK
	}
}
