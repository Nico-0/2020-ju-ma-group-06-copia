package controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.entidad.Categoria;
import dominio.entidad.Entidad;
import repositorios.RepositorioCategorias;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class MenuCategorias {
	
	public ModelAndView show(Request req, Response res){
		return new ModelAndView(RepositorioCategorias.getInstance(), "menuCategorias.hbs");
	}
	
	public ModelAndView mostrarCategoriaDefault(Request req, Response res){
		Categoria categoria = RepositorioCategorias.getInstance().getCategoria(new Long(req.params("id")));
		return new ModelAndView(categoria, "mostrarCategoriaDefault.hbs");
	}

	public ModelAndView borrarCategoria(Request req, Response res){
		Long id = new Long(req.params("id"));
		List<Entidad> entidades = RepositorioCategorias.getInstance().getEntidades(id);
		if(entidades.isEmpty()) {
			Categoria categoria = RepositorioCategorias.getInstance().getCategoria(id);
			final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
			EntityTransaction transaction = entityManager.getTransaction();
			transaction.begin();
			entityManager.remove(categoria);
			transaction.commit();
			res.redirect("/categorias");
			return null;
		}
		res.redirect("/categorias#popupNoSePuedeBorrarCategoria");
		return null;
	}
}