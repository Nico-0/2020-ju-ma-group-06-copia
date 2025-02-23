package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.entidad.Categoria;
import dominio.entidad.Entidad;
import dominio.entidad.EntidadBase;
import dominio.entidad.EntidadJuridica;
import repositorios.RepositorioCategorias;
import repositorios.RepositorioEntidades;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class EditarEntidadesBaseDeEntidad {
	static List<String> tiposEntidades = new ArrayList<>(Arrays.asList(
		    "entidades_base",
		    "empresas",
		    "organizaciones_sociales"
	));

	public ModelAndView show(Request req, Response res){
		this.verificarTipoEntidad(req.params("tipo_entidad"), res);
		Long id = new Long(req.params("id_entidad"));
		Entidad entidad = RepositorioEntidades.getInstance().getEntidad(id);
		return new ModelAndView(entidad, "editarEntidadesBaseDeEntidad.hbs");
	}

	public ModelAndView agregarEntidadBase(Request req, Response res){
		EntidadJuridica entidad = (EntidadJuridica) RepositorioEntidades
				.getInstance()
				.getEntidad(new Long(req.params("id_entidad")));
		EntidadBase entidadBase = (EntidadBase) RepositorioEntidades
				.getInstance()
				.getEntidad(new Long(req.params("id_entidad_base")));
		
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entidad.agregarEntidadBase(entidadBase);
		transaction.commit();
		
		res.redirect(entidad.getUrlEditarEntidadesBase());
		return null;
	}
	
	public ModelAndView quitarEntidadBase(Request req, Response res){
		EntidadJuridica entidad = (EntidadJuridica) RepositorioEntidades
				.getInstance()
				.getEntidad(new Long(req.params("id_entidad")));
		EntidadBase entidadBase = (EntidadBase) RepositorioEntidades
				.getInstance()
				.getEntidad(new Long(req.params("id_entidad_base")));
		
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entidad.quitarEntidadBase(entidadBase);
		transaction.commit();
		
		res.redirect(entidad.getUrlEditarEntidadesBase());
		return null;
	}
	
	public void verificarTipoEntidad(String tipoEntidad, Response res) {
		if(!tiposEntidades.contains(tipoEntidad))
			res.redirect("/unknown");
	}
}
