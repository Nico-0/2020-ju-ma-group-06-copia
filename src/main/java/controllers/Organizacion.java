package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class Organizacion {

	public ModelAndView login(Request req, Response res){
		return new ModelAndView(null, "organizacion.html");
	}
	
}
