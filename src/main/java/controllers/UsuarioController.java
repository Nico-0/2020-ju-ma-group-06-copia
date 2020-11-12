package controllers;

import dominio.compra.DireccionPostal;
import dominio.compra.Item;
import dominio.compra.Proveedor;
import dominio.presupuestos.CompraPendiente;
import dominio.presupuestos.Detalle;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.math.BigDecimal;

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
		return new ModelAndView(null, "compras_usuario.html");
	}
	
	/*public ModelAndView crear_compra(Request req, Response res){
		EntityManager em = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		
		int cant_presupuestos = new Integer(req.queryParams("descripcion"));
		int detalle = new Integer(req.queryParams("detalle"));
		int proveedor = new Integer(req.queryParams("proveedor"));
		int medioPago = new Integer(req.queryParams("medioPago"));
		//por query param me mandaron los id, necesito ir a buscarlos todos a la base?
		
		
		CompraPendiente compra = new CompraPendiente();
		
		
		return new ModelAndView(null, "compras_usuario.html");
	}*/
	
	public ModelAndView bandejaDeEntrada(Request req, Response res){
		return new ModelAndView(null, "bandeja_entrada.html");
	}
	
	public ModelAndView crear(Request req, Response res){
		return new ModelAndView(null, "crear_compra.html");
	}
	
	public Void creacion(Request req, Response res){	//los queryparam salen del campo name?
		EntityManager em = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		
		if(req.queryParams("detalle") != null) {

			Item itemNuevo = new Item(req.queryParams("desc_item"), new Float(req.queryParams("val_item")), new Integer(req.queryParams("cant_item")));
			Detalle detalle = em.find(Detalle.class, new Long(req.queryParams("detalle")));
			if(detalle == null) {
				detalle = new Detalle(/*new Long(req.queryParams("detalle"))*/);
				transaction.begin();
				em.persist(detalle);
				transaction.commit();
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
			if(proveedor != null) {
				DireccionPostal direccionPostal = new DireccionPostal();
				direccionPostal.setDireccion(req.queryParams("direccion"));
				direccionPostal.setterCiudad(req.queryParams("ciudad"));
				direccionPostal.setterProvincia(req.queryParams("provincia"));
				direccionPostal.setterPais(req.queryParams("pais"));
	
				transaction.begin();
				em.persist(direccionPostal);
				transaction.commit();
			}
			else {
				res.redirect("/errordp");
			}
		}		
		
		
		res.redirect("/usuario/crear");
		return null;		
	}

	

	
}
