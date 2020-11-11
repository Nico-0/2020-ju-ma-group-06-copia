package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class Usuario {

	public ModelAndView login(Request req, Response res){
		return new ModelAndView(null, "usuario/usuarioLogin.hbs");
	}
	
}
