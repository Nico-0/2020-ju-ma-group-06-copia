package controllers;

import dominio.compra.Proveedor;
import dominio.entidad.Entidad;
import dominio.presupuestos.CompraPendiente;
import repositorios.RepositorioComprasPendientes;
import repositorios.RepositorioEntidades;
import repositorios.RepositorioProveedores;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class SeleccionarEntidadDeCompraPendiente {
	public ModelAndView show(Request req, Response res){
		Long idCompraPendiente = new Long(req.params("id_compra_pendiente"));
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(idCompraPendiente);
		return new ModelAndView(compraPendiente, "seleccionarEntidadDeCompraPendiente.hbs");
	}
	
	public ModelAndView seleccionar(Request req, Response res){
		Long idEntidad = new Long(req.params("id_entidad"));
		Long idCompraPendiente = new Long(req.params("id_compra_pendiente"));
		Entidad entidad = RepositorioEntidades.getInstance().getEntidad(idEntidad);
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(idCompraPendiente);
		compraPendiente.setEntidad(entidad);
		res.redirect(compraPendiente.getUrlView() + "/seleccionar_entidad");
		return null;
	}
}
