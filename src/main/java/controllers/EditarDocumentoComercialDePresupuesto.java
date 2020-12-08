package controllers;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.compra.Item;
import dominio.compra.TipoDocumentoComercial;
import dominio.presupuestos.CompraPendiente;
import dominio.presupuestos.Presupuesto;
import repositorios.RepositorioComprasPendientes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class EditarDocumentoComercialDePresupuesto {
	
	public ModelAndView show(Request req, Response res){
		HashMap<String, Object> model = new HashMap<String, Object>();	
		Long idCompraPendiente = new Long(req.params("id_compra_pendiente"));
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(idCompraPendiente);
		Long idPresupuesto = new Long(req.params("id_presupuesto"));
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		Presupuesto presupuesto = getPresupuesto(idPresupuesto, entityManager);
		model.put("presupuesto",presupuesto);
		model.put("compra",compraPendiente);
		return new ModelAndView(model, "editarDocumentoComercialDePresupuesto.hbs");
	}

	public ModelAndView editar(Request req, Response res){
		String stringDocumentoComercial = req.queryParams("numero_documento_comercial");
		int numeroDocumentoComercial;
		if(stringDocumentoComercial.equals(""))
			numeroDocumentoComercial = 0;
		else
			numeroDocumentoComercial = Integer. parseInt(req.queryParams("numero_documento_comercial"));
		TipoDocumentoComercial tipoDocumentoComercial = toTipoDocumentoComercial(req.queryParams("tipo_documento_comercial"));
		Long idCompraPendiente = new Long(req.params("id_compra_pendiente"));
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(idCompraPendiente);
		Long idPresupuesto = new Long(req.params("id_presupuesto"));
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		Presupuesto presupuesto = getPresupuesto(idPresupuesto, entityManager);
					
		transaction.begin();
		presupuesto.editarDocumentoComercial(numeroDocumentoComercial, tipoDocumentoComercial);
		transaction.commit();
		res.redirect(compraPendiente.getUrlView() + "/presupuestos/" + presupuesto.getId());
		return null;
	}

	private TipoDocumentoComercial toTipoDocumentoComercial(String s) {
		if(s.equals("factura"))
			return TipoDocumentoComercial.FACTURA;
		if(s.equals("ticket"))
			return TipoDocumentoComercial.TICKET;
		return TipoDocumentoComercial.SIN_DOCUMENTO;
	}
	
	private Presupuesto getPresupuesto(Long idPresupuesto, EntityManager entityManager) {
		return (Presupuesto) entityManager
				.createQuery("from Presupuesto where id = :id")
				.setParameter("id", idPresupuesto)
				.getSingleResult();
	}
	
}
