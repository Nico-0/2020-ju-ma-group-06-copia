package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.entidad.EntidadBase;
import dominio.entidad.OrganizacionSocial;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class CrearOrganizacionSocial {
	
	public ModelAndView show(Request req, Response res){
		return new ModelAndView(null, "crearOrganizacionSocial.hbs");
	}
	
	public ModelAndView crear(Request req, Response res){
		String nombre = req.queryParams("nombre");
		String razonSocial = req.queryParams("razon_social");
		String direccionPostal= req.queryParams("direccion_postal");
		String cuit = req.queryParams("cuit");
	
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		List<EntidadBase> entidadesBase = new ArrayList<EntidadBase>();
		OrganizacionSocial organizacionSocial = new OrganizacionSocial(razonSocial, nombre, cuit, direccionPostal, entidadesBase);
		transaction.begin();
		entityManager.persist(organizacionSocial);
		transaction.commit();
		
		res.redirect("/entidades");
		return null;
	}
}
