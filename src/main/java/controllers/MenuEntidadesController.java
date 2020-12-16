package controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.entidad.Categoria;
import dominio.entidad.Entidad;
import repositorios.RepositorioCategorias;
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
		Entidad entidad = RepositorioEntidades.getInstance().getEntidad(id);
		
		if(entidad.tieneCompras()) {
			res.redirect("/entidades#popupTieneCompras");
			return null;
		}
		
		if(entidad.perteneceAEntidadJuridica()) {
			res.redirect("/entidades#popupPerteneceAEntidadJuridica");
			return null;
		}
		
		final EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(entidad);
		transaction.commit();
		res.redirect("/entidades");
		return null;
	}
}