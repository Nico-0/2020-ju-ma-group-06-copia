package controllers;

import dominio.entidad.TipoEmpresa;
import dominio.presupuestos.CompraPendiente;
import repositorios.RepositorioComprasPendientes;
import repositorios.RepositorioEntidades;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class EditarCompraPendiente {
	public ModelAndView show(Request req, Response res){
		return new ModelAndView(null, "editarCompraPendiente.hbs");
	}
	
	public ModelAndView crear(Request req, Response res){
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().crearCompraPendiente();
		String id = String.valueOf(compraPendiente.getId());
		res.redirect("/compras_pendientes/" + id + "/editar");
		return null;
	}
	
	public ModelAndView editar(Request req, Response res){
		/*
		String nombre = req.queryParams("nombre");
		String razonSocial = req.queryParams("razon_social");
		String direccionPostal= req.queryParams("direccion_postal");
		String cuit = req.queryParams("cuit");
		TipoEmpresa tipoEmpresa = toTipoEmpresa(req.queryParams("tipo_empresa"));
		
		RepositorioEntidades.getInstance().crearEmpresa(razonSocial, nombre, cuit, direccionPostal, tipoEmpresa);
		*/
		res.redirect("/compras_pendientes");
		return null;
	}
}
