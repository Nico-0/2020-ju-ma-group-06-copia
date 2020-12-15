package controllers;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.entidad.Entidad;
import repositorios.RepositorioEntidades;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class MenuEntidadesController {

	public ModelAndView show(Request req, Response res){
		return new ModelAndView(RepositorioEntidades.getInstance(), "entidades.hbs");
	}

	public ModelAndView mostrarEntidadBase(Request req, Response res){
		Entidad entidad = RepositorioEntidades.getInstance().getEntidad(new Long(req.params("id")));
		return new ModelAndView(entidad, "menuEntidadBase.hbs");
	}
	
	public ModelAndView mostrarEmpresa(Request req, Response res){
		Entidad entidad = RepositorioEntidades.getInstance().getEntidad(new Long(req.params("id")));
		return new ModelAndView(entidad, "menuEmpresa.hbs");
	}
	
	public ModelAndView mostrarOrganizacionSocial(Request req, Response res){
		Entidad entidad = RepositorioEntidades.getInstance().getEntidad(new Long(req.params("id")));
		return new ModelAndView(entidad, "menuOrganizacionSocial.hbs");
	}
	
	public ModelAndView borrarEntidad(Request req, Response res) {
		Long id = new Long(req.params("id"));
		
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		Entidad entidad = RepositorioEntidades.getInstance().getEntidad(id);
		transaction.begin();
		entityManager.remove(entidad);
		transaction.commit();
		
		res.redirect("/entidades");
		return null;
	}
}