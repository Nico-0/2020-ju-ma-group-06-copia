package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;

public class EntidadJuridica {

	public ModelAndView compras(Request req, Response res){
		return new ModelAndView(null, "compras.html");
	}

	public ModelAndView categorias(Request req, Response res){
		return new ModelAndView(null, "categorias.html");
	}

	public ModelAndView reportesMensuales(Request req, Response res){
		return new ModelAndView(null, "reportes_mensuales.html");
	}

	public ModelAndView entidadesBase(Request req, Response res){
		return new ModelAndView(null, "entidades_base.html");
	}
}