package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;

public class EntidadJuridica {

	public ModelAndView compras(Request req, Response res){
		return new ModelAndView(null, "compras.hbs");
	}

	public ModelAndView categorias(Request req, Response res){
		return new ModelAndView(null, "categorias.hbs");
	}

	public ModelAndView reportesMensuales(Request req, Response res){
		return new ModelAndView(null, "reportes_mensuales.hbs");
	}

	public ModelAndView entidadesBase(Request req, Response res){
		return new ModelAndView(null, "entidades_base.hbs");
	}
}