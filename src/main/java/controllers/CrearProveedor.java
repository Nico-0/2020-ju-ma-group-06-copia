package controllers;

import dominio.entidad.TipoEmpresa;
import repositorios.RepositorioEntidades;
import repositorios.RepositorioProveedores;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class CrearProveedor {
	public ModelAndView show(Request req, Response res){
		return new ModelAndView(null, "crearProveedor.hbs");
	}
	
	public ModelAndView crear(Request req, Response res){
		String razonSocial = req.queryParams("razon_social");
		String dniCuilCuit = req.queryParams("dni_cuil_cuit");
		String pais= req.queryParams("pais");
		String provincia = req.queryParams("provincia");
		String ciudad = req.queryParams("ciudad");
		String direccion = req.queryParams("direccion");
		
		RepositorioProveedores.getInstance().crearProveedor(razonSocial, dniCuilCuit, pais, provincia, ciudad, direccion);
		res.redirect("/proveedores");
		return null;
	}
}