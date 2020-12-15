package controllers;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.compra.Proveedor;
import dominio.presupuestos.CompraPendiente;
import repositorios.RepositorioComprasPendientes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class SeleccionarProveedorDeCompraPendiente {
	public ModelAndView show(Request req, Response res){
		Long idCompraPendiente = new Long(req.params("id_compra_pendiente"));
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(idCompraPendiente);
		return new ModelAndView(compraPendiente, "seleccionarProveedorDeCompraPendiente.hbs");
	}
	
	public ModelAndView seleccionar(Request req, Response res){
		Long idProveedor = new Long(req.params("id_proveedor"));
		Long idCompraPendiente = new Long(req.params("id_compra_pendiente"));
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		Proveedor proveedor = (Proveedor) entityManager
				.createQuery("from Proveedor where id = :id")
				.setParameter("id", idProveedor)
				.getSingleResult();	
		CompraPendiente compraPendiente = (CompraPendiente) entityManager
				.createQuery("from CompraPendiente where id = :id")
				.setParameter("id", idCompraPendiente)
				.getSingleResult();
		transaction.begin();
		compraPendiente.setProveedor(proveedor);
		transaction.commit();
		res.redirect(compraPendiente.getUrlView() + "/seleccionar_proveedor");
		return null;
	}
}
