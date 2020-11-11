package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class Entidad {

	public ModelAndView login(Request req, Response res){
		return new ModelAndView(null, "entidadLogin.hbs");
	}
	
}