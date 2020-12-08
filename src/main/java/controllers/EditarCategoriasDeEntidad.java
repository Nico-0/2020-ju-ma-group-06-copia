package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.entidad.Categoria;
import dominio.entidad.Entidad;
import repositorios.RepositorioCategorias;
import repositorios.RepositorioEntidades;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class EditarCategoriasDeEntidad {
	static List<String> tiposEntidades = new ArrayList<>(Arrays.asList(
		    "entidades_base",
		    "empresas",
		    "organizaciones_sociales"
	));

	
	public ModelAndView show(Request req, Response res){
		this.verificarTipoEntidad(req.params("tipo_entidad"), res);
		Long id = new Long(req.params("id_entidad"));
		Entidad entidad = RepositorioEntidades.getInstance().getEntidad(id);
		return new ModelAndView(entidad, "editarCategoriasDeEntidad.hbs");
	}

	public ModelAndView agregarCategoria(Request req, Response res){
		Entidad entidad = RepositorioEntidades
				.getInstance()
				.getEntidad(new Long(req.params("id_entidad")));
		Categoria categoria = RepositorioCategorias
					.getInstance()
					.getCategoria(new Long(req.params("id_categoria")));
		
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entidad.agregarCategoria(categoria);
		transaction.commit();
		
		res.redirect(entidad.getUrlEditarCategorias());
		return null;
	}
	
	public ModelAndView quitarCategoria(Request req, Response res){
		Entidad entidad = RepositorioEntidades
							.getInstance()
							.getEntidad(new Long(req.params("id_entidad")));
		Categoria categoria = RepositorioCategorias
								.getInstance()
								.getCategoria(new Long(req.params("id_categoria")));
		
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entidad.quitarCategoria(categoria);
		transaction.commit();
		
		res.redirect(entidad.getUrlEditarCategorias());
		return null;
	}
	
	public void verificarTipoEntidad(String tipoEntidad, Response res) {
		if(!tiposEntidades.contains(tipoEntidad))
			res.redirect("/unknown");
	}
}
