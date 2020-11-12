package server;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.usuario.TipoUsuario;
import dominio.usuario.Usuario;

public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps{

	public static void main(String[] args) throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
		new Bootstrap().init();
	}
	
	public static void init() throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException{
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		Usuario usuario = new Usuario("pepe","1234",TipoUsuario.ESTANDAR);
		transaction.begin();
		entityManager.persist(usuario.bandejaDeEntrada);
		entityManager.persist(usuario);	
		transaction.commit();
	}
}
