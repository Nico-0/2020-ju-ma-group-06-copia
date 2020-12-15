package repositorios;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.compra.DireccionPostal;
import dominio.compra.Proveedor;


public class RepositorioProveedores {
	private static RepositorioProveedores instance = null;
	
	public static RepositorioProveedores getInstance(){
		if (instance == null) {
			instance = new RepositorioProveedores();
		}
		return instance;
	}
	
	public List<Proveedor> getProveedores() {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		return entityManager.createQuery("from Proveedor").getResultList();
	}

	/*
	public void borrarProveedor(Long id) {
		Proveedor proveedor = this.getProveedor(id);
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(proveedor);
		transaction.commit();
	}*/
	
	public Proveedor getProveedor(Long id) {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		return (Proveedor) entityManager
				.createQuery("from Proveedor where id = :id")
				.setParameter("id", id)
				.getSingleResult();
	}

	/*
	public void crearProveedor(String razonSocial, String dniCuilCuit, String pais, String provincia, String ciudad,
			String direccion) {
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		DireccionPostal direccionPostal = new DireccionPostal(pais, provincia, ciudad, direccion);
		Proveedor proveedor = new Proveedor(razonSocial, dniCuilCuit, direccionPostal);
		final EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(direccionPostal);
		entityManager.persist(proveedor);
		transaction.commit();
	}*/
}
