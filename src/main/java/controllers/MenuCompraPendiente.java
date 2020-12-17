package controllers;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.presupuestos.CompraPendiente;
import repositorios.RepositorioComprasPendientes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class MenuCompraPendiente {
	
	/*
	public ModelAndView show(Request req, Response res){
		return new ModelAndView(null, "editarCompraPendiente.hbs");
	}
	
	public ModelAndView editar(Request req, Response res){
		/*
		String nombre = req.queryParams("nombre");
		String razonSocial = req.queryParams("razon_social");
		String direccionPostal= req.queryParams("direccion_postal");
		String cuit = req.queryParams("cuit");
		TipoEmpresa tipoEmpresa = toTipoEmpresa(req.queryParams("tipo_empresa"));
		
		RepositorioEntidades.getInstance().crearEmpresa(razonSocial, nombre, cuit, direccionPostal, tipoEmpresa);

		res.redirect("/compras_pendientes");
		return null;
	}*/

	public ModelAndView borrarCompra(Request req, Response res){	
		Long idCompraPendiente = new Long(req.params("idBorrado"));		
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(idCompraPendiente);
		
		if(compraPendiente.tienePresupuestos()) {
			res.redirect("/compras_pendientes#popupTienePresupuestos");
			return null;
		}
		
		if(compraPendiente.tieneUsuariosRevisores()) {
			res.redirect("/compras_pendientes#popupTieneUsuariosRevisores");
			return null;
		}
		
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(compraPendiente);
		transaction.commit();
		
		res.redirect("/compras_pendientes");
		return null;
	}
	
	public ModelAndView menuCompra(Request req, Response res){
		Long idCompraPendiente = new Long(req.params("idCompra"));
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance()
												.getCompraPendiente(idCompraPendiente);
		return new ModelAndView(compraPendiente, "menuCompraPendiente.hbs");
	}

	/*
	public ModelAndView editarPresupuesto(Request req, Response res){
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		String idC = req.params("idCompra");
		
		CompraPendiente compra = entityManager
				.createQuery("from CompraPendiente where id = "+idC, CompraPendiente.class)
				.getSingleResult();

		return new ModelAndView(compra, "presupuestos_compra.hbs");
	}
	
	public Void agregarPresupuesto(Request req, Response res){
		EntityManager em = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		
		String compra_id_string = req.queryParams("id_compra");
		Long compra_id = new Long(compra_id_string);
		Long proveedor_id = new Long(req.queryParams("proveedor"));
		Long detalle_id = new Long(req.queryParams("detalle"));
		Long documento_id = new Long(req.queryParams("documento_comerical"));
		
		CompraPendiente compra = em.find(CompraPendiente.class, compra_id);
		Presupuesto presupuesto = new Presupuesto();
		
		Proveedor proveedor = em.find(Proveedor.class, proveedor_id);
		if(proveedor == null) {
			res.redirect("/presupuestos/error/errorproveedor");
			return null;
		}
		Detalle detalle = em.find(Detalle.class, detalle_id);
		if(detalle == null) {
			res.redirect("/presupuestos/error/errordetalle");
			return null;
		}
		DocumentoComercial docComercial = em.find(DocumentoComercial.class, documento_id);
		if(docComercial == null) {
			res.redirect("/presupuestos/error/errordocumento");
			return null;
		}
		
		presupuesto.setProveedor(proveedor);
		presupuesto.setDetalle(detalle);
		presupuesto.setDocumentoComercial(docComercial);
		compra.agregarPresupuesto(presupuesto);
		
		transaction.begin();
		em.persist(presupuesto);
		em.persist(compra);
		transaction.commit();
		
		res.redirect("/compras/"+compra_id_string+"/presupuestos");
		return null;
	}
	
	public Void borrarPresupuesto(Request req, Response res){	
		EntityManager em = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		Long idCompra = new Long(req.params("idCompra"));
		Long idBorrado = new Long(req.params("idPresup"));
		
		Presupuesto presupuesto = em.find(Presupuesto.class, idBorrado);
		CompraPendiente compra = em.find(CompraPendiente.class, idCompra);
		
		transaction.begin();
		em.remove(presupuesto);
		compra.getPresupuestos().removeIf(presup -> presup.getId() == idBorrado);
		transaction.commit();
		
		res.redirect("/compras/"+idCompra+"/presupuestos");
		return null;
	}
	

	public ModelAndView updateCompra(Request req, Response res){
		EntityManager em = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		String idCompra = req.params("idCompra");
		
		CompraPendiente compra = em
				.createQuery("from CompraPendiente where id = "+idCompra, CompraPendiente.class)
				.getSingleResult();
		
		String detalle_id_string = req.queryParams("detalle");
		String proveedor_id_string = req.queryParams("proveedor");
		String mediopago_id_string = req.queryParams("medio_pago");
		String entidad_id_string = req.queryParams("entidad");
		String cant_presupuestos_string = req.queryParams("cant_presup");
		String criterio_pago_id_string = req.queryParams("criterio");
		Long criterio_pago_id;
		if(req.queryParams("criterio").equals("ninguno")) {
			criterio_pago_id_string = null;
			criterio_pago_id = new Long(-1);
			}
		else {
			criterio_pago_id = new Long(criterio_pago_id_string);}
		int cant_presupuestos = new Integer(cant_presupuestos_string);
		Long detalle_id = new Long(detalle_id_string);
		Long proveedor_id = new Long(proveedor_id_string);
		Long medioPago_id = new Long(mediopago_id_string);
		Long entidad_id = new Long(entidad_id_string);
		
		
		if(detalle_id_string != null) {
			Detalle detalle = em.find(Detalle.class, detalle_id);
			if(detalle == null) {
				res.redirect("/compras/error/errordetalle");
				return null;
			}
			compra.setDetalle(detalle);
		}

		if(proveedor_id_string != null) {
			Proveedor proveedor = em.find(Proveedor.class, proveedor_id);
			if(proveedor == null) {
				res.redirect("/compras/error/errorproveedor");
				return null;
			}
			compra.setProveedor(proveedor);
		}

		if(mediopago_id_string != null) {
			MedioPago medio_pago = em.find(MedioPago.class, medioPago_id);
			if(medio_pago == null) {
				res.redirect("/compras/error/errormediopago");
				return null;
			}
			compra.setMedioPago(medio_pago);
		}

		if(entidad_id_string != null) {
			Entidad entidad = em.find(Entidad.class, entidad_id);
			if(entidad == null) {
				res.redirect("/compras/error/errorentidad");
				return null;
			}
			compra.setEntidad(entidad);
		}
		
		if(cant_presupuestos_string != null) {
			compra.setCantidadPresupuestosRequeridos(cant_presupuestos);
		}
		
		if(criterio_pago_id_string != null) {
			compra.setCriterioSeleccion(criterio_pago_id);
		}
		
		transaction.begin();
		em.persist(compra);
		transaction.commit();

		res.redirect("/compras_pendientes/"+idCompra);
		return null;
	}*/
}
