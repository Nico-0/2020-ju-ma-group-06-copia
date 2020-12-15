package controllers;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.entidad.Categoria;
import repositorios.RepositorioCategorias;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class EditarCategoriaDefault {
	
	public ModelAndView show(Request req, Response res) {
		Categoria categoria = RepositorioCategorias.getInstance().getCategoria(new Long(req.params("id")));
		return new ModelAndView(categoria, "editarCategoriaDefault.hbs");
	}
	
	public ModelAndView editar(Request req, Response res) {
		Long egresosMaximos;
		Long id = new Long(req.params("id"));
		String nombre = req.queryParams("nombre");
		boolean bloquearNuevasCompras = toBoolean(req.queryParams("bloquearNuevasCompras"));
		boolean bloquearAgregarEntidadesBase = toBoolean(req.queryParams("bloquearAgregarEntidadesBase"));
		boolean bloquearFormarParteEntidadJuridica = toBoolean(req.queryParams("bloquearFormarParteEntidadJuridica"));
		if(req.queryParams("egresosMaximos").equals(""))
			egresosMaximos = (long) 0;
		else
			egresosMaximos = Long.parseLong(req.queryParams("egresosMaximos"));
		
		Categoria categoriaDefault = RepositorioCategorias.getInstance().getCategoria(id);
		final EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		final EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		categoriaDefault.setBloquarAgregarEntidadesBase(bloquearAgregarEntidadesBase);
		categoriaDefault.setBloquearFormarParteEntidadJuridica(bloquearFormarParteEntidadJuridica);
		categoriaDefault.setBloquearNuevosEgresos(bloquearNuevasCompras);
		categoriaDefault.setEgresosMaximos(egresosMaximos);
		categoriaDefault.setNombre(nombre);
		transaction.commit();
		
		res.redirect("/categorias/categorias_default/" + id.toString());
		return null;
	}
	
	public boolean toBoolean(String string) {
		if(string == null)	return false;
		if(string.equals("True"))	return true;
		throw new RuntimeException("El valor enviado no es válido");
	}
}
