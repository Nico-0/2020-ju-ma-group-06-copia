package controllers;

import dominio.compra.Proveedor;
import repositorios.RepositorioProveedores;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class MenuProveedores {
	
	public ModelAndView show(Request req, Response res) {
		return new ModelAndView(RepositorioProveedores.getInstance(), "proveedores.hbs");
	}
	
	public ModelAndView borrarProveedor(Request req, Response res) {
		Long id = new Long(req.params("id"));
		RepositorioProveedores.getInstance().borrarProveedor(id);
		res.redirect("/proveedores");
		return null;
	}
	
	public ModelAndView mostrarProveedor(Request req, Response res) {
		Long id = new Long(req.params("id"));
		Proveedor proveedor = RepositorioProveedores.getInstance().getProveedor(id);
		return new ModelAndView(proveedor, "mostrarProveedor.hbs");
	}
}
