package controllers;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.entidad.CategoriaDefault;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class CrearCategoriaDefault {
	public ModelAndView show(Request req, Response res){
		return new ModelAndView(null, "crearCategoriaDefault.hbs");
	}
	
	public ModelAndView crear(Request req, Response res){
		Long egresosMaximos;
		String nombre = req.queryParams("nombre");
		boolean bloquearNuevasCompras = toBoolean(req.queryParams("bloquearNuevasCompras"));
		boolean bloquearAgregarEntidadesBase = toBoolean(req.queryParams("bloquearAgregarEntidadesBase"));
		boolean bloquearFormarParteEntidadJuridica = toBoolean(req.queryParams("bloquearFormarParteEntidadJuridica"));
		if(req.queryParams("egresosMaximos").equals(""))
			egresosMaximos = (long) 0;
		else
			egresosMaximos = Long.parseLong(req.queryParams("egresosMaximos"));
		
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		CategoriaDefault categoriaDefault = new CategoriaDefault(nombre, bloquearNuevasCompras, 
				bloquearAgregarEntidadesBase, bloquearFormarParteEntidadJuridica, egresosMaximos);	
		transaction.begin();
		entityManager.persist(categoriaDefault);
		transaction.commit();
		
		res.redirect("/categorias");
		return null;
	}
	
	public boolean toBoolean(String string) {
		if(string == null)	return false;
		if(string.equals("True"))	return true;
		throw new RuntimeException("El valor enviado no es válido");
	}
}
