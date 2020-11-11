package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class Usuario {

	public ModelAndView login(Request req, Response res){
		return new ModelAndView(null, "menu_usuario.html");
	}
	
	public ModelAndView entidades(Request req, Response res){
		return new ModelAndView(null, "entidades_usuario.html");
	}
	
	
	public ModelAndView compras(Request req, Response res){
		return new ModelAndView(null, "compras.html");
	}
	
	public ModelAndView bandejaDeMensajes(Request req, Response res){
		return new ModelAndView(null, "bandeja_de_mensajes.html");
	}
	
	
}
