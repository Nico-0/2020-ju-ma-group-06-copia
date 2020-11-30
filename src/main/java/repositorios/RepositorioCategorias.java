package repositorios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.compra.Item;
import dominio.entidad.Categoria;
import dominio.entidad.CategoriaDefault;
import dominio.entidad.Empresa;
import dominio.entidad.Entidad;
import dominio.entidad.EntidadBase;
import dominio.entidad.EntidadJuridica;
import dominio.entidad.OrganizacionSocial;
import dominio.entidad.TipoEmpresa;
import dominio.usuario.Usuario;

public class RepositorioCategorias {
	
	public List<Categoria> getCategorias() {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		List<Categoria> listaCategorias = entityManager
				.createQuery("from Categoria")
				.getResultList();
		return listaCategorias;
	}

	public static void borrarCategoria(Long id) {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		Categoria categoria = getCategoria(id);
		transaction.begin();
		entityManager.remove(categoria);
		transaction.commit();
	}

	public static void crearCategoriaDefault(String nombre, boolean bloquearNuevasCompras,
			boolean bloquearAgregarEntidadesBase, boolean bloquearFormarParteEntidadJuridica, Long egresosMaximos) {
		CategoriaDefault categoriaDefault = new CategoriaDefault(nombre, bloquearNuevasCompras, 
				bloquearAgregarEntidadesBase, bloquearFormarParteEntidadJuridica, egresosMaximos);
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(categoriaDefault);
		transaction.commit();;
	}
	
	public static void editarCategoriaDefault(Long id, String nombre, boolean bloquearNuevasCompras,
			boolean bloquearAgregarEntidadesBase, boolean bloquearFormarParteEntidadJuridica, Long egresosMaximos) {
		Categoria categoriaDefault = getCategoria(id);
		categoriaDefault.setBloquarAgregarEntidadesBase(bloquearAgregarEntidadesBase);
		categoriaDefault.setBloquearFormarParteEntidadJuridica(bloquearFormarParteEntidadJuridica);
		categoriaDefault.setBloquearNuevosEgresos(bloquearNuevasCompras);
		categoriaDefault.setEgresosMaximos(egresosMaximos);
		categoriaDefault.setNombre(nombre);
	}
	
	public static Categoria getCategoria(Long id) {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		return (Categoria) entityManager
				.createQuery("from Categoria where id = :id")
				.setParameter("id", id)
				.getSingleResult();
	}

	public static List<Entidad> getEntidades(Long id) {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		List<Entidad> listaEntidades = entityManager
				.createQuery("from Entidad")
				.getResultList();
		return listaEntidades;
	}
}
