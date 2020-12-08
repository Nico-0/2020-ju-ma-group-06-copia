package controllers;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.compra.DocumentoComercial;
import dominio.compra.Item;
import dominio.compra.TipoDocumentoComercial;
import dominio.presupuestos.CompraPendiente;
import dominio.presupuestos.Detalle;
import dominio.presupuestos.Presupuesto;
import repositorios.RepositorioComprasPendientes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class EditarPresupuestoDeCompraPendiente {

	public ModelAndView show(Request req, Response res){
		HashMap<String, Object> model = new HashMap<String, Object>();
		Long idCompra = new Long(req.params("id_compra_pendiente"));
		Long idPresupuesto = new Long(req.params("id_presupuesto"));
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(idCompra);
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		Presupuesto presupuesto = (Presupuesto) entityManager
				.createQuery("from Presupuesto where id = :id")
				.setParameter("id", idPresupuesto)
				.getSingleResult();
		model.put("compra",compraPendiente);
		model.put("presupuesto",presupuesto);
		return new ModelAndView(model, "editarPresupuestoDeCompraPendiente.hbs");
	}

	/*
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
	}*/
	
	public ModelAndView crearPresupuesto(Request req, Response res){
		Long idCompraPendiente = new Long(req.params("id_compra_pendiente"));
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(idCompraPendiente);
		Presupuesto presupuesto = new Presupuesto();
		Detalle detalle = new Detalle();
		DocumentoComercial documentoComercial = new DocumentoComercial();
		documentoComercial.setNumeroDocumento(0);
		documentoComercial.setTipoDocumentoComercial(TipoDocumentoComercial.SIN_DOCUMENTO);
		
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(documentoComercial);
		entityManager.persist(detalle);
		entityManager.persist(presupuesto);
		presupuesto.setDetalle(detalle);
		presupuesto.setDocumentoComercial(documentoComercial);
		presupuesto.setIdCompraPendiente(idCompraPendiente);
		compraPendiente.agregarPresupuesto(presupuesto);
		transaction.commit();
			
		res.redirect(compraPendiente.getUrlView() + "/presupuestos/" + presupuesto.getId());
		return null;
	}
	
	public ModelAndView borrarPresupuesto(Request req, Response res){
		Long idCompra = new Long(req.params("id_compra_pendiente"));
		Long idPresupuesto = new Long(req.params("id_presupuesto"));
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(idCompra);
		
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		Presupuesto presupuesto = (Presupuesto) entityManager
				.createQuery("from Presupuesto where id = :id")
				.setParameter("id", idPresupuesto)
				.getSingleResult();
		
		transaction.begin();
		compraPendiente.quitarPresupuesto(presupuesto);
		presupuesto.setProveedor(null);
		entityManager.remove(presupuesto.getDocumentoComercial());
		presupuesto.getDetalle().getItems().clear();
		entityManager.remove(presupuesto.getDetalle());
		entityManager.remove(presupuesto);
		transaction.commit();
		
		res.redirect(compraPendiente.getUrlView());
		return null;
	}
}
