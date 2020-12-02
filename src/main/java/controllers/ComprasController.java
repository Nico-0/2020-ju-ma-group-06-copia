package controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.compra.MedioPago;
import dominio.compra.Proveedor;
import dominio.entidad.Entidad;
import dominio.presupuestos.CompraPendiente;
import dominio.presupuestos.Detalle;
import dominio.presupuestos.RepositorioComprasPendientes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ComprasController {

	public ModelAndView compras(Request req, Response res){
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		Map<String, List<CompraPendiente>> model = new HashMap<>();		
		List<CompraPendiente> compras = entityManager
				.createQuery("from CompraPendiente", CompraPendiente.class)
				.getResultList();
		model.put("compras", compras);
		return new ModelAndView(model, "compras_usuario.hbs");
	}
	
	public ModelAndView menu_compra(Request req, Response res){
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		String idC = req.params("idCompra");
		
		CompraPendiente compra = entityManager
				.createQuery("from CompraPendiente where id = "+idC, CompraPendiente.class)
				.getSingleResult();

		return new ModelAndView(compra, "menu_compra.hbs");
	}
	
	public Void crear_compra(Request req, Response res){
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
		
		res.redirect("/compras");
		return null;	
	}
	
	public Void validar_compras(Request req, Response res){
		EntityManager em = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa se validan compras");
		
		transaction.begin();
		RepositorioComprasPendientes.getInstance().validarCompras();
		transaction.commit();
		
		res.redirect("/compras");
		return null;
	}
	
	public Void borrar_compra(Request req, Response res){	
		EntityManager em = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		String idB = req.params("idBorrado");
		
		transaction.begin();
		em.createQuery("delete from CompraPendiente where id = "+idB).executeUpdate();
		transaction.commit();
		
		res.redirect("/compras");
		return null;
	}
	
}
