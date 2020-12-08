package controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.compra.Compra;
import dominio.compra.DocumentoComercial;
import dominio.compra.MedioPago;
import dominio.compra.Proveedor;
import dominio.compra.TipoPago;
import dominio.entidad.Entidad;
import dominio.presupuestos.CompraPendiente;
import dominio.presupuestos.Detalle;
import dominio.presupuestos.Presupuesto;
import dominio.usuario.Usuario;
import repositorios.RepositorioCategorias;
import repositorios.RepositorioComprasPendientes;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class MenuComprasPendientesController {

	public ModelAndView show(Request req, Response res){
		String nombreUsuario = req.cookie("usuario_logueado");
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		Usuario usuario = (Usuario) entityManager
				.createQuery("from Usuario where nombre = :nombre")
				.setParameter("nombre", nombreUsuario)
				.getSingleResult();
		RepositorioComprasPendientes.getInstance().setUsuarioLogueado(usuario);
		return new ModelAndView(RepositorioComprasPendientes.getInstance(), "menuComprasPendientes.hbs");
	}
	
	
	
	public ModelAndView suscribirUsuario(Request req, Response res){
		Long idCompraPendiente = new Long(req.params("id_compra_pendiente"));
		Long idUsuario = new Long(req.params("id_usuario"));
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		Usuario usuario = (Usuario) entityManager
				.createQuery("from Usuario where id = :id")
				.setParameter("id", idUsuario)
				.getSingleResult();
		CompraPendiente compraPendiente = (CompraPendiente) entityManager
				.createQuery("from CompraPendiente where id = :id")
				.setParameter("id", idCompraPendiente)
				.getSingleResult();
		transaction.begin();
		compraPendiente.agregarUsuarioRevisor(usuario);	
		transaction.commit();
		res.redirect("/compras_pendientes");
		return null;
	}
	
	public ModelAndView desuscribirUsuario(Request req, Response res){
		Long idCompraPendiente = new Long(req.params("id_compra_pendiente"));
		Long idUsuario = new Long(req.params("id_usuario"));
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		Usuario usuario = (Usuario) entityManager
				.createQuery("from Usuario where id = :id")
				.setParameter("id", idUsuario)
				.getSingleResult();
		CompraPendiente compraPendiente = (CompraPendiente) entityManager
				.createQuery("from CompraPendiente where id = :id")
				.setParameter("id", idCompraPendiente)
				.getSingleResult();
		transaction.begin();
		compraPendiente.quitarUsuarioRevisor(usuario);
		transaction.commit();
		res.redirect("/compras_pendientes");
		return null;
	}
	
	public Void crearCompra(Request req, Response res){		
		MedioPago medioPago = new MedioPago(TipoPago.EFECTIVO,"");
		CompraPendiente compraPendiente = new CompraPendiente();
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(medioPago);
		entityManager.persist(compraPendiente);
		compraPendiente.setMedioPago(medioPago);
		transaction.commit();
		res.redirect("/compras_pendientes/" + compraPendiente.getId());
		return null;
		
		/*
		EntityManager em = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		
		int cant_presupuestos = new Integer(req.queryParams("cant_presup"));
		Long detalle_id = new Long(req.queryParams("detalle"));
		Long proveedor_id = new Long(req.queryParams("proveedor"));
		Long medioPago_id = new Long(req.queryParams("medio_pago"));
		Long entidad_id = new Long(req.queryParams("entidad"));
		Long criterio_pago_id = new Long(req.queryParams("criterio"));
		
		Detalle detalle = em.find(Detalle.class, detalle_id);
		if(detalle == null) {
			res.redirect("/compras/error/errordetalle");
			return null;
		}
		Proveedor proveedor = em.find(Proveedor.class, proveedor_id);
		if(proveedor == null) {
			res.redirect("/compras/error/errorproveedor");
			return null;
		}
		MedioPago medio_pago = em.find(MedioPago.class, medioPago_id);
		if(medio_pago == null) {
			res.redirect("/compras/error/errormediopago");
			return null;
		}
		Entidad entidad = em.find(Entidad.class, entidad_id);
		if(entidad == null) {
			res.redirect("/compras/error/errorentidad");
			return null;
		}
		
		CompraPendiente compra = new CompraPendiente();
		
		compra.setFecha(LocalDate.now());
		compra.setCantidadPresupuestosRequeridos(cant_presupuestos);
		compra.setProveedor(proveedor);
		compra.setMedioPago(medio_pago);
		compra.setDetalle(detalle);
		compra.setCriterioSeleccion(criterio_pago_id);
		compra.setEntidad(entidad);
		
		transaction.begin();
		em.persist(compra);
		transaction.commit();
		
		res.redirect("/compras_pendientes");
		return null;	*/
	}
	
	public ModelAndView validar_compras(Request req, Response res){	
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		List<CompraPendiente> comprasPendientes = entityManager.createQuery("from CompraPendiente").getResultList();    	
		transaction.begin();
		comprasPendientes.stream().forEach(compraPendiente -> compraPendiente.validarCompra());
		transaction.commit();
		//RepositorioComprasPendientes.getInstance().validarCompras();
		res.redirect("/compras_pendientes");
		return null;
	}
}
