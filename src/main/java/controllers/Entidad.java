package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;

public class Entidad {

	public ModelAndView login(Request req, Response res){
		return new ModelAndView(null, "entidades.html");
	}
	
	public ModelAndView entidadBase(Request req, Response res){
		return new ModelAndView(null, "entidad_base.html");
	}
	
	public ModelAndView entidadJuridica(Request req, Response res){
		return new ModelAndView(null, "entidad_juridica.html");
	}
}