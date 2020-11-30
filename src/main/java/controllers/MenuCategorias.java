package controllers;

import dominio.entidad.Categoria;
import repositorios.RepositorioCategorias;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;

public class MenuCategorias {
	
	public ModelAndView show(Request req, Response res){
		return new ModelAndView(new RepositorioCategorias(), "menuCategorias.hbs");
	}
	
	public ModelAndView mostrarCategoriaDefault(Request req, Response res){
		Categoria categoria = RepositorioCategorias.getCategoria(new Long(req.params("id")));
		return new ModelAndView(categoria, "mostrarCategoriaDefault.hbs");
	}

	public ModelAndView borrarCategoria(Request req, Response res){
		Long id = new Long(req.params("id"));
		RepositorioCategorias.borrarCategoria(id);
		res.redirect("/categorias");
		return null;
	}
}