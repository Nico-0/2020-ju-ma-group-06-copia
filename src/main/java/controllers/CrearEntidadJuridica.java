package controllers;

import repositorios.RepositorioEntidades;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class CrearEntidadJuridica {
	public ModelAndView show(Request req, Response res){
		return new ModelAndView(null, "crearEntidadJuridica.hbs");
	}
	
	public ModelAndView crear(Request req, Response res){
		// Agregar entidad base a la base de datos
		String nombre = req.queryParams("nombre");
		String razonSocial = req.queryParams("razon_social");
		String direccionPostal= req.queryParams("direccion_postal");
		String cuit = req.queryParams("cuit");
		
		RepositorioEntidades.crearEntidadJuridica(razonSocial, nombre, cuit, direccionPostal);;
		res.redirect("/entidades");
		return null;
	}
}
