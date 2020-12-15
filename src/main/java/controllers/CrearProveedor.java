package controllers;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.compra.DireccionPostal;
import dominio.compra.Proveedor;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class CrearProveedor {
	public ModelAndView show(Request req, Response res){
		return new ModelAndView(null, "crearProveedor.hbs");
	}
	
	public ModelAndView crear(Request req, Response res){
		String razonSocial = req.queryParams("razon_social");
		String dniCuilCuit = req.queryParams("dni_cuil_cuit");
		String pais= req.queryParams("pais");
		String provincia = req.queryParams("provincia");
		String ciudad = req.queryParams("ciudad");
		String direccion = req.queryParams("direccion");
				
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		DireccionPostal direccionPostal = new DireccionPostal(pais, provincia, ciudad, direccion);
		Proveedor proveedor = new Proveedor(razonSocial, dniCuilCuit, direccionPostal);
		final EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(direccionPostal);
		entityManager.persist(proveedor);
		transaction.commit();
		
		res.redirect("/proveedores");
		return null;
	}
}