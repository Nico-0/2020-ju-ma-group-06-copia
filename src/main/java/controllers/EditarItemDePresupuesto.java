package controllers;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.compra.Item;
import dominio.presupuestos.CompraPendiente;
import dominio.presupuestos.Presupuesto;
import repositorios.RepositorioComprasPendientes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class EditarItemDePresupuesto {
	public ModelAndView show(Request req, Response res){
		HashMap<String, Object> model = new HashMap<String, Object>();
		Long idPresupuesto = new Long(req.params("id_presupuesto"));
		Long idItem = new Long(req.params("id_item"));
		Long idCompra = new Long(req.params("id_compra_pendiente"));
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(idCompra);
		
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		Presupuesto presupuesto = getPresupuesto(idPresupuesto, entityManager);
		Item item = getItem(idItem, entityManager);
		model.put("presupuesto",presupuesto);
		model.put("item",item);
		model.put("compra", compraPendiente);
		return new ModelAndView(model, "editarItemDePresupuesto.hbs");
	}

	public ModelAndView editarItem(Request req, Response res){
		Long idPresupuesto = new Long(req.params("id_presupuesto"));
		Long idItem = new Long(req.params("id_item"));
		Long idCompra = new Long(req.params("id_compra_pendiente"));
		String descripcion = req.queryParams("descripcion");
		int cantidad = Integer. parseInt(req.queryParams("cantidad"));
		double valorUnitario = Double.parseDouble(req.queryParams("valor_unitario"));
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(idCompra);
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		Presupuesto presupuesto = getPresupuesto(idPresupuesto, entityManager);
		Item item = getItem(idItem, entityManager);
		transaction.begin();
		item.setDescripcion(descripcion);
		item.setCantidad(cantidad);
		item.setValorUnitario(valorUnitario);		
		transaction.commit();
		res.redirect(compraPendiente.getUrlView() + "/presupuestos/" + presupuesto.getId());
		return null;
	}
	
	public ModelAndView crearItem(Request req, Response res){
		Long idPresupuesto = new Long(req.params("id_presupuesto"));
		Long idCompra = new Long(req.params("id_compra_pendiente"));
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(idCompra);
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		Presupuesto presupuesto = getPresupuesto(idPresupuesto, entityManager);
		Item item = new Item();
		transaction.begin();
		entityManager.persist(item);
		presupuesto.agregarItem(item);
		transaction.commit();
			
		res.redirect(compraPendiente.getUrlView() + "/presupuestos/" + presupuesto.getId() + "/items/" + item.getId() + "/editar");
		return null;
	}
	
	public ModelAndView quitarItem(Request req, Response res){
		
		Long idCompra = new Long(req.params("id_compra_pendiente"));
		Long idPresupuesto = new Long(req.params("id_presupuesto"));
		Long idItem = new Long(req.params("id_item"));
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(idCompra);		
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		Presupuesto presupuesto = getPresupuesto(idPresupuesto, entityManager);
		Item item = getItem(idItem, entityManager);
		transaction.begin();
		presupuesto.quitarItem(item);
		entityManager.remove(item);
		transaction.commit();
		
		res.redirect(compraPendiente.getUrlView() + "/presupuestos/" + presupuesto.getId());
		return null;
	}
	
	private Presupuesto getPresupuesto(Long idPresupuesto, EntityManager entityManager) {
		return (Presupuesto) entityManager
				.createQuery("from Presupuesto where id = :id")
				.setParameter("id", idPresupuesto)
				.getSingleResult();
	}
	
	private Item getItem(Long idItem, EntityManager entityManager) {
		return (Item) entityManager
				.createQuery("from Item where id = :id")
				.setParameter("id", idItem)
				.getSingleResult();
	}
}
