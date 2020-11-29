package controllers;

import repositorios.RepositorioCategorias;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;

public class MenuCategorias {
	
	public ModelAndView show(Request req, Response res){
		return new ModelAndView(new RepositorioCategorias(), "menuCategorias.hbs");
	}
	
	/*
	public ModelAndView mostrarCategoria(Request req, Response res){
		return new ModelAndView(new RepositorioCategorias(), "mostrarCategoria.hbs");
	}*/
	
	public ModelAndView borrarCategoria(Request req, Response res){
		Long id = new Long(req.params("id"));
		RepositorioCategorias.borrarCategoria(id);
		res.redirect("/categorias");
		return null;
	}
}
