package repositorios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.compra.Item;
import dominio.entidad.Entidad;
import dominio.entidad.EntidadBase;
import dominio.entidad.EntidadJuridica;
import dominio.usuario.Usuario;

public class RepositorioEntidades {
	

	public static void crearEntidadBase(String nombre, String descripcion) {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntidadBase entidadBase = new EntidadBase(nombre, descripcion);
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(entidadBase);
		transaction.commit();
	}
	
	public static void crearEntidadJuridica(String razonSocial, String nombre, String cuit, String direccionPostal) {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		List<EntidadBase> entidadesBase = new ArrayList<EntidadBase>();
		//EntidadJuridica entidadJuridica = new EntidadJuridica(razonSocial, nombre, cuit, direccionPostal, entidadesBase);
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		//entityManager.persist(entidadJuridica);
		transaction.commit();
	}
	
	public List<Entidad> getEntidades() {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		Usuario usuario;
		List<Entidad> listaEntidades = entityManager
				.createQuery("from Entidad")
				.getResultList();
		return listaEntidades;
	}
}
