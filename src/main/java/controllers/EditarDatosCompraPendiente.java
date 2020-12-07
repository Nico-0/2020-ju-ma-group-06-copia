package controllers;

import dominio.entidad.Entidad;
import dominio.presupuestos.CompraPendiente;
import repositorios.RepositorioComprasPendientes;
import repositorios.RepositorioEntidades;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class EditarDatosCompraPendiente {
	public ModelAndView show(Request req, Response res){
		Long idCompraPendiente = new Long(req.params("id_compra_pendiente"));
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(idCompraPendiente);
		return new ModelAndView(compraPendiente, "editarDatosCompraPendiente.hbs");
	}
	
	public ModelAndView editar(Request req, Response res){
		Long idCompraPendiente = new Long(req.params("id_compra_pendiente"));
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(idCompraPendiente);
		res.redirect(compraPendiente.getUrlView());
		return null;
	}
}