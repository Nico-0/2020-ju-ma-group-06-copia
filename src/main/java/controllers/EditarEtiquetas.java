package controllers;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.compra.Compra;
import dominio.entidad.Entidad;
import dominio.presupuestos.CompraPendiente;
import repositorios.RepositorioCompras;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class EditarEtiquetas {

	public ModelAndView show(Request req, Response res){
		Long idCompra = new Long(req.params("id_compra"));
		Compra compra = RepositorioCompras.getInstance().getCompra(idCompra);
		return new ModelAndView(compra, "agregarEtiqueta.hbs");
	}
	
	public ModelAndView agregarEtiqueta(Request req, Response res){
		Long idCompra = new Long(req.params("id_compra"));
		String etiqueta = req.queryParams("etiqueta");
		Compra compra = RepositorioCompras.getInstance().getCompra(idCompra);
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();		
		transaction.begin();
		compra.agregarEtiqueta(etiqueta);
		transaction.commit();
		res.redirect("/compras/"+compra.getId());
		return null;
	}
	
	public ModelAndView quitarEtiqueta(Request req, Response res){
		Long idCompra = new Long(req.params("id_compra"));
		String etiqueta = req.params("etiqueta");
		Compra compra = RepositorioCompras.getInstance().getCompra(idCompra);
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();		
		transaction.begin();
		compra.quitarEtiqueta(etiqueta);
		transaction.commit();
		res.redirect("/compras/"+compra.getId());
		return null;
	}
	
	
}
