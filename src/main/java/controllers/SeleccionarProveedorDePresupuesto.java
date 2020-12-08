package controllers;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.compra.Proveedor;
import dominio.presupuestos.CompraPendiente;
import dominio.presupuestos.Presupuesto;
import repositorios.RepositorioComprasPendientes;
import repositorios.RepositorioProveedores;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class SeleccionarProveedorDePresupuesto {
	
	public ModelAndView show(Request req, Response res){
		HashMap<String, Object> model = new HashMap<String, Object>();
		Long idPresupuesto = new Long(req.params("id_presupuesto"));
		Long idCompraPendiente = new Long(req.params("id_compra_pendiente"));
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(idCompraPendiente);
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		Presupuesto presupuesto = getPresupuesto(idPresupuesto, entityManager);
		model.put("presupuesto",presupuesto);
		model.put("compra",compraPendiente);
		return new ModelAndView(model, "seleccionarProveedorDePresupuesto.hbs");
	}
	
	public ModelAndView seleccionar(Request req, Response res){
		Long idProveedor = new Long(req.params("id_proveedor"));
		Long idCompraPendiente = new Long(req.params("id_compra_pendiente"));
		Long idPresupuesto = new Long(req.params("id_presupuesto"));
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		Presupuesto presupuesto = getPresupuesto(idPresupuesto, entityManager);
		Proveedor proveedor = RepositorioProveedores.getInstance().getProveedor(idProveedor);
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(idCompraPendiente);
		
		final EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		presupuesto.setProveedor(proveedor);
		transaction.commit();
		
		res.redirect(compraPendiente.getUrlView() + "/presupuestos/" + presupuesto.getId() + "/seleccionar_proveedor");
		return null;
	}
	
	private Presupuesto getPresupuesto(Long idPresupuesto, EntityManager entityManager) {
		return (Presupuesto) entityManager
				.createQuery("from Presupuesto where id = :id")
				.setParameter("id", idPresupuesto)
				.getSingleResult();
	}
}
