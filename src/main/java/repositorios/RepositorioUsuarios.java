package repositorios;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.usuario.TipoUsuario;
import dominio.usuario.Usuario;

public class RepositorioUsuarios implements WithGlobalEntityManager {

	private static RepositorioUsuarios instance = null;
	
	public static RepositorioUsuarios getInstance(){
		if (instance == null) {
			instance = new RepositorioUsuarios();
		}
		return instance;
	}
	
	public Usuario getUsuario(String nombre) {
		Usuario usuario;
		List<Usuario> listaUsuarios = entityManager()
				.createQuery("from Usuario where nombre = :nombre")
				.setParameter("nombre", nombre)
				.getResultList();
		if(!listaUsuarios.isEmpty()) {
			usuario = listaUsuarios.get(0);
			return usuario;
		}		
		return null;
	}

	public Usuario crearUsuario(String nombre, String contrasenia, TipoUsuario tipoUsuario) throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
		EntityTransaction transaction = entityManager().getTransaction();
		Usuario usuario = new Usuario(nombre,contrasenia,tipoUsuario);
		transaction.begin();
		entityManager().persist(usuario.bandejaDeEntrada);
		entityManager().persist(usuario);	
		transaction.commit();
		return usuario;
	}
}
