package repositorios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.compra.Item;
import dominio.entidad.Empresa;
import dominio.entidad.Entidad;
import dominio.entidad.EntidadBase;
import dominio.entidad.EntidadJuridica;
import dominio.entidad.OrganizacionSocial;
import dominio.entidad.TipoEmpresa;
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
	
	public static void crearOrganizacionSocial(String razonSocial, String nombre, String cuit, String direccionPostal) {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		List<EntidadBase> entidadesBase = new ArrayList<EntidadBase>();
		OrganizacionSocial organizacionSocial = new OrganizacionSocial(razonSocial, nombre, cuit, direccionPostal, entidadesBase);
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(organizacionSocial);
		transaction.commit();
	}
	
	public static void crearEmpresa(String razonSocial, String nombre, String cuit, String direccionPostal, TipoEmpresa tipoEmpresa) {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		List<EntidadBase> entidadesBase = new ArrayList<EntidadBase>();
		Empresa empresa = new Empresa(razonSocial, nombre, cuit, direccionPostal, tipoEmpresa, entidadesBase);
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(empresa);
		transaction.commit();
	}
	
	public List<Entidad> getEntidades() {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		List<Entidad> listaEntidades = entityManager
				.createQuery("from Entidad")
				.getResultList();
		return listaEntidades;
	}

	public static void borrarEntidad(Long id) {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		Entidad entidad = getEntidad(id);
		transaction.begin();
		entityManager.remove(entidad);
		transaction.commit();
	}

	private static Entidad getEntidad(Long id) {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		Entidad entidad;
		List<Entidad> listaEntidades = entityManager
				.createQuery("from Entidad where id = :id")
				.setParameter("id", id)
				.getResultList();
		if(!listaEntidades.isEmpty()) {
			entidad = listaEntidades.get(0);
			return entidad;
		}		
		return null;
	}
}
