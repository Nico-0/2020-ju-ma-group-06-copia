package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;

public class Entidad {

	public ModelAndView login(Request req, Response res){
		return new ModelAndView(null, "entidades.hbs");
	}
	
	public ModelAndView entidadBase(Request req, Response res){
		return new ModelAndView(null, "entidad_base.hbs");
	}
	
	public ModelAndView entidadJuridica(Request req, Response res){
		return new ModelAndView(null, "entidad_juridica.hbs");
	}
}