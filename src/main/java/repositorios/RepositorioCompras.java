package repositorios;

import java.util.List;

import javax.persistence.EntityManager;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.compra.Compra;
import dominio.usuario.Usuario;


public class RepositorioCompras implements WithGlobalEntityManager{
	private static RepositorioCompras INSTANCE = null;
	Usuario usuarioLogueado;
	
	public static RepositorioCompras getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new RepositorioCompras();
		}
		return INSTANCE;
	}
	
	public List<Compra> getCompras() {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		return entityManager.createQuery("from Compra").getResultList();
	}

	public Compra getCompra(Long id) {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		return (Compra) entityManager
				.createQuery("from Compra where id = :id")
				.setParameter("id", id)
				.getSingleResult();
	}
}
