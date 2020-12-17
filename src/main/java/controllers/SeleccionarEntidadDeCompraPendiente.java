package controllers;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.entidad.Entidad;
import dominio.presupuestos.CompraPendiente;
import repositorios.RepositorioComprasPendientes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class SeleccionarEntidadDeCompraPendiente {
	public ModelAndView show(Request req, Response res){
		Long idCompraPendiente = new Long(req.params("id_compra_pendiente"));
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(idCompraPendiente);
		return new ModelAndView(compraPendiente, "seleccionarEntidadDeCompraPendiente.hbs");
	}
	
	public ModelAndView seleccionar(Request req, Response res){
		Long idEntidad = new Long(req.params("id_entidad"));
		Long idCompraPendiente = new Long(req.params("id_compra_pendiente"));
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		Entidad entidad = (Entidad) entityManager
				.createQuery("from Entidad where id = :id")
				.setParameter("id", idEntidad)
				.getSingleResult();
		CompraPendiente compraPendiente = (CompraPendiente) entityManager
				.createQuery("from CompraPendiente where id = :id")
				.setParameter("id", idCompraPendiente)
				.getSingleResult();
		
		transaction.begin();
		compraPendiente.setEntidad(entidad);
		transaction.commit();
		
		res.redirect(compraPendiente.getUrlView() + "/seleccionar_entidad");
		return null;
	}
	
	public ModelAndView quitar(Request req, Response res){
		Long idCompraPendiente = new Long(req.params("id_compra_pendiente"));
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		CompraPendiente compraPendiente = (CompraPendiente) entityManager
				.createQuery("from CompraPendiente where id = :id")
				.setParameter("id", idCompraPendiente)
				.getSingleResult();
		transaction.begin();
		compraPendiente.setEntidad(null);
		transaction.commit();
		res.redirect(compraPendiente.getUrlView());
		return null;
	}
	
}
