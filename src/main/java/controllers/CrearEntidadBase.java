package controllers;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.entidad.EntidadBase;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class CrearEntidadBase {
	
	public ModelAndView show(Request req, Response res){
		return new ModelAndView(null, "crearEntidadBase.hbs");
	}
	
	public ModelAndView crear(Request req, Response res){
		String nombre = req.queryParams("nombre");
		String descripcion = req.queryParams("descripcion");
		
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		EntidadBase entidadBase = new EntidadBase(nombre, descripcion);
		transaction.begin();
		entityManager.persist(entidadBase);
		transaction.commit();
		
		res.redirect("/entidades");
		return null;
	}
}
