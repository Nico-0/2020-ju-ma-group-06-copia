package controllers;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.compra.Proveedor;
import dominio.presupuestos.CompraPendiente;
import repositorios.RepositorioComprasPendientes;
import repositorios.RepositorioProveedores;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class SeleccionarProveedorDeCompraPendiente {
	public ModelAndView show(Request req, Response res){
		Long idCompraPendiente = new Long(req.params("id_compra_pendiente"));
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(idCompraPendiente);
		return new ModelAndView(compraPendiente, "seleccionarProveedorDeCompraPendiente.hbs");
	}
	
	public ModelAndView seleccionar(Request req, Response res){
		Long idProveedor = new Long(req.params("id_proveedor"));
		Long idCompraPendiente = new Long(req.params("id_compra_pendiente"));
		Proveedor proveedor = RepositorioProveedores.getInstance().getProveedor(idProveedor);
		CompraPendiente compraPendiente = RepositorioComprasPendientes.getInstance().getCompraPendiente(idCompraPendiente);
		compraPendiente.setProveedor(proveedor);
		res.redirect(compraPendiente.getUrlView() + "/seleccionar_proveedor");
		return null;
	}
}
