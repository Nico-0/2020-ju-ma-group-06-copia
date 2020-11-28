package dominio;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.usuario.TipoUsuario;
import dominio.usuario.Usuario;

public class RepositorioUsuarios {

	public static Usuario getUsuario(String nombre) {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		Usuario usuario;
		List<Usuario> listaUsuarios = entityManager
				.createQuery("from Usuario where nombre = :nombre")
				.setParameter("nombre", nombre)
				.getResultList();
		if(!listaUsuarios.isEmpty()) {
			usuario = listaUsuarios.get(0);
			return usuario;
		}		
		return null;
	}

	public static Usuario crearUsuario(String nombre, String contrasenia, TipoUsuario tipoUsuario) throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		Usuario usuario = new Usuario(nombre,contrasenia,tipoUsuario);
		transaction.begin();
		entityManager.persist(usuario.bandejaDeEntrada);
		entityManager.persist(usuario);	
		transaction.commit();
		return usuario;
	}
}
