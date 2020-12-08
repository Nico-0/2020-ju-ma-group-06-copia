package controllers;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;


import dominio.compra.Item;
import dominio.presupuestos.CompraPendiente;
import repositorios.RepositorioComprasPendientes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class EditarItemDeCompraPendiente {

	public ModelAndView show(Request req, Response res){
		HashMap<String, Object> model = new HashMap<String, Object>();
		Long idCompra = new Long(req.params("id_compra_pendiente"));
		Long idItem = new Long(req.params("id_item"));
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(idCompra);
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		Item item = (Item) entityManager
				.createQuery("from Item where id = :id")
				.setParameter("id", idItem)
				.getSingleResult();
		model.put("compra",compraPendiente);
		model.put("item",item);
		return new ModelAndView(model, "editarItemDeCompraPendiente.hbs");
	}

	public ModelAndView editarItem(Request req, Response res){
		Long idCompra = new Long(req.params("id_compra_pendiente"));
		Long idItem = new Long(req.params("id_item"));
		String descripcion = req.queryParams("descripcion");
		int cantidad = Integer. parseInt(req.queryParams("cantidad"));
		double valorUnitario = Double.parseDouble(req.queryParams("valor_unitario"));
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(idCompra);
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		Item item = (Item) entityManager
				.createQuery("from Item where id = :id")
				.setParameter("id", idItem)
				.getSingleResult();	
		transaction.begin();
		item.setDescripcion(descripcion);
		item.setCantidad(cantidad);
		item.setValorUnitario(valorUnitario);		
		transaction.commit();
		res.redirect(compraPendiente.getUrlView());
		return null;
	}
	
	public ModelAndView crearItem(Request req, Response res){
		Long id = new Long(req.params("id_compra_pendiente"));
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(id);
		Item item = new Item();
		
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(item);
		compraPendiente.agregarItem(item);
		transaction.commit();
		
		
		res.redirect(compraPendiente.getUrlView() + "/items/" + item.getId() + "/editar");
		return null;
	}
	
	public ModelAndView quitarItem(Request req, Response res){
		Long id = new Long(req.params("id_compra_pendiente"));
		Long idItem = new Long(req.params("id_item"));
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(id);
		
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		Item item = (Item) entityManager
				.createQuery("from Item where id = :id")
				.setParameter("id", idItem)
				.getSingleResult();
		
		transaction.begin();
		compraPendiente.quitarItem(item);
		entityManager.remove(item);
		transaction.commit();
		
		res.redirect(compraPendiente.getUrlView());
		return null;
	}
}
