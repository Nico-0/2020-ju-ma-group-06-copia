package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class Usuario {

	public ModelAndView login(Request req, Response res){
		return new ModelAndView(null, "menu_usuario.html");
	}
	
	public ModelAndView compras(Request req, Response res){
		return new ModelAndView(null, "compras_usuario.html");
	}
	
	public ModelAndView bandejaDeEntrada(Request req, Response res){
		return new ModelAndView(null, "bandeja_entrada.html");
	}
	
	public ModelAndView crear(Request req, Response res){
		return new ModelAndView(null, "crear_compra.html");
	}
}
