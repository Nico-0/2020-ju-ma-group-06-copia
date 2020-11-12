package controllers;

import dominio.compra.DireccionPostal;
import dominio.compra.Item;
import dominio.compra.MedioPago;
import dominio.compra.Proveedor;
import dominio.presupuestos.CompraPendiente;
import dominio.presupuestos.Detalle;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;



public class UsuarioController implements WithGlobalEntityManager{

	public ModelAndView menuUsuario(Request req, Response res){
		return new ModelAndView(null, "menu_usuario.html");
	}
	
	public ModelAndView compras(Request req, Response res){
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		Map<String, List<CompraPendiente>> model = new HashMap<>();		
		List<CompraPendiente> compras = entityManager
				.createQuery("from CompraPendiente")
				.getResultList();
		model.put("compras", compras);
		return new ModelAndView(model, "compras_usuario.html");
	}
	
	public Void crear_compra(Request req, Response res){
		EntityManager em = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		
		int cant_presupuestos = new Integer(req.queryParams("cant_presup"));
		Long detalle_id = new Long(req.queryParams("detalle"));
		Long proveedor_id = new Long(req.queryParams("proveedor"));
		Long medioPago_id = new Long(req.queryParams("medio_pago"));
		//TODO como tomar el valor de criterioDeSeleccion cuando no es un campo de texto?
		
		Detalle detalle = em.find(Detalle.class, detalle_id);
		if(detalle == null) {
			res.redirect("/usuario/compras/errordetalle");
			return null;
		}
		Proveedor proveedor = em.find(Proveedor.class, proveedor_id);
		if(proveedor == null) {
			res.redirect("/usuario/compras/errorproveedor");
			return null;
		}
		MedioPago medio_pago = em.find(MedioPago.class, medioPago_id);
		//TODO crear medios de pago y verificar aca que existan
		
		CompraPendiente compra = new CompraPendiente();
		
		compra.setFecha(LocalDate.now());
		compra.setCantidadPresupuestosRequeridos(cant_presupuestos);
		compra.setProveedor(proveedor);
		compra.setMedioPago(medio_pago);
		compra.setDetalle(detalle);
		
		transaction.begin();
		em.persist(compra);
		transaction.commit();
		
		res.redirect("/usuario/compras");
		return null;	
	}
	
	public ModelAndView bandejaDeEntrada(Request req, Response res){
		return new ModelAndView(null, "bandeja_entrada.html");
	}
	
	public ModelAndView crear(Request req, Response res){
		return new ModelAndView(null, "crear_compra.html");
	}
	
	public Void creacion(Request req, Response res){	//los queryparam salen del campo name
		EntityManager em = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = em.getTransaction();

		if(req.queryParams("detalle") != null) {

			Item itemNuevo = new Item(req.queryParams("desc_item"), new Float(req.queryParams("val_item")), new Integer(req.queryParams("cant_item")));
			Detalle detalle = em.find(Detalle.class, new Long(req.queryParams("detalle")));
			if(detalle == null) {
				res.redirect("/usuario/crear/erroritem");
				return null;
			}
	
			transaction.begin();
			em.persist(itemNuevo);
			detalle.agregarItem(itemNuevo);
			transaction.commit();
						
		}

		if(req.queryParams("moneda") != null) {

			Detalle detalle = new Detalle();
			detalle.setMoneda(req.queryParams("moneda"));

			transaction.begin();
			em.persist(detalle);
			transaction.commit();
			
		}
		
		if(req.queryParams("razon_social") != null) {

			Proveedor proveedor = new Proveedor();
			proveedor.setRazon_social(req.queryParams("razon_social"));
			proveedor.setDni_cuil_cuit(req.queryParams("dni"));

			transaction.begin();
			em.persist(proveedor);
			transaction.commit();
			
		}
		
		if(req.queryParams("id_proveedor") != null) {

			Proveedor proveedor = em.find(Proveedor.class, new Long(req.queryParams("id_proveedor")));
			if(proveedor == null) {
				res.redirect("/usuario/crear/errordp");
				return null;
			}
			DireccionPostal direccionPostal = new DireccionPostal();
			direccionPostal.setDireccion(req.queryParams("direccion"));
			direccionPostal.setterCiudad(req.queryParams("ciudad"));
			direccionPostal.setterProvincia(req.queryParams("provincia"));
			direccionPostal.setterPais(req.queryParams("pais"));

			transaction.begin();
			em.persist(direccionPostal);
			transaction.commit();
		}		
		
		
		res.redirect("/usuario/crear");
		return null;		
	}

	

	
}
