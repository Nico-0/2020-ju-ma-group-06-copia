package controllers;

import dominio.compra.Compra;
import repositorios.RepositorioCompras;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class MenuCompras {

	public ModelAndView show(Request req, Response res){
		return new ModelAndView(RepositorioCompras.getInstance(), "menuCompras.hbs");
	}
	
	public ModelAndView mostrarCompra(Request req, Response res){
		Long idCompra = new Long(req.params("id_compra"));
		Compra compra = RepositorioCompras.getInstance().getCompra(idCompra);
		return new ModelAndView(compra, "menuCompra.hbs");
	}
}
