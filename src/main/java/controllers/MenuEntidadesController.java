package controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.entidad.Entidad;
import dominio.entidad.Reporte;
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
		Entidad entidad = RepositorioEntidades.getInstance().getEntidad(id);
		
		if(entidad.tieneCompras()) {
			res.redirect("/entidades#popupTieneCompras");
			return null;
		}
		
		if(entidad.perteneceAEntidadJuridica()) {
			res.redirect("/entidades#popupPerteneceAEntidadJuridica");
			return null;
		}
		
		if(entidad.tieneComprasPendientes()) {
			res.redirect("/entidades#popupTieneComprasPendientes");
			return null;
		}
		
		if(entidad.tieneEntidadesBase()) {
			res.redirect("/entidades#popupTieneEntidadesBase");
			return null;
		}
		
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(entidad);
		transaction.commit();
		res.redirect("/entidades");
		return null;
	}
	
	public ModelAndView generarReportes(Request req, Response res){
		List<Entidad> entidades = RepositorioEntidades.getInstance().getEntidades();
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		entidades.stream().forEach((entidad) -> {
			Reporte reporte = entidad.generarReporte();
			//EntityTransaction transaction = entityManager.getTransaction();
			//transaction.begin();
			entityManager.persist(reporte);
			entidad.agregarReporte(reporte);
			//transaction.commit();
		});
		transaction.commit();
		
		res.redirect("/entidades");
		return null;
	}
}