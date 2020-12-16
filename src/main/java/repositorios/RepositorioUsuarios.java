package repositorios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.entidad.Entidad;
import dominio.usuario.TipoUsuario;
import dominio.usuario.Usuario;

public class RepositorioUsuarios {

	private static RepositorioUsuarios instance = null;
	
	public static RepositorioUsuarios getInstance(){
		if (instance == null) {
			instance = new RepositorioUsuarios();
		}
		return instance;
	}
	
	public Usuario getUsuario(String nombre) {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
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

	/*
	public Usuario crearUsuario(String nombre, String contrasenia, TipoUsuario tipoUsuario) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		Usuario usuario = new Usuario(nombre,contrasenia,tipoUsuario);
		transaction.begin();
		entityManager.persist(usuario.bandejaDeEntrada);
		entityManager.persist(usuario);	
		transaction.commit();
		return usuario;
	}*/

	public Usuario getUsuario(Long idUsuario) {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		return (Usuario) entityManager
				.createQuery("from Usuario where id = :id")
				.setParameter("id", idUsuario)
				.getSingleResult();
	}

	public void verificarNoExisteUsuario(String nombre) {
		Usuario usuario = getUsuario(nombre);
		if(usuario != null)
			throw new UsuarioYaExisteException("El usuario ya existe");
	}
}
