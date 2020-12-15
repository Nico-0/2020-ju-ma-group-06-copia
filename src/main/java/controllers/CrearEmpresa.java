package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.entidad.Empresa;
import dominio.entidad.EntidadBase;
import dominio.entidad.TipoEmpresa;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class CrearEmpresa {
	public ModelAndView show(Request req, Response res){
		return new ModelAndView(null, "crearEmpresa.hbs");
	}
	
	public ModelAndView crear(Request req, Response res){
		String nombre = req.queryParams("nombre");
		String razonSocial = req.queryParams("razon_social");
		String direccionPostal= req.queryParams("direccion_postal");
		String cuit = req.queryParams("cuit");
		TipoEmpresa tipoEmpresa = toTipoEmpresa(req.queryParams("tipo_empresa"));
				
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		List<EntidadBase> entidadesBase = new ArrayList<EntidadBase>();
		Empresa empresa = new Empresa(razonSocial, nombre, cuit, direccionPostal, tipoEmpresa, entidadesBase);
		transaction.begin();
		entityManager.persist(empresa);
		transaction.commit();
		
		res.redirect("/entidades");
		return null;
	}
	
	private TipoEmpresa toTipoEmpresa(String string) {
		if(string.equals("micro"))		return TipoEmpresa.MICRO;
		if(string.equals("pequena"))	return TipoEmpresa.PEQUENA;
		if(string.equals("mediana_1"))	return TipoEmpresa.MEDIANA_1;
		if(string.equals("mediana_2"))	return TipoEmpresa.MEDIANA_2;
		throw new RuntimeException("No existe el tipo de empresa pasado por la request");
	}
}
