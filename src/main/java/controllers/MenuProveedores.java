package controllers;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.compra.Proveedor;
import dominio.entidad.Entidad;
import repositorios.RepositorioEntidades;
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
		Proveedor proveedor = RepositorioProveedores.getInstance().getProveedor(id);		
		
		if(proveedor.tieneCompras()) {
			res.redirect("/proveedores#popupTieneCompras");
			return null;
		}
		
		if(proveedor.tieneComprasPendientes()) {
			res.redirect("/proveedores#popupTieneComprasPendientes");
			return null;
		}

		if(proveedor.tienePresupuestos()) {
			res.redirect("/proveedores#popupTienePresupuestos");
			return null;
		}		
	
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(proveedor);
		transaction.commit();
		
		res.redirect("/proveedores");
		return null;
	}
	
	public ModelAndView mostrarProveedor(Request req, Response res) {
		Long id = new Long(req.params("id"));
		Proveedor proveedor = RepositorioProveedores.getInstance().getProveedor(id);
		return new ModelAndView(proveedor, "mostrarProveedor.hbs");
	}
}
