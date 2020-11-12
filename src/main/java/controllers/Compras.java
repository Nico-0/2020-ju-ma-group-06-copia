package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;

public class Compras {

	public ModelAndView editarCompra(Request req, Response res){
		return new ModelAndView(null, "editar_compra.html");
	}
}